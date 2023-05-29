package cs3500.view;

import java.io.IOException;

/**
 * Implementation of the Image Processing view which renders messages to
 * the console.
 */
public class ImageProcessingView implements IImageProcessingView {
  private Appendable input;


  /**
   * Constructs an ImageProcessingView object.
   *
   * @param input the appendable object to output a message to the console
   */
  public ImageProcessingView(Appendable input) {
    if (input == null) {
      throw new IllegalArgumentException("Argument cannot be null");
    }
    this.input = input;
  }

  @Override
  public void renderMessage(String message) throws IOException, IllegalArgumentException {
    if (message == null) {
      throw new IllegalArgumentException("Cannot have a null message!!");
    }
    input.append(message);
  }
}
