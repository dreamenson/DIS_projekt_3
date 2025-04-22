package agents.cwagent;

import OSPABA.*;
import simulation.*;

//meta! id="14"
public class CWManager extends OSPABA.Manager
{
	public CWManager(int id, Simulation mySim, Agent myAgent)
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

	//meta! sender="WorkerAgent", id="83", type="Notice"
	public void processReleaseWorkerC(MessageForm message)
	{
	}

	//meta! sender="WorkerAgent", id="73", type="Request"
	public void processGetWorkerC(MessageForm message)
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
		case Mc.getWorkerC:
			processGetWorkerC(message);
		break;

		case Mc.releaseWorkerC:
			processReleaseWorkerC(message);
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
