package com.ganzo.delivery.authentication_server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseGeneric<T extends Serializable> implements Serializable {


    private T data;
    private String errorMessage;
    private HttpStatus httpStatus;


}
