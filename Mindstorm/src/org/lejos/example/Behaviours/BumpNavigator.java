package org.lejos.example.Behaviours;

import java.io.DataInputStream;
import org.lejos.example.AutomatedControl;

public class BumpNavigator implements AutomatedControl {
    DataInputStream dis;

    public BumpNavigator() {
    }

    public void start(DataInputStream dis) throws InterruptedException {
        this.dis = dis;
    }

    public boolean shouldStop() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}