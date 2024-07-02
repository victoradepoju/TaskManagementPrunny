package com.victor.task_management_with_security.auditor;

import com.victor.task_management_with_security.entity.User;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@NonNullApi
public class AppAuditAware implements AuditorAware<Integer> {
    @Override
    public Optional<Integer> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken)
        {

            return Optional.empty();
        }

        User user =  (User) authentication.getPrincipal();
        return Optional.ofNullable(user.getId());
    }
}
