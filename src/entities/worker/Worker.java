package entities.worker;

import OSPABA.Simulation;
import OSPAnimator.AnimShape;
import OSPAnimator.AnimShapeItem;
import OSPAnimator.AnimTextItem;
import OSPStat.Stat;
import OSPStat.WStat;
import entities.place.Place;
import entities.order.Product;
import simulation.Constants;

import java.awt.*;

public class Worker extends AnimShapeItem {
    private static final int START_X = 1205;
    private static final int START_Y = 120;
    private AnimTextItem text, activityText;
    private AnimShapeItem bar, barWhite;
    private final WorkerType type;
    private final int index;
    private boolean isFree = true;
    private Place place = null;
    private final WStat workRatio;
    private final Stat workRatioStatistics = new Stat();
    private Product product;
    private Activity activity;
    private final Simulation simulation;

    public Worker(WorkerType type, int index, Simulation simulation) {
        super(AnimShape.CIRCLE, 20);
        this.type = type;
        this.index = index;
        product = null;
        activity = null;
        workRatio = new WStat(simulation);
        this.simulation = simulation;
        if (simulation.animatorExists()) {
            setPosition(START_X, START_Y);
            setZIndex(20);
            setColor(Color.GREEN);
            createText();
            createProgressBar();
        }
    }

    private void createText() {
        String str = type + String.valueOf(index);
        text = new AnimTextItem(str, Color.BLACK, Constants.FONT_SMALL);
        text.setPosition(START_X+2, START_Y+2);
        text.setZIndex(25);
        simulation.animator().register(text);

        activityText = new AnimTextItem("pes", Color.BLACK, Constants.FONT_SMALL);
        activityText.setPosition(posX - 20, posY - 20);
        activityText.setZIndex(8);
        simulation.animator().register(activityText);
    }

    private void createProgressBar() {
        bar = new AnimShapeItem(AnimShape.RECTANGLE, 60, 5);
        bar.setColor(Color.DARK_GRAY);
        bar.setPosition(55,45);
        bar.setZIndex(1);
        bar.setVisible(false);
        simulation.animator().register(bar);

        barWhite = new AnimShapeItem(AnimShape.RECTANGLE, 60, 5);
        barWhite.setColor(Color.WHITE);
        barWhite.setPosition(55,45);
        barWhite.setZIndex(2);
        simulation.animator().register(barWhite);
    }

    public void setBusy(Product product, Activity activity) {
        addValueToStatistic(100);
        isFree = false;
        this.product = product;
        this.activity = activity;
        setColor(Color.RED);
    }

    public void setFree() {
        addValueToStatistic(0);
        isFree = true;
        product = null;
        activity = null;
        moveDown();
    }

    private void moveDown() {
        if (simulation.animatorExists()) {
            setColor(Color.GREEN);
            setPosition(simulation.currentTime(), posX, posY+20);
            text.setPosition(simulation.currentTime(), posX+2, posY+22);
        }
    }

    private void addValueToStatistic(double value) {
        workRatio.addSample(value);
    }

    public void updateAfterReplication() {
        workRatio.updateAfterReplication();
        workRatioStatistics.addSample(workRatio.mean());
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place, double transferTime, double activityTime) {
        this.place = place;
        moveToPlace(transferTime);
        makeActivityText(transferTime, activityTime);
        moveProduct(transferTime, activityTime);
        animateProgressBar(transferTime, activityTime);
    }

    private void animateProgressBar(double transferTime, double activityTime) {
        if (simulation.animatorExists()) {
            double eps = 1e-6;
            bar.setVisible(simulation.currentTime() + transferTime, true);
            bar.setVisible(simulation.currentTime() + transferTime + activityTime, false);
            if (activity ==Activity.PREPARING) {
                bar.setPosition(simulation.currentTime() + transferTime - eps, START_X, START_Y + index * 50 - 30);
                barWhite.setPosition(simulation.currentTime() + transferTime - eps, START_X, START_Y + index * 50 - 30);
            } else {
                if (place.getId() % 2 == 1) {
                    bar.setPosition(simulation.currentTime() + transferTime, place.getPosX() + 5, place.getPosY() + 20);
                    barWhite.setPosition(simulation.currentTime() + transferTime, place.getPosX() + 5, place.getPosY() + 20);
                } else {
                    bar.setPosition(simulation.currentTime() + transferTime, place.getPosX() + 5, place.getPosY() + 15);
                    barWhite.setPosition(simulation.currentTime() + transferTime, place.getPosX() + 5, place.getPosY() + 15);
                }
            }
            barWhite.move(simulation.currentTime() + transferTime, activityTime - eps, 60, 0);
        }
    }

    private void moveProduct(double transferTime, double activityTime) {
        if (simulation.animatorExists() && activity == Activity.PREPARING) {
            product.moveTo(simulation.currentTime(), transferTime, START_X, START_Y+index*50-25);
        }
    }

    private void makeActivityText(double start, double duration) {
        if (simulation.animatorExists()) {
            activityText.setText(activity.name());
            if (place == null) {
                activityText.setPosition(simulation.currentTime(), START_X, START_Y+index*50-15);
            } else {
                activityText.setPosition(simulation.currentTime(), place.getPosX(), place.getPosY() + 35);
            }
            activityText.setVisible(simulation.currentTime() + start, true);
            activityText.setVisible(simulation.currentTime() + start + duration, false);
        }
    }

    private void moveToPlace(double duration) {
        if (simulation.animatorExists()) {
            if (place == null) {
                moveTo(simulation.currentTime(), duration, START_X, START_Y+index*50);
                text.moveTo(simulation.currentTime(), duration, START_X+2, START_Y+index*50+2);
            } else {
                moveTo(simulation.currentTime(), duration, place.getPosX() + 25, place.getPosY() + 50);
                text.moveTo(simulation.currentTime(), duration, place.getPosX() + 27, place.getPosY() + 52);
                if (activity == Activity.CUTTING) {
                    product.moveTo(simulation.currentTime(), duration, place.getPosX(), place.getPosY() + 25);
                }
            }
        }
    }

    public WorkerType getType() {
        return type;
    }

    public double getWorkRatio() {
        return workRatio.mean();
    }

    public int getIndex() {
        return index;
    }

    public Product getProduct() {
        return product;
    }

    public Activity getActivity() {
        return activity;
    }

    public boolean isFree() {
        return isFree;
    }

    public Stat getWorkRatioStatistics() {
        return workRatioStatistics;
    }

    public void reset() {
        isFree = true;
        place = null;
        workRatio.clear();
        product = null;
        activity = null;
    }
}
