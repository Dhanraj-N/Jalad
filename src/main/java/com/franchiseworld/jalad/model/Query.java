package com.franchiseworld.jalad.model;

import java.time.LocalDate;

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

    @NotBlank(message = "Contact number is required")
    @Size(min = 10, max = 10, message = "Contact number must be exactly 10 digits")
    @Pattern(regexp = "^[0-9]*$", message = "Contact number must contain only digits")
    private String contactNo;
    @NotBlank(message = "queryMessage is required")
    private String queryMessage;
    @Enumerated(EnumType.STRING)
    private QueryStatus status;
   /* @ManyToOne
    @JoinColumn(name = "id") // This specifies the foreign key column name in the Query table
    private Users users;*/
   @ManyToOne
   @JoinColumn(name = "user_id") // This specifies the foreign key column name in the Query table
   private Users users;

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
