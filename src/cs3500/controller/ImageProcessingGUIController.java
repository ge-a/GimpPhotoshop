package cs3500.controller;

import cs3500.model.IMultiImageModelDownScaleSupport;
import cs3500.view.IViewListener;
import cs3500.view.IView;
import java.io.IOException;

/**
 * The controller of the image processing program which allows users to interact with the model
 * through a graphical user interface to manipulate a multi-layered image.
 */
public class ImageProcessingGUIController implements IImageProcessingController, IViewListener {

  private final IMultiImageModelDownScaleSupport model;
  private final IView view;

  /**
   * Constructs an ImageProcessingGUIController object.
   *
   * @param model the model of the image processing program
   * @param view  the GUI view
   * @throws IllegalArgumentException if the model or view are null
   */
  public ImageProcessingGUIController(IMultiImageModelDownScaleSupport model, IView view)
      throws IllegalArgumentException {
    if (model == null || view == null) {
      throw new IllegalArgumentException("Cannot have a null model or view!!");
    }
    this.model = model;
    this.view = view;
    this.view.registerViewEventListener(this);
  }

  @Override
  public void runPhotoshop() {
    // this method does not need to do anything because no
    // text based input is made as all of the user interaction
    // is handled through the graphical user interface
  }

  /**
   * Relays a string message to be displayed in the view class.
   *
   * @param message the string message to be relayed to the view
   * @throws IllegalStateException when writing to the appendable fails
   */
  private void makeMessage(String message) throws IllegalStateException {
    try {
      view.renderMessage(message);
    } catch (IOException e) {
      throw new IllegalStateException("Could not write to the appendable");
    }
  }

  @Override
  public void handleCreateEvent() {
    IImageProcessingCommand createCommand = new CommandAddLayer();
    createCommand.operate(this.model);
  }

  @Override
  public void handleLoadEvent(String file) throws IllegalArgumentException {
    if (file == null) {
      throw new IllegalArgumentException("Cannot have null file!!");
    }
    IImageProcessingCommand loadCommand = new CommandLoadImage(file);
    try {
      loadCommand.operate(this.model);
    } catch (IllegalStateException | IllegalArgumentException ia) {
      makeMessage(ia.getMessage());
    }
  }

  @Override
  public void handleCurrentEvent(String layerNum) throws IllegalArgumentException {
    if (layerNum == null) {
      throw new IllegalArgumentException("Cannot have null layer number!!");
    }
    IImageProcessingCommand currentCommand = new CommandMakeLayerCurrent(layerNum);
    try {
      currentCommand.operate(this.model);
    } catch (IllegalStateException | IllegalArgumentException ia) {
      makeMessage(ia.getMessage());
    }
  }

  @Override
  public void handleInvisibleEvent(String layerNum) throws IllegalArgumentException {
    if (layerNum == null) {
      throw new IllegalArgumentException("Cannot have null layer number!!");
    }
    IImageProcessingCommand invisibleCommand = new CommandMakeInvisible(layerNum);
    try {
      invisibleCommand.operate(this.model);
    } catch (IllegalStateException | IllegalArgumentException ia) {
      makeMessage(ia.getMessage());
    }
  }

  @Override
  public void handleVisibleEvent(String layerNum) throws IllegalArgumentException {
    if (layerNum == null) {
      throw new IllegalArgumentException("Cannot have null layer number!!");
    }
    IImageProcessingCommand visibleCommand = new CommandMakeVisible(layerNum);
    try {
      visibleCommand.operate(this.model);
    } catch (IllegalStateException | IllegalArgumentException ia) {
      makeMessage(ia.getMessage());
    }
  }

  @Override
  public void handleSaveEvent(String filename) throws IllegalArgumentException {
    if (filename == null) {
      throw new IllegalArgumentException("Cannot have null file name!!");
    }
    IImageProcessingCommand saveCommand = new CommandSaveImage(filename);
    try {
      saveCommand.operate(this.model);
    } catch (IllegalStateException | IllegalArgumentException ia) {
      makeMessage(ia.getMessage());
    }
  }

  @Override
  public void handleRemoveEvent(String layerNum) throws IllegalArgumentException {
    if (layerNum == null) {
      throw new IllegalArgumentException("Cannot have null layer number!!");
    }
    IImageProcessingCommand removeCommand = new CommandRemoveLayer(layerNum);
    try {
      removeCommand.operate(this.model);
    } catch (IllegalStateException | IllegalArgumentException ia) {
      makeMessage(ia.getMessage());
    }
  }

  @Override
  public void handleBlurEvent() {
    IImageProcessingCommand blurCommand = new CommandBlur();
    try {
      blurCommand.operate(this.model);
    } catch (IllegalStateException | IllegalArgumentException ia) {
      makeMessage(ia.getMessage());
    }
  }

  @Override
  public void handleSharpenEvent() {
    IImageProcessingCommand sharpenCommand = new CommandSharpen();
    try {
      sharpenCommand.operate(this.model);
    } catch (IllegalStateException | IllegalArgumentException ia) {
      makeMessage(ia.getMessage());
    }
  }

  @Override
  public void handleSepiaEvent() {
    IImageProcessingCommand sepiaCommand = new CommandSepia();
    try {
      sepiaCommand.operate(this.model);
    } catch (IllegalStateException | IllegalArgumentException ia) {
      makeMessage(ia.getMessage());
    }
  }

  @Override
  public void handleGreyscaleEvent() {
    IImageProcessingCommand greyscaleCommand = new CommandGreyscale();
    try {
      greyscaleCommand.operate(this.model);
    } catch (IllegalStateException | IllegalArgumentException ia) {
      makeMessage(ia.getMessage());
    }
  }

  @Override
  public void handleSaveAllEvent(String filetype, String folderName)
      throws IllegalArgumentException {
    if (filetype == null || folderName == null) {
      throw new IllegalArgumentException("Cannot have null file type or folder name!!");
    }
    IImageProcessingCommand saveAllCommand = new CommandSaveAllLayers(filetype, folderName);
    try {
      saveAllCommand.operate(this.model);
    } catch (IllegalStateException | IllegalArgumentException ia) {
      makeMessage(ia.getMessage());
    }

  }

  @Override
  public void handleLoadMultiImageEvent(String filename) throws IllegalArgumentException {
    if (filename == null) {
      throw new IllegalArgumentException("Cannot have null file name!!");
    }
    IImageProcessingCommand loadMultiImageCommand = new CommandImportMultiImage(filename);

    try {
      loadMultiImageCommand.operate(this.model);
    } catch (IllegalStateException | IllegalArgumentException ia) {
      makeMessage(ia.getMessage());
    }
  }

  @Override
  public void handleCreateCheckerboard(String tileSize, String rows, String cols)
      throws IllegalArgumentException {
    if (tileSize == null || rows == null || cols == null) {
      throw new IllegalArgumentException("Cannot have null checkerboard arguments!!");
    }
    IImageProcessingCommand createNewCheckerboardCommand =
        new CommandCreateNewCheckerboard(tileSize, rows, cols);
    try {
      createNewCheckerboardCommand.operate(this.model);
    } catch (IllegalStateException | IllegalArgumentException ia) {
      makeMessage(ia.getMessage());
    }
  }

  @Override
  public void handleDownScale(String newWidth, String newHeight) throws IllegalArgumentException {
    if (newWidth == null || newHeight == null) {
      throw new IllegalArgumentException("Cannot have null width or height!!");
    }
    IImageProcessingCommandDownScaleModel downScaleCommand
        = new CommandDownScale(newWidth, newHeight);

    try {
      downScaleCommand.operate(this.model);
    } catch (IllegalStateException | IllegalArgumentException ia) {
      makeMessage(ia.getMessage());
    }
  }

}
