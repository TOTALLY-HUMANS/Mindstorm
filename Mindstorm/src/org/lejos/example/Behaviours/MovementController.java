package org.lejos.example.Behaviours;

import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;

public class MovementController {

    static float movementSpeed = Motor.A.getMaxSpeed() * 0.40f;
    static float turnSpeed = 20;

    DifferentialPilot pilot = new DifferentialPilot(3f, 20f, Motor.A, Motor.B);

    public MovementController() {
        pilot.setTravelSpeed(movementSpeed);
        pilot.setRotateSpeed(turnSpeed);
    }

    public void moveForward() throws InterruptedException {
        moveForward(movementSpeed);
    }
    public void moveForward(float speed) throws InterruptedException {
        pilot.setTravelSpeed(speed);
        pilot.travel(2, false);
    }
    
    public void moveForward(float speed, float distance) throws InterruptedException {
        pilot.setTravelSpeed(speed);
        pilot.travel(distance);
    }
    
    public void moveForwardContinuously() throws InterruptedException {
        moveForwardContinuously(movementSpeed);
    }
    public void moveForwardContinuously(float speed) throws InterruptedException {
        pilot.setTravelSpeed(speed);
        pilot.forward();
    }

    public void moveBackward() throws InterruptedException {
        moveBackward(movementSpeed);
    }
    public void moveBackward(float speed) throws InterruptedException {
        pilot.setTravelSpeed(speed);
        pilot.travel(-2);
    }
    
    public void moveBackward(float speed, float distance) throws InterruptedException {
        pilot.setTravelSpeed(speed);
        pilot.travel(-1.0 * distance);
    }

    public void moveBackwardContinuously() throws InterruptedException {
        moveBackwardContinuously(movementSpeed);
    }
    public void moveBackwardContinuously(float speed) throws InterruptedException {
        pilot.setTravelSpeed(speed);
        pilot.backward();
    }

    public void turnRight() throws InterruptedException {
        turnRight(turnSpeed);
    }
    public void turnRight(float speed) throws InterruptedException {
        turnRight(speed, 90 / 16);
    }
    
    public void turnRight(float speed, float angle) throws InterruptedException {
        pilot.setRotateSpeed(speed);
        pilot.rotate(angle, false);
    }
    
    public void turnRight(float speed, float angle) throws InterruptedException {
        pilot.setRotateSpeed(speed);
        pilot.rotate(angle, false);
    }

    public void turnRightContinuously() throws InterruptedException {
        turnRightContinuously(turnSpeed);
    }
    public void turnRightContinuously(float speed) throws InterruptedException {
        pilot.setRotateSpeed(speed);
        pilot.rotateRight();
    }

    public void turnLeft() throws InterruptedException {
        turnLeft(turnSpeed);
    }
    
    public void turnLeft(float speed) throws InterruptedException {
        turnLeft(speed, 90 / 16);
    }
    
    public void turnLeft(float speed, float angle) throws InterruptedException {
        pilot.setRotateSpeed(speed);
        pilot.rotate(-angle, false);
    }
    
    public void turnLeft(float speed, float angle) throws InterruptedException {
        pilot.setRotateSpeed(speed);
        pilot.rotate(-1.0 * angle, false);
    }

    public void turnLeftContinuously() throws InterruptedException {
        turnLeftContinuously(turnSpeed);
    }
    public void turnLeftContinuously(float speed) throws InterruptedException {
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
