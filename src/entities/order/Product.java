package entities.order;

import entities.place.Place;

public class Product {
    private final ProductType type;
    private final double startTime;
    private final Order order;
    private Place place = null;
    private final long id;
    private final boolean needVarnishing;

    public Product(double time, long id, ProductType type, boolean needVarnishing, Order order) {
        startTime = time;
        this.type = type;
        this.needVarnishing = needVarnishing;
        this.id = id;
        this.order = order;
    }

    public ProductType getType() {
        return type;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Place getPlace() {
        return place;
    }

    public double getStartTime() {
        return startTime;
    }

    public long getId() {
        return id;
    }

    public boolean isNeedVarnishing() {
        return needVarnishing;
    }
}
