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
    
    public static void start() throws InterruptedException {
        advance();
    }
    
    private static void advance() throws InterruptedException {
        System.out.println("Forward!");
        MovementController.moveBackward();
        if (lineFound()) {
            advance();
        } else {
            searchForLine();
        }
    }
    
    private static boolean lineFound() {
        System.out.println("LV " + cs.getLightValue());
        if (cs.getLightValue() < colorThreshold) {
            return true;
        }
        return false;
    }
    
    private static void searchForLine() throws InterruptedException {
        // Left search
        for (int i = 0; i < lineSearchSteps; i++) {
            MovementController.turnLeft();
            if (lineFound()) advance();
        }
        // Return to middle
        for (int i = 0; i < lineSearchSteps; i++) {
            MovementController.turnRight();
            if (lineFound()) advance();
        }
        // Right search
        for (int i = 0; i < lineSearchSteps * 2; i++) {
            MovementController.turnRight();
            if (lineFound()) advance();
        }
        searchForLine();
    }
    
}
