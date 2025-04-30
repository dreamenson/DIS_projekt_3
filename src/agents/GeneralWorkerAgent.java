package agents;

import OSPABA.Agent;
import OSPABA.Simulation;
import entities.worker.Worker;
import entities.worker.WorkerType;
import simulation.MyMessage;

import java.util.*;

public class GeneralWorkerAgent extends OSPABA.Agent {
    protected final List<Worker> workers = new ArrayList<>();
    protected final Queue<Worker> availWorkers = new LinkedList<>();
    protected final PriorityQueue<MyMessage> messages = new PriorityQueue<>();

    public GeneralWorkerAgent(int id, Simulation mySim, Agent parent, int workerCount, WorkerType type) {
        super(id, mySim, parent);
        initWorkers(workerCount, type);
    }

    private void initWorkers(int count, WorkerType type) {
        for(int i = 0; i < count; ++i) {
            workers.add(new Worker(type, i + 1, mySim()));
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

    public boolean isAvailWorker() {
        return !availWorkers.isEmpty();
    }

    public Worker getAvailWorker() {
        return availWorkers.remove();
    }

    public void addAvailWorker(Worker worker) {
        availWorkers.add(worker);
    }

    public boolean hasMessage() {
        return !messages.isEmpty();
    }

    public MyMessage getMessage() {
        return messages.remove();
    }

    public void addMessage(MyMessage msg) {
        messages.add(msg);
    }

    public double getMeanWorkRatio() {
        double sum = 0;
        for (Worker worker : workers) {
            worker.updateAfterReplication();
            sum += worker.getWorkRatio();
        }
        return sum / workers.size();
    }

    public List<Worker> getWorkers() {
        return workers;
    }
}
