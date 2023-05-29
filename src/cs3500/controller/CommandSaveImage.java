package cs3500.controller;

import cs3500.model.IMultiImageProcessingModel;
import cs3500.ImageUtil;

/**
 * This class represents a command object that saves the top-most visible layer in the model under
 * a given filename.
 */
public class CommandSaveImage implements IImageProcessingCommand {

  private final String filename;

  /**
   * Creates a CommandSaveImage Object.
   * @param filename the name and type of the file that will be stored
   * @throws IllegalArgumentException if the filename is null
   */

  public CommandSaveImage(String filename) throws IllegalArgumentException {
    if (filename == null) {
      throw new IllegalArgumentException("Cannot have a null filename!!");
    }
    this.filename = filename;
  }

  @Override
  public void operate(IMultiImageProcessingModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Cannot have a null model!!");
    }
    if (getImageType().equals("ppm")) {
      ImageUtil.exportPPMImage(model.getTopmostVisible().toString(), filename);
    } else if (getImageType().equals("jpg")
        || getImageType().equals("png") || getImageType().equals("jpeg")) {
      ImageUtil.exportImage(model.getTopmostVisible(), filename, getImageType());
    } else {
      throw new IllegalArgumentException("This image type is not supported!!");
    }
  }

  /**
   * Gets the type of the image passed in. Whether it is png, jpeg, jpg, ppm, or
   * any other file type.
   *
   * @return the type of the image passed in
   */
  private String getImageType() {
    StringBuilder imageType = new StringBuilder();
    for (int i = 0; i < filename.length(); i++) {
      if (filename.charAt(i) == '.') {
        imageType.append(filename.substring(i + 1));
      }
    }
    return imageType.toString().toLowerCase();
  }
}
