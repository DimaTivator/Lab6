package server;

import commonModule.dataStructures.Request;
import commonModule.dataStructures.Response;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class NetworkProvider {

    private final int BUFFER_SIZE = 1024 * 1024;

    private DatagramChannel datagramChannel;

    private DatagramSocket serverSocket;

    public NetworkProvider(int port) throws IOException {
        datagramChannel = DatagramChannel.open();
        serverSocket = datagramChannel.socket();
        serverSocket.bind(new InetSocketAddress(port));
    }

    public Request receive() {

        Request request;

        try {

            ByteBuffer buf = ByteBuffer.allocate(BUFFER_SIZE);
            DatagramPacket datagramPacket = new DatagramPacket(buf.array(), buf.array().length);

            serverSocket.receive(datagramPacket);
            SocketAddress client = datagramPacket.getSocketAddress();

            System.out.println("========= Packet received ========= \n");

            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(buf.array()));

            request = (Request) objectInputStream.readObject();
            request.setHost(client);

            System.out.println("========= Packet unpacked ========= \n");

            return request;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public void send(Response response, SocketAddress client) {

        ObjectOutputStream objectOutputStream = null;

        try {

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(out);
            objectOutputStream.writeObject(response);

            DatagramPacket responsePacket = new DatagramPacket(out.toByteArray(), out.toByteArray().length);
            responsePacket.setSocketAddress(client);

            serverSocket.send(responsePacket);

        } catch (IOException e) {
            System.out.println(e.getMessage());

        } finally {
            try {
                objectOutputStream.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
