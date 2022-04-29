package com.deep.classcatalogue.classData;

import javax.sql.DataSource;

import com.deep.classcatalogue.classModel.Professors;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableBatchProcessing
public class ProfessorsConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    private final String[] FIELD_NAMES = new String[] {
            "id", "name", "subject", "location", "availability"
    };

    @Bean
    public FlatFileItemReader<ProfessorsData> reader() {
        return new FlatFileItemReaderBuilder<ProfessorsData>()
                .name("personItemReader")
                .resource(new ClassPathResource("profs.csv"))
                .delimited()
                .names(FIELD_NAMES)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<ProfessorsData>() {
                    {
                        setTargetType(ProfessorsData.class);
                    }
                })
                .build();
    }

    @Bean
    public ProfessorsDataProcessor processor() {
        return new ProfessorsDataProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Professors> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Professors>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO professors (id, name, subject, location, availability)"
                        + " VALUES (:id, :name, :subject, :location, :availability)")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<Professors> writer) {
        return stepBuilderFactory
                .get("step1")
                .<ProfessorsData, Professors>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }
}
