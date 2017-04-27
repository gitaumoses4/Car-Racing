package carracing;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * @author Moses Muigai Gitau
 */
public class Server implements Runnable {

    private Thread thread = null;
    private ServerSocket serverSocket;
    private static int PORT = 15000;

    protected DataInputStream inputStream;
    protected DataOutputStream outputStream;

    private ArrayList<TiltListener> listeners = new ArrayList();

    public Server() throws Exception {
        this(PORT);
    }

    public Server(int port) throws Exception {
        PORT = port;
        serverSocket = new ServerSocket(PORT);
        start();
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public int getPort() {
        return PORT;
    }

    public boolean validIpAddress() throws UnknownHostException {
        return !getIpAddress().startsWith("127.0.0.1");
    }

    public String getIpAddress() throws UnknownHostException {
        String address = InetAddress.getLocalHost().toString().substring(InetAddress.getLocalHost().toString().indexOf('/') + 1);
        return address;
    }

    @Override
    public void run() {
        try {
            Socket socket = serverSocket.accept();
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
            outputStream.flush();
            while (thread != null) {
                startTask();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void startTask() {
        try {
            double x = inputStream.readDouble();
            double y = inputStream.readDouble();
            double z = inputStream.readDouble();
            this.fireAngleChange(x, y, z);
        } catch (Exception ex) {

        }
    }

    private double calculateAngle(double x, double y, double z) {
        double angle = y * 20;
        return angle;
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    public void stop() {
        if (thread != null) {
            thread.stop();
            thread = null;
        }
    }

    public void addAngleChangeListener(TiltListener l) {
        listeners.add(l);
    }

    public void removeAngleChangeListener(TiltListener l) {
        listeners.remove(l);
    }

    public void fireAngleChange(double x, double y, double z) {
        listeners.stream().forEach((l) -> {
            l.angleChanged(x, y, z);
        });
    }
}
