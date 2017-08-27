package org.lejos.example;

import org.lejos.example.Behaviours.ManualControl;

/**
 * Main class for the program, named badly because based on a copy-pasted
 * example
 */
public class HelloWorld {

    /**
     * Start listening to bluetooth commands
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        ManualControl mc = new ManualControl();
        mc.listen();
    }
}
