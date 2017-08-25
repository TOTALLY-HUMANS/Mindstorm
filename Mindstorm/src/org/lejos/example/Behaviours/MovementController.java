package org.lejos.example.Behaviours;

import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;

public class MovementController {
    
    static int movementSpeed = 200;
    static int turnSpeed = 100;
    
    static int waitBetweenActions = 300;
    
    DifferentialPilot pilot = new DifferentialPilot(5.6f,10f,Motor.A, Motor.B);
    
    public MovementController() {
        pilot.setTravelSpeed(movementSpeed);
        pilot.setRotateSpeed(turnSpeed);
    }
    
    public void moveForward() throws InterruptedException {
        pilot.travel(5.0, false);
    }
    
    public void moveBackward() throws InterruptedException {
        pilot.travel(-5.0);
    }
    
    public void turnRight() throws InterruptedException {
        pilot.rotate(22.5, false);
    }
    
    public void turnLeft() throws InterruptedException {
        pilot.rotate(-22.5, false);
    }
    
}
