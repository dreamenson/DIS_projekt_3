package agents.awagent.continualassistants;

import OSPABA.*;
import agents.awagent.*;
import agents.workeragent.WorkerAgent;
import entities.worker.Activity;
import entities.worker.Worker;
import random.IRandomGenerator;
import random.RandomCreator;
import simulation.*;
import OSPABA.Process;

//meta! id="107"
public class Preparing extends OSPABA.Process
{
	private static final IRandomGenerator orderPreparationRandom =
			RandomCreator.newTriangularRandom(300, 900, 500);
	private static IRandomGenerator transferStorageRandom;


	public Preparing(int id, Simulation mySim, CommonAgent myAgent)
	{
		super(id, mySim, myAgent);
		transferStorageRandom = WorkerAgent.getTransferStorageRandom();
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
	}

	//meta! sender="AWAgent", id="108", type="Start"
	public void processStart(MessageForm message)
	{
		MyMessage msg = (MyMessage) message;

		Worker worker = msg.getWorker();
		worker.setBusy(msg.getProduct(), Activity.PREPARING);
		msg.setCode(Mc.prepareEnd);

		double time = orderPreparationRandom.nextValue().doubleValue();
		double transferTime = worker.getPlace() != null ? transferStorageRandom.nextValue().doubleValue() : 0;
		worker.setPlace(null);

		hold(time + transferTime, msg);
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
			case Mc.prepareEnd:
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
	public AWAgent myAgent()
	{
		return (AWAgent)super.myAgent();
	}

}
