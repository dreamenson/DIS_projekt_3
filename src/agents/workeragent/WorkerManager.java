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

	//meta! sender="AWAgent", id="71", type="Response"
	public void processPrepareAndCut(MessageForm message)
	{
	}

	//meta! sender="CWAgent", id="73", type="Response"
	public void processMordantAndVarnish(MessageForm message)
	{
	}

	//meta! sender="BWAgent", id="72", type="Response"
	public void processAssembly(MessageForm message)
	{
	}

	//meta! sender="CarpentryAgent", id="70", type="Request"
	public void processMakeProduct(MessageForm message)
	{
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
		}
	}

	//meta! sender="AWAgent", id="124", type="Response"
	public void processArmourA(MessageForm message)
	{
	}

	//meta! sender="CWAgent", id="125", type="Response"
	public void processArmourC(MessageForm message)
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
		case Mc.makeProduct:
			processMakeProduct(message);
		break;

		case Mc.assembly:
			processAssembly(message);
		break;

		case Mc.mordantAndVarnish:
			processMordantAndVarnish(message);
		break;

		case Mc.armourC:
			processArmourC(message);
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
	public WorkerAgent myAgent()
	{
		return (WorkerAgent)super.myAgent();
	}

}
