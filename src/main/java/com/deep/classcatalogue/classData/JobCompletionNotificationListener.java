package com.deep.classcatalogue.classData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

  private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public void afterJob(JobExecution jobExecution) {
    if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
      log.info("!!! JOB FINISHED! Time to verify the results");
      System.out.println("JOB FINISHED! Time to verify the results");

      jdbcTemplate.query("SELECT id, name, subject, location, availability FROM professors",
        (rs, row) -> rs.getString(1) + 
        " - " + rs.getString(2) +
        " - " + rs.getString(3)  +
        " - " + rs.getString(4)  +
        " - " + rs.getString(5)).forEach(str -> System.out.println(str));
    }
  }
}