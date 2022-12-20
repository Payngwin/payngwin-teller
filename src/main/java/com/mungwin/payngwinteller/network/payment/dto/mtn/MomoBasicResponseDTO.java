package com.mungwin.payngwinteller.network.payment.dto.mtn;

public class MomoBasicResponseDTO {
    /*[
            "ReceiverNumber" => "237653954068"
            "StatusCode" => "01"
            "Amount" => "10"
            "TransactionID" => "1132094515"
            "ProcessingNumber" => "157884740105537369640844120"
            "OpComment" => "Transaction completed DEBIT and CREDIT"
            "StatusDesc" => "Successfully processed transaction."
            "SenderNumber" => "237678462355"
            "OperationType" => "RequestPaymentIndividual"
            ]*/
    private String ReceiverNumber;
    private String StatusCode;
    private String Amount;
    private String TransactionID;
    private String ProcessingNumber;
    private String OpComment;
    private String StatusDesc;
    private String SenderNumber;
    private String OperationType;

    public String getReceiverNumber() {
        return ReceiverNumber;
    }

    public void setReceiverNumber(String receiverNumber) {
        ReceiverNumber = receiverNumber;
    }

    public String getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(String statusCode) {
        StatusCode = statusCode;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getTransactionID() {
        return TransactionID;
    }

    public void setTransactionID(String transactionID) {
        TransactionID = transactionID;
    }

    public String getProcessingNumber() {
        return ProcessingNumber;
    }

    public void setProcessingNumber(String processingNumber) {
        ProcessingNumber = processingNumber;
    }

    public String getOpComment() {
        return OpComment;
    }

    public void setOpComment(String opComment) {
        OpComment = opComment;
    }

    public String getStatusDesc() {
        return StatusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        StatusDesc = statusDesc;
    }

    public String getSenderNumber() {
        return SenderNumber;
    }

    public void setSenderNumber(String senderNumber) {
        SenderNumber = senderNumber;
    }

    public String getOperationType() {
        return OperationType;
    }

    public void setOperationType(String operationType) {
        OperationType = operationType;
    }
}
