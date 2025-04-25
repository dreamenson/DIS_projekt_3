package agents.surroundagent;

import OSPABA.*;
import agents.surroundagent.continualassistants.*;
import entities.order.Order;
import simulation.*;

//meta! id="3"
public class SurroundAgent extends OSPABA.Agent
{
	public SurroundAgent(int id, Simulation mySim, Agent parent)
	{
		super(id, mySim, parent);
		init();
		myInit();
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication

		Order.resetID();
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init()
	{
		new SurroundManager(Id.surroundManager, mySim(), this);
		new ArrivalScheduler(Id.arrivalScheduler, mySim(), this);
		addOwnMessage(Mc.init);
	}
	//meta! tag="end"

	private void myInit() {
		addOwnMessage(Mc.newOrder);
	}
}
