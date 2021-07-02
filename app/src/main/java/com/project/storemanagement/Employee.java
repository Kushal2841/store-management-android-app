package com.project.storemanagement;

public class Employee {
    String name, phone_number;
    Integer salary, leaves;

    public Employee(String name, String phone_number, Integer salary, Integer leaves) {
        this.name = name;
        this.phone_number = phone_number;
        this.salary = salary;
        this.leaves = leaves;
    }

    public Employee() {
    }

    public Integer getLeaves() {
        return leaves;
    }

    public void setLeaves(Integer leaves) {
        this.leaves = leaves;
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

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }
}
