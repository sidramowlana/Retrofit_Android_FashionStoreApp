package com.example.fashionstoreapp.DTO.Requests;

public class AddressRequest {
    Integer addressId;
    String postalCode;
    String city;
    String address;

    public AddressRequest(String postalCode, String city, String address) {
        this.postalCode = postalCode;
        this.city = city;
        this.address = address;
    }

       public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}