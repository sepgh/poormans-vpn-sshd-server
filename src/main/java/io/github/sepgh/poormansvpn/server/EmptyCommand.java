package io.github.sepgh.poormansvpn.server;

import org.apache.sshd.server.Environment;
import org.apache.sshd.server.ExitCallback;
import org.apache.sshd.server.channel.ChannelSession;
import org.apache.sshd.server.command.Command;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public class EmptyCommand implements Command {

    private String name;

    private InputStream inputStream;
    private OutputStream outputStream;
    private ExitCallback exitCallback;

    public EmptyCommand(String name) {
        this.name = name;
    }

    public void setInputStream(final InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void setOutputStream(final OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void setErrorStream(OutputStream outputStream) {
        System.out.println("error stream was set on command: " + name);
    }

    public void setExitCallback(ExitCallback exitCallback) {
        this.exitCallback = exitCallback;
    }

    public void start(ChannelSession channelSession, Environment environment) throws IOException {
        try {

            final PrintWriter writer = new PrintWriter(outputStream, true);
            writer.println("Welcome to Poorman's VPN");
            writer.flush();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy(ChannelSession channelSession) {
        System.out.println("destroy was called on command: " + name);
    }
}