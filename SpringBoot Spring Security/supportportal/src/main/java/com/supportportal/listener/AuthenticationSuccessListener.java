package com.supportportal.listener;


import com.supportportal.domain.UserPrinciple;
import com.supportportal.service.LoginAttemptService;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class AuthenticationSuccessListener {
    private LoginAttemptService loginAttemptService;

    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event)  {
        Object princpal = event.getAuthentication().getAuthorities();
        if(princpal instanceof UserPrinciple){
            UserPrinciple username = (UserPrinciple) event.getAuthentication().getAuthorities();
            loginAttemptService.addUserToLoginAttemptCache(username.getUsername());
        }
    }
}
