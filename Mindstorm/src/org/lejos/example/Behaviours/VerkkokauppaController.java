/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lejos.example.Behaviours;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
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
        try {
            mc.moveBackwardContinuously(Motor.A.getMaxSpeed());
            LightSensor ls = new LightSensor(SensorPort.S1);
            long startTime = 0;
            boolean stop = false;
            
            // from ramp to last spin
            while (!stop) {
                stop = checkStop();
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
                if (us.getDistance() > 100) {
                    TimeUnit.SECONDS.sleep(2);
                    mc.moveBackwardContinuously(Motor.A.getMaxSpeed());
                    break;
                }
            }
            mc.stop();
            
        } catch (InterruptedException ex) {
            System.out.println("Command failed");
        }
    }

    public boolean checkStop() {
        char n = 1;
        try {
            n = dis.readChar();
        } catch (IOException ex) {
            // error
        }
        if (n == '0') {
            return true;
        }
        return false;
    }
    
}
