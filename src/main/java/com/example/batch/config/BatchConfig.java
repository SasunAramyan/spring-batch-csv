package com.example.batch.config;

import com.example.batch.data.dto.UserCreateDTO;
import com.example.batch.data.jpa.entity.User;
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
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipFile;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

  private final static String ZIP_FILE_PATH = "src/main/resources/input/data.zip";
  private final static String WRITER_SQL = "INSERT INTO USER ( FIRSTNAME, LASTNAME ,DATE) VALUES ( :firstName, :lastName ,:date)";
  private final static int CHUNK_SIZE = 5;

  @Autowired
  private JobBuilderFactory jobBuilderFactory;

  @Autowired
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Autowired
  private StepBuilderFactory stepBuilderFactory;

  @Value("classPath:/input/part_1.csv")
  private Resource inputResource;

  @Bean
  public Job readCSVFileJob() throws IOException {
    return jobBuilderFactory
        .get("readCSVFileJob")
        .incrementer(new RunIdIncrementer())
        .start(step1())
        .build();
  }

  @Bean
  public Step step1() throws IOException {
    return stepBuilderFactory
        .get("step")
        .<UserCreateDTO, User>chunk(CHUNK_SIZE)
        .reader(reader())
        .processor(processor())
        .writer(writer())
        .build();
  }

  @Bean
  public ItemProcessor<UserCreateDTO, User> processor() {
    return new UserItemProcessor();
  }

  @Bean
  public MultiResourceItemReader<UserCreateDTO> reader() throws IOException {
    List<Resource> resources = new ArrayList<>();
    ZipMultiResourceItemReader.extractFiles(new ZipFile(new File(ZIP_FILE_PATH)), resources);
    MultiResourceItemReader<UserCreateDTO> multiResourceItemReader = new MultiResourceItemReader<>();
    FlatFileItemReader<UserCreateDTO> itemReader = new FlatFileItemReader<>();
    itemReader.setLineMapper(lineMapper());
    itemReader.setLinesToSkip(1);
    itemReader.setResource(inputResource);
    multiResourceItemReader.setResources(resources.toArray(new Resource[0]));
    multiResourceItemReader.setDelegate(itemReader);
    return multiResourceItemReader;
  }

  @Bean
  public LineMapper<UserCreateDTO> lineMapper() {
    DefaultLineMapper<UserCreateDTO> lineMapper = new DefaultLineMapper<UserCreateDTO>();
    DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
    lineTokenizer.setNames("firstName", "lastName", "date");
    lineTokenizer.setIncludedFields(0, 1, 2);
    BeanWrapperFieldSetMapper<UserCreateDTO> fieldSetMapper = new BeanWrapperFieldSetMapper<UserCreateDTO>();
    fieldSetMapper.setTargetType(UserCreateDTO.class);
    lineMapper.setLineTokenizer(lineTokenizer);
    lineMapper.setFieldSetMapper(fieldSetMapper);
    return lineMapper;
  }

  @Bean
  public JdbcBatchItemWriter<User> writer() {
    JdbcBatchItemWriter<User> itemWriter = new JdbcBatchItemWriter<User>();
    itemWriter.setJdbcTemplate(namedParameterJdbcTemplate);
    itemWriter.setSql(WRITER_SQL);
    itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<User>());
    return itemWriter;
  }

}
