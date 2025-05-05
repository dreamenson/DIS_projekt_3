package agents.placeagent;

import OSPABA.*;
import OSPAnimator.AnimShape;
import OSPAnimator.AnimShapeItem;
import OSPAnimator.AnimTextItem;
import entities.place.Place;
import entities.worker.Worker;
import simulation.*;

import java.awt.*;
import java.util.*;
import java.util.List;


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

		if (mySim().animatorExists()) {
			createWarehouse();

			for (int i = 0; i < placeCnt; i++) {
				Place place = new Place(i+1, mySim());
				places.add(place);
				mySim().animator().register(place);
			}
		} else {
			for (int i = 0; i < placeCnt; i++) {
				places.add(new Place(i+1, mySim()));
			}
		}
	}

	private void createWarehouse() {
		AnimShapeItem item = new AnimShapeItem(AnimShape.RECTANGLE, 100, 720);
		item.setPosition(1200, 50);
		item.setColor(Color.BLACK);
		item.setFill(false);

		AnimTextItem text = new AnimTextItem("Warehouse", Color.BLACK, Constants.FONT);
		text.setPosition(1205, 55);
		mySim().animator().register(item);
		mySim().animator().register(text);
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

	public List<Place> getPlaces() {
		return places;
	}
}