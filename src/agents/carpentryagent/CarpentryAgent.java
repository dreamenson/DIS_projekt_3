package agents.carpentryagent;

import OSPABA.*;
import agents.carpentryagent.continualassistants.*;
import simulation.*;

//meta! id="37"
public class CarpentryAgent extends OSPABA.Agent
{
	public CarpentryAgent(int id, Simulation mySim, Agent parent)
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
		new CarpentryManager(Id.carpentryManager, mySim(), this);
		new Scheduler1(Id.scheduler1, mySim(), this);
		addOwnMessage(Mc.assignPlace);
		addOwnMessage(Mc.makeOrder);
		addOwnMessage(Mc.getWorker);
	}
	//meta! tag="end"
}
