package com.project.storemanagement;

public class Purchase {
    String customerNamePur, customerPhonePur, productNamePur, purchaseStatus ;
    Integer quantityPur, amountPur;

    public Purchase(String customerNamePur, String customerPhonePur, String productNamePur, String purchaseStatus, Integer quantityPur, Integer amountPur) {
        this.customerNamePur = customerNamePur;
        this.customerPhonePur = customerPhonePur;
        this.productNamePur = productNamePur;
        this.purchaseStatus = purchaseStatus;
        this.quantityPur = quantityPur;
        this.amountPur = amountPur;
    }

    public Purchase() {
    }


    public String getCustomerNamePur() {
        return customerNamePur;
    }

    public void setCustomerNamePur(String customerNamePur) {
        this.customerNamePur = customerNamePur;
    }

    public String getCustomerPhonePur() {
        return customerPhonePur;
    }

    public void setCustomerPhonePur(String customerPhonePur) {
        this.customerPhonePur = customerPhonePur;
    }

    public String getProductNamePur() {
        return productNamePur;
    }

    public void setProductNamePur(String productNamePur) {
        this.productNamePur = productNamePur;
    }

    public String getPurchaseStatus() {
        return purchaseStatus;
    }

    public void setPurchaseStatus(String purchaseStatus) {
        this.purchaseStatus = purchaseStatus;
    }

    public Integer getQuantityPur() {
        return quantityPur;
    }

    public void setQuantityPur(Integer quantityPur) {
        this.quantityPur = quantityPur;
    }

    public Integer getAmountPur() {
        return amountPur;
    }

    public void setAmountPur(Integer amountPur) {
        this.amountPur = amountPur;
    }
}
