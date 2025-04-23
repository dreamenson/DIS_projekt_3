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

	//meta! sender="WorkerAgent", id="73", type="Request"
	public void processMordantAndVarnish(MessageForm message)
	{
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
		}
	}

	//meta! sender="Varnishing", id="121", type="Finish"
	public void processFinishVarnishing(MessageForm message)
	{
	}

	//meta! sender="Mordanting", id="114", type="Finish"
	public void processFinishMordanting(MessageForm message)
	{
	}

	//meta! sender="Armouring", id="123", type="Finish"
	public void processFinishArmouring(MessageForm message)
	{
	}

	//meta! sender="WorkerAgent", id="125", type="Request"
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
		case Mc.finish:
			switch (message.sender().id())
			{
			case Id.varnishing:
				processFinishVarnishing(message);
			break;

			case Id.mordanting:
				processFinishMordanting(message);
			break;

			case Id.armouring:
				processFinishArmouring(message);
			break;
			}
		break;

		case Mc.armourC:
			processArmourC(message);
		break;

		case Mc.mordantAndVarnish:
			processMordantAndVarnish(message);
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
