package burp.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ClearTableListener implements ActionListener {
    public ClearTableListener() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        burp.BurpExtender.tab.clearTable();
    }
}