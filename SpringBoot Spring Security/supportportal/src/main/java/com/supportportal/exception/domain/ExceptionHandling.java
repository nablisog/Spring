package com.supportportal.exception.domain;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.supportportal.domain.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.NoHandlerFoundException;


import javax.persistence.NoResultException;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

@RestControllerAdvice
public class ExceptionHandling implements ErrorController {
    private final Logger logger = LoggerFactory.getLogger(ExceptionHandling.class);
    public static final String ACCOUNT_LOCKED = "Your Account is Locked";
    public static final String METHOD_IS_NOT_ALLOWED = "This request method is not allowed. please send a '%s request";
    public static final String INTERNAL_SERVER_ERROR_MSG = "An error occured while processing";
    public static final String INCORRECT_CREDENTIAL = "Username / password incorrect. plz try again";
    public static final String ACCOUNT_DISABLED = "Your account has been disabled. plz contact the admin";
    public static final String ERROR_PROCESSING_FILE = "Error occured while processing file";
    public static final String NOT_ENOUGH_PERMISSION = "You don't have enough permission";
    public static final String ERROR_PATH = "/error";

    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String msg){
        HttpResponse httpResponse = new HttpResponse
                (new Date(),httpStatus.value(),httpStatus, httpStatus.getReasonPhrase().toUpperCase(),msg.toUpperCase());

        return new ResponseEntity<>(httpResponse,httpStatus);
    }

    @ExceptionHandler(DisabledException.class)
    private ResponseEntity<HttpResponse> accountDisabledException(){
        return createHttpResponse(HttpStatus.BAD_REQUEST,ACCOUNT_DISABLED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    private ResponseEntity<HttpResponse> badCredentialsException(){
        return createHttpResponse(HttpStatus.BAD_REQUEST,INCORRECT_CREDENTIAL);
    }

    @ExceptionHandler(AccessDeniedException.class)
    private ResponseEntity<HttpResponse> accessDeniedException(){
        return createHttpResponse(HttpStatus.FORBIDDEN,NOT_ENOUGH_PERMISSION);
    }

    @ExceptionHandler(LockedException.class)
    private ResponseEntity<HttpResponse> lockedException(){
        return createHttpResponse(HttpStatus.UNAUTHORIZED,ACCOUNT_LOCKED);
    }

    @ExceptionHandler(TokenExpiredException.class)
    private ResponseEntity<HttpResponse> tokenExpiredException(TokenExpiredException exception){
        return createHttpResponse(HttpStatus.UNAUTHORIZED,exception.getMessage().toUpperCase());
    }

    @ExceptionHandler(EmailExistException.class)
    private ResponseEntity<HttpResponse> emailExistException(EmailExistException e){
        return createHttpResponse(HttpStatus.BAD_REQUEST,e.getMessage().toUpperCase());
    }

    @ExceptionHandler(UsernameExistException.class)
    private ResponseEntity<HttpResponse> usernameExistException(UsernameExistException e){
        return createHttpResponse(HttpStatus.BAD_REQUEST,e.getMessage().toUpperCase());
    }

    @ExceptionHandler(EmailNotFoundException.class)
    private ResponseEntity<HttpResponse> emailNotFoundException(EmailNotFoundException e){
        return createHttpResponse(HttpStatus.BAD_REQUEST,e.getMessage().toUpperCase());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    private ResponseEntity<HttpResponse> usernameNotFoundException(UsernameNotFoundException e){
        return createHttpResponse(HttpStatus.BAD_REQUEST,e.getMessage().toUpperCase());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    private ResponseEntity<HttpResponse> methodNotSupportedException(HttpRequestMethodNotSupportedException e){
        HttpMethod supportedMethod = Objects.requireNonNull(e.getSupportedHttpMethods()).iterator().next();
        return createHttpResponse(HttpStatus.METHOD_NOT_ALLOWED,String.format(METHOD_IS_NOT_ALLOWED,supportedMethod));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    private ResponseEntity<HttpResponse> methodNotSupportedException(NoHandlerFoundException e){
        return createHttpResponse(HttpStatus.BAD_REQUEST,"This page was not found");
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<HttpResponse> internalServerErrorException(Exception e){
        return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR,INTERNAL_SERVER_ERROR_MSG);
    }

    @ExceptionHandler(NoResultException.class)
    private ResponseEntity<HttpResponse> notFoundException(NoResultException e){
        logger.error(e.getMessage());
        return createHttpResponse(HttpStatus.NOT_ACCEPTABLE,e.getMessage());
    }

    @ExceptionHandler(IOException.class)
    private ResponseEntity<HttpResponse> notFoundException(IOException e){
        logger.error(e.getMessage());
        return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR,ERROR_PROCESSING_FILE);
    }
    @RequestMapping(ERROR_PATH)
    private ResponseEntity<HttpResponse> notFound404(){
        return createHttpResponse(HttpStatus.NOT_FOUND,"There is no mapping for this URL");
    }


    public String getErrorPath(){
        return ERROR_PATH;
    }


}
