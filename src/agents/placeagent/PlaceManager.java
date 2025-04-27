package agents.placeagent;

import OSPABA.*;
import entities.place.Place;
import simulation.*;

//meta! id="5"
public class PlaceManager extends OSPABA.Manager
{
	public PlaceManager(int id, Simulation mySim, Agent myAgent)
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
	}

	//meta! sender="CarpentryAgent", id="85", type="Notice"
	public void processReleasePlace(MessageForm message)
	{
		Place place = ((MyMessage) message).getPlace();
		place.release();

		if (myAgent().hasMessage()) {
			MyMessage msg = myAgent().getMessage();
			msg.setPlace(place);
			msg.setPrevTime(mySim().currentTime());
			response(msg);
		} else {
			myAgent().addFreePlace(place);
		}
	}

	//meta! sender="CarpentryAgent", id="31", type="Request"
	public void processAssignPlace(MessageForm message)
	{
		MyMessage msg = (MyMessage) message;

		if (myAgent().isFreePlace()) {
			msg.setPlace(myAgent().getFreePlace());
			msg.setPrevTime(mySim().currentTime());
			response(msg);
		} else {
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

	//meta! userInfo="Generated code: do not modify", tag="begin"
	public void init()
	{
	}

	@Override
	public void processMessage(MessageForm message)
	{
		switch (message.code())
		{
		case Mc.releasePlace:
			processReleasePlace(message);
		break;

		case Mc.assignPlace:
			processAssignPlace(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public PlaceAgent myAgent()
	{
		return (PlaceAgent)super.myAgent();
	}

}