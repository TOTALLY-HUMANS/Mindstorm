package org.lejos.example.Behaviours;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import org.lejos.example.AutomatedControl;

public class LineFollower implements AutomatedControl {

    static LightSensor cs = new LightSensor(SensorPort.S1);
    static int colorThreshold = 50;
    static int lineSearchSteps = 8;

    MovementController mc = new MovementController();
    DataInputStream dis;
    
    boolean lastFoundLeft = false;
    
    public LineFollower() {
        
    }

    public void start(DataInputStream dis) throws InterruptedException {
        this.dis = dis;
        advance();
    }

    private void advance() throws InterruptedException {
        int n = 1;
        try {
            n = dis.readChar();
        } catch (IOException ex) {
            Logger.getLogger(LineFollower.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (n == '0') {
            stop();
            return;
        }
        
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
    
    public void stop() {
        System.out.println("Switched to manual control!");
    }

}
