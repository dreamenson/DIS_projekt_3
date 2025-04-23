package agents.workeragent;

import OSPABA.*;
import agents.awagent.AWAgent;
import agents.bwagent.BWAgent;
import agents.cwagent.CWAgent;
import entities.order.ProductType;
import simulation.*;

//meta! id="6"
public class WorkerManager extends OSPABA.Manager
{
	private AWAgent awAgent;
	private BWAgent bwAgent;
	private CWAgent cwAgent;

	public WorkerManager(int id, Simulation mySim, Agent myAgent)
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

		MySimulation sim = (MySimulation) mySim();
		awAgent = sim.aWAgent();
		bwAgent = sim.bWAgent();
		cwAgent = sim.cWAgent();
	}

	//meta! sender="AWAgent", id="71", type="Response"
	public void processPrepareAndCut(MessageForm message)
	{
		MyMessage msg = (MyMessage) message;
		msg.setAddressee(cwAgent);
		msg.setCode(Mc.mordantAndVarnish);
		request(msg);
	}

	//meta! sender="CWAgent", id="73", type="Response"
	public void processMordantAndVarnish(MessageForm message)
	{
		MyMessage msg = (MyMessage) message;
		msg.setAddressee(bwAgent);
		msg.setCode(Mc.assembly);
		request(msg);
	}

	//meta! sender="BWAgent", id="72", type="Response"
	public void processAssembly(MessageForm message)
	{
		MyMessage msg = (MyMessage) message;

		ProductType type = msg.getProduct().getType();
		if (type != ProductType.RACK) {
			msg.setCode(Mc.makeProduct);
			response(msg);
		} else {
			System.out.println("skrina");
			// TODO armour
		}
	}

	//meta! sender="CarpentryAgent", id="70", type="Request"
	public void processMakeProduct(MessageForm message)
	{
		MyMessage msg = (MyMessage) message;
		msg.setAddressee(awAgent);
		msg.setCode(Mc.prepareAndCut);
		request(msg);
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
		}
	}

	//meta! sender="AWAgent", id="124", type="Response"
	public void processArmourA(MessageForm message)
	{
	}

	//meta! sender="CWAgent", id="125", type="Response"
	public void processArmourC(MessageForm message)
	{
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
		case Mc.makeProduct:
			processMakeProduct(message);
		break;

		case Mc.assembly:
			processAssembly(message);
		break;

		case Mc.mordantAndVarnish:
			processMordantAndVarnish(message);
		break;

		case Mc.armourC:
			processArmourC(message);
		break;

		case Mc.prepareAndCut:
			processPrepareAndCut(message);
		break;

		case Mc.armourA:
			processArmourA(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public WorkerAgent myAgent()
	{
		return (WorkerAgent)super.myAgent();
	}

}
