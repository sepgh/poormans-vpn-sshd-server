package io.github.sepgh.poormansvpn.server;

import org.apache.sshd.server.Environment;
import org.apache.sshd.server.ExitCallback;
import org.apache.sshd.server.channel.ChannelSession;
import org.apache.sshd.server.command.Command;

import java.io.*;

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
    }

    public void setExitCallback(ExitCallback exitCallback) {
        this.exitCallback = exitCallback;
    }

    public void start(ChannelSession channelSession, Environment environment) throws IOException {
        try {
            final PrintWriter writer = new PrintWriter(outputStream, false);
            writer.print("Welcome to Poorman's VPN. ");
            writer.flush();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy(ChannelSession channelSession) {
        try {
            final PrintWriter writer = new PrintWriter(outputStream, false);
            writer.print("Have a nice day");
            writer.flush();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}