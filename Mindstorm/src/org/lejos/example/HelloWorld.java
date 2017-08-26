package org.lejos.example;

import lejos.nxt.*;
import lejos.nxt.comm.*;
import java.io.*;
import org.lejos.example.Behaviours.LineFollower;
import org.lejos.example.Behaviours.ManualControl;
import org.lejos.example.Behaviours.MovementController;

/**
 * Receive data from another NXT, a PC, a phone, 
 * or another bluetooth device.
 * 
 * Waits for a connection, receives an int and returns
 * its negative as a reply, 100 times, and then closes
 * the connection, and waits for a new one.
 * 
 * @author Lawrie Griffiths
 *
 */
public class HelloWorld {

  public static void main(String [] args)  throws Exception 
  {
      //MovementController m = new MovementController();
      //m.grab();
      //m.moveBackward();
      //m.moveBackward();
      //m.moveBackward();
      //m.ungrab();
      //m.moveForward();
      
      ManualControl mc = new ManualControl();
      mc.listen();
      //LineFollower lf = new LineFollower();
      //lf.start();
  }
}