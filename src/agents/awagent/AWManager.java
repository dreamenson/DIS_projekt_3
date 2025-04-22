package agents.awagent;

import OSPABA.*;
import simulation.*;

//meta! id="12"
public class AWManager extends OSPABA.Manager
{
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
	}

	//meta! sender="WorkerAgent", id="81", type="Notice"
	public void processReleaseWorkerA(MessageForm message)
	{
	}

	//meta! sender="WorkerAgent", id="71", type="Request"
	public void processGetWorkerA(MessageForm message)
	{
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
		}
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
		case Mc.releaseWorkerA:
			processReleaseWorkerA(message);
		break;

		case Mc.getWorkerA:
			processGetWorkerA(message);
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
