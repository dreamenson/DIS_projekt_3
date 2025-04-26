package agents.workeragent;

import OSPABA.*;
import random.IRandomGenerator;
import random.RandomCreator;
import simulation.*;

//meta! id="6"
public class WorkerAgent extends OSPABA.Agent
{
	private static final IRandomGenerator transferStorageRandom =
			RandomCreator.newTriangularRandom(60, 480, 120);
	private static final IRandomGenerator transferPlaceRandom =
			RandomCreator.newTriangularRandom(120, 500, 150);
	private static final IRandomGenerator rackArmouringRandom =
			RandomCreator.newContinuousRandom(15*60, 25*60);

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
		addOwnMessage(Mc.mordantAndVarnish);
		addOwnMessage(Mc.assembly);
		addOwnMessage(Mc.armourA);
		addOwnMessage(Mc.prepareAndCut);
		addOwnMessage(Mc.makeProduct);
		addOwnMessage(Mc.armourC);
	}
	//meta! tag="end"

	public static IRandomGenerator getTransferStorageRandom() {
		return transferStorageRandom;
	}

	public static IRandomGenerator getTransferPlaceRandom() {
		return transferPlaceRandom;
	}

	public static IRandomGenerator getRackArmouringRandom() {
		return rackArmouringRandom;
	}
}
