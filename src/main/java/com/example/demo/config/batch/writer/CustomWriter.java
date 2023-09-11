package com.example.demo.config.batch.writer;

import com.example.demo.models.entity.OutstandingEntity;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class CustomWriter implements ItemWriter<OutstandingEntity> {
    @Override
    public void write(Chunk<? extends OutstandingEntity> chunk) throws Exception {

    }
}
