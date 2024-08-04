package clojure_general_ms.java.domains.exceptions;

public class ResourceNotFoundException extends RuntimeException {

  private Object[] msgParams;

  public ResourceNotFoundException(final String message) {
    super(message);
  }

  public ResourceNotFoundException(final String message, final Object... params) {
    super(message);
    this.msgParams = params;
  }
}
