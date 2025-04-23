package agents.placeagent;

import OSPABA.*;
import simulation.*;



//meta! id="5"
public class PlaceAgent extends OSPABA.Agent
{
	public PlaceAgent(int id, Simulation mySim, Agent parent)
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
		new PlaceManager(Id.placeManager, mySim(), this);
		addOwnMessage(Mc.releasePlace);
		addOwnMessage(Mc.assignPlace);
	}
	//meta! tag="end"
}