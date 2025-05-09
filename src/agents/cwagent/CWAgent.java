package agents.cwagent;

import OSPABA.*;
import agents.GeneralWorkerAgent;
import entities.worker.WorkerType;
import simulation.*;
import agents.cwagent.continualassistants.*;

//meta! id="14"
public class CWAgent extends GeneralWorkerAgent
{
	public CWAgent(int id, Simulation mySim, Agent parent)
	{
		super(id, mySim, parent, ((MySimulation)mySim).getWorkerCCnt(), WorkerType.C);
		init();
		myInit();
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init()
	{
		new CWManager(Id.cWManager, mySim(), this);
		new Varnishing(Id.varnishing, mySim(), this);
		new ArmouringC(Id.armouringC, mySim(), this);
		new Mordanting(Id.mordanting, mySim(), this);
		new NewProc(Id.newProc, mySim(), this);
		addOwnMessage(Mc.mordantAndVarnish);
		addOwnMessage(Mc.armourC);
	}
	//meta! tag="end"

	private void myInit() {
		addOwnMessage(Mc.mordantEnd);
		addOwnMessage(Mc.varnishEnd);
		addOwnMessage(Mc.armourCEnd);
		addOwnMessage(Mc.newProcEnd);
	}
}
