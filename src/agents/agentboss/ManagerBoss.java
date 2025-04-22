package agents.agentboss;

import OSPABA.*;
import simulation.*;

//meta! id="1"
public class ManagerBoss extends OSPABA.Manager
{
	public ManagerBoss(int id, Simulation mySim, Agent myAgent)
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

	//meta! sender="SurroundAgent", id="26", type="Notice"
	public void processOrderArrive(MessageForm message)
	{
	}

	//meta! sender="CarpentryAgent", id="69", type="Response"
	public void processMakeOrder(MessageForm message)
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
		case Mc.orderArrive:
			processOrderArrive(message);
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
	public AgentBoss myAgent()
	{
		return (AgentBoss)super.myAgent();
	}

}
