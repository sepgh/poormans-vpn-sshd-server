package io.github.sepgh.poormansvpn.server;

import org.apache.sshd.common.AttributeRepository;
import org.apache.sshd.common.io.IoAcceptor;
import org.apache.sshd.common.io.IoConnector;
import org.apache.sshd.common.io.IoServiceEventListener;
import org.apache.sshd.common.session.Session;
import org.apache.sshd.common.util.net.SshdSocketAddress;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.forward.AcceptAllForwardingFilter;
import org.apache.sshd.server.forward.ForwardingFilter;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.shell.InteractiveProcessShellFactory;
import org.apache.sshd.server.shell.ProcessShellFactory;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.file.Paths;

public class Application {
    public static void main(String[] args) throws IOException {
        SshServer sshd = SshServer.setUpDefaultServer();
        sshd.setPort(8000);
        sshd.setHost("localhost");
        sshd.setKeyPairProvider(new SimpleGeneratorHostKeyProvider(Paths.get("/home/sepehr/keys/sample")));
        sshd.setPasswordAuthenticator((username, password, serverSession) -> {
            System.out.println(username + " : " + password);
            return true;
        });
        sshd.setIoServiceEventListener(new IoServiceEventListener() {
            @Override
            public void connectionEstablished(IoConnector connector, SocketAddress local, AttributeRepository context, SocketAddress remote) throws IOException {
                System.out.println("New connection from " + remote);
                System.out.println(context.attributeKeys());
                System.out.println(local);
            }

            @Override
            public void connectionAccepted(IoAcceptor acceptor, SocketAddress local, SocketAddress remote, SocketAddress service) throws IOException {
                System.out.println("New connection from " + remote);
            }
        });
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
        sshd.start();
    }
}