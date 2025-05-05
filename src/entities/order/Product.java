package entities.order;

import OSPABA.Simulation;
import OSPAnimator.AnimTextItem;
import entities.place.Place;
import simulation.Constants;

import java.awt.*;

public class Product extends AnimTextItem {
    private final ProductType type;
    private final double startTime;
    private double endTime;
    private final Order order;
    private Place place = null;
    private final long id;
    private final boolean needVarnishing;
    private final Simulation simulation;

    public Product(double time, long id, ProductType type, boolean needVarnishing, Order order, Simulation simulation) {
        super("", Color.BLACK, Constants.FONT_SMALL);
        startTime = time;
        this.type = type;
        this.needVarnishing = needVarnishing;
        this.id = id;
        this.order = order;
        this.simulation = simulation;
        if (simulation.animatorExists()) {
            setPosition(0,0);
            String text = "#" + order.getId() + "_"+ id + "-" + type;
            setZIndex(6);
            setText(text);
            simulation.animator().register(this);
        }
    }

    public ProductType getType() {
        return type;
    }

    public void setPlace(Place place) {
        this.place = place;
        setPosition(simulation.currentTime(), place.getPosX(), place.getPosY()+15);
        place.assignProduct(this);
    }

    public Place getPlace() {
        return place;
    }

    public double getStartTime() {
        return startTime;
    }

    public void setEndTime(double endTime) {
        this.endTime = endTime;
        if (simulation.animatorExists()) {
            simulation.animator().remove(this);
        }
    }

    public double getEndTime() {
        return endTime;
    }

    public long getId() {
        return id;
    }

    public boolean isNeedVarnishing() {
        return needVarnishing;
    }

    public Order getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return "Product{" +
                "type=" + type +
                ", startTime=" + startTime +
                ", order=" + order +
                ", place=" + place +
                ", id=" + id +
                ", needVarnishing=" + needVarnishing +
                '}';
    }

    public String getDetailString() {
        StringBuilder res = new StringBuilder(String.format("%d-%s ", id, type.name()));
        if (needVarnishing) {
            res.append(" need varnish");
        }
        return res.toString();
    }
}
