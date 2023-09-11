package com.example.demo.config.batch.processor;

import com.example.demo.models.entity.OutstandingEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class CustomProcessor implements ItemProcessor<OutstandingEntity, OutstandingEntity> {

    private static final Logger log = LoggerFactory.getLogger(CustomProcessor.class);
    @Override
    public OutstandingEntity process(OutstandingEntity item) throws Exception {
        //log.info("Holaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        return null;
    }
}
