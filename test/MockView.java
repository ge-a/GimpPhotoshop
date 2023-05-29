import cs3500.view.IView;
import cs3500.view.IViewListener;
import java.io.IOException;

/**
 * Mock view class for testing the MyWindow class.
 */
public class MockView implements IView {

  private IViewListener listener;
  private final Appendable out;

  /**
   * Constructs a mock view object.
   *
   * @param out the output of the class
   */
  public MockView(Appendable out) {
    if (out == null) {
      throw new IllegalArgumentException("Invalid argument");
    }
    this.out = out;
  }

  @Override
  public void renderMessage(String message) throws IllegalArgumentException {
    if (message == null) {
      throw new IllegalArgumentException("Cannot have null message");
    }
    try {
      out.append(message);
    } catch (IOException io) {
      throw new IllegalStateException("Test should fail, appendable failed");
    }
  }

  @Override
  public void registerViewEventListener(IViewListener listener) throws IllegalArgumentException {
    if (listener == null) {
      throw new IllegalArgumentException("listener cannot be null");
    }
    this.listener = listener;
  }

  @Override
  public void askForFocus() {
    try {
      out.append("Focused");
    } catch (IOException io) {
      throw new IllegalStateException("Test should fail, appendable failed");
    }
  }

  /**
   * Loads in a file to the current layer if it is empty.
   *
   * @param s the name of the file to load
   */
  public void fireLoadEvent(String s) {
    this.listener.handleLoadEvent(s);
  }

  /**
   * Saves the topmost visible image.
   *
   * @param s the name of the file to be saved as
   */
  public void fireSaveEvent(String s) {
    this.listener.handleSaveEvent(s);
  }

  /**
   * Saves all of the images in the multi-image and exports them in a folder with the inputted name
   * and of the inputted type.
   *
   * @param filetype   the type to save the images as
   * @param foldername the name of the folder to save it as
   */
  public void fireSaveAllEvent(String filetype, String foldername) {
    this.listener.handleSaveAllEvent(filetype, foldername);
  }

  /**
   * Loads an image with the inputted filename into the multi-image.
   *
   * @param foldername the name of the folder to be loaded in
   */
  public void fireLoadMultiImageEvent(String foldername) {
    this.listener.handleLoadMultiImageEvent(foldername);
  }

  /**
   * Applies the sepia filter at the current image.
   */
  public void fireSepiaEvent() {
    this.listener.handleSepiaEvent();
  }

  /**
   * Blurs the current image.
   */
  public void fireBlurEvent() {
    this.listener.handleBlurEvent();
  }

  /**
   * Applies a greyscale filter to the current image.
   */
  public void fireGreyscaleEvent() {
    this.listener.handleGreyscaleEvent();
  }

  /**
   * Sharpens the current image.
   */
  public void fireSharpenEvent() {
    this.listener.handleSharpenEvent();
  }

  /**
   * Removes the layer at the specified layer number.
   *
   * @param layerNum the layer to be removed
   */
  public void fireRemoveLayerEvent(String layerNum) {
    this.listener.handleRemoveEvent(layerNum);
  }

  /**
   * Creates a layer in the multi-image.
   */
  public void fireCreateLayerEvent() {
    this.listener.handleCreateEvent();
  }

  /**
   * Makes the image at the inputted layer invisible.
   *
   * @param layerNum the number of the layer to be made invisible
   */
  public void fireInvisibleEvent(String layerNum) {
    this.listener.handleInvisibleEvent(layerNum);
  }

  /**
   * Makes the image at the inputted layer visible.
   *
   * @param layerNum the number of the layer to be made visible
   */
  public void fireVisibleEvent(String layerNum) {
    this.listener.handleVisibleEvent(layerNum);
  }

  /**
   * Loads a checkerboard image with the given specifications into the multi-image.
   *
   * @param tileSize the size of a tile in pixels
   * @param rows     the number of tiles in each row
   * @param cols     the number of tiles in each col
   */
  public void fireCreateCheckerBoard(String tileSize, String rows, String cols) {
    this.listener.handleCreateCheckerboard(tileSize, rows, cols);
  }

  /**
   * Creates a new layer in the multi-image.
   *
   * @param layerNum the number of the layer to be made current
   */
  public void fireCurrentEvent(String layerNum) {
    this.listener.handleCurrentEvent(layerNum);
  }
}
