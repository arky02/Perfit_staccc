package com.peftif.android.Perfect_fit.ModelData;

public class Data_model {
    private String name;
    private String  height;
    private Double arm;
    private Double leg;
    private Double shoulder;
    //치수 추가

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height= height;
    }

    public Double getArm() {
        return arm;
    }

    public void setArm(Double arm) {
        this.arm = arm;
    }

    public Double getLeg() {
        return leg;
    }

    public void setLeg(Double leg) {
        this.leg = leg;
    }

    public Double getShoulder() {
        return shoulder;
    }

    public void setShoulder(Double shoulder) {
        this.shoulder = shoulder;
    }
}
