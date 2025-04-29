package agents.carpentryagent;

import OSPABA.*;
import OSPStat.WStat;
import entities.order.Order;
import simulation.*;

import java.util.ArrayList;
import java.util.List;

//meta! id="37"
public class CarpentryAgent extends OSPABA.Agent
{
	private final List<Order> unstartedOrders = new ArrayList<>();
	private final WStat unstartedOrdersWStat = new WStat(mySim());

	public CarpentryAgent(int id, Simulation mySim, Agent parent)
	{
		super(id, mySim, parent);
		init();
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
		unstartedOrders.clear();
		unstartedOrdersWStat.clear();
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init()
	{
		new CarpentryManager(Id.carpentryManager, mySim(), this);
		addOwnMessage(Mc.productStarted);
		addOwnMessage(Mc.assignPlace);
		addOwnMessage(Mc.makeOrder);
		addOwnMessage(Mc.makeProduct);
	}
	//meta! tag="end"

	public void addOrder(Order order) {
		unstartedOrders.add(order);
		unstartedOrdersWStat.addSample(unstartedOrders.size());
	}

	public void removeOrder(Order order) {
		if (unstartedOrders.remove(order)) {
			unstartedOrdersWStat.addSample(unstartedOrders.size());
		}
	}

	public WStat getUnstartedOrdersWStat() {
		return unstartedOrdersWStat;
	}
}
