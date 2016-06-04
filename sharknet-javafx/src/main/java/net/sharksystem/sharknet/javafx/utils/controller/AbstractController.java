package net.sharksystem.sharknet.javafx.utils.controller;

import javafx.scene.Parent;
import net.sharksystem.sharknet.javafx.context.ViewContext;
import net.sharksystem.sharknet.javafx.controls.ActionBar;
import net.sharksystem.sharknet.javafx.i18n.I18N;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * A Controller for a specific view.
 * A view is a fxml file which is load and initialized by this controller.
 * The controller is responsible to manage the specified view.
 */
public abstract class AbstractController {

	/**
	 * The logger for this class
	 */
	private Logger Log = LoggerFactory.getLogger(AbstractController.class);

	/**
	 * The FXML view/file which is related to this controller
	 */
	private URL fxmlFile;

	/**
	 * The current local Resource Bundle.
	 * The {@link ResourceBundle} contains all locale-specific
	 * texts and strings.
	 */
	private ResourceBundle resourceBundle;

	/**
	 * Context of the controlled view.
	 */
	private ViewContext<AbstractController> viewContext;

	/**
	 * Creates a controller for the view which is defined
	 * in the fxml file at the specified location.
     */
	public AbstractController(URL location) {
		this(location, I18N.getResourceBundle());
	}

	/**
	 * <b>This constructor is only for internal use</b>
	 *
	 * Creates a controller for the view which is defined
	 * in the fxml file at the specified location. And specifies
	 * the {code ResourceBundle} which should be used by the controlled
	 * view.
	 *
	 * @param location
	 * @param resources
     */
	protected AbstractController(URL location, ResourceBundle resources) {
		this.fxmlFile = location;
		this.resourceBundle = resources;
	}

	/**
	 * Retrieves the root element of the controlled view.
	 *
	 * @return the root element
     */
	public Parent getRoot() {
		return getContext().getRootNode();
	}

	/**
	 * Provides context information about this controller.
	 *
	 * @return context about this view object.
     */
	public ViewContext<AbstractController> getContext() {
		if (viewContext == null) {
			initiateController();
		}
		return viewContext;
	}

	/**
	 * Initiate the controller and load the corresponding fxml file which contains the
	 * view of this controller.
	 */
	protected void initiateController() {
		try {
			viewContext = ControllerBuilder.getInstance().createBy(this, (Class<AbstractController>) getClass());
		} catch (ControllerLoaderException e) {
			throw new RuntimeException("Failed to initiate controller" + this.getClass(), e);
		}
	}

	@PostConstruct
	private void triggerOnFXMLLoaded() {
		onFxmlLoaded();
	}

	/**
	 * A subclass must implement its view initial logic in this method.
	 *
	 * <p>
	 * Fields which are annotated with {@link javafx.fxml.FXML} are first accessible
	 * while this method is invoked by the {@link AbstractController}.
	 * </p>
	 *
	 * </p>
	 * Called when the fxml file was loaded this is the
	 * equivalent to {@link javafx.fxml.Initializable#initialize(URL, ResourceBundle)}.
	 * <p>
	 *
	 *  NOTE: this method is deprecated please annotate your init method
	 *  with: @PostConstruct
	 */
	@Deprecated
	abstract protected void onFxmlLoaded();

	/**
	 * @return the assigned resource bundle which
	 * is used for the controlled view.
	 * The {@link ResourceBundle} contains all locale-specific
	 * texts and strings.
	 */
	ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

	/**
	 * @return the location of the corresponding fxml file.
	 * This contains the view which is controlled by
	 * this controller.
	 */
	URL getLocation() {
		return fxmlFile;

	}

	/**
	 * Will be called if the controller is terminated.
	 * This can happen if the application exists or the
	 * controller is unregistered from the FrontController.
	 *
	 * Annotate your destroy method with javax.annotation.PreDestroy
	 *
	 * @deprecated
	 */
	public void onShutdown() {
	};

	/**
	 * Will be called before if controller
	 * is resumed from a pause state.
	 * @deprecated
	 */
	public void onResume() {
	};

	/**
	 * Will be called before a controller is paused.
	 * @deprecated
	 */
	public void onPause() {

	}


}