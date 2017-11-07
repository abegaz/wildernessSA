package logging;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/** Controls the login screen */
public class LoginController {
  @FXML private TextField user;
  @FXML private TextField password;
  @FXML private Button loginButton;

  public void initialize() {}

  public void initManager(final LoginManager loginManager) {
    loginButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        String sessionID = authorize();
        if (sessionID != null) {
          loginManager.authenticated(sessionID);
        }
      }
    });
  }

  /**
   * Check authorization credentials.
   *
   * If accepted, return a sessionID for the authorized session
   * otherwise, return null.
   */
  private String authorize() {
    return
      "admin".equals(user.getText()) && "admin".equals(password.getText())
            ? generateSessionID()
            : null;
  }

  private static int sessionID = 0;

  private String generateSessionID() {
    sessionID++;
    return "Welcome to the Winderness Admin " + sessionID;
  }
}
