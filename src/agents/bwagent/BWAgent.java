package agents.bwagent;

import OSPABA.*;
import agents.bwagent.continualassistants.*;
import entities.worker.Worker;
import entities.worker.WorkerType;
import simulation.*;

import java.util.*;

//meta! id="13"
public class BWAgent extends OSPABA.Agent
{
	private final List<Worker> workers = new ArrayList<>();
	private final Queue<Worker> availWorkers = new LinkedList<>();
	private final PriorityQueue<MyMessage> messages = new PriorityQueue<>();

	public BWAgent(int id, Simulation mySim, Agent parent)
	{
		super(id, mySim, parent);
		init();
		myInit();
		initWorkers(((MySimulation)mySim).getWorkerBCnt());
	}

	private void initWorkers(int count) {
		for(int i = 0; i < count; ++i) {
			workers.add(new Worker(WorkerType.B, i + 1, mySim()));
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
		messages.clear();
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

	public boolean isAvailWorker() {
		return !availWorkers.isEmpty();
	}

	public Worker getAvailWorker() {
		return availWorkers.remove();
	}

	public void addAvailWorker(Worker worker) {
		availWorkers.add(worker);
	}

	public boolean isMessageEmpty() {
		return messages.isEmpty();
	}

	public MyMessage getMessage() {
		return messages.remove();
	}

	public void addMessage(MyMessage msg) {
		messages.add(msg);
	}
}
