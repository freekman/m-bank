package com.clouway.bricky;

import com.google.gson.Gson;
import com.google.inject.TypeLiteral;
import com.google.sitebricks.client.Transport;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Set;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public class JsonM implements Transport {

  private Gson gson = new Gson();
  private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Override
  public <T> T in(InputStream inputStream, Class<T> aClass) throws IOException {

    String json = new BufferedReader(new BufferedReader(new InputStreamReader(inputStream))).readLine();

    System.out.println("JsonM json: " + json);

    T dto = gson.fromJson(json, aClass);

    Set<ConstraintViolation<T>> constraintViolations = validator.validate(dto, aClass);

    if (constraintViolations.isEmpty()) {
      return dto;
    }

    throw new JsonParseException();
  }

  @Override
  public <T> T in(InputStream inputStream, TypeLiteral<T> typeLiteral) throws IOException {
    return null;
  }

  @Override
  public <T> void out(OutputStream outputStream, Class<T> aClass, T t) throws IOException {

  }

  @Override
  public String contentType() {
    return null;
  }
}
