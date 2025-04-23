package simulation;

import OSPABA.*;
import entities.order.Order;
import entities.order.Product;
import entities.place.Place;
import entities.worker.Worker;

public class MyMessage extends OSPABA.MessageForm
{
	private Order order;
	private Worker worker;
	private Product product;
	private Place place;

	public MyMessage(Simulation mySim)
	{
		super(mySim);
	}

	public MyMessage(MyMessage original)
	{
		super(original);
		// copy() is called in superclass
	}

	@Override
	public MessageForm createCopy()
	{
		return new MyMessage(this);
	}

	@Override
	protected void copy(MessageForm message)
	{
		super.copy(message);
		MyMessage original = (MyMessage)message;
		// Copy attributes
		this.order = original.order;
		this.worker = original.worker;
		this.product = original.product;
		this.place = original.place;
	}

	public Worker getWorker() {
		return worker;
	}

	public Order getOrder() {
		return order;
	}

	public Product getProduct() {
		return product;
	}

	public Place getPlace() {
		return place;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public void setWorker(Worker worker) {
		this.worker = worker;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void setPlace(Place place) {
		this.place = place;
	}
}
