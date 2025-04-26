package agents.placeagent;

import OSPABA.*;
import entities.place.Place;
import simulation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;


//meta! id="5"
public class PlaceAgent extends OSPABA.Agent
{
	private final PriorityQueue<Place> freePlaces = new PriorityQueue<>();
	private final Queue<MyMessage> messages = new LinkedList<>();

	public PlaceAgent(int id, Simulation mySim, Agent parent)
	{
		super(id, mySim, parent);
		init();
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
		initPlaces();
		messages.clear();
	}

	private void initPlaces() {
		int placeCnt = ((MySimulation) mySim()).getPlaceCnt();
		for (int i = placeCnt; i > 0; i--) {
			freePlaces.add(new Place(i));
		}
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init()
	{
		new PlaceManager(Id.placeManager, mySim(), this);
		addOwnMessage(Mc.releasePlace);
		addOwnMessage(Mc.assignPlace);
	}
	//meta! tag="end"

	public boolean isFreePlace() {
		return !freePlaces.isEmpty();
	}

	public Place getFreePlace() {
		return freePlaces.remove();
	}

	public void addFreePlace(Place place) {
		freePlaces.add(place);
	}

	public boolean hasMessage() {
		return !messages.isEmpty();
	}

	public MyMessage getMessage() {
		return messages.remove();
	}

	public void addMessage(MyMessage msg) {
		messages.add(msg);
	}
}