package agents.cwagent;

import OSPABA.*;
import entities.worker.Worker;
import entities.worker.WorkerType;
import simulation.*;
import agents.cwagent.continualassistants.*;

import java.util.*;

//meta! id="14"
public class CWAgent extends OSPABA.Agent
{
	private final List<Worker> workers = new ArrayList<>();
	private final Queue<Worker> availWorkers = new LinkedList<>();
	private final PriorityQueue<MyMessage> messages = new PriorityQueue<>();

	public CWAgent(int id, Simulation mySim, Agent parent)
	{
		super(id, mySim, parent);
		init();
		myInit();
		initWorkers(((MySimulation)mySim).getWorkerCCnt());
	}

	private void initWorkers(int count) {
		for(int i = 0; i < count; ++i) {
			workers.add(new Worker(WorkerType.C, i + 1, mySim()));
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
		new CWManager(Id.cWManager, mySim(), this);
		new Varnishing(Id.varnishing, mySim(), this);
		new ArmouringC(Id.armouringC, mySim(), this);
		new Mordanting(Id.mordanting, mySim(), this);
		addOwnMessage(Mc.mordantAndVarnish);
		addOwnMessage(Mc.armourC);
	}
	//meta! tag="end"

	private void myInit() {
		addOwnMessage(Mc.mordantEnd);
		addOwnMessage(Mc.varnishEnd);
		addOwnMessage(Mc.armourCEnd);
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
