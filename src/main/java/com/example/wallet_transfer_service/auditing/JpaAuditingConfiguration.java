package com.example.wallet_transfer_service.auditing;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.data.domain.AuditorAware;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfiguration {
    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            // var serviceName = (String)
            // RequestContextHolder.currentRequestAttributes().getAttribute("serviceName",
            // RequestAttributes.SCOPE_REQUEST);

            return Optional.ofNullable("");
        };
    }
}
