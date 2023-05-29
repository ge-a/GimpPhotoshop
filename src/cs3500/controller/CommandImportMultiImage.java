package cs3500.controller;

import cs3500.ImageUtil;
import cs3500.model.IMultiImageProcessingModel;

/**
 * This class represents a command object that imports multiple
 * images in a specified order with specified visibility into the model.
 */
public class CommandImportMultiImage implements IImageProcessingCommand {

  private final String filename;

  /**
   * Constructs a CommandImportMultiImage object.
   *
   * @param filename the name of the file to be imported
   * @throws IllegalArgumentException if the filename is null
   */
  public CommandImportMultiImage(String filename) throws IllegalArgumentException {
    if (filename == null) {
      throw new IllegalArgumentException("Filename cannot be null");
    }
    this.filename = filename;
  }

  @Override
  public void operate(IMultiImageProcessingModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    ImageUtil.readMultiImage(filename, model);
  }
}
