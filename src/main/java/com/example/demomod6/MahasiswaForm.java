package com.example.demomod6;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MahasiswaForm extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Form Biodata Mahasiswa");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(8);
        grid.setHgap(10);

        Label nameLabel = new Label("Nama:");
        GridPane.setConstraints(nameLabel, 0, 0);
        TextField nameInput = new TextField();
        GridPane.setConstraints(nameInput, 1, 0);

        Label nimLabel = new Label("NIM:");
        GridPane.setConstraints(nimLabel, 0, 1);
        TextField nimInput = new TextField();
        GridPane.setConstraints(nimInput, 1, 1);

        Label emailLabel = new Label("Email:");
        GridPane.setConstraints(emailLabel, 0, 2);
        TextField emailInput = new TextField();
        GridPane.setConstraints(emailInput, 1, 2);

        Label fakultasLabel = new Label("Fakultas:");
        GridPane.setConstraints(fakultasLabel, 0, 3);
        TextField fakultasInput = new TextField();
        GridPane.setConstraints(fakultasInput, 1, 3);

        Label jurusanLabel = new Label("Jurusan:");
        GridPane.setConstraints(jurusanLabel, 0, 4);
        TextField jurusanInput = new TextField();
        GridPane.setConstraints(jurusanInput, 1, 4);

        Label alamatLabel = new Label("Alamat:");
        GridPane.setConstraints(alamatLabel, 0, 5);
        TextField alamatInput = new TextField();
        GridPane.setConstraints(alamatInput, 1, 5);

        Label kotaLabel = new Label("Kota:");
        GridPane.setConstraints(kotaLabel, 0, 6);
        TextField kotaInput = new TextField();
        GridPane.setConstraints(kotaInput, 1, 6);

        Button createButton = new Button("Create");
        GridPane.setConstraints(createButton, 1, 7);
        createButton.setOnAction(e -> {
            if (isValidInput(nameInput.getText(), nimInput.getText(), emailInput.getText())) {
                displayData(nameInput.getText(), nimInput.getText(), emailInput.getText(),
                        fakultasInput.getText(), jurusanInput.getText(), alamatInput.getText(), kotaInput.getText());
            } else {
                showAlert("Input tidak valid. Harap periksa kembali input Anda.");
            }
        });

        grid.getChildren().addAll(nameLabel, nameInput, nimLabel, nimInput, emailLabel, emailInput,
                fakultasLabel, fakultasInput, jurusanLabel, jurusanInput, alamatLabel, alamatInput,
                kotaLabel, kotaInput, createButton);

        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);

        primaryStage.show();
    }
    private boolean isValidInput(String nama, String nim, String email) {
        return !nama.isEmpty() && isNumeric(nim) && isValidEmail(email);
    }
    private boolean isNumeric(String str) {
        return str.matches("\\d+");
    }
    private boolean isValidEmail(String email) {
        return email.endsWith("@webmail.umm.ac.id");
    }
    private void displayData(String nama, String nim, String email, String fakultas, String jurusan, String alamat, String kota) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Data Mahasiswa");
        alert.setHeaderText(null);
        alert.setContentText(
                "Nama: " + nama + "\nNIM: " + nim + "\nEmail: " + email + "\nFakultas: " + fakultas + "\nJurusan: "
                        + jurusan + "\nAlamat: " + alamat + "\nKota: " + kota);
        alert.showAndWait();
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
