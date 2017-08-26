package org.lejos.example.Behaviours;

import lejos.robotics.navigation.*;
import lejos.robotics.objectdetection.*;
import lejos.nxt.*;
import lejos.robotics.RegulatedMotor;
import lejos.util.PilotProps;
import java.io.IOException;
import java.util.Random;

public class BumpNavigator implements FeatureListener {

    public static int AREA_WIDTH = 150;
    public static int AREA_LENGTH = 150;
    public static int LEFT_SIDE = 1;
    public static int RIGHT_SIDE = -1;
    private WayPoint target;

    private NavPathController nav;
    private DifferentialPilot pilot;
    Random rand = new Random();

    public BumpNavigator(final NavPathController aNavigator) {
        nav = aNavigator;
    }

    public void goTo(double x, double y) {
        target = new WayPoint(x, y);
        nav.goTo(target);
    }

    /**
     * test of BumpNavitator. destination is 200 cm directly ahead.
     *
     * @param args
     * @throws IOException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        PilotProps pp = new PilotProps();
        pp.loadPersistentValues();
        float wheelDiameter = Float.parseFloat(pp.getProperty(PilotProps.KEY_WHEELDIAMETER, "4.32"));
        float trackWidth = Float.parseFloat(pp.getProperty(PilotProps.KEY_TRACKWIDTH, "16.35"));
        RegulatedMotor leftMotor = PilotProps.getMotor(pp.getProperty(PilotProps.KEY_LEFTMOTOR, "B"));
        RegulatedMotor rightMotor = PilotProps.getMotor(pp.getProperty(PilotProps.KEY_RIGHTMOTOR, "C"));
        boolean reverse = Boolean.parseBoolean(pp.getProperty(PilotProps.KEY_REVERSE, "false"));

        DifferentialPilot p = new DifferentialPilot(wheelDiameter, trackWidth, leftMotor, rightMotor, reverse);
        NavPathController nav = new NavPathController(p);

        BumpNavigator robot = new BumpNavigator(nav);
        robot.pilot = p;

        // TODO: For version 1.0. 
        // This is overly complex to make NavPathController and a FeatureListener do something simple like
        // a bumper car. Might want to look at ways to change API so we can simplify this type of sample. Coding
        // this type of example is not very intuitive with the current API.
        // Repeatedly drive to random points:
        while (!Button.ESCAPE.isPressed()) {
            System.out.println("Target: ");
            double x_targ = Math.random() * AREA_WIDTH;
            double y_targ = Math.random() * AREA_LENGTH;
            System.out.println("X: " + (int) x_targ);
            System.out.println("Y: " + (int) y_targ);
            System.out.println("Press ENTER key");
            Button.ENTER.waitForPressAndRelease();

            // When an obstacle is encountered and stop() is called, the method goTo() returns 
            // even though it didn't reach the target waypoint... 
            robot.goTo(x_targ, y_targ);

            // ...therefore this delay is needed.
            while (p.isMoving()) {
                Thread.sleep(500);
            }

            // Output arrival:
            Pose curPose = nav.getPoseProvider().getPose();
            System.out.println("Arrived: " + (int) curPose.getX() + ", " + (int) curPose.getY());
            Sound.beepSequenceUp();
        }
    }

    /**
     * causes the robot to back up, turn away from the obstacle. returns when
     * obstacle is cleared or if an obstacle is detected while traveling.
     */
    public void featureDetected(Feature feature, FeatureDetector detector) {
        detector.enableDetection(false);
        Sound.beepSequence();
        int side = 0;

        // Identify which bumper was pressed:
        if (feature.getRangeReading().getAngle() < 0) {
            side = LEFT_SIDE;
        } else {
            side = RIGHT_SIDE;
        }

        // Perform a movement to avoid the obstacle.
        pilot.travel(-5 - rand.nextInt(5));
        int angle = 60 + rand.nextInt(60);
        pilot.rotate(-side * angle);
        detector.enableDetection(true);
        pilot.travel(10 + rand.nextInt(60));
        nav.goTo(target);
    }
}
