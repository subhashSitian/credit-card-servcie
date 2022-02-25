package com.card.application.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CardDetailsVo {


    @Valid
    @NotEmpty(message = "customer name should not be blank")
    @Pattern(regexp = "([a-zA-z\\s]{2,20})$")
    private String customerName;

    @Valid
    @NotNull(message = "card number should not be blank")
    @ToString.Exclude
    @Pattern(regexp = ".*[0-9].*")
    @Length(min=10, max=19)
    private String cardNumber;

    private Double balanceAmount;

}
