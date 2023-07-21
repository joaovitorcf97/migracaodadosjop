package com.joao.migracaodadosjob.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.joao.migracaodadosjob.domain.Pessoa;

@Configuration
public class MigrarDadosPessoaStepConfig {

    @Bean
    public Step migrarPessoaStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<Pessoa> arquivoIPessoaReader,
            ItemWriter<Pessoa> bancoPessoWriter) {
        return new StepBuilder("migrarPessoaStep", jobRepository)
                .<Pessoa, Pessoa>chunk(1, transactionManager)
                .reader(arquivoIPessoaReader)
                .writer(bancoPessoWriter)
                .build();
    }
}
