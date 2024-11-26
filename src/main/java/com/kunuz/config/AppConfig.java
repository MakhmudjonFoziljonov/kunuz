package com.kunuz.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import javax.sql.DataSource;
import java.util.Locale;
import java.util.ResourceBundle;

@Configuration
public class AppConfig {
    @Bean
    public ResourceBundleMessageSource resourceBundleMessageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("messages/message");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setDefaultLocale(new Locale("uz"));
        return messageSource;
    }

    @Configuration
    public class MigrationConfiguration {
        @Value("${spring.datasource.url}")
        private String dataSourceUrl;
        @Value("${spring.datasource.username}")
        private String dataSourceUsername;
        @Value("${spring.datasource.password}")
        private String dataSourcePassword;

        @Bean
        public DataSource getDataSource() {
            DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
            dataSourceBuilder.url(dataSourceUrl);
            dataSourceBuilder.username(dataSourceUsername);
            dataSourceBuilder.password(dataSourcePassword);
            return dataSourceBuilder.build();
        }
    }

}