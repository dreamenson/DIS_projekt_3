package agents.cwagent;

import OSPABA.*;
import simulation.*;

//meta! id="14"
public class CWAgent extends OSPABA.Agent
{
	public CWAgent(int id, Simulation mySim, Agent parent)
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
		new CWManager(Id.cWManager, mySim(), this);
		addOwnMessage(Mc.releaseWorkerC);
		addOwnMessage(Mc.getWorkerC);
	}
	//meta! tag="end"
}
