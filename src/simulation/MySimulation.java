package simulation;

import OSPStat.Stat;
import OSPStat.WStat;
import agents.surroundagent.*;
import agents.bwagent.*;
import agents.carpentryagent.*;
import agents.awagent.*;
import agents.placeagent.*;
import agents.agentboss.*;
import agents.cwagent.*;
import agents.workeragent.*;
import logger.LoggerInitializer;
import random.RandomCreator;

import java.util.Arrays;

public class MySimulation extends OSPABA.Simulation
{
	private final int workerACnt, workerBCnt, workerCCnt, placeCnt;
	private Long seed = null;
	private final Stat orderDuration = new Stat();
	private final Stat unstartedOrders = new Stat();
	private final Stat orderCount = new Stat();
	private final Stat orderFinishedCount = new Stat();

	private final Stat aWorkRatio = new Stat();
	private final Stat bWorkRatio = new Stat();
	private final Stat cWorkRatio = new Stat();
	private final Stat placeBusyRatio = new Stat();

	public MySimulation(int workersA, int workersB, int workersC, int places, long seed, boolean setAnim)
	{
		workerACnt = workersA;
		workerBCnt = workersB;
		workerCCnt = workersC;
		placeCnt = places;
		_simEndTime = (double) 249*8*60*60;
		if (setAnim) {
			createAnimator();
		}
		init();
		if (seed != 0) {
			this.seed = seed;
			RandomCreator.setSeed(seed);
		}
	}

	@Override
	public void prepareSimulation()
	{
		super.prepareSimulation();
		// Create global statistics
		LoggerInitializer.initLogging();
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Reset entities, queues, local statistics, etc...
	}

	@Override
	public void replicationFinished()
	{
		// Collect local statistics into global, update UI, etc...
		super.replicationFinished();

		// stats
		orderDuration.addSample(_agentBoss.getOrderDurationStat().mean());
		orderCount.addSample(_agentBoss.getOrderCount());
		orderFinishedCount.addSample(_agentBoss.getOrderFinishedCount());
		// wstats
		updateWStat(unstartedOrders, _carpentryAgent.getUnstartedOrdersWStat());
		updateWorkerStat();
		placeBusyRatio.addSample(_placeAgent.getMeanBusyRatio());
	}

	private void updateWStat(Stat repStat, WStat wStat) {
		wStat.updateAfterReplication();
		repStat.addSample(wStat.mean());
	}

	private void updateWorkerStat() {
		aWorkRatio.addSample(_aWAgent.getMeanWorkRatio());
		bWorkRatio.addSample(_bWAgent.getMeanWorkRatio());
		cWorkRatio.addSample(_cWAgent.getMeanWorkRatio());
	}

	@Override
	public void simulationFinished()
	{
		// Display simulation results
		super.simulationFinished();

		System.out.println("\n------------------");
		System.out.println(workerACnt+"/"+workerBCnt+"/"+workerCCnt+"/"+placeCnt+
				" reps: "+(currentReplication()+1)+" seed: "+seed);
		printStat(orderDuration, "Order duration");
		printStat(unstartedOrders, "Unstart orders");
		printStat(aWorkRatio, "A work ratio");
		printStat(bWorkRatio, "B work ratio");
		printStat(cWorkRatio, "C work ratio");
		printStat(placeBusyRatio, "Place busy ratio");
	}

	private void printStat(Stat stat, String name) {
		System.out.println(name + "\t" + stat + "\t" + Arrays.toString(stat.confidenceInterval_95()));
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init()
	{
		setAgentBoss(new AgentBoss(Id.agentBoss, this, null));
		setSurroundAgent(new SurroundAgent(Id.surroundAgent, this, agentBoss()));
		setCarpentryAgent(new CarpentryAgent(Id.carpentryAgent, this, agentBoss()));
		setWorkerAgent(new WorkerAgent(Id.workerAgent, this, carpentryAgent()));
		setAWAgent(new AWAgent(Id.aWAgent, this, workerAgent()));
		setBWAgent(new BWAgent(Id.bWAgent, this, workerAgent()));
		setCWAgent(new CWAgent(Id.cWAgent, this, workerAgent()));
		setPlaceAgent(new PlaceAgent(Id.placeAgent, this, carpentryAgent()));
	}

	private AgentBoss _agentBoss;

public AgentBoss agentBoss()
	{ return _agentBoss; }

	public void setAgentBoss(AgentBoss agentBoss)
	{_agentBoss = agentBoss; }

	private SurroundAgent _surroundAgent;

public SurroundAgent surroundAgent()
	{ return _surroundAgent; }

	public void setSurroundAgent(SurroundAgent surroundAgent)
	{_surroundAgent = surroundAgent; }

	private CarpentryAgent _carpentryAgent;

public CarpentryAgent carpentryAgent()
	{ return _carpentryAgent; }

	public void setCarpentryAgent(CarpentryAgent carpentryAgent)
	{_carpentryAgent = carpentryAgent; }

	private WorkerAgent _workerAgent;

public WorkerAgent workerAgent()
	{ return _workerAgent; }

	public void setWorkerAgent(WorkerAgent workerAgent)
	{_workerAgent = workerAgent; }

	private AWAgent _aWAgent;

public AWAgent aWAgent()
	{ return _aWAgent; }

	public void setAWAgent(AWAgent aWAgent)
	{_aWAgent = aWAgent; }

	private BWAgent _bWAgent;

public BWAgent bWAgent()
	{ return _bWAgent; }

	public void setBWAgent(BWAgent bWAgent)
	{_bWAgent = bWAgent; }

	private CWAgent _cWAgent;

public CWAgent cWAgent()
	{ return _cWAgent; }

	public void setCWAgent(CWAgent cWAgent)
	{_cWAgent = cWAgent; }

	private PlaceAgent _placeAgent;

public PlaceAgent placeAgent()
	{ return _placeAgent; }

	public void setPlaceAgent(PlaceAgent placeAgent)
	{_placeAgent = placeAgent; }
	//meta! tag="end"


	public int getWorkerACnt() {
		return workerACnt;
	}

	public int getWorkerBCnt() {
		return workerBCnt;
	}

	public int getWorkerCCnt() {
		return workerCCnt;
	}

	public int getPlaceCnt() {
		return placeCnt;
	}

	public Stat getOrderDuration() {
		return orderDuration;
	}

	public Stat getUnstartedOrders() {
		return unstartedOrders;
	}

	public Stat getaWorkRatio() {
		return aWorkRatio;
	}

	public Stat getbWorkRatio() {
		return bWorkRatio;
	}

	public Stat getcWorkRatio() {
		return cWorkRatio;
	}

	public Stat getPlaceBusyRatio() {
		return placeBusyRatio;
	}

	public Stat getOrderCount() {
		return orderCount;
	}

	public Stat getOrderFinishedCount() {
		return orderFinishedCount;
	}
}
