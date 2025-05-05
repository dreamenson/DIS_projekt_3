package agents.awagent.continualassistants;

import OSPABA.*;
import agents.awagent.*;
import agents.workeragent.WorkerAgent;
import entities.order.Product;
import entities.order.ProductType;
import entities.place.Place;
import entities.worker.Activity;
import entities.worker.Worker;
import random.EmpiricalRandom;
import random.IRandomGenerator;
import random.RandomCreator;
import random.Type;
import simulation.*;
import OSPABA.Process;

//meta! id="105"
public class Cutting extends OSPABA.Process
{
	private static IRandomGenerator transferStorageRandom;
	private static IRandomGenerator tableCuttingRandom;
	private static final IRandomGenerator chairCuttingRandom =
			RandomCreator.newContinuousRandom(12*60, 16*60);
	private static final IRandomGenerator rackCuttingRandom =
			RandomCreator.newContinuousRandom(15*60, 80*60);

	public Cutting(int id, Simulation mySim, CommonAgent myAgent)
	{
		super(id, mySim, myAgent);

		EmpiricalRandom emp = RandomCreator.newEmpiricalRandom(Type.CONTINUOUS);
		RandomCreator.addInterval(emp, 10*60, 25*60, 0.6);
		RandomCreator.addInterval(emp, 25*60, 50*60, 0.4);
		tableCuttingRandom = emp;

		transferStorageRandom = WorkerAgent.getTransferStorageRandom();
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
	}

	//meta! sender="AWAgent", id="106", type="Start"
	public void processStart(MessageForm message)
	{
		MyMessage msg = (MyMessage) message;

		Worker worker = msg.getWorker();
		Product product = msg.getProduct();
		Place place = msg.getPlace();

		worker.setBusy(product, Activity.CUTTING);
		product.setPlace(place);
		msg.setCode(Mc.cutEnd);

		double transferTime = transferStorageRandom.nextValue().doubleValue();
		double activityTime = getHoldTime(product);
		worker.setPlace(place, transferTime, activityTime);

		hold(transferTime + activityTime, msg);
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
			case Mc.cutEnd:
				assistantFinished(message);
				break;
		}
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	@Override
	public void processMessage(MessageForm message)
	{
		switch (message.code())
		{
		case Mc.start:
			processStart(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public AWAgent myAgent()
	{
		return (AWAgent)super.myAgent();
	}

	private double getHoldTime(Product product) {
        return switch (product.getType()) {
            case ProductType.TABLE -> tableCuttingRandom.nextValue().doubleValue();
            case ProductType.CHAIR -> chairCuttingRandom.nextValue().doubleValue();
            case ProductType.RACK -> rackCuttingRandom.nextValue().doubleValue();
        };
	}
}
