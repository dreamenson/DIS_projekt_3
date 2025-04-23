package agents.carpentryagent;

import OSPABA.*;
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
		addOwnMessage(Mc.assignPlace);
		addOwnMessage(Mc.makeOrder);
		addOwnMessage(Mc.makeProduct);
	}
	//meta! tag="end"
}
