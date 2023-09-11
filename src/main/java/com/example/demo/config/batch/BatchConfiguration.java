package com.example.demo.config.batch;

import com.example.demo.config.batch.processor.CobroAutomaticoProcessor;
import com.example.demo.config.batch.processor.CustomProcessor;
import com.example.demo.config.batch.reader.CobroAutomaticoMensualReader;
import com.example.demo.config.batch.reader.CobroAutomaticoSemanalReader;
import com.example.demo.config.batch.reader.CobroAutomaticoTrimestralReader;
import com.example.demo.config.batch.reader.CustomDatabaseReader;
import com.example.demo.config.batch.writer.CobroAutomaticoWriter;
import com.example.demo.config.batch.writer.CustomWriter;
import com.example.demo.models.entity.OutstandingEntity;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration{

    @Autowired
    private JobExecutionListener hwJobExecutionListener;

    @Autowired
    private CustomProcessor customProcessor;
    @Autowired
    private CustomDatabaseReader customDatabaseReader;
    @Autowired
    private CustomWriter customWriter;

    @Autowired
    private CobroAutomaticoSemanalReader cobroAutomaticoSemanalReader;

    @Autowired
    private CobroAutomaticoMensualReader cobroAutomaticoMensualReader;
    @Autowired
    private CobroAutomaticoProcessor cobroAutomaticoProcessor;
    @Autowired
    private CobroAutomaticoTrimestralReader cobroAutomaticoTrimestralReader;

    @Autowired
    private CobroAutomaticoWriter cobroAutomaticoWriter;

    @Bean
    public Job cobroAutomaticoSemanalJob(JobRepository jobRepository, @Qualifier("stepCobroAutomaticoSemanal") Step step) {
        return new JobBuilder("cobroAutomaticoSemanalJob", jobRepository)
                .flow(step)
                .end()
                .build();
    }

    @Bean
    @Qualifier("stepCobroAutomatico")
    public Step stepCobroAutomaticoSemanal(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("stepCobroAutomaticoSemanal", jobRepository)
                .<OutstandingEntity, OutstandingEntity> chunk(50, transactionManager)
                .reader(cobroAutomaticoSemanalReader)
                .processor(cobroAutomaticoProcessor)
                .writer(cobroAutomaticoWriter)
                .build();
    }

    @Bean
    public Job cobroAutomaticoMensualJob(JobRepository jobRepository, @Qualifier("stepCobroAutomaticoMensual") Step step) {
        return new JobBuilder("cobroAutomaticoMensualJob", jobRepository)
                .flow(step)
                .end()
                .build();
    }

    @Bean
    @Qualifier("stepCobroAutomaticoMensual")
    public Step stepCobroAutomaticoMensual(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("stepCobroAutomaticoMensual", jobRepository)
                .<OutstandingEntity, OutstandingEntity> chunk(50, transactionManager)
                .reader(cobroAutomaticoMensualReader)
                .processor(cobroAutomaticoProcessor)
                .writer(cobroAutomaticoWriter)
                .build();
    }

    @Bean
    public Job cobroAutomaticoTrimestralJob(JobRepository jobRepository, @Qualifier("stepCobroAutomaticoTrimestral") Step step) {
        return new JobBuilder("cobroAutomaticoTrimestralJob", jobRepository)
                .flow(step)
                .end()
                .build();
    }

    @Bean
    @Qualifier("stepCobroAutomaticoTrimestral")
    public Step stepCobroAutomaticoTrimestral(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("stepCobroAutomaticoTrimestral", jobRepository)
                .<OutstandingEntity, OutstandingEntity> chunk(50, transactionManager)
                .reader(cobroAutomaticoTrimestralReader)
                .processor(cobroAutomaticoProcessor)
                .writer(cobroAutomaticoWriter)
                .build();
    }



}
