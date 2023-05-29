package cs3500.controller;

import cs3500.controller.ImageProcessingControllerCreator.ControllerType;
import cs3500.model.IMultiImageProcessingModel;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class represents a command object that executes a script stored in a file under the given
 * filename.
 */
public class CommandScriptCommand implements IImageProcessingCommand {

  private final String filename;

  /**
   * Creates a CommandScriptCommand Object.
   * @param filename the name of the file which the script is stored
   * @throws IllegalArgumentException if the filename is null
   */
  public CommandScriptCommand(String filename) throws IllegalArgumentException {
    if (filename == null) {
      throw new IllegalArgumentException("Cannot have null filename!!");
    }
    this.filename = filename;
  }

  @Override
  public void operate(IMultiImageProcessingModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Cannot have null model!!");
    }
    try {
      Readable inputStream = new FileReader(filename);
      IImageProcessingController c =
          ImageProcessingControllerCreator
              .createControllerSameReadable(ControllerType.SCRIPTINGANDMANUALINPUT,
                  model, inputStream);
      c.runPhotoshop();
    }
    catch (IOException io) {
      throw new IllegalArgumentException("Unable to read file");
    }
  }
}
