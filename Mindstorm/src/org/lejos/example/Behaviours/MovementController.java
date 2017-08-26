package org.lejos.example.Behaviours;

import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;

public class MovementController {

    static int movementSpeed = 10;
    static int turnSpeed = 25;

    DifferentialPilot pilot = new DifferentialPilot(5.6f, 10f, Motor.A, Motor.B);

    public MovementController() {
        pilot.setTravelSpeed(movementSpeed);
        pilot.setRotateSpeed(turnSpeed);
    }

    public void moveForward() throws InterruptedException {
        moveForward(movementSpeed);
    }
    public void moveForward(int speed) throws InterruptedException {
        pilot.setTravelSpeed(speed);
        pilot.travel(2, false);
    }
    
    public void moveForwardContinuously() throws InterruptedException {
        moveForwardContinuously(movementSpeed);
    }
    public void moveForwardContinuously(int speed) throws InterruptedException {
        pilot.setTravelSpeed(speed);
        pilot.forward();
    }

    public void moveBackward() throws InterruptedException {
        moveBackward(movementSpeed);
    }
    public void moveBackward(int speed) throws InterruptedException {
        pilot.setTravelSpeed(speed);
        pilot.travel(-2);
    }

    public void moveBackwardContinuously() throws InterruptedException {
        moveBackwardContinuously(movementSpeed);
    }
    public void moveBackwardContinuously(int speed) throws InterruptedException {
        pilot.setTravelSpeed(speed);
        pilot.backward();
    }

    public void turnRight() throws InterruptedException {
        turnRight(turnSpeed);
    }
    public void turnRight(int speed) throws InterruptedException {
        pilot.setRotateSpeed(speed);
        pilot.rotate(90 / 8, false);
    }

    public void turnRightContinuously() throws InterruptedException {
        turnRightContinuously(turnSpeed);
    }
    public void turnRightContinuously(int speed) throws InterruptedException {
        pilot.setRotateSpeed(speed);
        pilot.rotateRight();
    }

    public void turnLeft() throws InterruptedException {
        turnLeft(turnSpeed);
    }
    public void turnLeft(int speed) throws InterruptedException {
        pilot.setRotateSpeed(speed);
        pilot.rotate(-90 / 8, false);
    }

    public void turnLeftContinuously() throws InterruptedException {
        turnLeftContinuously(turnSpeed);
    }
    public void turnLeftContinuously(int speed) throws InterruptedException {
        pilot.setRotateSpeed(speed);
        pilot.rotateLeft();
    }

    public void stop() throws InterruptedException {
        pilot.stop();
    }

    public void grab() throws InterruptedException {
        Motor.C.setSpeed(40);
        Motor.C.forward();
    }

    public void ungrab() throws InterruptedException {
        Motor.C.setSpeed(40);
        Motor.C.backward();
    }
    
    public void release() {
        Motor.C.stop();
    }
}
