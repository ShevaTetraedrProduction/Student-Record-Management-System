package sample;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class optionController implements Initializable {

    @FXML
    private AnchorPane mainPane;
    @FXML
    private Pane myPane;
    @FXML
    private JFXButton classes_btn, students_btn, journlal_btn, menu_btn, exit_btn;
    @FXML
    private Label info_label;
    @FXML
    private JFXButton changeLogin_btn, changePassword_btn;
    @FXML
    private TextField password_field, nickName_field;

    int id = new authenticController().getId();

    @FXML
    void setChangeLogin_btn(ActionEvent event) {
        if (!nickName_field.isVisible()) {
            nickName_field.setVisible(true);
            password_field.setVisible(false);
            return;
        }

        String query = "UPDATE users_table SET login = ? WHERE user_id = ?";

        if (nickName_field.getLength() == 0) return;
        HandlerDb.executeQuery(query, new String[]{nickName_field.getText()}, new int[]{id});
        nickName_field.setText("");
        HelpMethod.Message(COLORS.YELLOW, "Логін змінений");
    }

    @FXML
    void setChangePassword_btn(ActionEvent event) {
        if (!password_field.isVisible()) {
            password_field.setVisible(true);
            nickName_field.setVisible(false);
            return;
        }

        String query = "UPDATE users_table SET hash_password = ? WHERE user_id = ?";

        if (password_field.getLength() == 0) return;
        HandlerDb.executeQuery(query, new String[]{String.valueOf(HelpMethod.getMd5(password_field.getText()))}, new int[]{id});
        password_field.setText("");
        HelpMethod.Message(COLORS.YELLOW, "Пароль змінений");
    }

    // navigation
    @FXML
    void setClasses_btn(ActionEvent event) {  HelpMethod.makeFadeOut(mainPane, WINDOWS.CLASSES);  }
    @FXML
    void setExit_btn(ActionEvent event) {  HelpMethod.makeFadeOut(mainPane, WINDOWS.Authentic);  }
    @FXML
    void setJournal_btn(ActionEvent event) {  HelpMethod.makeFadeOut(mainPane, WINDOWS.JOURNAL);  }
    @FXML
    void setMenu_btn(ActionEvent event) {   HelpMethod.makeFadeOut(mainPane, WINDOWS.MENU); }
    @FXML
    void setStudents_btn(ActionEvent event) {  HelpMethod.makeFadeOut(mainPane, WINDOWS.STUDENTS);  }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        info_label.setText(info_label.getText() + "  " +  getName(id));
    }

    public static String getName(int id) {
        String name = HandlerDb.getOneStr(
            "SELECT first_name FROM students_table WHERE user_id = ?", new Integer[]{id});
        String lastName = HandlerDb.getOneStr(
            "SELECT last_name FROM students_table WHERE user_id = ?", new Integer[]{id});
        return name + " " + lastName;
    }
}