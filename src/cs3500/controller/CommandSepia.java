package cs3500.controller;

import cs3500.model.IMultiImageProcessingModel;

/**
 * This class represents a command object that applies a sepia filter to the current layer in the
 * model.
 */
public class CommandSepia implements IImageProcessingCommand {

  @Override
  public void operate(IMultiImageProcessingModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    model.changeLayerImage(model.sepia(model.getCurrentImage()));
  }
}
