package org.lejos.example.Behaviours;

import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;

public class MovementController {
    
    static int movementSpeed = 10;
    static int turnSpeed = 25;
    
    static int waitBetweenActions = 300;
    
    DifferentialPilot pilot = new DifferentialPilot(5.6f,10f,Motor.A, Motor.B);
    
    public MovementController() {
        pilot.setTravelSpeed(movementSpeed);
        pilot.setRotateSpeed(turnSpeed);
    }
    
    public void moveForward() throws InterruptedException {
        pilot.travel(2, false);
    }
    
    public void moveBackward() throws InterruptedException {
        pilot.travel(-2);
    }
    
    public void turnRight() throws InterruptedException {
        pilot.rotate(90/8, false);
    }
    
    public void turnLeft() throws InterruptedException {
        pilot.rotate(-90/8, false);
    }
    
    public void grab() throws InterruptedException {
        Motor.C.setSpeed(40);
        Motor.C.forward();
        Thread.sleep(1500);
    }
    
    public void ungrab() throws InterruptedException {
        Motor.C.setSpeed(40);
        Motor.C.backward();
        Thread.sleep(3000);
    }
    
}
