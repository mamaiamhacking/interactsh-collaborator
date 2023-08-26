package burp.listeners;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import interactsh.Client;

public class InteractshListener implements ActionListener {
    public ArrayList<Thread> pollers = new ArrayList<Thread>();
    public boolean running = true;

    public InteractshListener() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        burp.BurpExtender.api.logging().logToOutput("Generating new Interactsh client");
        Client c = new Client();
        try {
            c.generateKeys();

            Thread polling = new Thread(new Runnable() {
                public void run() {
                    try {
                        if (c.registerClient()) {
                            burp.BurpExtender.addClient(c);
                            while (running == true) {
                                if (!c.poll()) {
                                    return;
                                }
                                TimeUnit.SECONDS.sleep(burp.BurpExtender.getPollTime());
                            }
                        } else {
                            burp.BurpExtender.api.logging().logToOutput("Error registering client");
                        }
                    } catch (InterruptedException ie) {
                    } catch (Exception ex) {
                        burp.BurpExtender.api.logging().logToError(ex.getMessage());
                    }
                }
            });
            pollers.add(polling);
            polling.start();

            TimeUnit.SECONDS.sleep(1);
            // Set clipboard with new interactsh domain
            String domain = c.getInteractDomain();
            burp.BurpExtender.api.logging().logToOutput("New domain is: " + domain);
            StringSelection stringSelection = new StringSelection(domain);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
        } catch (Exception ex) {
            burp.BurpExtender.api.logging().logToError(ex.getMessage());
        }
    }
}