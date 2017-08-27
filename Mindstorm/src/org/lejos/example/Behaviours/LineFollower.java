package org.lejos.example.Behaviours;

import java.io.DataInputStream;
import java.io.IOException;
import lejos.nxt.LightSensor;
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
    int rotSpeed = 80;

    MovementController mc = new MovementController();
    DataInputStream dis;

    boolean lastFoundLeft = false;

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
        mc.moveForward();
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
        searchForLine(1);
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
        // Left search
        if (lastFoundLeft) {
            lineFound = searchLeft(steps) || searchRight(steps) || searchRight(steps + 1) || searchLeft(steps + 1);
        } else {
            lineFound = searchRight(steps) || searchLeft(steps) || searchLeft(steps + 1) || searchRight(steps + 1);
        }
        if (!lineFound) {
            searchForLine(attempt + 2);
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
