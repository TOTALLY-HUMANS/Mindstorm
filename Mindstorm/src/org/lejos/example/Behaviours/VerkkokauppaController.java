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
            long startTime = System.currentTimeMillis();
            boolean stop = false;
            startTime = System.currentTimeMillis();
            // from ramp to last spin
            while (!stop) {
                stop = shouldStop();
                if (System.currentTimeMillis() - startTime > 5000) {
                    mc.stop();
                    break;
                }
            }
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
