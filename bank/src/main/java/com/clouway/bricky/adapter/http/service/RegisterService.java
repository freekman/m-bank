package com.clouway.bricky.adapter.http.service;

import com.clouway.bricky.core.db.user.UserRepository;
import com.clouway.bricky.core.user.User;
import com.clouway.bricky.core.user.UserDTO;
import com.clouway.bricky.core.user.UserDTORule;
import com.clouway.bricky.core.validation.Validator;
import com.google.inject.Inject;
import com.google.sitebricks.At;
import com.google.sitebricks.client.transport.Json;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Request;
import com.google.sitebricks.headless.Service;
import com.google.sitebricks.http.Get;
import com.google.sitebricks.http.Post;

import static javax.servlet.http.HttpServletResponse.SC_ACCEPTED;
import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;

/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */
@Service
@At("/register/new")
public class RegisterService {

  private final UserRepository repository;
  private Validator<MessagesDTO, UserDTO> validator;

  @Inject
  public RegisterService(UserRepository repository, Validator<MessagesDTO, UserDTO> validator) {
    this.repository = repository;
    this.validator = validator;
  }

  @Get
  public Reply<?> lookupUser(Request request) {
    String username = request.param("username");

    if (repository.isExisting(username)) {
      return Reply.with("Username exists").as(Json.class).status(SC_FORBIDDEN);
    }
    return Reply.with("Username is free").as(Json.class).status(SC_ACCEPTED);
  }

  @Post
  public Reply<?> register(Request request) {
    UserDTO dto = request.read(UserDTO.class).as(Json.class);

    MessagesDTO response = validator.validate(dto, new UserDTORule());
    if (!response.messages.isEmpty()) {
      return Reply.with(response).as(Json.class).status(SC_FORBIDDEN);
    }

    User user = new User(dto.username, dto.password);
    if (repository.register(user)) {
      return Reply.with(new MessagesDTO("Registration successful")).as(Json.class).status(SC_ACCEPTED);
    }

    return Reply.with(new MessagesDTO("Username already exists.")).as(Json.class).status(SC_FORBIDDEN);
  }

}
