package cs3500.controller;

import cs3500.model.IMultiImageModelDownScaleSupport;
import cs3500.model.IMultiImageProcessingModel;

/**
 * This class represents a command object which resizes the image to the inputted
 * specifications.
 */
public class CommandDownScale implements IImageProcessingCommandDownScaleModel {

  private final String newWidth;
  private final String newHeight;

  /**
   * Constructs a CommandDownScale object.
   *
   * @param newWidth  the new width of the image
   * @param newHeight the new height of the image
   * @throws IllegalArgumentException if either of the arguments are null
   */
  public CommandDownScale(String newWidth, String newHeight) throws IllegalArgumentException {
    if (newWidth == null || newHeight == null) {
      throw new IllegalArgumentException("Cannot have null arguments!!");
    }
    this.newWidth = newWidth;
    this.newHeight = newHeight;
  }

  @Override
  public void operate(IMultiImageProcessingModel model) throws IllegalArgumentException {
    throw new IllegalArgumentException("Invalid program version");
  }

  @Override
  public void operate(IMultiImageModelDownScaleSupport model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    try {
      model.downScaleMultiImage(Integer.parseInt(newWidth), Integer.parseInt(newHeight));
    } catch (NumberFormatException nfe) {
      throw new IllegalArgumentException("Invalid Width/Height");
    }
  }
}
