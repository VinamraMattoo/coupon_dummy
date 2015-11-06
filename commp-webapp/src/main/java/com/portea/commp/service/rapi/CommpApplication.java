package com.portea.commp.service.rapi;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * A JAX-RS REST application for Communication Platform. This application registers and provides
 * communication platform services.
 */
public class CommpApplication extends Application {
    private Set<Object> singletons = new HashSet<>();
    private Set<Class<?>> classes = new HashSet<>();

    public CommpApplication() {
        classes.add(CommpRestService.class);
        classes.add(CommpRestTestService.class);
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
