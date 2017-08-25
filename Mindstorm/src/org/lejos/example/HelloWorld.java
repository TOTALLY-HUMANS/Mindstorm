package org.lejos.example;

import lejos.nxt.*;
import lejos.nxt.comm.*;
import java.io.*;
import org.lejos.example.Behaviours.LineFollower;
import org.lejos.example.Behaviours.ManualControl;

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
      ManualControl mc = new ManualControl();
      mc.listen();
      //LineFollower.start();
  }
}