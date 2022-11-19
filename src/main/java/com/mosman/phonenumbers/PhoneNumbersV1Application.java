package com.mosman.phonenumbers;

import org.springdoc.core.SpringDocConfigProperties;
import org.springdoc.core.SpringDocConfiguration;
import org.springdoc.core.providers.ObjectMapperProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Application entrypoint
 */
@SpringBootApplication
public class PhoneNumbersV1Application {

    public static void main(String[] args) {
        SpringApplication.run(PhoneNumbersV1Application.class, args);
    }

    /**
     * Beans for SpringDoc with default configuration as autoconfiguration is disabled
     *
     * @return {@link SpringDocConfiguration} bean
     */
    @Bean
    SpringDocConfiguration springDocConfiguration() {
        return new SpringDocConfiguration();
    }

    /**
     * Beans for SpringDoc with default configuration as autoconfiguration is disabled
     *
     * @return {@link SpringDocConfigProperties} bean
     */
    @Bean
    public SpringDocConfigProperties springDocConfigProperties() {
        return new SpringDocConfigProperties();
    }

    @Bean
    public ObjectMapperProvider objectMapperProvider() { return new ObjectMapperProvider(springDocConfigProperties());}

}
