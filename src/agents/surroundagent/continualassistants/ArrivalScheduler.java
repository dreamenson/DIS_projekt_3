package agents.surroundagent.continualassistants;

import OSPABA.*;
import agents.surroundagent.*;
import entities.order.Order;
import random.IRandomGenerator;
import random.RandomCreator;
import simulation.*;

//meta! id="28"
public class ArrivalScheduler extends OSPABA.Scheduler
{
	private static final IRandomGenerator rand = RandomCreator.newExponentialRandom(30*60);
	private int count = 1;

	public ArrivalScheduler(int id, Simulation mySim, CommonAgent myAgent)
	{
		super(id, mySim, myAgent);
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
		count = 1;
	}

	//meta! sender="SurroundAgent", id="29", type="Start"
	public void processStart(MessageForm message)
	{
		message.setCode(Mc.newOrder);
		hold(rand.nextValue().doubleValue(), message);
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code()) {
			case Mc.newOrder:
				hold(rand.nextValue().doubleValue(), message.createCopy());

				((MyMessage)message).setOrder(new Order(mySim()));
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
	public SurroundAgent myAgent()
	{
		return (SurroundAgent)super.myAgent();
	}

}
