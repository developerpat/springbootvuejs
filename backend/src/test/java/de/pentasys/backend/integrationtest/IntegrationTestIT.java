package de.pentasys.backend.integrationtest;

import de.pentasys.backend.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { Application.class })
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class IntegrationTestIT {

  @Test
  public void integrationTest() {
    System.out.println("Integration-Test");
  }
}
