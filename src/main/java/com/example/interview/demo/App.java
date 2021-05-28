package com.example.interview.demo;
 
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class App
{
    @Autowired
    JobLauncher jobLauncher;
      
    @Autowired
    Job job;
      
    public static void main(String[] args)
    {
        SpringApplication.run(App.class, args);
    }
      
//    @Scheduled(cron = "0 */1 * * * ?")
//    public void perform() throws Exception
//    {
//        JobParameters params = new JobParametersBuilder()
//                .addString("JobID", String.valueOf(System.currentTimeMillis()))
//                .toJobParameters();
//        jobLauncher.run(job, params);
//    }
}