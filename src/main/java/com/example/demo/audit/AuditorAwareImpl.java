package com.example.demo.audit;

import com.example.demo.security.util.SecurityUtils;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        final String uname = SecurityUtils.getCurrentUserLogin();
        return Optional.ofNullable(uname);
    }

}
