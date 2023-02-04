package io.github.sepgh.poormansvpn.server;

import org.apache.sshd.server.auth.AsyncAuthException;
import org.apache.sshd.server.auth.password.PasswordAuthenticator;
import org.apache.sshd.server.auth.password.PasswordChangeRequiredException;
import org.apache.sshd.server.session.ServerSession;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PlainFilePasswordAuthenticator implements PasswordAuthenticator {
    private final String filePath;

    public PlainFilePasswordAuthenticator(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public boolean authenticate(String username, String password, ServerSession serverSession) throws PasswordChangeRequiredException, AsyncAuthException {
        BufferedReader reader;

        boolean authenticated = false;
        try {
            reader = new BufferedReader(new FileReader(this.filePath));
            String line = reader.readLine();

            while (line != null) {
                String[] up = line.split(" ");
                line = reader.readLine();
                if (up.length != 2)
                    continue;
                if (up[0].equals(username) && up[1].equals(password)){
                    System.out.println("Logging in " + username);
                    authenticated = true;
                    break;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!authenticated){
            System.out.println("Invalid username and password for user " + username);
        }
        return authenticated;
    }

    @Override
    public boolean handleClientPasswordChangeRequest(ServerSession session, String username, String oldPassword, String newPassword) {
        return false;
    }
}
