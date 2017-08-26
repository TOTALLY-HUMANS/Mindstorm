package org.lejos.example.Behaviours;

import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;

public class LineFollower {

    static LightSensor cs = new LightSensor(SensorPort.S1);
    static int colorThreshold = 50;
    static int lineSearchSteps = 8;

    MovementController mc = new MovementController();

    boolean lastFoundLeft = false;

    public void start() throws InterruptedException {
        advance();
    }

    private void advance() throws InterruptedException {
        System.out.println("Forward!");
        mc.moveForward();
        if (lineFound()) {
            advance();
        } else {
            searchForLine();
        }
    }

    private boolean lineFound() {
        System.out.println("LV " + cs.getLightValue());
        return cs.getLightValue() < colorThreshold;
    }

    private void searchForLine() throws InterruptedException {
        boolean lineFound = false;
        // Left search
        if (lastFoundLeft) {
            lineFound = searchLeft() || searchRight() || searchRight();
        } else {
            lineFound = searchRight() || searchLeft() || searchLeft();
        }
        if (lineFound) {
            advance();
        } else {
            searchForLine();
        }
    }

    private boolean searchLeft() throws InterruptedException {
        for (int i = 0; i < lineSearchSteps; i++) {
            mc.turnLeft();
            if (lineFound()) {
                lastFoundLeft = true;
                return true;
            }
        }
        return false;
    }

    private boolean searchRight() throws InterruptedException {
        // Right search
        for (int i = 0; i < lineSearchSteps; i++) {
            mc.turnRight();
            if (lineFound()) {
                lastFoundLeft = false;
                return true;
            }
        }
        return false;
    }

}
