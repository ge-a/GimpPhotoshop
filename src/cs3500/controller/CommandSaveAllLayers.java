package cs3500.controller;

import cs3500.ImageUtil;
import cs3500.model.IMultiImageProcessingModel;

/**
 * This class represents a command object that will save the Multi-layer file with each file saved
 * in the given type, in a folder with the given name, and a provided text file describing
 * the state each layer was in when it was saved.
 */
public class CommandSaveAllLayers implements IImageProcessingCommand {

  private final String filetype;
  private final String folderName;

  /**
   * Creates a CommandSaveAllLayers object.
   * @param filetype the type all the layers will be saved as
   * @param folderName the name of the folder which all the layers will be stored
   * @throws IllegalArgumentException if the filetype or foldername are null
   */
  public CommandSaveAllLayers(String filetype, String folderName) {
    if (filetype == null || folderName == null) {
      throw new IllegalArgumentException("Cannot have a null filetype or foldername!!");
    }
    this.filetype = filetype;
    this.folderName = folderName;
  }

  @Override
  public void operate(IMultiImageProcessingModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Cannot have a null model!!");
    }
    if (filetype.equals("ppm")) {
      ImageUtil.exportLayeredImage(model.getLayers(), filetype, folderName);
    } else if (filetype.equals("jpg")
        || filetype.equals("png") || filetype.equals("jpeg")) {
      ImageUtil.exportLayeredImage(model.getLayers(), filetype, folderName);
    } else {
      throw new IllegalArgumentException("This image type is not supported!!");
    }
  }
}
