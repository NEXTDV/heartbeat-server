package com.nextdv.api.common;

/**
 * 클래스명: CommonResponse
 * 작성자: JBumLee
 *
 * API 공통 응답 래퍼 클래스
 */
public class CommonResponse<T> {

  private final boolean success;
  private final T data;
  private final String message;

  private CommonResponse(boolean success, T data, String message) {
    this.success = success;
    this.data = data;
    this.message = message;
  }

  /**
   * 메소드이름: ok
   * 데이터를 포함한 성공 응답을 생성한다
   *
   * @param data 응답 데이터
   * @return 성공 응답 객체
   */
  public static <T> CommonResponse<T> ok(T data) {
    return new CommonResponse<>(true, data, null);
  }

  /**
   * 메소드이름: ok
   * 데이터와 메시지를 포함한 성공 응답을 생성한다
   *
   * @param data 응답 데이터, message - 응답 메시지
   * @return 성공 응답 객체
   */
  public static <T> CommonResponse<T> ok(T data, String message) {
    return new CommonResponse<>(true, data, message);
  }

  /**
   * 메소드이름: fail
   * 실패 메시지를 포함한 실패 응답을 생성한다
   *
   * @param message 실패 메시지
   * @return 실패 응답 객체
   */
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
