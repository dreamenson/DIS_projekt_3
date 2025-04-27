package agents.bwagent;

import OSPABA.*;
import entities.worker.Activity;
import entities.worker.Worker;
import simulation.*;

//meta! id="13"
public class BWManager extends OSPABA.Manager
{
	private AgentComponent assemblingAgent;

	public BWManager(int id, Simulation mySim, Agent myAgent)
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

		assemblingAgent = myAgent().findAssistant(Id.assembling);
	}

	//meta! sender="WorkerAgent", id="72", type="Request"
	public void processAssembly(MessageForm message)
	{
		MyMessage msg = (MyMessage) message;

		if (myAgent().isAvailWorker()) {
			msg.setWorker(myAgent().getAvailWorker());
			msg.setAddressee(assemblingAgent);
			startContinualAssistant(msg);
		} else {
			msg.setNextActivity(Activity.ASSEMBLING);
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

	//meta! sender="Assembling", id="112", type="Finish"
	public void processFinish(MessageForm message)
	{
		MyMessage msg = (MyMessage) message;
		Worker worker = msg.getWorker();
		msg.setWorker(null);
		msg.setPrevTime(mySim().currentTime());
		msg.setCode(Mc.assembly);
		response(msg);

		if (!myAgent().isMessageEmpty()) {
			MyMessage msg1 = myAgent().getMessage();
			msg1.setWorker(worker);
			msg1.setAddressee(assemblingAgent);
			startContinualAssistant(msg1);
		} else {
			worker.setFree();
			myAgent().addAvailWorker(worker);
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
		case Mc.assembly:
			processAssembly(message);
		break;

		case Mc.finish:
			processFinish(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public BWAgent myAgent()
	{
		return (BWAgent)super.myAgent();
	}

}
