package com.victor.task_management_with_security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableAsync
@EnableJpaRepositories(basePackages = "com.victor.task_management_with_security.repository"
)
public class TaskManagementWithSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskManagementWithSecurityApplication.class, args);
	}

}
