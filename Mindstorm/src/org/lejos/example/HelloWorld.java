package org.lejos.example;

import lejos.nxt.*;

/**
 * $Id: HelloWorld.java 1587 2008-05-02 17:19:41Z lgriffiths $
 *
 * @author Lawrie Griffiths
 *
 */
public class HelloWorld {

    public static void main(String[] aArg) throws Exception {
        LCD.drawString("" + Motor.A.getMaxSpeed(), 3, 4);
        Thread.sleep(2000);

        Motor.A.setSpeed(Motor.A.getMaxSpeed());
        Motor.A.forward();

        Motor.B.setSpeed(Motor.A.getMaxSpeed());
        Motor.B.forward();

        Motor.C.setSpeed(Motor.A.getMaxSpeed());
        Motor.C.forward();

        Thread.sleep(5000);

        Motor.A.stop();
        Motor.B.stop();
        Motor.C.stop();

        UltrasonicSensor sonic = new UltrasonicSensor(SensorPort.S3);
        for (int i = 0; i < 5; i++) {
            LCD.clear();
            LCD.drawString(sonic.getProductID(), 0, 1);
            LCD.drawString(sonic.getVersion(), 0, 2);
            LCD.drawInt(sonic.getDistance(), 0, 3);
            Thread.sleep(1000);
        }

        TouchSensor ts = new TouchSensor(SensorPort.S2);
        for (int i = 0; i < 5; i++) {
            LCD.drawString("" + ts.isPressed(), 3, 4);
            Thread.sleep(1000);
        }

        LightSensor light = new LightSensor(SensorPort.S1);
        while (true) {
            LCD.clear();
            LCD.drawInt(light.getLightValue(), 4, 0, 0);
            LCD.drawInt(light.getNormalizedLightValue(), 4, 0, 1);
            LCD.drawInt(SensorPort.S1.readRawValue(), 4, 0, 2);
            LCD.drawInt(SensorPort.S1.readValue(), 4, 0, 3);
            Thread.sleep(1000);
        }

    }
}
