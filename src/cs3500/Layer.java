package cs3500;

import java.util.ArrayList;

/**
 * Represents a layer of a multi-image and supports getting the image
 * at the layer, getting the layer's visibility, or changing the layer's visibility.
 */
public class Layer implements ILayer {

  protected IImage image;
  protected boolean visible;

  /**
   * Constructs a layer object.
   *
   * @param image the image stored at the layer
   * @param visible whether or not the layer is visible
   */
  public Layer(IImage image, boolean visible) {
    if (image == null) {
      throw new IllegalArgumentException("Cannot have null image");
    }
    this.image = image;
    this.visible = visible;
  }

  /**
   * Convenience constructor to create a layer object with a
   * blank image that is not visible.
   */
  public Layer() {
    this.image = new Image(new ArrayList<>(),-1, -1);
    this.visible = false;
  }

  @Override
  public IImage getImage() {
    return new Image(this.image.getPixels(), this.image.getHeight(), this.image.getWidth());
  }

  @Override
  public void changeVisibility(boolean status) {
    this.visible = status;
  }

  @Override
  public boolean getVisibility() {
    return this.visible;
  }
}
