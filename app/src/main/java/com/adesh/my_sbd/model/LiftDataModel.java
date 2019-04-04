package com.adesh.my_sbd.model;

/**
 * Created by elisha on 12/9/2018.
 */

public class LiftDataModel {

    private String lift_id;
    private String date;
    private String week_no;
    private String session_no;
    private String weight;
    private String reps;
    private String tag;

    public LiftDataModel() {
    }

    public LiftDataModel(String lift_id, String date, String week_no, String session_no, String weight, String reps, String tag) {
        this.lift_id = lift_id;
        this.date = date;
        this.week_no = week_no;
        this.session_no = session_no;
        this.weight = weight;
        this.reps = reps;
        this.tag = tag;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLift_id() {
        return lift_id;
    }

    public String getWeek_no() {
        return week_no;
    }

    public String getSession_no() {
        return session_no;
    }

    public String getWeight() {
        return weight;
    }

    public String getReps() {
        return reps;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setLift_id(String lift_id) {
        this.lift_id = lift_id;
    }

    public void setWeek_no(String week_no) {
        this.week_no = week_no;
    }

    public void setSession_no(String session_no) {
        this.session_no = session_no;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setReps(String reps) {
        this.reps = reps;
    }
}
