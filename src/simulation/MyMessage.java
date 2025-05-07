package simulation;

import OSPABA.*;
import entities.order.Order;
import entities.order.Product;
import entities.place.Place;
import entities.worker.Activity;
import entities.worker.Worker;

import java.awt.*;

public class MyMessage extends OSPABA.MessageForm implements Comparable<MyMessage>
{
	private Order order;
	private Worker worker;
	private Product product;
	private Place place;
	private Activity nextActivity;
	private double prevTime;

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
		this.nextActivity = original.nextActivity;
		this.prevTime = original.prevTime;
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

	public Activity getNextActivity() {
		return nextActivity;
	}

	public double getPrevTime() {
		return prevTime;
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
		if (mySim().animatorExists()) {
			place.setColor(Color.RED);
		}
	}

	public void setNextActivity(Activity nextActivity) {
		this.nextActivity = nextActivity;
	}

	public void setPrevTime(double prevTime) {
		this.prevTime = prevTime;
	}

	@Override
	public int compareTo(MyMessage o) {
		double thisTime = this.getProduct().getStartTime();
		double otherTime = o.getProduct().getStartTime();
		if (thisTime < otherTime) return -1;
		if (thisTime > otherTime) return 1;
		return Double.compare(this.getPrevTime(), o.getPrevTime());
	}
}
