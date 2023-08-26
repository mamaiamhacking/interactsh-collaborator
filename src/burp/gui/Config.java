package burp.gui;

import burp.api.montoya.persistence.Preferences;

public class Config {
    public static void generateConfig() {
        Preferences preferences = burp.BurpExtender.api.persistence().preferences();

        String server = preferences.getString("interactsh-server");
        String port = preferences.getString("interactsh-port");

        if ((server == null || server.isEmpty()) ||
                (port == null || port.isEmpty()) ||
                !preferences.stringKeys().contains("interactsh-authorization") ||
                !preferences.stringKeys().contains("interactsh-cidl") ||
                !preferences.stringKeys().contains("interactsh-cidn") ||
                !preferences.stringKeys().contains("interactsh-uses-tls")) {
            preferences.setString("interactsh-server", "oast.pro");
            preferences.setString("interactsh-port", "443");
            preferences.setString("interactsh-authorization", "");
            preferences.setString("interactsh-cidl", "20");
            preferences.setString("interactsh-cidn", "13");
            preferences.setString("interactsh-uses-tls", Boolean.toString(true));
        }
    }

    public static void loadConfig() {
        Preferences preferences = burp.BurpExtender.api.persistence().preferences();
        String server = preferences.getString("interactsh-server");
        String port = preferences.getString("interactsh-port");
        String tls = preferences.getString("interactsh-uses-tls");
        String authorization = preferences.getString("interactsh-authorization");
        String cidl = preferences.getString("interactsh-cidl");
        String cidn = preferences.getString("interactsh-cidn");

        // Update each of the text boxes on the Configuration pane
        burp.BurpExtender.tab.setServerText(server);
        burp.BurpExtender.tab.setPortText(port);
        burp.BurpExtender.tab.setAuthText(authorization);
        burp.BurpExtender.tab.setCidlText(cidl);
        burp.BurpExtender.tab.setCidnText(cidn);
        burp.BurpExtender.tab.setTlsBox(Boolean.parseBoolean(tls));
    }

    public static void updateConfig() {
        Preferences preferences = burp.BurpExtender.api.persistence().preferences();

        // Read each of the text boxes on the Configuration pane
        String server = burp.BurpExtender.tab.getServerText();
        String port = burp.BurpExtender.tab.getPortText();
        String authorization = burp.BurpExtender.tab.getAuthText();
        String cidl = burp.BurpExtender.tab.getCidlText();
        String cidn = burp.BurpExtender.tab.getCidnText();
        String tls = burp.BurpExtender.tab.getTlsBox();

        preferences.setString("interactsh-server", server);
        preferences.setString("interactsh-port", port);
        preferences.setString("interactsh-uses-tls", tls);
        preferences.setString("interactsh-authorization", authorization);
        preferences.setString("interactsh-cidl", cidl);
        preferences.setString("interactsh-cidn", cidn);
    }

    public static String getHost() {
        return burp.BurpExtender.api.persistence().preferences().getString("interactsh-server");
    }

    public static String getPort() {
        return burp.BurpExtender.api.persistence().preferences().getString("interactsh-port");
    }

    public static boolean getScheme() {
        return Boolean.parseBoolean(burp.BurpExtender.api.persistence().preferences().getString("interactsh-uses-tls"));
    }

    public static String getAuth() {
        return burp.BurpExtender.api.persistence().preferences().getString("interactsh-authorization");
    }

    public static String getCidl() {
        return burp.BurpExtender.api.persistence().preferences().getString("interactsh-cidl");
    }

    public static String getCidn() {
        return burp.BurpExtender.api.persistence().preferences().getString("interactsh-cidn");
    }
}


