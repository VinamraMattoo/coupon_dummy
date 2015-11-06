package com.portea.cpnen.web.rapi.service;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class CouponWebApplication extends Application{
    private Set<Object> singletons = new HashSet<>();
    private Set<Class<?>> classes = new HashSet<>();

    public CouponWebApplication(){
        classes.add(CouponWebRestService.class);
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
