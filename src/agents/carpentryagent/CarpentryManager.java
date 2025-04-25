package agents.carpentryagent;

import OSPABA.*;
import agents.placeagent.PlaceAgent;
import agents.workeragent.WorkerAgent;
import entities.order.Product;
import simulation.*;

//meta! id="37"
public class CarpentryManager extends OSPABA.Manager
{
	private PlaceAgent placeAgent;
	private WorkerAgent workerAgent;

	public CarpentryManager(int id, Simulation mySim, Agent myAgent)
	{
		super(id, mySim, myAgent);
		init();
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication

		if (petriNet() != null)
		{
			petriNet().clear();
		}

		placeAgent = ((MySimulation) mySim()).placeAgent();
		workerAgent = ((MySimulation) mySim()).workerAgent();
	}

	//meta! sender="PlaceAgent", id="31", type="Response"
	public void processAssignPlace(MessageForm message)
	{
		MyMessage msg = (MyMessage) message;
		msg.setAddressee(workerAgent);
		msg.setCode(Mc.makeProduct);
		request(msg);
	}

	//meta! sender="AgentBoss", id="69", type="Request"
	public void processMakeOrder(MessageForm message)
	{
		MyMessage msg = (MyMessage) message;
		msg.setAddressee(placeAgent);
		msg.setCode(Mc.assignPlace);

		for (Product product : msg.getOrder().getProducts()) {
			MyMessage msg1 = (MyMessage) msg.createCopy();
			msg1.setProduct(product);
			System.out.println("Make product: " + product);
			request(msg1);
		}
	}

	//meta! sender="WorkerAgent", id="70", type="Response"
	public void processMakeProduct(MessageForm message)
	{
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
		}
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	public void init()
	{
	}

	@Override
	public void processMessage(MessageForm message)
	{
		switch (message.code())
		{
		case Mc.assignPlace:
			processAssignPlace(message);
		break;

		case Mc.makeOrder:
			processMakeOrder(message);
		break;

		case Mc.makeProduct:
			processMakeProduct(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public CarpentryAgent myAgent()
	{
		return (CarpentryAgent)super.myAgent();
	}

}
