package entities.place;

import OSPABA.Simulation;
import OSPStat.WStat;
import entities.order.Product;

public class Place implements Comparable<Place> {
    private final int id;
    private Product product;
    private final WStat busyRatio;

    public Place(int id, Simulation simulation) {
        this.id = id;
        busyRatio = new WStat(simulation);
    }

    public int getId() {
        return id;
    }

    public boolean isFree() {
        return product == null;
    }

    public void assignProduct(Product product) {
        busyRatio.addSample(1);
        this.product = product;
    }

    public void release() {
        busyRatio.addSample(0);
        product = null;
    }

    public void updateAfterReplication() {
        busyRatio.updateAfterReplication();
    }

    public void reset() {
        release();
        busyRatio.clear();
    }

    public double getBusyRatio() {
        return busyRatio.mean();
    }

    @Override
    public int compareTo(Place other) {
        return Integer.compare(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Place{" +
                "id=" + id +
                '}';
    }
}
