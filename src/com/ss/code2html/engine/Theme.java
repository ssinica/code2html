package com.ss.code2html.engine;

public enum Theme {

    DEFAULT("similar to default eclipse theme");

    private String description;

    private Theme(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static Theme getByName(String name) {
        for (Theme th : values()) {
            if (th.name().equalsIgnoreCase(name)) {
                return th;
            }
        }
        return null;
    }

}
