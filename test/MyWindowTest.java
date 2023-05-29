import static org.junit.Assert.assertEquals;

import cs3500.model.IMultiImageProcessingModel;
import cs3500.model.MultiImageModelDownScaleSupport;
import cs3500.model.MultiImageProcessingModel;
import cs3500.view.IView;
import cs3500.view.MyWindow;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the MyWindow class.
 */
public class MyWindowTest {
  MockView mockView;
  MockController mockController;
  IView view;
  IMultiImageProcessingModel model;

  @Before
  public void setup() {
    model = new MultiImageProcessingModel();
    view = new MyWindow(model);
  }

  @Test
  public void testWiringHandleLoadEvent() {
    Appendable c_out = new StringBuilder();
    Appendable v_out = new StringBuilder();
    mockView = new MockView(v_out);
    mockController = new MockController(mockView,
        new MultiImageModelDownScaleSupport(),c_out);
    mockView.fireLoadEvent("1");
    assertEquals("handleLoadEvent 1", c_out.toString());
    assertEquals("Handled loadEvent", v_out.toString());
  }

  @Test
  public void testWiringHandleSaveEvent() {
    Appendable c_out = new StringBuilder();
    Appendable v_out = new StringBuilder();
    mockView = new MockView(v_out);
    mockController = new MockController(mockView,
        new MultiImageModelDownScaleSupport(),c_out);
    mockView.fireSaveEvent("filename");
    assertEquals("handleSaveEvent filename", c_out.toString());
    assertEquals("Handled saveEvent", v_out.toString());
  }

  @Test
  public void testWiringHandleLoadMultiImageEvent() {
    Appendable c_out = new StringBuilder();
    Appendable v_out = new StringBuilder();
    mockView = new MockView(v_out);
    mockController = new MockController(mockView,
        new MultiImageModelDownScaleSupport(),c_out);
    mockView.fireLoadMultiImageEvent("foldername");
    assertEquals("handleLoadMultiImageEvent foldername", c_out.toString());
    assertEquals("Handled loadmultiimage event", v_out.toString());
  }

  @Test
  public void testWiringSaveAllEvent() {
    Appendable c_out = new StringBuilder();
    Appendable v_out = new StringBuilder();
    mockView = new MockView(v_out);
    mockController = new MockController(mockView,
        new MultiImageModelDownScaleSupport(),c_out);
    mockView.fireSaveAllEvent("filetype", "foldername");
    assertEquals("handleSaveAllEvent filetype foldername", c_out.toString());
    assertEquals("Handled saveAll event", v_out.toString());
  }

  @Test
  public void testWiringGreyscaleEvent() {
    Appendable c_out = new StringBuilder();
    Appendable v_out = new StringBuilder();
    mockView = new MockView(v_out);
    mockController = new MockController(mockView,
        new MultiImageModelDownScaleSupport(),c_out);
    mockView.fireGreyscaleEvent();
    assertEquals("handleGreyscaleEvent", c_out.toString());
    assertEquals("Handled greyscale event", v_out.toString());
  }

  @Test
  public void testWiringSepiaEvent() {
    Appendable c_out = new StringBuilder();
    Appendable v_out = new StringBuilder();
    mockView = new MockView(v_out);
    mockController = new MockController(mockView,
        new MultiImageModelDownScaleSupport(),c_out);
    mockView.fireSepiaEvent();
    assertEquals("handleSepiaEvent", c_out.toString());
    assertEquals("Handled sepia event", v_out.toString());
  }

  @Test
  public void testWiringSharpenEvent() {
    Appendable c_out = new StringBuilder();
    Appendable v_out = new StringBuilder();
    mockView = new MockView(v_out);
    mockController = new MockController(mockView,
        new MultiImageModelDownScaleSupport(),c_out);
    mockView.fireSharpenEvent();
    assertEquals("handleSharpenEvent", c_out.toString());
    assertEquals("Handled sharpen event", v_out.toString());
  }

  @Test
  public void testWiringBlurEvent() {
    Appendable c_out = new StringBuilder();
    Appendable v_out = new StringBuilder();
    mockView = new MockView(v_out);
    mockController = new MockController(mockView,
        new MultiImageModelDownScaleSupport(),c_out);
    mockView.fireBlurEvent();
    assertEquals("handleBlurEvent", c_out.toString());
    assertEquals("Handled blur event", v_out.toString());
  }

  @Test
  public void testWiringRemoveEvent() {
    Appendable c_out = new StringBuilder();
    Appendable v_out = new StringBuilder();
    mockView = new MockView(v_out);
    mockController = new MockController(mockView,
        new MultiImageModelDownScaleSupport(),c_out);
    mockView.fireRemoveLayerEvent("5");
    assertEquals("handleRemoveEvent 5", c_out.toString());
    assertEquals("Handled remove event", v_out.toString());
  }

  @Test
  public void testWiringSaveEvent() {
    Appendable c_out = new StringBuilder();
    Appendable v_out = new StringBuilder();
    mockView = new MockView(v_out);
    mockController = new MockController(mockView,
        new MultiImageModelDownScaleSupport(),c_out);
    mockView.fireSaveEvent("filename");
    assertEquals("handleSaveEvent filename", c_out.toString());
    assertEquals("Handled saveEvent", v_out.toString());
  }

  @Test
  public void testWiringVisibleEvent() {
    Appendable c_out = new StringBuilder();
    Appendable v_out = new StringBuilder();
    mockView = new MockView(v_out);
    mockController = new MockController(mockView,
        new MultiImageModelDownScaleSupport(),c_out);
    mockView.fireVisibleEvent("8");
    assertEquals("handleVisibleEvent 8", c_out.toString());
    assertEquals("Handled visibleEvent", v_out.toString());
  }

  @Test
  public void testWiringInvisibleEvent() {
    Appendable c_out = new StringBuilder();
    Appendable v_out = new StringBuilder();
    mockView = new MockView(v_out);
    mockController = new MockController(mockView,
        new MultiImageModelDownScaleSupport(),c_out);
    mockView.fireInvisibleEvent("8");
    assertEquals("handleInvisibleEvent 8", c_out.toString());
    assertEquals("Handled InvisibleEvent", v_out.toString());
  }

  @Test
  public void testWiringCurrentEvent() {
    Appendable c_out = new StringBuilder();
    Appendable v_out = new StringBuilder();
    mockView = new MockView(v_out);
    mockController = new MockController(mockView,
        new MultiImageModelDownScaleSupport(),c_out);
    mockView.fireCurrentEvent("8");
    assertEquals("handleCurrentEvent 8", c_out.toString());
    assertEquals("Handled CurrentEvent", v_out.toString());
  }

  @Test
  public void testWiringCreateEvent() {
    Appendable c_out = new StringBuilder();
    Appendable v_out = new StringBuilder();
    mockView = new MockView(v_out);
    mockController = new MockController(mockView,
        new MultiImageModelDownScaleSupport(),c_out);
    mockView.fireCreateLayerEvent();
    assertEquals("handleCreateEvent", c_out.toString());
    assertEquals("Handled createEvent", v_out.toString());
  }

  @Test
  public void testWiringCreateCheckerboardvent() {
    Appendable c_out = new StringBuilder();
    Appendable v_out = new StringBuilder();
    mockView = new MockView(v_out);
    mockController = new MockController(mockView,
        new MultiImageModelDownScaleSupport(),c_out);
    mockView.fireCreateCheckerBoard("50", "8", "8");
    assertEquals("handledCreateCheckerboard 50 8 8", c_out.toString());
    assertEquals("handled createCheckerboard event", v_out.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void registerViewEventListener() {
    view.registerViewEventListener(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void renderMessage() {
    try {
      view.renderMessage(null);
    }
    catch (IOException ia) {
      // should not get here
    }
  }
}