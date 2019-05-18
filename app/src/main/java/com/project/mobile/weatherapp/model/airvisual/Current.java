package com.project.mobile.weatherapp.model.airvisual;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Current {
    @SerializedName("pollution")
    @Expose
    private Pollution pollution;

    public Pollution getPollution() {
        return pollution;
    }

    public void setPollution(Pollution pollution) {
        this.pollution = pollution;
    }
}
