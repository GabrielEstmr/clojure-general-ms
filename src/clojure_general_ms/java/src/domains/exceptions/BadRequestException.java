package src.domains.exceptions;

public class BadRequestException extends RuntimeException {

  private Object[] msgParams;

  public BadRequestException(final String message) {
    super(message);
  }

  public BadRequestException(final String message, Object... params) {
    super(message);
    this.msgParams = params;
  }
}
