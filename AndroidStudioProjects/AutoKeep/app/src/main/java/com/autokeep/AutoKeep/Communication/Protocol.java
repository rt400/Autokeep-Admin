package com.autokeep.AutoKeep.Communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class Protocol {
    private ObjectInputStream in;
    private ObjectOutputStream out;

    // ***** constructor ***** //

    /**
     * Constructs output and input object stream.
     * The ObjectOutputStream is constructed 1st so that when ObjectInputStream handshake occurs
     * the output response can be delivered.
     *
     * @param in  - server client socket InputStream
     * @param out - server client socket OutputStream
     * @throws IOException - thrown by the construction of ObjectOutputStream and ObjectInputStream
     */
    public Protocol(InputStream in, OutputStream out) throws IOException {
        this.out = new ObjectOutputStream(out);
        this.in = new ObjectInputStream(in);
    }
    // ***** message handling ***** //

    /**
     * Reads serialized Message object from the TCP socket and converts it to a Message object
     *
     * @return Message
     * @throws IOException            - thrown by the socket
     * @throws ClassNotFoundException - thrown by serialization
     */
    public String read() throws IOException {
        String msg = null;
        try {
            msg = (String) in.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return msg;
    }

    /**
     * Serialize a Message object to the TCP socket
     *
     * @param msg - Message
     * @throws IOException - thrown by the socket
     */
    public void write(String msg) throws IOException {
        out.writeObject(msg);
    }

    public void reset() throws IOException {
        out.reset();

    }

    public void flush() throws IOException {
        out.flush();
    }

    public void close() throws IOException {
        in.close();
        out.close();
    }
}
