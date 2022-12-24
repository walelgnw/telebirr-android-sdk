package com.vintechplc.telebirr.model;

import java.util.Random;

public class TeleBirrPack {
    private String message;
    private int code;

    private String entityId;
    private String bearerId;
    private double totalAmount;
    private double price_usd;
    private double price_etb;
    private String appId;
    private String receiverName;
    private String shortCode;
    private String subject;
    private String returnUrl;
    private String notifyUrl;
    private String timeoutExpress;
    private String appKey;
    private String publicKey;
    private String outTradeNo;
    private String productionClientId;
    private String inAppPaymentUrl;

    public String getInAppPaymentUrl() {
        return inAppPaymentUrl;
    }

    public void setInAppPaymentUrl(String inAppPaymentUrl) {
        this.inAppPaymentUrl = inAppPaymentUrl;
    }

    public String getMessage() {
        return message;
    }

    public double getPrice_etb() {
        return price_etb;
    }

    public void setPrice_etb(double price_etb) {
        this.price_etb = price_etb;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getTimeoutExpress() {
        return timeoutExpress;
    }

    public void setTimeoutExpress(String timeoutExpress) {
        this.timeoutExpress = timeoutExpress;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getProductionClientId() {
        return productionClientId;
    }

    public void setProductionClientId(String productionClientId) {
        this.productionClientId = productionClientId;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getBearerId() {
        return bearerId;
    }

    public void setBearerId(String bearerId) {
        this.bearerId = bearerId;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getPrice_usd() {
        return price_usd;
    }

    public void setPrice_usd(double price_usd) {
        this.price_usd = price_usd;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }
    public TeleBirrPack local(){
        Random random = new Random();
        int randomi = random.nextInt(1000);
        TeleBirrPack  teleBirrPack = new TeleBirrPack();
        teleBirrPack.setCode(200);
        teleBirrPack.setMessage("test");
        teleBirrPack.setAppId("488c101855ed4f428452956652203161");
        teleBirrPack.setOutTradeNo("639d59b314e685b36f27051"+randomi);
        teleBirrPack.setReceiverName("winux");
        teleBirrPack.setShortCode("500477");
        teleBirrPack.setSubject("tetsting");
        teleBirrPack.setAppKey("a4d82be4d08246cc87c9446183384562");
        teleBirrPack.setNotifyUrl("https://mobiletransportservice.com/backend/api/credits/pay-with-telebirr");
        teleBirrPack.setReturnUrl("com.ravenioet.tbirrsdk");
        teleBirrPack.setPrice_etb(100);
        teleBirrPack.setTimeoutExpress("60");
        teleBirrPack.setPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzD/6Pj5V0vUGq+7nhb8GbYzf6L0AtIaDCUAVrBD3wmCkYHlEKdKkx2605wAmaeRYhkEJjpU8+mDv7i59Mg4g8/z8GRTcnBJ75+TN7YnqmuGdFboK/B7T1oZH0IqF0XULEOu2406V7FoKZlsEcMT91Kwrupe02SujKEe4tNXVgDbz5CAQHC6zcpu/hksdMaM74HMhfhhMAOUGla9JPGPbUfKWplyFjKA4b4SEq9n4qr5ulmDSNZbVjhCI6ZKj9mSAAlTOCHi7xfOtUqQDS7enGz86SEMnOJmX8AbQ3WE5GJCfNzTt3Nq/ADi3VB37wS66RaKH7qPh7ROwROcLWFcRYQIDAQAB");
        teleBirrPack.setInAppPaymentUrl("https://app.ethiomobilemoney.et:2121/ammapi/payment/service-openup/");
        return teleBirrPack;
    }
}
