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
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

/**
 *
 * @author talon
 */
public class ManualControl {
    
    public void listen() throws IOException, InterruptedException {
    String connected = "Connected";
    String waiting = "Waiting...";
    String closing = "Closing...";
    
    MovementController mc = new MovementController();
    
    while (true)
    {
      LCD.drawString(waiting,0,0);
      LCD.refresh();

          BTConnection btc = Bluetooth.waitForConnection();
          
      LCD.clear();
      LCD.drawString(connected,0,0);
      LCD.refresh();  

      DataInputStream dis = btc.openDataInputStream();
      DataOutputStream dos = btc.openDataOutputStream();
      
      while (true) {
        int n = dis.readChar();
        switch (n) {
            case 'w':
                mc.moveForward();
                break;
            case 's':
                mc.moveBackward();
                break;
            case 'a':
                mc.turnLeft();
                break;
            case 'd':
                mc.turnRight();
        }
        dos.writeInt(1);
        dos.flush();
        
        if (1 == 2) break;
      }
      
      dis.close();
      dos.close();
      Thread.sleep(100); // wait for data to drain
      LCD.clear();
      LCD.drawString(closing,0,0);
      LCD.refresh();
      btc.close();
      LCD.clear();
    }
    }
}
