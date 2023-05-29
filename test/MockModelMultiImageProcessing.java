import cs3500.IImage;
import cs3500.model.MultiImageProcessingModel;
import java.io.IOException;

/**
 * The mock model to help test the ImageProcessingController.
 */
public class MockModelMultiImageProcessing extends MultiImageProcessingModel {

  Appendable ap;

  /**
   * Constructs a MockModeMultiImageProcessing object.
   * @param ap the output of the mock model
   */
  public MockModelMultiImageProcessing(Appendable ap) {
    this.ap = ap;
  }


  @Override
  public void updateTransparency(int layerNumber, boolean status) throws IllegalArgumentException {
    try {
      ap.append("Transparency updated ").append(Integer.toString(layerNumber)).append("Status: ")
          .append(Boolean.toString(status));
    }
    catch (IOException io) {
      throw new IllegalArgumentException("unable to write to appendable");
    }

  }

  @Override
  public void updateCurrentLayer(int layerNumber) throws IllegalArgumentException {
    try {
      ap.append("Current Layer updated to ").append(Integer.toString(layerNumber));
    }
    catch (IOException io) {
      throw new IllegalArgumentException("unable to write to appendable");
    }
  }

  @Override
  public void removeLayer(int layerNumber) throws IllegalArgumentException {
    try {
      ap.append("Layer").append(Integer.toString(layerNumber)).append("removed");
    }
    catch (IOException io) {
      throw new IllegalArgumentException("unable to write to appendable");
    }
  }

  @Override
  public void addEmptyLayer() throws IllegalArgumentException {
    try {
      ap.append("Layer added ");
    }
    catch (IOException io) {
      throw new IllegalArgumentException("unable to write to appendable");
    }
  }

  @Override
  public void loadNewImage(IImage image) throws IllegalArgumentException {
    try {
      ap.append("Image Loaded with height").append(Integer.toString(image.getHeight()))
      .append(" and width").append(Integer.toString(image.getWidth()));
    }
    catch (IOException io) {
      throw new IllegalArgumentException("unable to write to appendable");
    }
  }

  @Override
  public void changeLayerImage(IImage image) throws IllegalArgumentException {
    try {
      ap.append("Layer changed to image with height").append(Integer.toString(image.getHeight()))
          .append(" and width").append(Integer.toString(image.getWidth()));
    }
    catch (IOException io) {
      throw new IllegalArgumentException("unable to write to appendable");
    }
  }

}
