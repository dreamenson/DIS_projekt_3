package agents.carpentryagent;

import OSPABA.*;
import simulation.*;

//meta! id="37"
public class CarpentryManager extends OSPABA.Manager
{
	public CarpentryManager(int id, Simulation mySim, Agent myAgent)
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

	//meta! sender="PlaceAgent", id="31", type="Response"
	public void processAssignPlace(MessageForm message)
	{
	}

	//meta! sender="AgentBoss", id="69", type="Request"
	public void processMakeOrder(MessageForm message)
	{
	}

	//meta! sender="Scheduler1", id="54", type="Finish"
	public void processFinish(MessageForm message)
	{
	}

	//meta! sender="WorkerAgent", id="70", type="Response"
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
		case Mc.finish:
			processFinish(message);
		break;

		case Mc.assignPlace:
			processAssignPlace(message);
		break;

		case Mc.getWorker:
			processGetWorker(message);
		break;

		case Mc.makeOrder:
			processMakeOrder(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public CarpentryAgent myAgent()
	{
		return (CarpentryAgent)super.myAgent();
	}

}
