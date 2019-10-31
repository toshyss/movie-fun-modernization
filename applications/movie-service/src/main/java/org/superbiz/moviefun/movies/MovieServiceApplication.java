package org.superbiz.moviefun.movies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication
public class MovieServiceApplication {

    public static void main(String... args) {
        SpringApplication.run(MovieServiceApplication.class, args);
    }

    // @Bean
    // public ServletRegistrationBean actionServletRegistration(MovieServlet movieServlet) {
    //     return new ServletRegistrationBean(movieServlet, "/moviefun/*");
    // }

}
