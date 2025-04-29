package agents.awagent;

import OSPABA.*;
import agents.GeneralWorkerAgent;
import entities.worker.WorkerType;
import simulation.*;
import agents.awagent.continualassistants.*;

//meta! id="12"
public class AWAgent extends GeneralWorkerAgent
{
	public AWAgent(int id, Simulation mySim, Agent parent)
	{
		super(id, mySim, parent, ((MySimulation)mySim).getWorkerACnt(), WorkerType.A);
		init();
		myInit();
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

	private void myInit() {
		addOwnMessage(Mc.prepareEnd);
		addOwnMessage(Mc.cutEnd);
		addOwnMessage(Mc.armourAEnd);
	}
}
