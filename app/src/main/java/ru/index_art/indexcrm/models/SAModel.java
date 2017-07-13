package ru.index_art.indexcrm.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SAModel {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("error")
    @Expose
    private String error;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}