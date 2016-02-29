package com.portea.commp.smsen.engine;

public enum Brand {

    PORTEA("Portea"),
    MANIPAL("Manipal"),
    MAX_HOMECARE("Max Homecare"),
    MEDANTA("Medanta"),
    ;

    private final String name;

    Brand(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
