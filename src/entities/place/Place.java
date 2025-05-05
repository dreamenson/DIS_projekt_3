package entities.place;

import OSPABA.Simulation;
import OSPAnimator.AnimShape;
import OSPAnimator.AnimShapeItem;
import OSPAnimator.AnimTextItem;
import OSPStat.Stat;
import OSPStat.WStat;
import entities.order.Product;
import simulation.Constants;

import java.awt.*;

public class Place extends AnimShapeItem implements Comparable<Place> {
    private final int id;
    private Product product;
    private final WStat busyRatio;
    private final Stat busyRatioStat = new Stat();
    private final Simulation simulation;

    public Place(int id, Simulation simulation) {
        super(AnimShape.SQUARE, 70);
        this.id = id;
        this.simulation = simulation;
        busyRatio = new WStat(simulation);
        if (simulation.animatorExists()) {
            setPosition(getStartX(), getStartY());
            setZIndex(1);
            setColor(Color.GREEN);
            setFill(false);
            createText();
        }
    }

    private void createText() {
        AnimTextItem text = new AnimTextItem(String.valueOf(id), Color.BLACK, Constants.FONT);
        text.setPosition(getStartX()+1, getStartY()+1);
        simulation.animator().register(text);
    }

    private double getStartX() {
        int col = (id-1) % 10;
        return 50 + col * 110;
    }

    private double getStartY() {
        int row = (id-1) / 10;
        return 50 + row * 130;
    }

    public int getId() {
        return id;
    }

    public boolean isFree() {
        return product == null;
    }

    public void assignProduct(Product product) {
        busyRatio.addSample(100);
        this.product = product;
        if (simulation.animatorExists()) {
            setColor(Color.RED);
        }
    }

    public void release() {
        busyRatio.addSample(0);
        product = null;
        if (simulation.animatorExists()) {
            setColor(Color.GREEN);
        }
    }

    public void updateAfterReplication() {
        busyRatio.updateAfterReplication();
        busyRatioStat.addSample(busyRatio.mean());
    }

    public void reset() {
        release();
        busyRatio.clear();
    }

    public double getBusyRatio() {
        return busyRatio.mean();
    }

    public Stat getBusyRatioStat() {
        return busyRatioStat;
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
