package com.example.demomod6;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class JadwalKuliahApp extends Application {

    private TableView<JadwalKuliah> tableView;
    private ObservableList<JadwalKuliah> jadwalList = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Aplikasi Jadwal Kuliah");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(8);
        grid.setHgap(10);

        // Nama Dosen
        Label namaDosenLabel = new Label("Nama Dosen:");
        GridPane.setConstraints(namaDosenLabel, 0, 0);
        TextField namaDosenInput = new TextField();
        namaDosenInput.setPrefWidth(200);
        GridPane.setConstraints(namaDosenInput, 1, 0);

        // Mata Kuliah
        Label mataKuliahLabel = new Label("Mata Kuliah:");
        GridPane.setConstraints(mataKuliahLabel, 0, 1);
        TextField mataKuliahInput = new TextField();
        GridPane.setConstraints(mataKuliahInput, 1, 1);

        // GKB
        Label gkbLabel = new Label("GKB:");
        GridPane.setConstraints(gkbLabel, 0, 2);
        TextField gkbInput = new TextField();
        GridPane.setConstraints(gkbInput, 1, 2);

        // Waktu
        Label waktuLabel = new Label("Waktu:");
        GridPane.setConstraints(waktuLabel, 0, 3);
        TextField waktuInput = new TextField();
        GridPane.setConstraints(waktuInput, 1, 3);

        // Ruangan
        Label ruanganLabel = new Label("Ruangan:");
        GridPane.setConstraints(ruanganLabel, 0, 4);
        TextField ruanganInput = new TextField();
        GridPane.setConstraints(ruanganInput, 1, 4);

        // Button Create
        Button createButton = new Button("Create");
        GridPane.setConstraints(createButton, 1, 5);
        createButton.setOnAction(e -> {
            if (isValidInput(namaDosenInput.getText(), mataKuliahInput.getText(), gkbInput.getText(),
                    waktuInput.getText(), ruanganInput.getText())) {
                JadwalKuliah jadwal = new JadwalKuliah(
                        namaDosenInput.getText(), mataKuliahInput.getText(), gkbInput.getText(),
                        waktuInput.getText(), ruanganInput.getText());

                jadwalList.add(jadwal);
                tableView.setItems(jadwalList);

                clearInputFields(namaDosenInput, mataKuliahInput, gkbInput, waktuInput, ruanganInput);
            } else {
                showAlert("Input tidak valid. Harap periksa kembali input Anda.");
            }
        });

        // Tabel
        tableView = new TableView<>();
        GridPane.setConstraints(tableView, 0, 6, 2, 1);

        TableColumn<JadwalKuliah, String> namaDosenCol = new TableColumn<>("Nama Dosen");
        namaDosenCol.setCellValueFactory(new PropertyValueFactory<>("namaDosen"));

        TableColumn<JadwalKuliah, String> mataKuliahCol = new TableColumn<>("Mata Kuliah");
        mataKuliahCol.setCellValueFactory(new PropertyValueFactory<>("mataKuliah"));

        TableColumn<JadwalKuliah, String> gkbCol = new TableColumn<>("GKB");
        gkbCol.setCellValueFactory(new PropertyValueFactory<>("gkb"));

        TableColumn<JadwalKuliah, String> waktuCol = new TableColumn<>("Waktu");
        waktuCol.setCellValueFactory(new PropertyValueFactory<>("waktu"));

        TableColumn<JadwalKuliah, String> ruanganCol = new TableColumn<>("Ruangan");
        ruanganCol.setCellValueFactory(new PropertyValueFactory<>("ruangan"));

        // Button Delete
        TableColumn<JadwalKuliah, Void> deleteCol = new TableColumn<>("Delete");
        deleteCol.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    JadwalKuliah jadwal = getTableView().getItems().get(getIndex());
                    jadwalList.remove(jadwal);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });
        deleteCol.setMinWidth(60);

        // Button Update
        TableColumn<JadwalKuliah, Void> updateCol = new TableColumn<>("Update");
        updateCol.setCellFactory(param -> new TableCell<>() {
            private final Button updateButton = new Button("Update");

            {
                updateButton.setOnAction(event -> {
                    JadwalKuliah jadwal = getTableView().getItems().get(getIndex());
                    showUpdateDialog(jadwal);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(updateButton);
                }
            }
        });
        updateCol.setMinWidth(60);

        tableView.getColumns().addAll(namaDosenCol, mataKuliahCol, gkbCol, waktuCol, ruanganCol, updateCol, deleteCol);

        grid.getChildren().addAll(namaDosenLabel, namaDosenInput, mataKuliahLabel, mataKuliahInput,
                gkbLabel, gkbInput, waktuLabel, waktuInput, ruanganLabel, ruanganInput, createButton, tableView);

        Scene scene = new Scene(grid, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private boolean isValidInput(String namaDosen, String mataKuliah, String gkb, String waktu, String ruangan) {
        if (namaDosen.isEmpty() || mataKuliah.isEmpty() || gkb.isEmpty() || waktu.isEmpty() || ruangan.isEmpty()) {
            return false;
        }
        if (!isValidTimeFormat(waktu)) {
            return false;
        }
        try {
            Integer.parseInt(gkb);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private boolean isValidTimeFormat(String time) {
        String regex = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
        return time.matches(regex);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearInputFields(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }

    private void showUpdateDialog(JadwalKuliah jadwal) {
        Stage updateStage = new Stage();
        updateStage.setTitle("Update Jadwal Kuliah");

        GridPane updateGrid = new GridPane();
        updateGrid.setPadding(new Insets(20, 20, 20, 20));
        updateGrid.setVgap(8);
        updateGrid.setHgap(10);

        // Nama Dosen
        Label namaDosenLabel = new Label("Nama Dosen:");
        GridPane.setConstraints(namaDosenLabel, 0, 0);
        TextField namaDosenInput = new TextField(jadwal.getNamaDosen());
        GridPane.setConstraints(namaDosenInput, 1, 0);

        // Mata Kuliah
        Label mataKuliahLabel = new Label("Mata Kuliah:");
        GridPane.setConstraints(mataKuliahLabel, 0, 1);
        TextField mataKuliahInput = new TextField(jadwal.getMataKuliah());
        GridPane.setConstraints(mataKuliahInput, 1, 1);

        // GKB
        Label gkbLabel = new Label("GKB:");
        GridPane.setConstraints(gkbLabel, 0, 2);
        TextField gkbInput = new TextField(jadwal.getGkb());
        GridPane.setConstraints(gkbInput, 1, 2);

        // Waktu
        Label waktuLabel = new Label("Waktu:");
        GridPane.setConstraints(waktuLabel, 0, 3);
        TextField waktuInput = new TextField(jadwal.getWaktu());
        GridPane.setConstraints(waktuInput, 1, 3);

        // Ruangan
        Label ruanganLabel = new Label("Ruangan:");
        GridPane.setConstraints(ruanganLabel, 0, 4);
        TextField ruanganInput = new TextField(jadwal.getRuangan());
        GridPane.setConstraints(ruanganInput, 1, 4);

        // Button Update
        Button updateButton = new Button("Update");
        GridPane.setConstraints(updateButton, 1, 5);
        updateButton.setOnAction(e -> {
            if (isValidInput(namaDosenInput.getText(), mataKuliahInput.getText(), gkbInput.getText(), waktuInput.getText(), ruanganInput.getText())) {
                jadwal.setNamaDosen(namaDosenInput.getText());
                jadwal.setMataKuliah(mataKuliahInput.getText());
                jadwal.setGkb(gkbInput.getText());
                jadwal.setWaktu(waktuInput.getText());
                jadwal.setRuangan(ruanganInput.getText());

                tableView.refresh();
                updateStage.close();
            } else {
                showAlert("Input tidak valid. Harap periksa kembali input Anda.");
            }
        });

        updateGrid.getChildren().addAll(namaDosenLabel, namaDosenInput, mataKuliahLabel, mataKuliahInput,
                gkbLabel, gkbInput, waktuLabel, waktuInput, ruanganLabel, ruanganInput, updateButton);

        Scene updateScene = new Scene(updateGrid, 400, 300);
        updateStage.setScene(updateScene);
        updateStage.show();
    }

    public static class JadwalKuliah {
        private String namaDosen;
        private String mataKuliah;
        private String gkb;
        private String waktu;
        private String ruangan;

        public JadwalKuliah(String namaDosen, String mataKuliah, String gkb, String waktu, String ruangan) {
            this.namaDosen = namaDosen;
            this.mataKuliah = mataKuliah;
            this.gkb = gkb;
            this.waktu = waktu;
            this.ruangan = ruangan;
        }

        public String getNamaDosen() {
            return namaDosen;
        }

        public void setNamaDosen(String namaDosen) {
            this.namaDosen = namaDosen;
        }

        public String getMataKuliah() {
            return mataKuliah;
        }

        public void setMataKuliah(String mataKuliah) {
            this.mataKuliah = mataKuliah;
        }

        public String getGkb() {
            return gkb;
        }

        public void setGkb(String gkb) {
            this.gkb = gkb;
        }

        public String getWaktu() {
            return waktu;
        }

        public void setWaktu(String waktu) {
            this.waktu = waktu;
        }

        public String getRuangan() {
            return ruangan;
        }

        public void setRuangan(String ruangan) {
            this.ruangan = ruangan;
        }
    }
}
