package agents.cwagent;

import OSPABA.*;
import entities.worker.Activity;
import entities.worker.Worker;
import random.IRandomGenerator;
import random.RandomCreator;
import simulation.*;

//meta! id="14"
public class CWManager extends OSPABA.Manager
{
	private AgentComponent mordantingAgent, varnishingAgent, armouringAgent, newProcAgent;
	private static final IRandomGenerator prob = RandomCreator.newContinuousRandom(0, 1);

	public CWManager(int id, Simulation mySim, Agent myAgent)
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

		mordantingAgent = myAgent().findAssistant(Id.mordanting);
		varnishingAgent = myAgent().findAssistant(Id.varnishing);
		armouringAgent = myAgent().findAssistant(Id.armouringC);
		newProcAgent = myAgent().findAssistant(Id.newProc);
	}

	//meta! sender="WorkerAgent", id="73", type="Request"
	public void processMordantAndVarnish(MessageForm message)
	{
		MyMessage msg = (MyMessage) message;

		if (myAgent().isAvailWorker()) {
			msg.setWorker(myAgent().getAvailWorker());
			msg.setAddressee(mordantingAgent);
			startContinualAssistant(msg);
		} else {
			msg.setNextActivity(Activity.MORDANTING);
			myAgent().addMessage(msg);
		}
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
		}
	}

	//meta! sender="Varnishing", id="121", type="Finish"
	public void processFinishVarnishing(MessageForm message)
	{
		workerFinished(message, Mc.mordantAndVarnish);
	}

	private void workerFinished(MessageForm message, int code) {
		MyMessage msg = (MyMessage) message;
		Worker worker = msg.getWorker();
		msg.setWorker(null);
		msg.setPrevTime(mySim().currentTime());
		msg.setCode(code);
		response(msg);

		if (myAgent().hasMessage()) {
			MyMessage msg1 = myAgent().getMessage();
			msg1.setWorker(worker);
			if (msg1.getNextActivity() == Activity.MORDANTING) {
				msg1.setAddressee(mordantingAgent);
			}
			else if (msg1.getNextActivity() == Activity.ARMOURING) {
				msg1.setAddressee(armouringAgent);
			}
			startContinualAssistant(msg1);
		} else {
			worker.setFree();
			myAgent().addAvailWorker(worker);
		}
	}

	//meta! sender="Mordanting", id="114", type="Finish"
	public void processFinishMordanting(MessageForm message)
	{
		MyMessage msg = (MyMessage) message;

		if (prob.nextValue().doubleValue() < 0.5) {
			message.setAddressee(newProcAgent);
			startContinualAssistant(message);
		}
		else if (msg.getProduct().isNeedVarnishing()) {
			message.setAddressee(varnishingAgent);
			startContinualAssistant(message);
		} else {
			workerFinished(message, Mc.mordantAndVarnish);
		}
	}

	//meta! sender="ArmouringC", id="123", type="Finish"
	public void processFinishArmouringC(MessageForm message)
	{
		workerFinished(message, Mc.armourC);
	}

	//meta! sender="WorkerAgent", id="125", type="Request"
	public void processArmourC(MessageForm message)
	{
		MyMessage msg = (MyMessage) message;

		if (myAgent().isAvailWorker()) {
			msg.setWorker(myAgent().getAvailWorker());
			msg.setAddressee(armouringAgent);
			startContinualAssistant(msg);
		} else {
			msg.setNextActivity(Activity.ARMOURING);
			response(msg);
		}
	}

	//meta! sender="NewProc", id="182", type="Finish"
	public void processFinishNewProc(MessageForm message)
	{
		MyMessage msg = (MyMessage) message;
		if (msg.getProduct().isNeedVarnishing()) {
			message.setAddressee(varnishingAgent);
			startContinualAssistant(message);
		} else {
			workerFinished(message, Mc.mordantAndVarnish);
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
		case Mc.finish:
			switch (message.sender().id())
			{
			case Id.mordanting:
				processFinishMordanting(message);
			break;

			case Id.newProc:
				processFinishNewProc(message);
			break;

			case Id.varnishing:
				processFinishVarnishing(message);
			break;

			case Id.armouringC:
				processFinishArmouringC(message);
			break;
			}
		break;

		case Mc.armourC:
			processArmourC(message);
		break;

		case Mc.mordantAndVarnish:
			processMordantAndVarnish(message);
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

}
