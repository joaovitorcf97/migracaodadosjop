package com.joao.migracaodadosjob.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
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
            FlatFileItemWriter<Pessoa> arquivoPessoasInvalidasWriter,
            ClassifierCompositeItemWriter<Pessoa> pessoClassifierWriter,
            ItemReader<Pessoa> arquivoPessoaReader) {
        return new StepBuilder("migrarPessoaStep", jobRepository)
                .<Pessoa, Pessoa>chunk(10000, transactionManager)
                .reader(arquivoPessoaReader)
                .writer(pessoClassifierWriter)
                .stream(arquivoPessoasInvalidasWriter)
                .build();
    }
}
