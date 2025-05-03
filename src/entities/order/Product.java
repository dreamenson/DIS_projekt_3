package entities.order;

import entities.place.Place;

public class Product {
    private final ProductType type;
    private final double startTime;
    private double endTime;
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
