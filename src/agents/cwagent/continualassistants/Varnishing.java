package agents.cwagent.continualassistants;

import OSPABA.*;
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
import agents.cwagent.*;
import OSPABA.Process;

//meta! id="120"
public class Varnishing extends OSPABA.Process
{
	private static IRandomGenerator tableVarnishingRandom;
	private static final IRandomGenerator chairVarnishingRandom =
			RandomCreator.newContinuousRandom(40*60, 200*60);
	private static final IRandomGenerator rackVarnishingRandom =
			RandomCreator.newContinuousRandom(250*60, 560*60);

	public Varnishing(int id, Simulation mySim, CommonAgent myAgent)
	{
		super(id, mySim, myAgent);

		EmpiricalRandom emp = RandomCreator.newEmpiricalRandom(Type.CONTINUOUS);
		RandomCreator.addInterval(emp, 50*60, 70*60, 0.1);
		RandomCreator.addInterval(emp, 70*60, 150*60, 0.6);
		RandomCreator.addInterval(emp, 150*60, 200*60, 0.3);
		tableVarnishingRandom = emp;
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
	}

	//meta! sender="CWAgent", id="121", type="Start"
	public void processStart(MessageForm message)
	{
		MyMessage msg = (MyMessage) message;

		Worker worker = msg.getWorker();
		Product product = msg.getProduct();

		worker.setBusy(product, Activity.VARNISHING);
		message.setCode(Mc.varnishEnd);

		hold(product.isNeedVarnishing() ? getHoldTime(product) : 0, message);
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
			case Mc.varnishEnd:
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
	public CWAgent myAgent()
	{
		return (CWAgent)super.myAgent();
	}

	private double getHoldTime(Product product) {
		return switch (product.getType()) {
			case ProductType.TABLE -> tableVarnishingRandom.nextValue().doubleValue();
			case ProductType.CHAIR -> chairVarnishingRandom.nextValue().doubleValue();
			case ProductType.RACK -> rackVarnishingRandom.nextValue().doubleValue();
		};
	}
}
