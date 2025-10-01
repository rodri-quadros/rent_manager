package com.user.gestaoaluguel.model

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Morador {
    private Integer id;
    private String name;
    private String document;
    private String houseNumber;
    private String phoneNumber;
    private BigDecimal rentValue;
    private LocalDate dueDate;
    private boolean payStatus;
    private LocalDateTIme payDate;
    private boolean active = true;

    public Morador () {}
}

public Integer getId() {return id; }
public void setId(Integer id) {this.id = id;}

public String  getName() {return name; }
public void setName(String name) {this.name = name;}

public String  getDocument() {return document; }
public void setDocument(String document) {this.document = document;}

public String  getHouseNumber() {return houseNumber; }
public void setHouseNumber(String houseNumber) {this.houseNumber = houseNumber;}

public String  getPhoneNumber() {return phoneNumber; }
public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}

public BigDecimal getRentValue() {return rentValue; }
public void setRentValue(BigDecimal rentValue) {this.rentValue = rentValue;}

public LocalDate getDueDate() {return dueDate; }
public void setDueDate(LocalDate dueDate) {this.dueDate = dueDate;}

public boolean getPayStatus() {return payStatus; }
public void setPayStatus(boolean payStatus) {this.payStatus = payStatus;}

public boolean isActive() {return active; }
public void setActive(boolean active) {this.active = active;}

@Override
public String toString() {
    String home = (houseNumber == null || houseNumber.isBlank()) ? "" : "(Casa" + houseNumber + ")";
}
