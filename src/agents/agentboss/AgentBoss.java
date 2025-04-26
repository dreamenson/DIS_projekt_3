package agents.agentboss;

import OSPABA.*;
import OSPStat.Stat;
import simulation.*;

//meta! id="1"
public class AgentBoss extends OSPABA.Agent
{
	private final Stat orderDurationStat = new Stat();

	public AgentBoss(int id, Simulation mySim, Agent parent)
	{
		super(id, mySim, parent);
		init();
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
		orderDurationStat.clear();

		MyMessage msg = new MyMessage(mySim());
		msg.setAddressee(((MySimulation)mySim()).surroundAgent());
		msg.setCode(Mc.init);
		myManager().notice(msg);
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init()
	{
		new ManagerBoss(Id.managerBoss, mySim(), this);
		addOwnMessage(Mc.makeOrder);
		addOwnMessage(Mc.orderArrive);
	}
	//meta! tag="end"

	public void addOrderDuration(double duration) {
		orderDurationStat.addSample(duration / 3600);
	}

	public Stat getOrderDurationStat() {
		return orderDurationStat;
	}
}
