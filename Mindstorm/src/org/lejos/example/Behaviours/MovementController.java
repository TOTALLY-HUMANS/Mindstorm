package org.lejos.example.Behaviours;

import lejos.nxt.Motor;

public class MovementController {
    
    static int movementSpeed = 200;
    static int turnSpeed = 100;
    
    static int waitBetweenActions = 300;
    
    public static void moveForward() throws InterruptedException {
        Motor.B.setSpeed(movementSpeed);
        Motor.A.setSpeed(movementSpeed);
        Motor.B.forward();
        Motor.A.forward();
        Thread.sleep(waitBetweenActions);
        Motor.B.stop();
        Motor.A.stop();
    }
    
    public static void moveBackward() throws InterruptedException {
        Motor.B.setSpeed(movementSpeed);
        Motor.A.setSpeed(movementSpeed);
        Motor.B.backward();
        Motor.A.backward();
        Thread.sleep(waitBetweenActions);
        Motor.B.stop();
        Motor.A.stop();
    }
    
    public static void turnRight() throws InterruptedException {
        Motor.B.setSpeed(turnSpeed);
        Motor.B.backward();
        Motor.A.setSpeed(turnSpeed);
        Motor.A.forward();
        Thread.sleep(waitBetweenActions);
        Motor.B.stop();
        Motor.A.stop();
    }
    
    public static void turnLeft() throws InterruptedException {
        Motor.B.setSpeed(turnSpeed);
        Motor.B.forward();
        Motor.C.setSpeed(turnSpeed);
        Motor.C.backward();
        Thread.sleep(waitBetweenActions);
        Motor.B.stop();
        Motor.C.stop();
    }
    
}
