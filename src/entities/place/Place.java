package entities.place;

import entities.order.Product;

public class Place implements Comparable<Place> {
    private final int id;
    private Product product;

    public Place(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean isFree() {
        return product == null;
    }

    public void assignProduct(Product product) {
        this.product = product;
    }

    public void release() {
        product = null;
    }

    @Override
    public int compareTo(Place other) {
        return Integer.compare(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Place{" +
                "id=" + id +
                ", product=" + product +
                '}';
    }
}
