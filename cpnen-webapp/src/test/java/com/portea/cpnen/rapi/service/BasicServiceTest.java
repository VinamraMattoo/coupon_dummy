package com.portea.cpnen.rapi.service;


import com.portea.cpnen.rapi.domain.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Arrays;

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
