package agents.awagent;

import OSPABA.*;
import entities.worker.Worker;
import entities.worker.WorkerType;
import simulation.*;
import agents.awagent.continualassistants.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

//meta! id="12"
public class AWAgent extends OSPABA.Agent
{
	private final List<Worker> workers = new ArrayList<>();
	private final Queue<Worker> availWorkers = new LinkedList<>();

	public AWAgent(int id, Simulation mySim, Agent parent)
	{
		super(id, mySim, parent);
		init();
		initWorkers(((MySimulation)mySim).getWorkerACnt());
	}

	private void initWorkers(int count) {
		for(int i = 0; i < count; ++i) {
			workers.add(new Worker(WorkerType.A, i + 1, mySim()));
		}
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
		availWorkers.clear();
		for(Worker worker : workers) {
			worker.reset();
			availWorkers.add(worker);
		}
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

	public boolean isAvailWorker() {
		return !availWorkers.isEmpty();
	}

	public Worker getAvailWorker() {
		return availWorkers.remove();
	}

	public void addAvailWorker(Worker worker) {
		availWorkers.add(worker);
	}
}
