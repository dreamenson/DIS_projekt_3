package agents.cwagent.continualassistants;

import OSPABA.*;
import agents.workeragent.WorkerAgent;
import entities.order.Product;
import entities.worker.Activity;
import entities.worker.Worker;
import random.IRandomGenerator;
import simulation.*;
import agents.cwagent.*;
import OSPABA.Process;

//meta! id="122"
public class ArmouringC extends OSPABA.Process
{
	private static IRandomGenerator rackArmouringRandom;
	private static IRandomGenerator transferPlaceRandom;
	private static IRandomGenerator transferStorageRandom;

	public ArmouringC(int id, Simulation mySim, CommonAgent myAgent)
	{
		super(id, mySim, myAgent);
		rackArmouringRandom = WorkerAgent.getRackArmouringRandom();
		transferPlaceRandom = WorkerAgent.getTransferPlaceRandom();
		transferStorageRandom = WorkerAgent.getTransferStorageRandom();
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
	}

	//meta! sender="CWAgent", id="123", type="Start"
	public void processStart(MessageForm message)
	{
		MyMessage msg = (MyMessage) message;

		Worker worker = msg.getWorker();
		Product product = msg.getProduct();

		worker.setBusy(product, Activity.ARMOURING);
		msg.setCode(Mc.armourCEnd);

		double transferTime = 0;
		if (worker.getPlace() == null) {
			transferTime += transferStorageRandom.nextValue().doubleValue();
		} else if (worker.getPlace() != product.getPlace()) {
			transferTime += transferPlaceRandom.nextValue().doubleValue();
		}
		worker.setPlace(product.getPlace());

		hold(transferTime + rackArmouringRandom.nextValue().doubleValue(), msg);
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
			case Mc.armourCEnd:
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

}
