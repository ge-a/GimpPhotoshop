package cs3500.model;

import cs3500.IImage;
import cs3500.ILayer;
import java.util.ArrayList;

/**
 * Interface representing operations a multi-image model should be able to perform
 * that the view can have access to as they do not mutate the model in any way.
 */
public interface IModelView {
  /**
   * Returns the IImage in the top-most visible layer.
   * @return the IImage in the top-most visible layer
   * @throws IllegalStateException if there are no visible images
   */
  IImage getTopmostVisible() throws IllegalStateException;

  /**
   * Returns the IImage in the current layer of the multi-image.
   * @return the IImage in the current layer of the multi-image
   * @throws IllegalStateException if there are no layers to get an image from
   */
  IImage getCurrentImage() throws IllegalStateException;

  /**
   * Returns all the layers in the multi-image.
   * @return a list of all the layers in the multi-image
   */
  ArrayList<ILayer> getLayers();

  /**
   * Returns the list of currents.
   * @return the list of current layers, with the first element being the most current.
   */
  ArrayList<Integer> getCurrentOrder();
}
