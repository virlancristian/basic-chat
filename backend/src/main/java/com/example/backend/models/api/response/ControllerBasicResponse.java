package com.example.backend.models.api.response;

import com.example.backend.api.common.RequestCode;

import java.util.List;

public class ControllerBasicResponse {
    private static final String[] acceptedStatusMessage = {"SUCCESS", "ERROR"};
    private static final List<String> ACCEPTED_STATUS_MESSAGE = List.of(acceptedStatusMessage);
    private String status;
    private RequestCode response;

    public ControllerBasicResponse(String status, RequestCode response) {
        this.status = status;
        this.response = response;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if(ACCEPTED_STATUS_MESSAGE.contains(status)) {
            this.status = status;
        }
    }

    public RequestCode getResponse() {
        return response;
    }

    public void setResponse(RequestCode response) {
        this.response = response;
    }
}
