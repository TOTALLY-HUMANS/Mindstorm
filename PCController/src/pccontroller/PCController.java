package pccontroller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import lejos.pc.comm.*;
import java.io.*;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PCController extends JFrame implements KeyListener {

    JLabel label;
    DataOutputStream dos;
    DataInputStream dis;
    NXTConnector conn;
    HashMap<Character, Boolean> keyDownMap = new HashMap<Character, Boolean>();

    public PCController(String s) throws InterruptedException, IOException {
        super(s);

        connect();
        JPanel p = new JPanel();
        label = new JLabel("Key Listener!");
        p.add(label);
        add(p);
        addKeyListener(this);
        setSize(1000, 500);
        setVisible(true);

        //logSensors();
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        //Scanner reader = new Scanner(System.in);

        new PCController("Key Listener Tester");
        
        
        /*
        while (true) {
            try {
                // Käskyt: w a s d
                System.out.print("Anna käsky: ");
                char n = reader.nextLine().charAt(0);
                System.out.println("Sending " + (n));
                dos.writeInt(n);
                dos.flush();

            } catch (IOException ioe) {
                System.out.println("IO Exception writing bytes:");
                System.out.println(ioe.getMessage());
                break;
            }

            try {
                System.out.println("Received " + dis.readInt());
            } catch (IOException ioe) {
                System.out.println("IO Exception reading bytes:");
                System.out.println(ioe.getMessage());
                break;
            }
        }
         */

    }
    
    public void logSensors() throws InterruptedException, IOException {
        Thread.sleep(1000);
        if (dis.available() != 0) {
            System.out.println("SENSOR: " + dis.readInt());
        }
        logSensors();
    }

    @Override
    public void keyTyped(KeyEvent e) {

//        try {
//            dos.writeInt(e.getKeyChar());
//            dos.flush();
//            System.out.println("Key typed: " + e.getKeyChar());
//        } catch (IOException ex) {

//            System.out.println("ERROR: Key typed: " + e.getKeyChar());
//        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        char character = e.getKeyChar();

        if (keyDownMap.containsKey(character) && keyDownMap.get(character) == true) {
            return;
        }

        try {
            keyDownMap.put(character, true);
            dos.writeInt(character);
            dos.flush();
            System.out.println("Key pressed: " + e.getKeyChar());
        } catch (IOException ex) {
            System.out.println("ERROR: Key pressed: " + e.getKeyChar());
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (keyDownMap.get(e.getKeyChar()) == false) {
            return;
        }
        System.out.println("Key released: " + e.getKeyChar());

        try {
            keyDownMap.put(e.getKeyChar(), false);
            if (e.getKeyChar() == 'r') {
                dos.writeInt('x');
            } else {
                dos.writeInt('n');
            }
            dos.flush();
            System.out.println("Key released: " + e.getKeyChar());
        } catch (IOException ex) {
            System.out.println("ERROR: Key released: " + e.getKeyChar());
        }
    }

    private void connect() {
        NXTConnector conn = new NXTConnector();
        conn.addLogListener(new NXTCommLogListener() {

            public void logEvent(String message) {
                System.out.println("BTSend Log.listener: " + message);

            }

            public void logEvent(Throwable throwable) {
                System.out.println("BTSend Log.listener - stack trace: ");
                throwable.printStackTrace();

            }
        }
        );
        // Connect to any NXT over Bluetooth
        boolean connected = conn.connectTo("btspp://");

        if (!connected) {
            System.err.println("Failed to connect to any NXT");
            System.exit(1);
        }
        System.out.println("CONNECTED: " + connected);
        this.conn = conn;
        this.dis = conn.getDataIn();
        this.dos = conn.getDataOut();
    }

    private static void quitProgram(DataOutputStream dos, DataInputStream dis, NXTConnector conn) {
        try {
            dis.close();
            dos.close();
            conn.close();
        } catch (IOException ioe) {
            System.out.println("IOException closing connection:");
            System.out.println(ioe.getMessage());
        }
    }
}
