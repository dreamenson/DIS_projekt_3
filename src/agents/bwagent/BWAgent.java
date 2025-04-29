package agents.bwagent;

import OSPABA.*;
import agents.GeneralWorkerAgent;
import agents.bwagent.continualassistants.*;
import entities.worker.WorkerType;
import simulation.*;

//meta! id="13"
public class BWAgent extends GeneralWorkerAgent
{
	public BWAgent(int id, Simulation mySim, Agent parent)
	{
		super(id, mySim, parent, ((MySimulation)mySim).getWorkerBCnt(), WorkerType.B);
		init();
		myInit();
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init()
	{
		new BWManager(Id.bWManager, mySim(), this);
		new Assembling(Id.assembling, mySim(), this);
		addOwnMessage(Mc.assembly);
	}
	//meta! tag="end"

	private void myInit() {
		addOwnMessage(Mc.assemblyEnd);
	}
}
