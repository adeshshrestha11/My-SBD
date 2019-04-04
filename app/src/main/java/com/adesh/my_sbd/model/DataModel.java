package com.adesh.my_sbd.model;

/**
 * Created by elisha on 12/9/2018.
 */

public class DataModel {
    private String lift_id;
    private String date;
    private String week_no;
    private String notes;
    private String tag;

    public DataModel() {
    }

    public DataModel(String lift_id, String date, String week_no, String notes, String tag) {
        this.lift_id = lift_id;
        this.date = date;
        this.week_no = week_no;
        this.notes = notes;
        this.tag = tag;
    }

    public String getLift_id() {
        return lift_id;
    }

    public void setLift_id(String lift_id) {
        this.lift_id = lift_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek_no() {
        return week_no;
    }

    public void setWeek_no(String week_no) {
        this.week_no = week_no;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
