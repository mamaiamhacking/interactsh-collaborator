package burp.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import interactsh.Client;

public class PollNowListener implements ActionListener {
    public boolean running = false;
    private int count = 0;

    public PollNowListener() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            return;
        }
        running = true;
        try {
            ArrayList<Client> clients = burp.BurpExtender.getClients();
            count = clients.size();
            int i = 0;
            for (;i < clients.size(); i++ ) {
                Client c = clients.get(i);
                Thread poller = new Thread(new Runnable() {
                    public void run() {
                        try {
                            c.poll();
                        } catch (InterruptedException ie) {
                        } catch (Exception ex) {
                            burp.BurpExtender.api.logging().logToError(ex.getMessage());
                        } 
                        count --;
                        if (count == 0) {
                            running = false;
                        }
                    }
                });
                poller.start();
            }
        } catch (Exception ex) {
            burp.BurpExtender.api.logging().logToError(ex.getMessage());
        }
    }
}