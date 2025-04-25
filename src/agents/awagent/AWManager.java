package agents.awagent;

import OSPABA.*;
import entities.worker.Activity;
import entities.worker.Worker;
import simulation.*;

//meta! id="12"
public class AWManager extends OSPABA.Manager
{
	private AgentComponent preparingAgent, cuttingAgent, armouringAgent;

	public AWManager(int id, Simulation mySim, Agent myAgent)
	{
		super(id, mySim, myAgent);
		init();
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication

		if (petriNet() != null)
		{
			petriNet().clear();
		}

		preparingAgent = myAgent().findAssistant(Id.preparing);
		cuttingAgent = myAgent().findAssistant(Id.cutting);
		armouringAgent = myAgent().findAssistant(Id.armouringA);
	}

	//meta! sender="WorkerAgent", id="71", type="Request"
	public void processPrepareAndCut(MessageForm message)
	{
		MyMessage msg = (MyMessage) message;

		if (myAgent().isAvailWorker()) {
			msg.setWorker(myAgent().getAvailWorker());
			msg.setAddressee(preparingAgent);
			startContinualAssistant(message);
		} else {
			msg.setNextActivity(Activity.PREPARING);
			myAgent().addMessage(msg);
		}
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
		}
	}

	//meta! sender="WorkerAgent", id="124", type="Request"
	public void processArmourA(MessageForm message)
	{
		MyMessage msg = (MyMessage) message;

		if (myAgent().isAvailWorker()) {
			msg.setWorker(myAgent().getAvailWorker());
			msg.setAddressee(armouringAgent);
			startContinualAssistant(message);
		} else {
			msg.setNextActivity(Activity.ARMOURING);
			myAgent().addMessage(msg);
		}
	}

	//meta! sender="Preparing", id="108", type="Finish"
	public void processFinishPreparing(MessageForm message)
	{
		message.setAddressee(cuttingAgent);
		startContinualAssistant(message);
	}

	//meta! sender="Cutting", id="106", type="Finish"
	public void processFinishCutting(MessageForm message)
	{
		workerFinished(message, Mc.prepareAndCut);
	}

	private void workerFinished(MessageForm message, int code) {
		MyMessage msg = (MyMessage) message;
		Worker worker = msg.getWorker();
		msg.setWorker(null);
		msg.setPrevTime(mySim().currentTime());
		msg.setCode(code);
		response(msg);

		if (!myAgent().isMessageEmpty()) {
			MyMessage msg1 = myAgent().getMessage();
			msg1.setWorker(worker);
			if (msg1.getNextActivity() == Activity.PREPARING) {
				msg1.setAddressee(cuttingAgent);
			}
			else if (msg1.getNextActivity() == Activity.ARMOURING) {
				msg1.setAddressee(armouringAgent);
			}
			startContinualAssistant(msg1);
		} else {
			worker.setFree();
			myAgent().addAvailWorker(worker);
		}
	}

	//meta! sender="ArmouringA", id="110", type="Finish"
	public void processFinishArmouringA(MessageForm message)
	{
		workerFinished(message, Mc.armourA);
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	public void init()
	{
	}

	@Override
	public void processMessage(MessageForm message)
	{
		switch (message.code())
		{
		case Mc.finish:
			switch (message.sender().id())
			{
			case Id.cutting:
				processFinishCutting(message);
			break;

			case Id.preparing:
				processFinishPreparing(message);
			break;

			case Id.armouringA:
				processFinishArmouringA(message);
			break;
			}
		break;

		case Mc.prepareAndCut:
			processPrepareAndCut(message);
		break;

		case Mc.armourA:
			processArmourA(message);
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
