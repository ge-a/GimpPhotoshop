import cs3500.controller.IImageProcessingController;
import cs3500.model.MultiImageModelDownScaleSupport;
import cs3500.view.IView;
import cs3500.view.IViewListener;
import java.io.IOException;

/**
 * Mock controller class for testing the MyFrame class.
 */
public class MockController implements IImageProcessingController, IViewListener {

  private final IView mockView;
  private final Appendable out;

  /**
   * Constructs a mock controller object.
   * @param view the view of the image processing program
   * @param model the model of the image processing program
   * @param out the output
   */
  public MockController(IView view, MultiImageModelDownScaleSupport model, Appendable out) {
    if (view == null || model == null || out == null) {
      throw new IllegalArgumentException("Cannot have null view or model");
    }
    this.mockView = view;
    this.mockView.registerViewEventListener(this);
    this.out = out;
  }

  @Override
  public void runPhotoshop() throws IllegalArgumentException {
    // this method will never get called as it is in the mock so
    // we do not have anything in it
  }

  /**
   * Writes a message out to the console.
   *
   * @param message the message to be written
   * @throws IllegalStateException if it cannot write to the console
   * @throws IllegalArgumentException if the message is null
   */
  private void write(String message)
      throws IllegalStateException, IllegalArgumentException {
    if (message == null) {
      throw new IllegalArgumentException("Cannot have a null message");
    }
    try {
      this.out.append(message);
    } catch (IOException io) {
      throw new IllegalStateException("Appendable failed, test should fail");
    }
  }

  /**
   * Writes a message to the console through the mock view.
   *
   * @param message the message to be written
   * @throws IllegalStateException if it cannot write to the console
   * @throws IllegalArgumentException if the message is null
   */
  private void writeView(String message)
      throws IllegalStateException, IllegalArgumentException {
    if (message == null) {
      throw new IllegalArgumentException("Cannot have a null message");
    }
    try {
      mockView.renderMessage(message);
    }
    catch (IOException io) {
      throw new IllegalStateException("Writing to View failed, test should fail");
    }
  }

  @Override
  public void handleCreateEvent() {
    write("handleCreateEvent");
    writeView("Handled createEvent");
  }

  @Override
  public void handleLoadEvent(String file) throws IllegalArgumentException {
    write("handleLoadEvent " + file);
    writeView("Handled loadEvent");
  }

  @Override
  public void handleCurrentEvent(String layerNum) throws IllegalArgumentException {
    write("handleCurrentEvent " + layerNum);
    writeView("Handled CurrentEvent");

  }

  @Override
  public void handleInvisibleEvent(String layerNum) throws IllegalArgumentException {
    write("handleInvisibleEvent " + layerNum);
    writeView("Handled InvisibleEvent");
  }

  @Override
  public void handleVisibleEvent(String layerNum) throws IllegalArgumentException {
    write("handleVisibleEvent " + layerNum);
    writeView("Handled visibleEvent");
  }

  @Override
  public void handleSaveEvent(String filename) throws IllegalArgumentException {
    write("handleSaveEvent " + filename);
    writeView("Handled saveEvent");
  }

  @Override
  public void handleRemoveEvent(String layerNum) throws IllegalArgumentException {
    write("handleRemoveEvent " + layerNum);
    writeView("Handled remove event");
  }

  @Override
  public void handleBlurEvent() {
    write("handleBlurEvent");
    writeView("Handled blur event");
  }

  @Override
  public void handleSharpenEvent() {
    write("handleSharpenEvent");
    writeView("Handled sharpen event");
  }

  @Override
  public void handleSepiaEvent() {
    write("handleSepiaEvent");
    writeView("Handled sepia event");
  }

  @Override
  public void handleGreyscaleEvent() {
    write("handleGreyscaleEvent");
    writeView("Handled greyscale event");
  }

  @Override
  public void handleSaveAllEvent(String filetype, String folderName)
      throws IllegalArgumentException {
    if (filetype == null || folderName == null) {
      throw new IllegalArgumentException("Cannot have null filetype or foldername");
    }
    write("handleSaveAllEvent " + filetype + " " + folderName);
    writeView("Handled saveAll event");
  }

  @Override
  public void handleLoadMultiImageEvent(String filename) throws IllegalArgumentException {
    if (filename == null) {
      throw new IllegalArgumentException("filename cannot be null");
    }
    write("handleLoadMultiImageEvent " + filename);
    writeView("Handled loadmultiimage event");
  }

  @Override
  public void handleCreateCheckerboard(String tileSize, String rows, String cols)
      throws IllegalArgumentException {
    if (tileSize == null || rows == null || cols == null) {
      throw new IllegalArgumentException("Tilesize, rows, cols cannot be null");
    }
    write("handledCreateCheckerboard " + tileSize + " " + rows + " " + cols);
    writeView("handled createCheckerboard event");
  }

  @Override
  public void handleDownScale(String newWidth, String newHeight) {
    if (newWidth == null || newHeight == null) {
      throw new IllegalArgumentException("Cannot have null width or neight");
    }
    write("handledDownScale " + newWidth + " " + newHeight);
    writeView("handled downscale event");
  }
}
