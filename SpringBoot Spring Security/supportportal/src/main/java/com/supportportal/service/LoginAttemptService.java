package com.supportportal.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptService {
    private static final int MAXIMUM_NR_OF_ATTEMPTS = 3;
    private static final int ATTEMPT_INCREMENT = 1;
    private LoadingCache<String,Integer> loginAttemptsCache;

    public LoginAttemptService() {
        super();
        loginAttemptsCache = CacheBuilder.newBuilder().
                expireAfterWrite(15, TimeUnit.MINUTES)
                .maximumSize(100).build(new CacheLoader<String, Integer>() {
                    @Override
                    public Integer load(String key) throws Exception {
                        return 0;
                    }
                });
    }

    public void evictUserFromLoginAttempt(String username){
        loginAttemptsCache.invalidate(username);
    }

    public void addUserToLoginAttemptCache(String username){
        int attempts = 0;
        try {
            attempts = ATTEMPT_INCREMENT + loginAttemptsCache.get(username);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        loginAttemptsCache.put(username,attempts);

    }

    public boolean hasExcededMaxAttempt(String username)  {
        try {
            return loginAttemptsCache.get(username) >= MAXIMUM_NR_OF_ATTEMPTS;
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }
}
