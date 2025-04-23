package agents.awagent;

import OSPABA.*;
import simulation.*;
import agents.awagent.continualassistants.*;

//meta! id="12"
public class AWAgent extends OSPABA.Agent
{
	public AWAgent(int id, Simulation mySim, Agent parent)
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
		new AWManager(Id.aWManager, mySim(), this);
		new ArmouringA(Id.armouringA, mySim(), this);
		new Cutting(Id.cutting, mySim(), this);
		new Preparing(Id.preparing, mySim(), this);
		addOwnMessage(Mc.armourA);
		addOwnMessage(Mc.prepareAndCut);
	}
	//meta! tag="end"
}
