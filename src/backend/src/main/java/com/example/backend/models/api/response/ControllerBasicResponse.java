package com.example.backend.models.api.response;

import com.example.backend.api.common.RequestCode;

import java.util.List;

public class ControllerBasicResponse {
    private static final String[] acceptedStatus = {"SUCCESS", "ERROR"};
    private static final List<String> ACCEPTED_STATUS = List.of(acceptedStatus);
    private String status;
    private RequestCode requestValidationMessage;

    public ControllerBasicResponse(String status, RequestCode response) {
        this.status = status;
        this.requestValidationMessage = response;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if(ACCEPTED_STATUS.contains(status)) {
            this.status = status;
        }
    }

    public RequestCode getRequestValidationMessage() {
        return requestValidationMessage;
    }

    public void setRequestValidationMessage(RequestCode requestValidationMessage) {
        this.requestValidationMessage = requestValidationMessage;
    }
}
