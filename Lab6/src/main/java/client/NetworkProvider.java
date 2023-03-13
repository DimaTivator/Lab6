package client;

import commonModule.dataStructures.Request;
import commonModule.dataStructures.Response;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class NetworkProvider {

    private final int BUFFER_SIZE = 1024 * 1024;

    InetSocketAddress socketAddress;
    DatagramChannel datagramChannel;
    DatagramSocket datagramSocket;


    public NetworkProvider(String address, int port) throws SocketException, IOException {
        socketAddress = new InetSocketAddress(address, port);
        datagramChannel = DatagramChannel.open();
        datagramSocket = datagramChannel.socket();

        datagramChannel.configureBlocking(false);

        System.out.println("===== Client started! Server on " + address + ":" + port + " =====");
    }


    public void send(Request request) {

        ObjectOutputStream objectOutputStream = null;

        try {

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(out);
            objectOutputStream.writeObject(request);

            datagramChannel.send(ByteBuffer.wrap(out.toByteArray()), socketAddress);

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


    public Response receive() {

        Response response;

        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

        try {

            SocketAddress address = null;

            int i = 0;
            while (address == null) {
                address = datagramChannel.receive(buffer);

                try {
                    Thread.sleep(40);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }

                i++;
                if (i % 200 == 0) {
                    System.out.println("Server is down, try again later...");
                    return null;
                }
                if (i % 40 == 0) {
                    System.out.println("Waiting for the server...");
                }
            }

            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(buffer.array()));
            response = (Response) objectInputStream.readObject();

            return response;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

}
