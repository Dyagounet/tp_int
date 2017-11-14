package fr.elfoa.drone;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.elfoa.AbstractBootstraper;

/**
 * @author Pierre Colomb
 */
public class DroneTest extends AbstractBootstraper {

    @BeforeClass
    public static void start() {
        init();
    }

    @AfterClass
    public static void stop() {
        shutdown();
    }



    private static final Point ORIGIN = new Point(0d,0d,0d);

    @Test
    public void tackOff() throws Exception {

        //Drone drone = new Drone(ORIGIN);

        Drone drone = getInstance(Drone.class);

        drone.tackOff();

        assertEquals(50d,drone.getCurrentPosition().getAltitude(),0);

    }



    @Test
    public void flyTo() throws Exception {

    }



    @Test
    public void landing() throws Exception {

    }



    @Test
    public void isCanFly() throws Exception {

    }



    @Test
    public void getCurrentPosition() throws Exception {

    }

}