package com.supportportal.listener;

import com.supportportal.domain.UserPrinciple;
import com.supportportal.service.LoginAttemptService;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class AuthenticationFailureListener {
    private LoginAttemptService loginAttemptService;

    @EventListener
    public void onAuthenticationFailure(AuthenticationFailureBadCredentialsEvent event)  {
        Object principal = event.getAuthentication().getPrincipal();
        if (principal instanceof UserPrinciple){
            UserPrinciple username = (UserPrinciple) event.getAuthentication().getPrincipal();
            loginAttemptService.addUserToLoginAttemptCache(username.getUsername());
        }
    }
}
