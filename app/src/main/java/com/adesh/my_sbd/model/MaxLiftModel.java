package com.adesh.my_sbd.model;

/**
 * Created by elisha on 12/11/2018.
 */

public class MaxLiftModel {

    private String max_lift_id;
    private String squat;
    private String bench;
    private String deadlift;

    public MaxLiftModel() {
    }

    public MaxLiftModel(String max_lift_id, String squat, String bench, String deadlift) {
        this.max_lift_id = max_lift_id;
        this.squat = squat;
        this.bench = bench;
        this.deadlift = deadlift;
    }

    public String getMax_lift_id() {
        return max_lift_id;
    }

    public void setMax_lift_id(String max_lift_id) {
        this.max_lift_id = max_lift_id;
    }

    public String getSquat() {
        return squat;
    }

    public void setSquat(String squat) {
        this.squat = squat;
    }

    public String getBench() {
        return bench;
    }

    public void setBench(String bench) {
        this.bench = bench;
    }

    public String getDeadlift() {
        return deadlift;
    }

    public void setDeadlift(String deadlift) {
        this.deadlift = deadlift;
    }
}
