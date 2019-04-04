package com.adesh.my_sbd.model;

/**
 * Created by elisha on 12/17/2018.
 */

public class PressModel {

    private String press_id;
    private String press_weight;
    private String press_reps;

    public PressModel() {
    }

    public PressModel(String press_id, String press_weight, String press_reps) {
        this.press_id = press_id;
        this.press_weight = press_weight;
        this.press_reps = press_reps;
    }

    public String getPress_id() {
        return press_id;
    }

    public void setPress_id(String press_id) {
        this.press_id = press_id;
    }

    public String getPress_weight() {
        return press_weight;
    }

    public void setPress_weight(String press_weight) {
        this.press_weight = press_weight;
    }

    public String getPress_reps() {
        return press_reps;
    }

    public void setPress_reps(String press_reps) {
        this.press_reps = press_reps;
    }
}
