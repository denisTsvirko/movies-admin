package com.movies.admin.service.api;

import com.movies.admin.service.api.request.RegistrationRequest;

import java.io.IOException;

public interface RegistrationService {
    void registration(RegistrationRequest registrationRequest) throws IOException, InterruptedException;
}
