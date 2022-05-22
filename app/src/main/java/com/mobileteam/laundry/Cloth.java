package com.mobileteam.laundry;

import java.io.Serializable;

public class Cloth implements Serializable {

    private int src;

    public int getSrc() {
        return src;
    }

    public Cloth(int src) {
        this.src = src;
    }
}
