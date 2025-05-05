package agents.bwagent.continualassistants;

import OSPABA.*;
import agents.bwagent.*;
import agents.workeragent.WorkerAgent;
import entities.order.Product;
import entities.order.ProductType;
import entities.worker.Activity;
import entities.worker.Worker;
import random.IRandomGenerator;
import random.RandomCreator;
import simulation.*;
import OSPABA.Process;

//meta! id="111"
public class Assembling extends OSPABA.Process
{
	private static final IRandomGenerator tableAssemblingRandom =
			RandomCreator.newContinuousRandom(30*60, 60*60);
	private static final IRandomGenerator chairAssemblingRandom =
			RandomCreator.newContinuousRandom(14*60, 24*60);
	private static final IRandomGenerator rackAssemblingRandom =
			RandomCreator.newContinuousRandom(35*60, 75*60);
	private static IRandomGenerator transferPlaceRandom;
	private static IRandomGenerator transferStorageRandom;

	public Assembling(int id, Simulation mySim, CommonAgent myAgent)
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

	//meta! sender="BWAgent", id="112", type="Start"
	public void processStart(MessageForm message)
	{
		MyMessage msg = (MyMessage) message;

		Worker worker = msg.getWorker();
		Product product = msg.getProduct();

		worker.setBusy(product, Activity.ASSEMBLING);
		msg.setCode(Mc.assemblyEnd);

		double transferTime = 0;
		if (worker.getPlace() == null) {
			transferTime += transferStorageRandom.nextValue().doubleValue();
		} else if (worker.getPlace() != product.getPlace()) {
			transferTime += transferPlaceRandom.nextValue().doubleValue();
		}
		double activityTime = getHoldTime(product);
		worker.setPlace(product.getPlace(), transferTime, activityTime);

		hold(transferTime + activityTime, msg);
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
			case Mc.assemblyEnd:
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
	public BWAgent myAgent()
	{
		return (BWAgent)super.myAgent();
	}

	private double getHoldTime(Product product) {
		return switch (product.getType()) {
			case ProductType.TABLE -> tableAssemblingRandom.nextValue().doubleValue();
			case ProductType.CHAIR -> chairAssemblingRandom.nextValue().doubleValue();
			case ProductType.RACK -> rackAssemblingRandom.nextValue().doubleValue();
		};
	}
}
