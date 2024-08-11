package com.movies.admin.service.api;

import com.movies.admin.model.User;

import java.io.IOException;
import java.util.List;

public interface UserService {
    List<User> list() throws IOException, InterruptedException;
    void delete(Integer id) throws IOException, InterruptedException;
}
