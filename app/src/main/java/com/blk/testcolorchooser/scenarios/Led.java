package com.blk.testcolorchooser.scenarios;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Led {

    @SerializedName("nleds")
    @Expose
    private String nleds;
    @SerializedName("ledId")
    @Expose
    private String ledId;
    @SerializedName("lastLedId")
    @Expose
    private String lastLedId;

    @SerializedName("nextLedId")
    @Expose
    private String nextLedId;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("brightness")
    @Expose
    private String brightness;
    @SerializedName("delayBefore")
    @Expose
    private String delayBefore;
    @SerializedName("delayAfter")
    @Expose
    private String delayAfter;

    /**
     * No args constructor for use in serialization
     *
     */
    public Led() {
    }

    /**
     *
     * @param duration
     * @param delayAfter
     * @param brightness
     * @param delayBefore
     * @param color
     * @param nleds
     * @param ledId
     * @param nextLedId
     * @param lastLedId
     */
    public Led(String nleds, String ledId, String lastLedId, String nextLedId, String duration, String color, String brightness, String delayBefore, String delayAfter) {
        super();
        this.nleds = nleds;
        this.ledId = ledId;
        this.lastLedId = lastLedId;
        this.nextLedId = nextLedId;
        this.duration = duration;
        this.color = color;
        this.brightness = brightness;
        this.delayBefore = delayBefore;
        this.delayAfter = delayAfter;
    }

    public String getNleds() {
        return nleds;
    }

    public void setNleds(String nleds) {
        this.nleds = nleds;
    }

    public String getLedId() {
        return ledId;
    }

    public void setLedId(String ledId) {
        this.ledId = ledId;
    }

    public String getLastLedId() {
        return lastLedId;
    }

    public void setLastLedId(String lastLedId) {
        this.lastLedId = lastLedId;
    }

    public String getNextLedId() {
        return nextLedId;
    }

    public void setNextLedId(String nextLedId) {
        this.nextLedId = nextLedId;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBrightness() {
        return brightness;
    }

    public void setBrightness(String brightness) {
        this.brightness = brightness;
    }

    public String getDelayBefore() {
        return delayBefore;
    }

    public void setDelayBefore(String delayBefore) {
        this.delayBefore = delayBefore;
    }

    public String getDelayAfter() {
        return delayAfter;
    }

    public void setDelayAfter(String delayAfter) {
        this.delayAfter = delayAfter;
    }

}