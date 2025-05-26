import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ATMGUI extends Application {
    private double balance = 1000.0;
    private final int USER_PIN = 1234;
    private Stage window;
    private Scene loginScene, menuScene;

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("ATM Machine");

        // --- Login UI ---
        Label pinLabel = new Label("Enter PIN:");
        PasswordField pinInput = new PasswordField();

        Button loginBtn = new Button("Login");
        Button clearBtn = new Button("Clear");

        loginBtn.setPrefWidth(100);
        clearBtn.setPrefWidth(100);

        HBox loginButtons = new HBox(10, loginBtn, clearBtn);
        loginButtons.setAlignment(Pos.CENTER);

        VBox loginLayout = new VBox(15,
            new Text("ATM Login"),
            pinLabel,
            pinInput,
            loginButtons
        );
        loginLayout.setAlignment(Pos.CENTER);
        loginLayout.setPadding(new Insets(20));

        loginScene = new Scene(loginLayout, 300, 250);

        // --- Menu UI ---
        Button checkBalanceBtn = new Button("Check Balance");
        Button depositBtn = new Button("Deposit");
        Button withdrawBtn = new Button("Withdraw");
        Button exitBtn = new Button("Exit");

        checkBalanceBtn.setPrefWidth(200);
        depositBtn.setPrefWidth(200);
        withdrawBtn.setPrefWidth(200);
        exitBtn.setPrefWidth(200);

        VBox menuLayout = new VBox(15,
            checkBalanceBtn,
            depositBtn,
            withdrawBtn,
            exitBtn
        );
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.setPadding(new Insets(20));
        menuScene = new Scene(menuLayout, 300, 300);

        // --- Event Handling ---
        loginBtn.setOnAction(e -> {
            try {
                int pin = Integer.parseInt(pinInput.getText());
                if (pin == USER_PIN) {
                    window.setScene(menuScene);
                } else {
                    showAlert("Incorrect PIN", "Please try again.");
                }
            } catch (NumberFormatException ex) {
                showAlert("Invalid Input", "Please enter numbers only.");
            }
        });

        clearBtn.setOnAction(e -> pinInput.clear());

        checkBalanceBtn.setOnAction(e -> {
            showAlert("Balance", "Your balance is ₹" + balance);
        });

        depositBtn.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Deposit");
            dialog.setHeaderText("Enter deposit amount:");
            dialog.showAndWait().ifPresent(amountStr -> {
                try {
                    double amount = Double.parseDouble(amountStr);
                    if (amount > 0) {
                        balance += amount;
                        showAlert("Success", "₹" + amount + " deposited.");
                    } else {
                        showAlert("Error", "Enter a positive number.");
                    }
                } catch (NumberFormatException ex) {
                    showAlert("Error", "Invalid amount.");
                }
            });
        });

        withdrawBtn.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Withdraw");
            dialog.setHeaderText("Enter withdraw amount:");
            dialog.showAndWait().ifPresent(amountStr -> {
                try {
                    double amount = Double.parseDouble(amountStr);
                    if (amount > 0 && amount <= balance) {
                        balance -= amount;
                        showAlert("Success", "₹" + amount + " withdrawn.");
                    } else {
                        showAlert("Error", "Insufficient balance or invalid amount.");
                    }
                } catch (NumberFormatException ex) {
                    showAlert("Error", "Invalid amount.");
                }
            });
        });

        exitBtn.setOnAction(e -> window.setScene(loginScene));

        window.setScene(loginScene);
        window.show();
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
