package com.clouway.bricky.adapter.http.service;

import com.clouway.bricky.core.validation.Validator;
import com.clouway.bricky.core.db.user.UserRepository;
import com.clouway.bricky.core.user.User;
import com.clouway.bricky.core.user.UserDTO;
import com.clouway.bricky.core.user.UserDTORule;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.sitebricks.At;
import com.google.sitebricks.client.transport.Json;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Request;
import com.google.sitebricks.headless.Service;
import com.google.sitebricks.http.Get;
import com.google.sitebricks.http.Post;

/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */
@Service
@At("/register/new")
public class RegisterService {

  private final UserRepository repository;
  private Validator<FormResponse, UserDTO> validator;

  @Inject
  public RegisterService(UserRepository repository, Validator<FormResponse, UserDTO> validator) {
    this.repository = repository;
    this.validator = validator;
  }

  @Get
  public Reply<?> lookupUser(Request request) {
    String username = request.param("username");

    if (repository.isExisting(username)) {
      return Reply.with(new FormResponse(false, Lists.newArrayList("Username exists"))).as(Json.class);
    }
    return Reply.with(new FormResponse(true, Lists.newArrayList("Username is free"))).as(Json.class);
  }

  @Post
  public Reply<?> register(Request request) {
    UserDTO dto = request.read(UserDTO.class).as(Json.class);

    FormResponse response = validator.validate(dto, new UserDTORule());
    if (!response.isValid) {
      return Reply.with(response).as(Json.class);
    }

    User user = new User(dto.username, dto.password);
    if (repository.register(user)) {
      return Reply.with(new FormResponse(true, Lists.newArrayList("Registration successful"))).as(Json.class);
    }

    return Reply.with(new FormResponse(false, Lists.newArrayList("Username already exists."))).as(Json.class);
  }

}
