package com.card.application.repository;

import com.card.application.model.CardDetaisEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface CardDetailsRepository extends JpaRepository<CardDetaisEntity,Integer> {

    Optional<CardDetaisEntity> findByCardNumber(String cardNumber);
}
