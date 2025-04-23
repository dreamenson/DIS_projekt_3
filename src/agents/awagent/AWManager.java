package agents.awagent;

import OSPABA.*;
import simulation.*;

//meta! id="12"
public class AWManager extends OSPABA.Manager
{
	private AgentComponent preparingAgent, cuttingAgent;

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
			// TODO prior front
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
	}

	//meta! sender="Preparing", id="108", type="Finish"
	public void processFinishPreparing(MessageForm message)
	{
	}

	//meta! sender="Cutting", id="106", type="Finish"
	public void processFinishCutting(MessageForm message)
	{
	}

	//meta! sender="ArmouringA", id="110", type="Finish"
	public void processFinishArmouringA(MessageForm message)
	{
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
