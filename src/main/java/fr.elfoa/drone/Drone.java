package fr.elfoa.drone;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Pierre Colomb
 */
public class Drone {

    @Inject
    private Battery battery;

    @Inject
    private Propellers propellers;

    private List<Container> containers = new ArrayList<Container>();

    @Inject
    private ConsumptionCalculator consumptionCalculator;

    private Point current = new Point(0.0,0.0,0.0);

    private Boolean isFlying;

    @PostConstruct
    public void init() {
        this.propellers.setBattery(battery);
    }

    public void setPosStart (Point current)
    {
        this.current = current;
        if(current.getAltitude() != 0){
            throw new IllegalArgumentException();
        }
    }

    public Drone(){
    }

    public void tackOff(){

        if(!isCanFly()){
            return;
        }

        Integer weight;

        try {
            weight = containers.stream()
                    .mapToInt(Container::getWeight)
                    .sum();
        } catch (NullPointerException e) {
            weight = 0;
        }

        propellers.start();

        battery.use(consumptionCalculator.getConsumption(50d,Direction.VERTICAL,weight));

        current = new Point(current.getLatitude(),current.getLongitude(),50d);

        isFlying = true;

    }

    public void flyTo(Point point){

        if(!isFlying){
            throw new IllegalStateException();
        }

        double distance = point.distanceTo(current);

        Integer weight = containers.stream()
                                   .mapToInt(Container::getWeight)
                                   .sum();

        battery.use(consumptionCalculator.getConsumption(distance,Direction.HORIZONTAL,weight));

        current = point;
    }

    public void landing(){

        Integer weight = containers.stream()
                                   .mapToInt(Container::getWeight)
                                   .sum();

        battery.use(consumptionCalculator.getConsumption(50d,Direction.VERTICAL,weight));

        current = new Point(current.getLatitude(),current.getLongitude(),0d);

        propellers.stop();
    }

    public boolean isCanFly(){

        Integer weight;

        try {
            weight = containers.stream()
                    .mapToInt(Container::getWeight)
                    .sum();
        } catch (NullPointerException e) {
            weight = 0;
        }

        return weight == 0 || (weight / propellers.getNumberOfPropelle() * 5) != 0;
    }

    public Point getCurrentPosition(){
        return current;
    }
}
