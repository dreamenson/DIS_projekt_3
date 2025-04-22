package agents.workeragent;

import OSPABA.*;
import simulation.*;

//meta! id="6"
public class WorkerManager extends OSPABA.Manager
{
	public WorkerManager(int id, Simulation mySim, Agent myAgent)
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

	//meta! sender="CarpentryAgent", id="78", type="Notice"
	public void processReleaseWorker(MessageForm message)
	{
	}

	//meta! sender="AWAgent", id="71", type="Response"
	public void processGetWorkerA(MessageForm message)
	{
	}

	//meta! sender="CWAgent", id="73", type="Response"
	public void processGetWorkerC(MessageForm message)
	{
	}

	//meta! sender="BWAgent", id="72", type="Response"
	public void processGetWorkerB(MessageForm message)
	{
	}

	//meta! sender="CarpentryAgent", id="70", type="Request"
	public void processGetWorker(MessageForm message)
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

		case Mc.getWorkerB:
			processGetWorkerB(message);
		break;

		case Mc.releaseWorker:
			processReleaseWorker(message);
		break;

		case Mc.getWorker:
			processGetWorker(message);
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
	public WorkerAgent myAgent()
	{
		return (WorkerAgent)super.myAgent();
	}

}
