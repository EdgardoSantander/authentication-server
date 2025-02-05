package com.ganzo.delivery.authentication_server.dto.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseAuthentication implements Serializable {

    private String jwt;
    @JsonFormat(pattern = "DD/MM/yyyy HH:MM:ssss")
    private Date expire;

}
