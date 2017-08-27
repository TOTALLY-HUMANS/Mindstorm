package org.lejos.example.Behaviours;

import java.io.DataInputStream;
import java.io.IOException;
import lejos.nxt.Motor;
import org.lejos.example.AutomatedControl;

public class BumpNavigator implements AutomatedControl {

    DataInputStream dis;
    MovementController mc = new MovementController();

    static float movementSpeed = Motor.A.getMaxSpeed() * 0.40f;
    static float turnSpeed = 20;
    
    public BumpNavigator() {
    }

    public void start(DataInputStream dis) throws InterruptedException {
        this.dis = dis;
        mc.moveForward(movementSpeed, 20);
        if (shouldStop()) return;
        mc.turnLeft(turnSpeed, 30);
        if (shouldStop()) return;
        mc.moveForward(movementSpeed, 35);
        if (shouldStop()) return;
        mc.turnRight(turnSpeed, 42);
        if (shouldStop()) return;
        mc.moveForward(movementSpeed, 27);
        if (shouldStop()) return;
        mc.turnRight(turnSpeed, 40);
        if (shouldStop()) return;
        mc.moveForward(movementSpeed, 37);
        if (shouldStop()) return;
        // HYVISSÄ ASEMISSA
        mc.turnLeft(turnSpeed, 35);
        if (shouldStop()) return;
        mc.moveForward(movementSpeed, 17);
        if (shouldStop()) return;
        // Kulmaa
        mc.turnLeft(turnSpeed, 30);
        if (shouldStop()) return;
        mc.moveForward(movementSpeed, 20);
        if (shouldStop()) return;
        mc.turnRight(turnSpeed, 5);
        if (shouldStop()) return;
        mc.moveForward(movementSpeed, 5);
        if (shouldStop()) return;
        mc.turnRight(turnSpeed, 50);
        if (shouldStop()) return;
        mc.moveForward(movementSpeed, 43);
        // MAALISSA
        if (shouldStop()) return;
        mc.turnLeft(turnSpeed, 30);
        if (shouldStop()) return;
        mc.moveForward(movementSpeed, 20);
    }

    public boolean shouldStop() {
        char n = 1;
        try {
            if (dis.available() != 0) {
                n = dis.readChar();
            }
        } catch (IOException ex) {
            // error
        }
        if (n == '0') {
            return true;
        }
        return false;
    }
}
