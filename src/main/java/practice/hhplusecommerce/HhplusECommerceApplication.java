package practice.hhplusecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableJpaAuditing
@ServletComponentScan
@SpringBootApplication
public class HhplusECommerceApplication {

  public static void main(String[] args) {
    SpringApplication.run(HhplusECommerceApplication.class, args);
  }
}
