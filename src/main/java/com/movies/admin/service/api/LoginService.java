package com.movies.admin.service.api;

import com.movies.admin.service.api.dto.Token;
import com.movies.admin.service.api.request.LoginRequest;

import java.io.IOException;

public interface LoginService {
    Token login(LoginRequest loginRequest) throws IOException, InterruptedException;
}
