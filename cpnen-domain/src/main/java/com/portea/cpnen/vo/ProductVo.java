package com.portea.cpnen.vo;

public class ProductVo {

    public int getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(int unitCost) {
        this.unitCost = unitCost;
    }

    private int unitCost;
    private int id;
    private int count;

    public ProductVo() {}

    public ProductVo(int id, int count, int unitCost) {
        this.id = id;
        this.count = count;
        this.unitCost = unitCost;
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
