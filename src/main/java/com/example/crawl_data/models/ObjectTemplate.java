package com.example.crawl_data.models;
public class ObjectTemplate {
    private String tax;
    private String company;
    private String customer;
    private String phone;
    private String email;
    private String industry;
    private String address;
    private String createdDate;

    public ObjectTemplate() {
    }

    public ObjectTemplate(String tax,
                          String company,
                          String customer,
                          String phone,
                          String email,
                          String industry,
                          String address, String createdDate) {
        this.tax = tax;
        this.company = company;
        this.customer = customer;
        this.phone = phone;
        this.email = email;
        this.industry = industry;
        this.address = address;
        this.createdDate = createdDate;
    }


    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "ObjectTemplate{" +
                "tax='" + tax + '\'' +
                ", company='" + company + '\'' +
                ", customer='" + customer + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", industry='" + industry + '\'' +
                ", address='" + address + '\'' +
                ", createdDate='" + createdDate + '\'' +
                '}';
    }
}
