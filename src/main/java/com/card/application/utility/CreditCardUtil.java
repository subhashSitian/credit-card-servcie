package com.card.application.utility;

import com.card.application.model.CardDetaisEntity;
import com.card.application.vo.CardDetailsVo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreditCardUtil {

    @Autowired
    ModelMapper modelMapper;

    public CardDetailsVo convertToCardDetailsVo(CardDetaisEntity cardDetaisEntity) {
        return modelMapper.map(cardDetaisEntity, CardDetailsVo.class);
    }
    public CardDetaisEntity convertToCreditCardEntity(CardDetailsVo cardDetailsVo) {
        return modelMapper.map(cardDetailsVo, CardDetaisEntity.class);
    }
}
