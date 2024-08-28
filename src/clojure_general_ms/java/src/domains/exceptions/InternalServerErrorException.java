package src.domains.exceptions;

public class InternalServerErrorException extends RuntimeException {

  public InternalServerErrorException(final String message) {
    super(message);
  }

  public InternalServerErrorException(final String message, final Exception cause) {
    super(message, cause);
  }
}
