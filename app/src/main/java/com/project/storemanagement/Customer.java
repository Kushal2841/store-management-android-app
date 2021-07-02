package com.project.storemanagement;

public class Customer {
    String name, phone_number;
    Integer debt;

    public Customer() {

    }

    public Customer( Integer debt) {

        this.debt = debt;
    }

    public Customer(String name, String phone_number, Integer debt) {
        this.name = name;
        this.phone_number = phone_number;
        this.debt = debt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public Integer getDebt() {
        return debt;
    }

    public void setDebt(Integer debt) {
        this.debt = debt;
    }
}
