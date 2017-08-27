package org.lejos.example.Behaviours;

import java.io.DataInputStream;
import java.io.IOException;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import org.lejos.example.AutomatedControl;

/**
 * Follows a line on the ground based on data from the light sensor
 */
public class LineFollower implements AutomatedControl {

    static LightSensor cs = new LightSensor(SensorPort.S1);
    static int colorThreshold = 40;
    static boolean stopping = false;

    int smallRot = 5;
    int bigRot = 60;
    int rotSpeed = 50;

    MovementController mc = new MovementController();
    DataInputStream dis;

    boolean lastFoundLeft = false;
    int lastAttemps = 0;
    
    static float movementSpeed = Motor.A.getMaxSpeed() * 0.60f;

    public LineFollower() {

    }

    /**
     * Start movement
     *
     * @param dis
     * @throws InterruptedException
     */
    public void start(DataInputStream dis) throws InterruptedException {
        this.dis = dis;
        stopping = false;
        while (!shouldStop()) {
            if (!advance()) {
                searchForLine();
            } else {
                lastAttemps = 0;
            }
        }
    }

    /**
     * Move forward
     *
     * @return
     * @throws InterruptedException
     */
    private boolean advance() throws InterruptedException {
        //System.out.println("Forward!");
        mc.moveForward(movementSpeed);
        return lineFound();
    }

    /**
     * Checks if the line on the ground is seen
     *
     * @return
     * @throws InterruptedException
     */
    private boolean lineFound() throws InterruptedException {
        System.out.println("LV " + cs.getLightValue());
        boolean found = cs.getLightValue() < colorThreshold;
        return found;
    }

    /**
     * Start looking for the line if it is lost
     *
     * @throws InterruptedException
     */
    private void searchForLine() throws InterruptedException {
        int attemps = lastAttemps >= 8 ? 7 : 2;
        searchForLine(attemps);
    }

    /**
     * Start looking for the line if it is lost
     *
     * @param attempt
     * @throws InterruptedException
     */
    private void searchForLine(int attempt) throws InterruptedException {
        if (shouldStop()) {
            return;
        }

        boolean lineFound = false;
        int steps = attempt;
        int plus = 0;
        if (steps == 2) plus = 6;
        // Left search
        if (lastFoundLeft) {
            lineFound = searchLeft(steps) || searchRight(steps) || searchRight(steps + plus) || searchLeft(steps + plus);
        } else {
            lineFound = searchRight(steps) || searchLeft(steps) || searchLeft(steps + plus) || searchRight(steps + plus);
        }
        if (!lineFound) {
            searchForLine(attempt + 1 + plus);
        }
    }

    /**
     * Look for the line on the left side
     *
     * @param steps
     * @return
     * @throws InterruptedException
     */
    private boolean searchLeft(int steps) throws InterruptedException {
        if (shouldStop()) {
            return true;
        }

        for (int i = 0; i < steps; i++) {
            mc.turnLeft(rotSpeed, smallRot);
            if (lineFound()) {
                lastFoundLeft = true;
                lastAttemps = steps;
                return true;
            }
        }

        return false;
    }

    /**
     * Look for the line on the right side
     *
     * @param steps
     * @return
     * @throws InterruptedException
     */
    private boolean searchRight(int steps) throws InterruptedException {
        if (shouldStop()) {
            return true;
        }
        // Right search
        for (int i = 0; i < steps; i++) {
            mc.turnRight(rotSpeed, smallRot);
            if (lineFound()) {
                lastFoundLeft = false;
                if (lastAttemps < 5) {
                    lastAttemps = steps;
                } else {
                    lastAttemps = 0;
                }
                return true;
            }
        }

        return false;
    }

    /**
     * Check if we have received a command to stop this behaviour
     *
     * @return
     */
    public boolean shouldStop() {
        if (stopping) {
            return true;
        }

        char n = 1;
        try {
            if (dis.available() != 0) {
                n = dis.readChar();
            }
        } catch (IOException ex) {
            // error
        }
        if (n == '0') {
            stopping = true;
            return true;
        }
        return false;
    }

}
