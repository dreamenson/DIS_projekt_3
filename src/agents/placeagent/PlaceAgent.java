package agents.placeagent;

import OSPABA.*;
import entities.place.Place;
import entities.worker.Worker;
import simulation.*;

import java.util.*;


//meta! id="5"
public class PlaceAgent extends OSPABA.Agent
{
	private final PriorityQueue<Place> freePlaces = new PriorityQueue<>();
	private final Queue<MyMessage> messages = new LinkedList<>();
	private final List<Place> places = new ArrayList<>();

	public PlaceAgent(int id, Simulation mySim, Agent parent)
	{
		super(id, mySim, parent);
		init();
		initPlaces();
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
		messages.clear();
		freePlaces.clear();
		for (Place place : places) {
			place.reset();
			freePlaces.add(place);
		}
	}

	private void initPlaces() {
		int placeCnt = ((MySimulation) mySim()).getPlaceCnt();
		for (int i = placeCnt; i > 0; i--) {
			places.add(new Place(i, mySim()));
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

	public double getMeanBusyRatio() {
		double sum = 0;
		for (Place place : places) {
			place.updateAfterReplication();
			sum += place.getBusyRatio();
		}
		return sum / places.size();
	}
}