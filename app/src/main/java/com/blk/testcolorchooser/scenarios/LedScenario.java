package com.blk.testcolorchooser.scenarios;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LedScenario {

    @SerializedName("nameMode")
    @Expose
    private String nameMode;
    @SerializedName("colorMode")
    @Expose
    private String colorMode;
    @SerializedName("brigthness")
    @Expose
    private String brigthness;
    @SerializedName("writeToController")
    @Expose
    private Boolean writeToController;
    @SerializedName("leds")
    @Expose
    private List<Led> leds = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public LedScenario() {
    }

    /**
     *
     * @param writeToController
     * @param colorMode
     * @param brigthness
     * @param leds
     * @param nameMode
     */
    public LedScenario(String nameMode, String colorMode, String brigthness, Boolean writeToController, List<Led> leds) {
        super();
        this.nameMode = nameMode;
        this.colorMode = colorMode;
        this.brigthness = brigthness;
        this.writeToController = writeToController;
        this.leds = leds;
    }

    public String getNameMode() {
        return nameMode;
    }

    public void setNameMode(String nameMode) {
        this.nameMode = nameMode;
    }

    public String getColorMode() {
        return colorMode;
    }

    public void setColorMode(String colorMode) {
        this.colorMode = colorMode;
    }

    public String getBrigthness() {
        return brigthness;
    }

    public void setBrigthness(String brigthness) {
        this.brigthness = brigthness;
    }

    public Boolean getWriteToController() {
        return writeToController;
    }

    public void setWriteToController(Boolean writeToController) {
        this.writeToController = writeToController;
    }

    public List<Led> getLeds() {
        return leds;
    }

    public void setLeds(List<Led> leds) {
        this.leds = leds;
    }

}