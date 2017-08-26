package org.lejos.example.Behaviours;

import java.io.DataInputStream;
import java.io.IOException;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import org.lejos.example.AutomatedControl;

public class LineFollower implements AutomatedControl {

    static LightSensor cs = new LightSensor(SensorPort.S1);
    static int colorThreshold = 40;
    static int lineSearchSteps = 8;
    static boolean stopping = false;

    MovementController mc = new MovementController();
    DataInputStream dis;

    boolean lastFoundLeft = false;

    public LineFollower() {

    }

    public void start(DataInputStream dis) throws InterruptedException {
        this.dis = dis;
        stopping = false;
        while (!shouldStop()) {
            if (!advance()) {
                searchForLine();
            }
        }
    }

    private boolean advance() throws InterruptedException {
        //System.out.println("Forward!");
        mc.moveForward();
        return lineFound();
    }

    private boolean lineFound() {
        System.out.println("LV " + cs.getLightValue());
        return cs.getLightValue() < colorThreshold;
    }

    private void searchForLine() throws InterruptedException {
        searchForLine(1);
    }

    private void searchForLine(int attempt) throws InterruptedException {
        if (shouldStop()) {
            return;
        }

        boolean lineFound = false;
        int steps = attempt;
        // Left search
        if (lastFoundLeft) {
            lineFound = searchLeft(steps) || searchRight(steps) || searchRight(steps);
        } else {
            lineFound = searchRight(steps) || searchLeft(steps) || searchLeft(steps);
        }
        if (!lineFound) {
            searchForLine(attempt + 1);
        }
    }

    private boolean searchLeft(int steps) throws InterruptedException {
        if (shouldStop()) {
            return true;
        }

        for (int i = 0; i < steps; i++) {
            mc.turnLeft();
            if (lineFound()) {
                lastFoundLeft = true;
                return true;
            }
        }
        return false;
    }

    private boolean searchRight(int steps) throws InterruptedException {
        if (shouldStop()) {
            return true;
        }

        // Right search
        for (int i = 0; i < steps; i++) {
            mc.turnRight();
            if (lineFound()) {
                lastFoundLeft = false;
                return true;
            }
        }
        return false;
    }

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
