package com.suncorp.image.autoadjust.util;

public class CostUtils {

    private long startF = System.currentTimeMillis();
    private long lastStepCost = startF;

    public void reset() {
        startF = System.currentTimeMillis();
    }

    public void point(String pointName) {
        System.err.println(pointName + " Cost "
                + (System.currentTimeMillis() - lastStepCost));
        lastStepCost = System.currentTimeMillis();
    }

    public void totalCost() {
        System.err.println("Total Cost "
                + (System.currentTimeMillis() - startF));
    }

}
