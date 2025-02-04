package com.ganzo.delivery.authentication_server.dto.auth;

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
    private Date expire;

}
