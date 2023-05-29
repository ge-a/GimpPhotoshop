package cs3500.controller;

import cs3500.IImage;
import cs3500.model.IMultiImageProcessingModel;
import cs3500.ImageUtil;

/**
 * This class represents a command object
 * that imports a specified image into an empty layer.
 */
public class CommandLoadImage implements IImageProcessingCommand {

  private final String filename;

  /**
   * Constructs a CommandLoadImage object.
   *
   * @param filename the name of the file to be imported in
   * @throws IllegalArgumentException if the filename is null
   */
  public CommandLoadImage(String filename) throws IllegalArgumentException {
    if (filename == null) {
      throw new IllegalArgumentException("Cannot have a null filename!!");
    }
    this.filename = filename;
  }

  @Override
  public void operate(IMultiImageProcessingModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Cannot have a null model!!");
    }
    if (getImageType().equals("ppm")) {
      IImage newImage = ImageUtil.readPPM(filename);
      model.loadNewImage(newImage);
    }
    else if (getImageType().equals("jpeg") || getImageType().equals("png")
        || getImageType().equals("jpg")) {
      IImage newImage = ImageUtil.readImage(filename);
      model.loadNewImage(newImage);
    } else {
      throw new IllegalArgumentException("Invalid filetype");
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
