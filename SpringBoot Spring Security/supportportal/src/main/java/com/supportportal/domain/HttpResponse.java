package com.supportportal.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy hh:mm:ss", timezone = "Europe/Oslo")
    private Date timeStamp;
    private int httpstatusCode;
    private HttpStatus httpStatus;
    private String reason;
    private String message;

}
