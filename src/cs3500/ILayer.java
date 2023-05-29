package cs3500;

/**
 * Interface representing the operations a layer of an multi-image should be able
 * to perform. Includes the ability to get the image at the layer, get the
 * visibility of the layer, and change the visibility of the layer.
 */
public interface ILayer {

  /**
   * Observes the image stored in the layer.
   *
   * @return the layer stored in the image
   */
  IImage getImage();

  /**
   * Changes the visibility to match the passed in visibility status.
   *
   * @param status the new visibility status
   */
  void changeVisibility(boolean status);

  /**
   * Observes the visibility of the layer.
   *
   * @return whether or not the layer is visible.
   */
  boolean getVisibility();
}
