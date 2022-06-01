package com.agrokaszuby.front.agrokaszubyfront.domain;


import java.time.LocalDateTime;
import java.util.Objects;

public class Reservation {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String city;
    private String postalCode;
    private String street;
    private String email;

    public Reservation() {
    }

    public Reservation(LocalDateTime startDate, LocalDateTime endDate,
                       String firstName, String lastName, String phoneNumber,
                       String city, String postalCode, String street, String email) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.postalCode = postalCode;
        this.street = street;
        this.email = email;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate)
                && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName)
                && Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(city, that.city)
                && Objects.equals(postalCode, that.postalCode) && Objects.equals(street, that.street)
                && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, firstName, lastName,
                phoneNumber, city, postalCode, street, email);
    }
}
