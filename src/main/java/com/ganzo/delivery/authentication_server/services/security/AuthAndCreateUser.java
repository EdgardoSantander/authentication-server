package com.ganzo.delivery.authentication_server.services.security;

import com.ganzo.delivery.authentication_server.dto.auth.RequestAuthentication;
import com.ganzo.delivery.authentication_server.dto.auth.ResponseAuthentication;
import com.ganzo.libreries.entity.User;

public interface AuthAndCreateUser {

    User createAccount(User user) throws Exception;

    ResponseAuthentication authentication(RequestAuthentication authentication) throws Exception;

}
