package com.nextdv.api.common;

public class CommonResponse<T> {

  private final boolean success;
  private final T data;
  private final String message;

  private CommonResponse(boolean success, T data, String message) {
    this.success = success;
    this.data = data;
    this.message = message;
  }

  public static <T> CommonResponse<T> ok(T data) {
    return new CommonResponse<>(true, data, null);
  }

  public static <T> CommonResponse<T> ok(T data, String message) {
    return new CommonResponse<>(true, data, message);
  }

  public static <T> CommonResponse<T> fail(String message) {
    return new CommonResponse<>(false, null, message);
  }

  public boolean isSuccess() {
    return success;
  }

  public T getData() {
    return data;
  }

  public String getMessage() {
    return message;
  }
}
