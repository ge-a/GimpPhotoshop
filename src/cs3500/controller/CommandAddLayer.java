package cs3500.controller;

import cs3500.model.IMultiImageProcessingModel;

/**
 * This class that represends a command object that adds a layer
 * to the list of layers within the model.
 */
public class CommandAddLayer implements IImageProcessingCommand {

  @Override
  public void operate(IMultiImageProcessingModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Cannot have a null model!!");
    }
    model.addEmptyLayer();
  }
}
