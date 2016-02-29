package com.portea.cpnen.rapi.service;


import org.junit.After;
import org.junit.Before;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class BasicServiceTest {

    private Client client;
    private String baseURI = "http://localhost:8080/cpnen/rapi";

    @Before
    public void init() {
        this.client = ClientBuilder.newClient();
    }

    @After
    public void close() {
        if (client != null) {
            client.close();
        }
    }
}
