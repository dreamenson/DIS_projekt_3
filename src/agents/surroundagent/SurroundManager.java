package agents.surroundagent;

import OSPABA.*;
import simulation.*;

//meta! id="3"
public class SurroundManager extends OSPABA.Manager
{
	private final Agent agentBoss;

	public SurroundManager(int id, Simulation mySim, Agent myAgent)
	{
		super(id, mySim, myAgent);
		init();
		agentBoss = mySim.boss();
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

	//meta! sender="AgentBoss", id="24", type="Notice"
	public void processInit(MessageForm message)
	{
		message.setAddressee(myAgent().findAssistant(Id.arrivalScheduler));
		startContinualAssistant(message);
	}

	//meta! sender="ArrivalScheduler", id="29", type="Finish"
	public void processFinish(MessageForm message)
	{
		message.setAddressee(agentBoss);
		message.setCode(Mc.orderArrive);
		notice(message);
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		System.out.println("zle");
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

		case Mc.init:
			processInit(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public SurroundAgent myAgent()
	{
		return (SurroundAgent)super.myAgent();
	}

}
