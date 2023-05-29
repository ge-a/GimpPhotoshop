package cs3500.view;

import java.io.IOException;

/**
 * Interface representing the view of the image processing program. Supports
 * writing a messsage to the console.
 */
public interface IImageProcessingView {

  /**
   * Renders a message to be output in the console.
   * @param message the message to be output
   * @throws IOException if it cannot write to the console
   * @throws IllegalArgumentException if message is null
   */
  void renderMessage(String message) throws IOException, IllegalArgumentException;

}
