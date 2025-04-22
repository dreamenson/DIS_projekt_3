package agents.surroundagent;

import OSPABA.*;
import agents.surroundagent.continualassistants.*;
import simulation.*;

//meta! id="3"
public class SurroundAgent extends OSPABA.Agent
{
	public SurroundAgent(int id, Simulation mySim, Agent parent)
	{
		super(id, mySim, parent);
		init();
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init()
	{
		new SurroundManager(Id.surroundManager, mySim(), this);
		new ArrivalScheduler(Id.arrivalScheduler, mySim(), this);
		addOwnMessage(Mc.init);
	}
	//meta! tag="end"
}
