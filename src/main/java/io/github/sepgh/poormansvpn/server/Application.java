package io.github.sepgh.poormansvpn.server;

import org.apache.sshd.common.session.Session;
import org.apache.sshd.common.util.net.SshdSocketAddress;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.channel.ChannelSession;
import org.apache.sshd.server.command.Command;
import org.apache.sshd.server.forward.ForwardingFilter;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.shell.ShellFactory;

import java.io.IOException;
import java.nio.file.Paths;

public class Application {
    public static void main(String[] args) throws IOException {
        SshServer sshd = SshServer.setUpDefaultServer();
        if (args.length != 3){
            System.out.println("Needs 3 arguments. 1: port to run in, 2: path to use in host key provider, 3: path to authentication file");
            System.exit(1);
        }
        sshd.setPort(Integer.parseInt(args[0]));
        sshd.setKeyPairProvider(new SimpleGeneratorHostKeyProvider(Paths.get(args[1])));
        sshd.setPasswordAuthenticator(new PlainFilePasswordAuthenticator(args[2]));
        sshd.setForwardingFilter(new ForwardingFilter() {
            @Override
            public boolean canForwardX11(Session session, String s) {
                return false;
            }

            @Override
            public boolean canForwardAgent(Session session, String s) {
                return false;
            }

            @Override
            public boolean canListen(SshdSocketAddress address, Session session) {
                return true;
            }

            @Override
            public boolean canConnect(Type type, SshdSocketAddress sshdSocketAddress, Session session) {
                return true;
            }

        });
        sshd.setCommandFactory(new EmptyCommandFactory());
        sshd.setShellFactory(new ShellFactory() {
            @Override
            public Command createShell(ChannelSession channelSession) throws IOException {
                return new EmptyCommand("None");
            }
        });
        sshd.start();
    }
}
