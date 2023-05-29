package cs3500.controller;

import cs3500.model.IMultiImageModelDownScaleSupport;
import cs3500.model.IMultiImageProcessingModel;
import cs3500.model.MultiImageModelDownScaleSupport;
import cs3500.model.MultiImageProcessingModel;
import cs3500.view.ImageProcessingView;
import cs3500.view.MyWindow;
import java.io.InputStreamReader;

/**
 * This class represents a creator class for ImageProcessing controllers.
 */
public class ImageProcessingControllerCreator {

  /**
   * Type for only type of controller and features available at the moment.
   * <br> SCRIPTINGANDMANUALINPUT supports scripting and manual input commands.
   */
  public enum ControllerType {
    SCRIPTINGANDMANUALINPUT,
    GUI;

    ControllerType() {
      // nothing needed in here for the enumeration
    }
  }

  /**
   * Returns the corresponding ImageProcessingController given a ControllerType.
   *
   * @param type the ControllerTYpe to be generated
   * @return returns a new IImageProcessingController
   * @throws IllegalArgumentException if the ControllerType provided is invalid
   */
  public static IImageProcessingController create(ControllerType type) {
    if (type == null) {
      throw new IllegalArgumentException("Cannot have null gametype");
    }
    switch (type) {
      case SCRIPTINGANDMANUALINPUT:
        return new ImageProcessingController(
            new MultiImageProcessingModel(), new InputStreamReader(System.in),
            new ImageProcessingView(System.out));
      case GUI:
        IMultiImageModelDownScaleSupport model = new MultiImageModelDownScaleSupport();
        MyWindow window = new MyWindow(model);
        window.setVisible(true);
        return new ImageProcessingGUIController(model, window);
      default :
        throw new IllegalArgumentException("Invalid Type");
    }
  }

  /**
   * Returns the corresponding ImageProcessingController with a particular model given a
   * ControllerType and IMultiImageProcessingModel model.
   *
   * @param type  the ControllerTYpe to be generated
   * @param model the model being provided to the Controller
   * @param input the input provided to the controller
   * @return returns a new IImageProcessingController
   * @throws IllegalArgumentException if the ControllerType provided is invalid
   */
  public static IImageProcessingController createControllerSameReadable(ControllerType type,
      IMultiImageProcessingModel model, Readable input) {
    if (type == null || model == null || input == null) {
      throw new IllegalArgumentException("Cannot have null argument");
    }
    if (type == ControllerType.SCRIPTINGANDMANUALINPUT) {
      return new ImageProcessingController(
          model, input,
          new ImageProcessingView(System.out));
    }
    throw new IllegalArgumentException("Invalid Type");
  }

}
