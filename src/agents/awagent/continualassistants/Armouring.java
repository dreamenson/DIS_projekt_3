package agents.awagent.continualassistants;

import OSPABA.*;
import agents.awagent.*;
import simulation.*;
import OSPABA.Process;

//meta! id="109"
public class Armouring extends OSPABA.Process
{
	public Armouring(int id, Simulation mySim, CommonAgent myAgent)
	{
		super(id, mySim, myAgent);
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
	}

	//meta! sender="AWAgent", id="110", type="Start"
	public void processStart(MessageForm message)
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
	@Override
	public void processMessage(MessageForm message)
	{
		switch (message.code())
		{
		case Mc.start:
			processStart(message);
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
