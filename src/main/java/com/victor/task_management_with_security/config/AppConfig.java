package com.victor.task_management_with_security.config;

import com.victor.task_management_with_security.auditor.AppAuditAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class AppConfig {

    @Bean
    public AuditorAware<Integer> auditorAware() {
        return new AppAuditAware();
    }
}
