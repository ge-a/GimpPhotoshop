package cs3500.controller;

import cs3500.model.IMultiImageProcessingModel;

/**
 * This class represents a command object
 * that blurs the image at the current layer.
 */
public class CommandBlur implements IImageProcessingCommand {

  @Override
  public void operate(IMultiImageProcessingModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Cannot have a null model!!");
    }
    model.changeLayerImage(model.blur(model.getCurrentImage()));
  }
}
