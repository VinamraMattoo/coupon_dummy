package com.portea.commp.web.rapi.service;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class CommpWebApplication extends Application {
    private Set<Object> singletons = new HashSet<>();
    private Set<Class<?>> classes = new HashSet<>();

    public CommpWebApplication() {
        classes.add(CommpWebRestService.class);
        classes.add(CommpWebApplicationExceptionMapper.class);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
