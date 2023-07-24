package com.joao.migracaodadosjob.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.joao.migracaodadosjob.domain.DadosBancarios;

@Configuration
public class MigrarDadosBancariosStepConfig {

    @Bean
    public Step migrarDadosBancariosStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<DadosBancarios> arquivoDadosBacariosReader,
            ItemWriter<DadosBancarios> bancoDadosBacariosWriter) {
        return new StepBuilder("migrarDadosBancariosStep", jobRepository)
                .<DadosBancarios, DadosBancarios>chunk(10000, transactionManager)
                .reader(arquivoDadosBacariosReader)
                .writer(bancoDadosBacariosWriter)
                .build();
    }
}
