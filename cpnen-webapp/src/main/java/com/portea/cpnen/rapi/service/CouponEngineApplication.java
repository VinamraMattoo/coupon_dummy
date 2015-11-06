package com.portea.cpnen.rapi.service;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * A JAX-RS REST application for Coupon Engine. This application registers and provides
 * coupon engine services.
 */
public class CouponEngineApplication extends Application {
    private Set<Object> singletons = new HashSet<>();
    private Set<Class<?>> classes = new HashSet<>();

    public CouponEngineApplication() {
        classes.add(CouponEngineRestService.class);
        classes.add(CouponEngineRestTestService.class);
        classes.add(CouponApplicationExceptionMapper.class);
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
