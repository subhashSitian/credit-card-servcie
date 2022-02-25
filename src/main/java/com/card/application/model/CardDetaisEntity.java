package com.card.application.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "CREDIT_CARD_DETIALS")
public class CardDetaisEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    int id;
    @Column(name = "Customer_Name", nullable = false)
    private String customerName;
    @Column(name = "Card_Number", nullable = false)
    private String cardNumber;
    @Column(name = "Card_Balance", nullable = false)
    private Double balanceAmount;
}
