package pt.ipb.dsys.assessment.three;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * 3rd Assessment (5)
 * Books (variant-1)
 * Fernando Furtado (314866)
 */
@SpringBootApplication
public class Main {

  public static void main(String[] args) {
    new SpringApplicationBuilder()
        .sources(Main.class)
        .bannerMode(Banner.Mode.OFF)
        .run(args);
  }


}
