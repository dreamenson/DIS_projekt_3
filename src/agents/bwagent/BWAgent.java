package agents.bwagent;

import OSPABA.*;
import simulation.*;

//meta! id="13"
public class BWAgent extends OSPABA.Agent
{
	public BWAgent(int id, Simulation mySim, Agent parent)
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
		new BWManager(Id.bWManager, mySim(), this);
		addOwnMessage(Mc.releaseWorkerB);
		addOwnMessage(Mc.getWorkerB);
	}
	//meta! tag="end"
}
