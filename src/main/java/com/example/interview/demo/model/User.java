package com.example.interview.demo.model;

import java.time.LocalDate;

public class User {

    private String firstName;

    private String lastName;

    private LocalDate date;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Employee [date=" + date + ", firstName=" + firstName + ", lastName=" + lastName + "]";
    }
}