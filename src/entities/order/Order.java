package entities.order;

import OSPABA.Simulation;
import random.IRandomGenerator;
import random.RandomCreator;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private static long nextID = 1;
    private static final IRandomGenerator rndCount = RandomCreator.newDiscreteRandom(1, 6);
    private static final IRandomGenerator rndType = RandomCreator.newContinuousRandom(0, 1);
    private static final IRandomGenerator rndNeedVarnish = RandomCreator.newContinuousRandom(0, 1);

    private final long id;
    private final double startTime;
    private double endTime;
    private final List<Product> products = new ArrayList<>();
    private int productCnt;

    public Order(Simulation sim) {
        this.startTime = sim.currentTime();
        this.id = nextID++;
        initProducts(sim);
    }

    private void initProducts(Simulation sim) {
        productCnt = rndCount.nextValue().intValue();

        for (int i = 0; i < productCnt; i++) {
            ProductType type;
            double prob = rndType.nextValue().doubleValue();
            if (prob < 0.5) type = ProductType.TABLE;
            else if (prob < 0.85) type = ProductType.RACK;
            else type = ProductType.CHAIR;

            boolean needVarnish = rndNeedVarnish.nextValue().doubleValue() < 0.15;
            products.add(new Product(startTime, i + 1, type, needVarnish, this, sim));
        }
    }

    public long getId() {
        return id;
    }

    public double getStartTime() {
        return startTime;
    }

    public double getEndTime() {
        return endTime;
    }

    public boolean isLastProductDone(Product product) {
        if (--productCnt == 0) {
            endTime = product.getEndTime();
            return true;
        }
        return false;
    }

    public List<Product> getProducts() {
        return products;
    }

    public static void resetID() {
        nextID = 1;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                '}';
    }

    public String getDetailString() {
        StringBuilder res = new StringBuilder(String.format("Order #%d:%n", id));
        for (Product product : products) {
            res.append("  ");
            res.append(product.getDetailString());
            res.append("\n");
        }
        return res.toString();
    }
}
