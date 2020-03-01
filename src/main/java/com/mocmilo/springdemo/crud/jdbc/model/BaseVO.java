package com.mocmilo.springdemo.crud.jdbc.model;

public abstract class BaseVO {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
