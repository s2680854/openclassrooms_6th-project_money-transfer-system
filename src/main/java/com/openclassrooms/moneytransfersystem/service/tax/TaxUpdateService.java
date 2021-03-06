package com.openclassrooms.moneytransfersystem.service.tax;

import com.openclassrooms.moneytransfersystem.dao.TaxRepository;
import com.openclassrooms.moneytransfersystem.model.Tax;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaxUpdateService {

    @Autowired
    private TaxRepository taxRepository;

    Logger logger = LogManager.getLogger(TaxUpdateService.class);

    public Tax updateTax(Tax tax) {

        Tax taxUpdated;
        Optional<Tax> optionalTransfer = taxRepository.findById(tax.getId());

        if (optionalTransfer.isPresent()) {
            taxUpdated = optionalTransfer.get();
            taxRepository.save(taxUpdated);
        } else {
            logger.debug("[updateTax] id not found: " + tax.getId());

            return new Tax();
        }
        logger.debug("[updateTax] tax:" + taxUpdated);

        return taxUpdated;
    }
}
