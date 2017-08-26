/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lejos.example;

import java.io.DataInputStream;

/**
 *
 * @author pulli
 */
public interface AutomatedControl {
    public void start(DataInputStream dis) throws InterruptedException;
    public void stop();
}
