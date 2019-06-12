package com.accounts.model;

public class TransferResponse {

    private Boolean success;
    private String errorDetails;

    public static TransferResponse failedTranser(String errorDetails){
        return new TransferResponse(errorDetails);
    }

    public static TransferResponse successedTranser(){
        return new TransferResponse();
    }

    private TransferResponse(String errorDetails) {
        this.success = false;
        this.errorDetails = errorDetails;
    }

    private TransferResponse() {
        this.success = true;
    }
}
