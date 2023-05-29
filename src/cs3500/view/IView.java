package cs3500.view;

/**
 * Represents the operations a GUI view should be able to perform.
 */
public interface IView extends IImageProcessingView {

  /**
   * Adds a listener to the list of listeners stored in the window.
   *
   * @param listener the listener to be added to the view's list of listeners
   * @throws IllegalArgumentException if the listener is null
   */
  void registerViewEventListener(IViewListener listener) throws IllegalArgumentException;

  /**
   * Resets the focus of the GUI.
   */
  void askForFocus();
}
