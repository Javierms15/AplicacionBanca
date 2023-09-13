package com.example.demo.config.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Profile("!test")
@Configuration
@EnableScheduling
public class SchedulerConfig {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job cobroAutomaticoSemanalJob;

    @Autowired
    private Job cobroAutomaticoMensualJob;

    @Autowired
    private Job cobroAutomaticoTrimestralJob;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

    @Scheduled(fixedDelay = 604800, initialDelay = 5000)
    public void scheduleByFixedRateSemanal() throws Exception {
        System.out.println("Cobro Semanal Comienza");
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("time", format.format(Calendar.getInstance().getTime())).toJobParameters();
        jobLauncher.run(cobroAutomaticoSemanalJob, jobParameters);
        System.out.println("Cobro semanal termina exitosamente\n");
    }

    @Scheduled(fixedDelay = 2628000, initialDelay = 5000)
    public void scheduleByFixedRateMensual() throws Exception {
        System.out.println("Cobro mensual Comienza");
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("time", format.format(Calendar.getInstance().getTime())).toJobParameters();
        jobLauncher.run(cobroAutomaticoMensualJob, jobParameters);
        System.out.println("Cobro mensual termina exitosamente\n");
    }

    @Scheduled(fixedDelay = 7884000, initialDelay = 5000)
    public void scheduleByFixedRateTrimestral() throws Exception {
        System.out.println("Cobro trimestral Comienza");
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("time", format.format(Calendar.getInstance().getTime())).toJobParameters();
        jobLauncher.run(cobroAutomaticoTrimestralJob, jobParameters);
        System.out.println("Cobro trimestral termina exitosamente\n");
    }

}
