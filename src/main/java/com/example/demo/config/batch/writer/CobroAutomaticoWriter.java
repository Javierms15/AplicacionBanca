package com.example.demo.config.batch.writer;

import com.example.demo.models.entity.OutstandingEntity;
import com.example.demo.models.service.OutstandingServiceImpl;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CobroAutomaticoWriter implements ItemWriter<OutstandingEntity> {

    @Autowired
    private OutstandingServiceImpl outstandingService;

    @Override
    public void write(Chunk<? extends OutstandingEntity> chunk) throws Exception {
        for(OutstandingEntity data : chunk){
            outstandingService.save(data);
        }
    }
}