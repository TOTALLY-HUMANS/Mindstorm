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
import lejos.nxt.Sound;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import org.lejos.example.AutomatedControl;

/**
 *
 * @author pulli
 */
/*
    Moves through rotating plates, using ultrasonic senor to detect when to change plate.
*/
public class VerkkokauppaController implements AutomatedControl {

    private float maxSpeed = Motor.A.getMaxSpeed();
    MovementController mc = new MovementController();
    DataInputStream dis;
    int blacksFound = 0;

    public void start(DataInputStream dis) throws InterruptedException {
        this.dis = dis;
        try {
            LightSensor light = new LightSensor(SensorPort.S1);
            UltrasonicSensor sonic = new UltrasonicSensor(SensorPort.S3);
            
            boolean stop = false;
            int count = 0;
            
            
            mc.moveBackward(maxSpeed, 40);
            mc.turnRight(maxSpeed, 90);
            
            Sound.beep();
            while (!stop && sonic.getDistance() > 40) {
                stop = shouldStop();
                System.out.println("LS: " + light.getLightValue());
                //wait
            }
            Sound.beep();
            mc.turnLeft(maxSpeed, 29);
            
            mc.moveForward(maxSpeed, 70);
            
            mc.turnLeft(maxSpeed, 10);
            mc.moveForward(maxSpeed, 50);
            
            /*
            while (!stop && sonic.getDistance() > 30) {
                stop = shouldStop();
                System.out.println("LS: " + light.getLightValue());
                //wait
            }
            
            mc.turnLeft(maxSpeed, 35);
            mc.moveForward(maxSpeed, 40);
            */
            
            //mc.moveForwardContinuously(maxSpeed);
            /*
            // from ramp to last spin
            while (!stop) {
                stop = shouldStop();
                 System.out.println("LS: " + light.getLightValue());
                if (light.readValue() < 55 && light.readValue() > 45) {
                    mc.stop();
                    mc.moveForward(maxSpeed, 8);
                    while (!stop && sonic.getDistance() > 35) {
                        stop = shouldStop();
                        System.out.println("LS: " + light.getLightValue());
                        //wait
                    }
                    if (stop) {
                        break;
                    }
                    
                    if (count == 1) {
                        mc.turnLeft(maxSpeed, 50);
                        mc.moveForward(maxSpeed, 50);
                    } else {
                        mc.turnLeft(maxSpeed, 40);
                        mc.moveForwardContinuously(maxSpeed);
                    }
                    
                    count++;
                } 
                
                if (count == 2) {
                    break;
                }
            }*/
            
            
            //System.out.println("BF: " + blacksFound);
            
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
