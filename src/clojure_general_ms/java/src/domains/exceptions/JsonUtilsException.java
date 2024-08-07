package src.domains.exceptions;

public class JsonUtilsException extends RuntimeException {

  public JsonUtilsException(final Exception cause) {
    super(cause);
  }

  public JsonUtilsException(final String message, final Exception cause) {
    super(message, cause);
  }
}
