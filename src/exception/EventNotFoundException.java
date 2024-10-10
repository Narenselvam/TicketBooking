package exception;

import java.io.Serial;

public class EventNotFoundException extends Exception {
  @Serial
  private static final long serialVersionUID = 1L;
  public EventNotFoundException(String message) {
    super(message);
  }
}
