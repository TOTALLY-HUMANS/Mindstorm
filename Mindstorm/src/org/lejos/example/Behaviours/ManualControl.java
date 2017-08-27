/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lejos.example.Behaviours;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.Sound;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

/**
 * Controls the robot with keyboard input via bluetooth
 */
public class ManualControl {

    LineFollower lf;
    VerkkokauppaController vk;
    BumpNavigator bn;

    public ManualControl() {
        this.lf = new LineFollower();
        this.vk = new VerkkokauppaController();
        this.bn = new BumpNavigator();
    }

    /**
     * Start listening to bluetooth commands
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public void listen() throws IOException, InterruptedException {
        String connected = "Connected";
        String waiting = "Waiting...";
        String closing = "Closing...";
        boolean stop = false;

        MovementController mc = new MovementController();

        while (true) {
            LCD.drawString(waiting, 0, 0);
            LCD.refresh();

            BTConnection btc = Bluetooth.waitForConnection();

            LCD.clear();
            LCD.drawString(connected, 0, 0);

            DataInputStream dis = btc.openDataInputStream();
            DataOutputStream dos = btc.openDataOutputStream();

            /**
             * Read characters and start behaviours based on them
             */
            while (true) {
                int n = dis.readChar();

                switch (n) {
                    case 'w':
                        mc.moveForwardContinuously();
                        break;
                    case 's':
                        mc.moveBackwardContinuously();
                        break;
                    case 'd':
                        mc.turnLeftContinuously();
                        break;
                    case 'a':
                        mc.turnRightContinuously();
                        break;
                    case 'i':
                        mc.moveForwardContinuously(Motor.A.getMaxSpeed() * 0.80f);
                        break;
                    case 'k':
                        mc.moveBackwardContinuously(Motor.A.getMaxSpeed() * 0.80f);
                        break;
                    case 'l':
                        mc.turnLeftContinuously(Motor.A.getMaxSpeed() * 0.350f);
                        break;
                    case 'j':
                        mc.turnRightContinuously(Motor.A.getMaxSpeed() * 0.350f);
                        break;
                    case 'r':
                        mc.grab();
                        break;
                    case 'e':
                        mc.ungrab();
                        break;
                    case '1':
                        Sound.beep();
                        bn.start(dis);
                        Sound.beep();
                        break;
                    case '2':
                        Sound.beep();
                        vk.start(dis);
                        Sound.beep();
                        break;
                    case '3':
                        Sound.beep();
                        lf.start(dis);
                        Sound.beep();
                        break;
                    case '0':
                        mc.stop();
                        break;
                    case 'n':
                        mc.stop();
                        break;
                    case 'x':
                        mc.release();
                        break;
                    case 'q':
                        stop = true;
                        break;
                }

                if (stop) {
                    break;
                }
            }

            dis.close();
            dos.close();
            Thread.sleep(100); // wait for data to drain
            LCD.clear();
            LCD.drawString(closing, 0, 0);
            LCD.refresh();
            btc.close();
            LCD.clear();
        }
    }
}
