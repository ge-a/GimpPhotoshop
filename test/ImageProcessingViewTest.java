import static org.junit.Assert.assertEquals;

import cs3500.view.IImageProcessingView;
import cs3500.view.ImageProcessingView;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the ImageProcessingView class.
 */
public class ImageProcessingViewTest {

  IImageProcessingView view;
  Appendable out;

  @Before
  public void init() {
    this.out = new StringBuilder();
    this.view = new ImageProcessingView(this.out);
  }

  @Test
  public void testRenderMessageRandomMessage() {
    try {
      this.view.renderMessage("random message");
    } catch (IOException e) {
      // don't do anything here
    }
    assertEquals("random message", this.out.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRenderMessageNullMessage() {
    try {
      this.view.renderMessage(null);
    } catch (IOException e) {
      // don't do anything here
    }
  }
}