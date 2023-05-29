package cs3500.controller;

import cs3500.model.IMultiImageProcessingModel;

/**
 * This class represents a command object that will remove a layer located at
 * the provided layer number from the multilayer image in the provided model.
 */
public class CommandRemoveLayer implements IImageProcessingCommand {
  private final String layer;

  /**
   * Creates a CommandRemoveLayer object.
   * @param layer the layer number which will be removed
   */
  public CommandRemoveLayer(String layer) {
    if (layer == null) {
      throw new IllegalArgumentException("Cannot have a null layer!!");
    }
    this.layer = layer;
  }

  @Override
  public void operate(IMultiImageProcessingModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Cannot have a null model!!");
    }

    try {
      model.removeLayer(Integer.parseInt(layer));
    }
    catch (NumberFormatException nfe) {
      throw new IllegalArgumentException("Invalid layer number");
    }
  }
}
