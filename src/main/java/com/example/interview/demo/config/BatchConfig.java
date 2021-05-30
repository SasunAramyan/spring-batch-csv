package com.example.interview.demo.config;

import com.example.interview.demo.model.dto.UserDTO;
import com.example.interview.demo.model.entity.User;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipFile;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

  @Autowired
  private JobBuilderFactory jobBuilderFactory;


  @Autowired
  private StepBuilderFactory stepBuilderFactory;

  @Value("classPath:/input/part_1.csv")
  private Resource inputResource;

  @Bean
  public Job readCSVFileJob() throws IOException {
    return jobBuilderFactory
        .get("readCSVFileJob")
        .incrementer(new RunIdIncrementer())
        .start(step())
        .build();
  }

  @Bean
  public Step step() throws IOException {
    return stepBuilderFactory
        .get("step")
        .<UserDTO, User>chunk(5)
        .reader(reader())
        .processor(processor())
        .writer(writer())
        .build();
  }

  @Bean
  public ItemProcessor<UserDTO, User> processor() {
    return new UserItemProcessor();
  }

  @Bean
  public MultiResourceItemReader<UserDTO> reader() throws IOException {
    List<Resource> resources = new ArrayList<>();
    ZipMultiResourceItemReader.extractFiles(new ZipFile(new File("src/main/resources/input/data.zip")), resources);
    MultiResourceItemReader<UserDTO> multiResourceItemReader = new MultiResourceItemReader<>();
    FlatFileItemReader<UserDTO> itemReader = new FlatFileItemReader<>();
    itemReader.setLineMapper(lineMapper());
    itemReader.setLinesToSkip(1);
    itemReader.setResource(inputResource);
    multiResourceItemReader.setResources(resources.toArray(new Resource[0]));
    multiResourceItemReader.setDelegate(itemReader);
    return multiResourceItemReader;
  }

  @Bean
  public LineMapper<UserDTO> lineMapper() {
    DefaultLineMapper<UserDTO> lineMapper = new DefaultLineMapper<UserDTO>();
    DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
    lineTokenizer.setNames("firstName", "lastName", "date");
    lineTokenizer.setIncludedFields(0, 1, 2);
    BeanWrapperFieldSetMapper<UserDTO> fieldSetMapper = new BeanWrapperFieldSetMapper<UserDTO>();
    fieldSetMapper.setTargetType(UserDTO.class);
    lineMapper.setLineTokenizer(lineTokenizer);
    lineMapper.setFieldSetMapper(fieldSetMapper);
    return lineMapper;
  }

  @Bean
  public JdbcBatchItemWriter<User> writer() {
    JdbcBatchItemWriter<User> itemWriter = new JdbcBatchItemWriter<User>();
    itemWriter.setSql("INSERT INTO USER ( FIRSTNAME, LASTNAME ,DATE) VALUES ( :firstName, :lastName ,:date)");
    itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<User>());
    return itemWriter;
  }



}
