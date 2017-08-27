package org.lejos.example;

import java.io.DataInputStream;

/**
 * Interface for behaviours launchable with keyboard input via bluetooth
 *
 * @author pulli
 */
public interface AutomatedControl {

    public void start(DataInputStream dis) throws InterruptedException;

    public boolean shouldStop();
}
