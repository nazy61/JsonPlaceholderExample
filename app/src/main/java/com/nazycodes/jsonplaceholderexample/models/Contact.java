package com.nazycodes.jsonplaceholderexample.models;

public class Contact {
    private final String name;
    private final String email;
    private final String phone;
    private final String website;

    public Contact(String name, String email, String phone, String website) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.website = website;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }
}
