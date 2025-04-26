package agents.cwagent.continualassistants;

import OSPABA.*;
import agents.workeragent.WorkerAgent;
import entities.order.Product;
import entities.order.ProductType;
import entities.worker.Activity;
import entities.worker.Worker;
import random.IRandomGenerator;
import random.RandomCreator;
import simulation.*;
import agents.cwagent.*;
import OSPABA.Process;

//meta! id="113"
public class Mordanting extends OSPABA.Process
{
	private static final IRandomGenerator tableMordantingRandom =
			RandomCreator.newContinuousRandom(100*60, 480*60);
	private static final IRandomGenerator chairMordantingRandom =
			RandomCreator.newContinuousRandom(90*60, 400*60);
	private static final IRandomGenerator rackMordantingRandom =
			RandomCreator.newContinuousRandom(300*60, 600*60);
	private static IRandomGenerator transferPlaceRandom;
	private static IRandomGenerator transferStorageRandom;

	public Mordanting(int id, Simulation mySim, CommonAgent myAgent)
	{
		super(id, mySim, myAgent);
		transferPlaceRandom = WorkerAgent.getTransferPlaceRandom();
		transferStorageRandom = WorkerAgent.getTransferStorageRandom();
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
	}

	//meta! sender="CWAgent", id="114", type="Start"
	public void processStart(MessageForm message)
	{
		MyMessage msg = (MyMessage) message;

		Worker worker = msg.getWorker();
		Product product = msg.getProduct();

		worker.setBusy(product, Activity.MORDANTING);
		message.setCode(Mc.mordantEnd);

		double transferTime = 0;
		if (worker.getPlace() == null) {
			transferTime += transferStorageRandom.nextValue().doubleValue();
		} else if (worker.getPlace() != product.getPlace()) {
			transferTime += transferPlaceRandom.nextValue().doubleValue();
		}
		worker.setPlace(product.getPlace());

		hold(transferTime + getHoldTime(product), message);
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
			case Mc.mordantEnd:
				assistantFinished(message);
				break;
		}
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	@Override
	public void processMessage(MessageForm message)
	{
		switch (message.code())
		{
		case Mc.start:
			processStart(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public CWAgent myAgent()
	{
		return (CWAgent)super.myAgent();
	}

	private double getHoldTime(Product product) {
		return switch (product.getType()) {
			case ProductType.TABLE -> tableMordantingRandom.nextValue().doubleValue();
			case ProductType.CHAIR -> chairMordantingRandom.nextValue().doubleValue();
			case ProductType.RACK -> rackMordantingRandom.nextValue().doubleValue();
		};
	}
}
