package agents.workeragent;

import OSPABA.*;
import simulation.*;

//meta! id="6"
public class WorkerAgent extends OSPABA.Agent
{
	public WorkerAgent(int id, Simulation mySim, Agent parent)
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
		new WorkerManager(Id.workerManager, mySim(), this);
		addOwnMessage(Mc.releaseWorker);
		addOwnMessage(Mc.getWorkerA);
		addOwnMessage(Mc.getWorkerC);
		addOwnMessage(Mc.getWorkerB);
		addOwnMessage(Mc.getWorker);
	}
	//meta! tag="end"
}
