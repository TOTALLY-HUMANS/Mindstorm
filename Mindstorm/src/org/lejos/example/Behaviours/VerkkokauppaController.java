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
import lejos.nxt.UltrasonicSensor;
import org.lejos.example.AutomatedControl;

/**
 *
 * @author pulli
 */
public class VerkkokauppaController implements AutomatedControl {

    MovementController mc = new MovementController();
    DataInputStream dis;
    
    public void start(DataInputStream dis) throws InterruptedException {
        this.dis = dis;
        try {
            mc.moveBackwardContinuously(Motor.A.getMaxSpeed());
            LightSensor ls = new LightSensor(SensorPort.S1);
            long startTime = 0;
            boolean stop = false;
            
            // from ramp to last spin
            while (!stop) {
                stop = shouldStop();
                float lightValue = ls.getLightValue();
                if (lightValue < 50) {
                    if (startTime == 0) {
                        startTime = System.currentTimeMillis();
                    } else {
                        if (System.currentTimeMillis() - startTime > 3000) {
                            mc.stop();
                            break;
                        }
                    }
                }
            }
            
            UltrasonicSensor us = new UltrasonicSensor(SensorPort.S4);
            while (!stop) {
                Integer distance = us.getDistance();
                if (distance != null && us.getDistance() > 100) {
                    Thread.sleep(2000);
                    mc.moveForwardContinuously(Motor.A.getMaxSpeed());
                    break;
                }
            }
            mc.stop();
            
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
