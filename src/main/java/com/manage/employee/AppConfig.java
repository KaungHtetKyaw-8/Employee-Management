package com.manage.employee;

import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class AppConfig {

//    @Bean({"googleLocalServerReciever"})
//    public LocalServerReceiver localServerReceiver(){
//        return new LocalServerReceiver();
//    }
}
