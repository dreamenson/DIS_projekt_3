package agents.cwagent.continualassistants;

import OSPABA.*;
import entities.order.Product;
import entities.worker.Activity;
import entities.worker.Worker;
import simulation.*;
import agents.cwagent.*;
import OSPABA.Process;

//meta! id="181"
public class NewProc extends OSPABA.Process
{
	public NewProc(int id, Simulation mySim, CommonAgent myAgent)
	{
		super(id, mySim, myAgent);
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
	}

	//meta! sender="CWAgent", id="182", type="Start"
	public void processStart(MessageForm message)
	{
		MyMessage msg = (MyMessage) message;

		Worker worker = msg.getWorker();
		Product product = msg.getProduct();

		worker.setBusy(product, Activity.NEW_ACTIVITY);
		msg.setCode(Mc.newProcEnd);
		double activityTime = 15*60;
		worker.setPlace(product.getPlace(), 0, activityTime);

		hold(activityTime, msg);
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
			case Mc.newProcEnd:
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

}
