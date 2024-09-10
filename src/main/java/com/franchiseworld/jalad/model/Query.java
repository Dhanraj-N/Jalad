package com.franchiseworld.jalad.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Query {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long queryId;

    private LocalDate date;
    private String contactNo;
    private String queryMessage;
    @Enumerated(EnumType.STRING)
    private QueryStatus status;

    public Long getQueryId() {
        return queryId;
    }
    public void setQueryId(Long queryId) {
        this.queryId = queryId;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public String getContactNo() {
        return contactNo;
    }
    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }
    public String getQueryMessage() {
        return queryMessage;
    }
    public void setQueryMessage(String queryMessage) {
        this.queryMessage = queryMessage;
    }
    public QueryStatus getStatus() {
        return status;
    }
    public void setStatus(QueryStatus status) {
        this.status = status;
    }
}

