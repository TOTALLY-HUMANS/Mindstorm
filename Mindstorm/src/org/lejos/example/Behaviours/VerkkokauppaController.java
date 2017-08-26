/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lejos.example.Behaviours;

import java.io.DataInputStream;
import java.io.IOException;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import org.lejos.example.AutomatedControl;

/**
 *
 * @author pulli
 */
public class VerkkokauppaController implements AutomatedControl {

    private float maxSpeed = Motor.A.getMaxSpeed();
    MovementController mc = new MovementController();
    DataInputStream dis;
    int blacksFound = 0;

    public void start(DataInputStream dis) throws InterruptedException {
        this.dis = dis;
        try {
            TouchSensor touch = new TouchSensor(SensorPort.S4);
            LightSensor light = new LightSensor(SensorPort.S1);
            
            mc.moveBackward(maxSpeed, 100);
            long startTime = System.currentTimeMillis();
            boolean stop = false;
            startTime = System.currentTimeMillis();
            // from ramp to last spin
            while (!stop) {
                stop = shouldStop();
                if (light.readValue() < 40) {
                    blacksFound++;
                }
                
                if (touch.isPressed()) {
                    mc.stop();
                    mc.moveForward(maxSpeed * 0.75f, 10);
                    mc.turnRight(maxSpeed * 0.5f, 33);
                    mc.moveBackward(maxSpeed * 0.75f, 20);
                    break;
                }
            }
            System.out.println("BF: " + blacksFound);
            
        } catch (InterruptedException ex) {
            System.out.println("Command failed");
        }
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
