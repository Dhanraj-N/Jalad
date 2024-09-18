
package com.franchiseworld.jalad.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Query {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long queryId;
    @Column(name = "date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDate date;

    public Query(LocalDate date) {
        this.date = date;
    }

    private String firstName;
    @Column(unique = true, nullable = false)
    @NotBlank(message = "Contact number is required")
    @Size(min = 10, max = 10, message = "Contact number must be exactly 10 digits")
    @Pattern(regexp = "^[0-9]*$", message = "Contact number must contain only digits")
    private String contactNo;
    private String queryMessage;
    @Enumerated(EnumType.STRING)
    private QueryStatus status;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_id", referencedColumnName = "id",nullable = false)
    private Users user;
    public Long getQueryId() {
        return queryId;
    }
    public void setQueryId(Long queryId) {
        this.queryId = queryId;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
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
    public Users getUser() {
        return user;
    }
    public void setUser(Users user) {
        this.user = user;
    }


}
