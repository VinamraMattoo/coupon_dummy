package com.portea.commp.smsen.util;

import java.io.BufferedReader;
import java.net.HttpURLConnection;

public class ConnectionHolder {

    private final BufferedReader reader;
    private final HttpURLConnection connection;

    public ConnectionHolder(HttpURLConnection connection, BufferedReader reader) {
        this.connection = connection;
        this.reader = reader;
    }

    public HttpURLConnection getConnection() {
        return connection;
    }

    public BufferedReader getReader() {
        return reader;
    }
}
