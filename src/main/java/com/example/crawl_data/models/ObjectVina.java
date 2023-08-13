
package com.example.crawl_data.models;
public class ObjectVina {
    private String companyName;
    private String companyCode;
    private String date;
    private String address;
    private String phone;
    private String email;
    private String personal;
    private String personal_phone;
    private String director;
    private String director_phone;
    private String accountant;
    private String accountant_phone;
    private String job;


    public ObjectVina(String companyName, String companyCode, String date, String address, String phone, String email, String personal, String personal_phone, String director, String director_phone, String accountant, String accountant_phone, String job) {
        this.companyName = companyName;
        this.companyCode = companyCode;
        this.date = date;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.personal = personal;
        this.personal_phone = personal_phone;
        this.director = director;
        this.director_phone = director_phone;
        this.accountant = accountant;
        this.accountant_phone = accountant_phone;
        this.job = job;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getPersonal() {
        return personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public String getPersonal_phone() {
        return personal_phone;
    }

    public void setPersonal_phone(String personal_phone) {
        this.personal_phone = personal_phone;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getDirector_phone() {
        return director_phone;
    }

    public void setDirector_phone(String director_phone) {
        this.director_phone = director_phone;
    }

    public String getAccountant() {
        return accountant;
    }

    public void setAccountant(String accountant) {
        this.accountant = accountant;
    }

    public String getAccountant_phone() {
        return accountant_phone;
    }

    public void setAccountant_phone(String accountant_phone) {
        this.accountant_phone = accountant_phone;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return "Object{" +
                "companyName='" + companyName + '\'' +
                ", companyCode='" + companyCode + '\'' +
                ", date='" + date + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", personal='" + personal + '\'' +
                ", personal_phone='" + personal_phone + '\'' +
                ", director='" + director + '\'' +
                ", director_phone='" + director_phone + '\'' +
                ", accountant='" + accountant + '\'' +
                ", accountant_phone='" + accountant_phone + '\'' +
                ", job='" + job + '\'' +
                '}';
    }
}
