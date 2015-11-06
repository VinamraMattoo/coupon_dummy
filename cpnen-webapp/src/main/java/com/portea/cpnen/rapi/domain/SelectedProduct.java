package com.portea.cpnen.rapi.domain;

public class SelectedProduct {

    private int id;
    private int count;

    public SelectedProduct() {}

    public SelectedProduct(int id, int count) {
        this.id = id;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
