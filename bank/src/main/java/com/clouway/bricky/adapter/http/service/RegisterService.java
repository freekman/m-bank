package com.clouway.bricky.adapter.http.service;

import com.clouway.bricky.core.Validator;
import com.clouway.bricky.core.db.user.MongoUserRepository;
import com.clouway.bricky.core.db.user.UserRepository;
import com.clouway.bricky.core.user.UserDTO;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.sitebricks.At;
import com.google.sitebricks.client.transport.Json;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Request;
import com.google.sitebricks.headless.Service;
import com.google.sitebricks.http.Get;
import com.google.sitebricks.http.Post;

import java.util.List;

/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */
@Service
@At("/register/new")
public class RegisterService {

  private final UserRepository repository;
  private final Validator<UserDTO> validator;

  @Inject
  public RegisterService(UserRepository repository, Validator<UserDTO> validator) {
    this.repository = repository;
    this.validator = validator;
  }

  @Get
  public Reply<?> lookupUser(Request request) {
    boolean isExisting = repository.isExisting(request.param("username"));
    if (isExisting) {
      return Reply.with(new FormResponse(false, Lists.newArrayList("Username exists"))).as(Json.class);
    }
    return Reply.with(new FormResponse(true, Lists.newArrayList("Username is free"))).as(Json.class);
  }

  @Post
  public Reply<?> register(Request request) {

    UserDTO dto = request.read(UserDTO.class).as(Json.class);

    List<String> messages = Lists.newArrayList();
    if (dto.password == null || dto.username == null || dto.repassword == null) {
      messages.add("Please enter all fields");
    }

    if (!messages.isEmpty()) {
      return Reply.with(new FormResponse(false, messages)).as(Json.class);
    }

    messages = validator.validate(dto);

    if (!messages.isEmpty()) {
      return Reply.with(new FormResponse(false, messages)).as(Json.class);
    }
    boolean successfulRegister = repository.register(dto);
    if (successfulRegister) {
      return Reply.with(new FormResponse(true, Lists.newArrayList("Registration successful"))).as(Json.class);
    }
    return Reply.with(new FormResponse(false, Lists.newArrayList("Username already exists."))).as(Json.class);
  }
}
