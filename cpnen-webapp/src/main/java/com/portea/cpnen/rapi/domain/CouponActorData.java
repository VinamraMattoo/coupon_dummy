package com.portea.cpnen.rapi.domain;


import com.portea.cpnen.domain.ActorType;

public class CouponActorData {

    private ActorType actorType;

    private Long count;

    public ActorType getActorType() {
        return actorType;
    }

    public void setActorType(ActorType actorType) {
        this.actorType = actorType;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
