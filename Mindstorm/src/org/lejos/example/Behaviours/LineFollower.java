package org.lejos.example.Behaviours;

import lejos.nxt.ColorSensor;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorConstants;
import lejos.nxt.SensorPort;

public class LineFollower {

    static LightSensor cs = new LightSensor(SensorPort.S1);
    static int colorThreshold = 24;
    static int lineSearchSteps = 4;
    
    MovementController mc = new MovementController();
    
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
        if (cs.getLightValue() < colorThreshold) {
            return true;
        }
        return false;
    }
    
    private void searchForLine() throws InterruptedException {
        // Left search
        for (int i = 0; i < lineSearchSteps; i++) {
            mc.turnLeft();
            if (lineFound()) advance();
        }
        // Return to middle
        for (int i = 0; i < lineSearchSteps; i++) {
            mc.turnRight();
            if (lineFound()) advance();
        }
        // Right search
        for (int i = 0; i < lineSearchSteps * 2; i++) {
            mc.turnRight();
            if (lineFound()) advance();
        }
        searchForLine();
    }
    
}
