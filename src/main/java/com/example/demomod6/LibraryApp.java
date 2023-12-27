package com.example.demomod6;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LibraryApp extends Application {

    private List<Book> bookList = new ArrayList<>();
    private List<Borrower> borrowerList = new ArrayList<>();
    private TableView<Book> tableView = new TableView<>();
    private TableView<Borrower> borrowerTableView = new TableView<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("SISTEM MANAJEMEN PERPUSTAKAAN");

        // Gradient Background
        Stop[] stops = new Stop[]{
                new Stop(0, Color.web("#191970")),
                new Stop(1, Color.web("#FF1493"))
        };
        LinearGradient gradient = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);
        BackgroundFill backgroundFill = new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);

        // Layout
        VBox welcomeLayout = new VBox(20);
        welcomeLayout.setAlignment(Pos.CENTER);
        welcomeLayout.setBackground(background);
        welcomeLayout.setPadding(new Insets(20));

        // Title
        Label titleLabel = new Label("SELAMAT DATANG");
        titleLabel.setFont(Font.font("Rockwell Extra Bold", FontWeight.BOLD, 40));
        titleLabel.setTextFill(Color.WHITE);

        // Animation for changing text color
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(titleLabel.textFillProperty(), Color.GOLD)),
                new KeyFrame(Duration.seconds(1), new KeyValue(titleLabel.textFillProperty(), Color.PINK)),
                new KeyFrame(Duration.seconds(1.6), new KeyValue(titleLabel.textFillProperty(), Color.AQUA))
                // Add more KeyFrames for additional color changes
        );
        timeline.setAutoReverse(true);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // Subtitle
        Label subtitleLabel = new Label("Aplikasi Sistem Manajemen Perpustakaan Kami");
        subtitleLabel.setFont(Font.font("Cambria", FontWeight.BOLD, 25));
        subtitleLabel.setTextFill(Color.WHITE);

        // Continue Button
        Button continueButton = new Button("Klik Untuk Lanjutkan");
        continueButton.setStyle(
                "-fx-background-color: linear-gradient(#61a2b1, #2A5058); " +
                        "-fx-background-radius: 20; " +
                        "-fx-background-insets: 0; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px;"
        );
        continueButton.setOnMouseEntered(e -> continueButton.setStyle(
                "-fx-background-color: linear-gradient(#89CFF0, #5384D0); " +
                        "-fx-background-radius: 20; " +
                        "-fx-background-insets: 0; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14.12px;"
        ));
        continueButton.setOnMouseExited(e -> continueButton.setStyle(
                "-fx-background-color: linear-gradient(#61a2b1, #2A5058); " +
                        "-fx-background-radius: 20; " +
                        "-fx-background-insets: 0; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px;"
        ));
        continueButton.setOnAction(e -> showMainScreen(primaryStage));

        welcomeLayout.getChildren().addAll(titleLabel, subtitleLabel, continueButton);

        Scene welcomeScene = new Scene(welcomeLayout, 600, 400);
        primaryStage.setScene(welcomeScene);
        primaryStage.show();
    }

    private void showMainScreen(Stage primaryStage) {
       
        // Initialize GUI for books
        tableView.setEditable(true);
        TableColumn<Book, String> titleColumn = new TableColumn<>("Judul");
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        titleColumn.setPrefWidth(150); // Set the preferred width
        TableColumn<Book, String> authorColumn = new TableColumn<>("Penulis");
        authorColumn.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
        authorColumn.setPrefWidth(150); // Set the preferred width
        TableColumn<Book, String> availableColumn = new TableColumn<>("Ketersediaan");
        availableColumn.setCellValueFactory(cellData -> {
            StringProperty property = new SimpleStringProperty();
            property.setValue(cellData.getValue().isAvailable() ? "Tersedia" : "Dipinjam");
            return property;
        });
        availableColumn.setPrefWidth(100);
        tableView.getColumns().addAll(titleColumn, authorColumn, availableColumn);

        // Initialize GUI for borrowers
        borrowerTableView.setEditable(true);
        TableColumn<Borrower, String> nameColumn = new TableColumn<>("Nama");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        nameColumn.setPrefWidth(150);
        TableColumn<Borrower, String> nimColumn = new TableColumn<>("NIM");
        nimColumn.setCellValueFactory(cellData -> cellData.getValue().nimProperty());
        nimColumn.setPrefWidth(100);
        TableColumn<Borrower, String> prodiColumn = new TableColumn<>("Program Studi");
        prodiColumn.setCellValueFactory(cellData -> cellData.getValue().prodiProperty());
        prodiColumn.setPrefWidth(150);
        borrowerTableView.getColumns().addAll(nameColumn, nimColumn, prodiColumn);

        // Add some initial books
        bookList.add(new Book("To Kill a Mockingbird", "Harper Lee", true));
        bookList.add(new Book("1984", "George Orwell", false));

        borrowerList.add(new Borrower("John Doe", "123456", "Computer Science"));
        borrowerList.add(new Borrower("Jane Smith", "789012", "Electrical Engineering"));

        tableView.getItems().addAll(bookList);
        borrowerTableView.getItems().addAll(borrowerList);

        // Create GUI components for books
        TextField titleInput = new TextField();
        titleInput.setPromptText("Judul");
        TextField authorInput = new TextField();
        authorInput.setPromptText("Penulis");
        Button addButton = new Button("Tambah Buku");
        addButton.setOnAction(e -> {
            String title = titleInput.getText();
            String author = authorInput.getText();
            // Validasi input
            if (!title.isEmpty() && !author.isEmpty()) {
                Book newBook = new Book(title, author, true);
                bookList.add(newBook);
                tableView.getItems().add(newBook);
                //saveToFile();
                titleInput.clear();
                authorInput.clear();
            } else {
                showInfoPopup("Error", "Judul dan Penulis tidak boleh kosong.");
            }
        });

        Button borrowButton = new Button("Pinjam Buku");
        borrowButton.setOnAction(e -> {
            Book selectedBook = tableView.getSelectionModel().getSelectedItem();
            Borrower selectedBorrower = borrowerTableView.getSelectionModel().getSelectedItem();
            if (selectedBook != null && selectedBorrower != null && selectedBook.isAvailable()) {
                selectedBook.setAvailable(false);
                selectedBook.setBorrower(selectedBorrower);
                tableView.refresh();
                //saveToFile();

                //menampilkan popup
                showInfoPopup("Buku berhasil dipinjam",
                        "Judul Buku: " + selectedBook.getTitle() +
                                "\nMahasiswa: " + selectedBorrower.getName() +
                                "\nNIM Mahasiswa: " + selectedBorrower.getNim() +
                                "\nProgram Studi: " + selectedBorrower.getProdi());

                // Menyimpan hasil data pinjaman dalam file txt
                saveToTxtFile("Buku berhasil dipinjam\n" +
                                "Judul Buku: " + selectedBook.getTitle() +
                                "\nMahasiswa: " + selectedBorrower.getName() +
                                "\nNIM Mahasiswa: " + selectedBorrower.getNim() +
                                "\nProgram Studi: " + selectedBorrower.getProdi(),
                        "output.txt");
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Buku Tidak Tersedia");
                alert.setHeaderText(null);
                alert.setContentText("Buku yang di pilih tidak dapat di pinjam");
                alert.showAndWait();
            }
        });
        borrowButton.setStyle(
                "-fx-background-color: linear-gradient(#61a2b1, #2A5058); " +
                        "-fx-background-radius: 10; " +
                        "-fx-background-insets: 0; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px;"
        );
        borrowButton.setOnMouseEntered(e -> borrowButton.setStyle(
                "-fx-background-color: linear-gradient(#89CFF0, #5384D0); " +
                        "-fx-background-radius: 30; " +
                        "-fx-background-insets: 0; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px;"
        ));
        borrowButton.setOnMouseExited(e -> borrowButton.setStyle(
                "-fx-background-color: linear-gradient(#61a2b1, #2A5058); " +
                        "-fx-background-radius: 30; " +
                        "-fx-background-insets: 0; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px;"
        ));

        Button resetBooksButton = new Button("Reset");
        resetBooksButton.setOnAction(e -> {
            bookList.clear();
            tableView.getItems().clear();
            // saveToFile();
        });

        // Create GUI components for borrowers
        TextField nameInput = new TextField();
        nameInput.setPromptText("Name");
        TextField nimInput = new TextField();

        nimInput.setPromptText("NIM");
        TextField prodiInput = new TextField();

        prodiInput.setPromptText("Program Studi");
        Button addBorrowerButton = new Button("Tambah Peminjam");

        addBorrowerButton.setOnAction(e -> {
            String name = nameInput.getText();
            String nim = nimInput.getText();
            String prodi = prodiInput.getText();
            // Validasi input
            if (!name.isEmpty() && !nim.isEmpty() && !prodi.isEmpty() && isNumeric(nim)) {
                Borrower newBorrower = new Borrower(name, nim, prodi);
                borrowerList.add(newBorrower);
                borrowerTableView.getItems().add(newBorrower);
                //saveToFile();
                nameInput.clear();
                nimInput.clear();
                prodiInput.clear();
            } else {
                showInfoPopup("Error", "Masukkan data diri pengunjung sesuai dengan KTM");
            }
        });

        Button resetBorrowersButton = new Button("Reset");
        resetBorrowersButton.setOnAction(e -> {
            borrowerList.clear();
            borrowerTableView.getItems().clear();
            // saveToFile();
        });

        // Set up the layout
        VBox bookLayout = new VBox(10);
        bookLayout.setPadding(new Insets(10, 10, 10, 10));
        bookLayout.getChildren().addAll(tableView, titleInput, authorInput, addButton, resetBooksButton, borrowButton);

        VBox borrowerLayout = new VBox(10);
        borrowerLayout.setPadding(new Insets(10, 10, 10, 10));
        borrowerLayout.getChildren().addAll(borrowerTableView, nameInput, nimInput, prodiInput, addBorrowerButton, resetBorrowersButton);

        HBox mainLayout = new HBox(20);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.getChildren().addAll(bookLayout, borrowerLayout);

        // Set up the scene
        Scene scene = new Scene(mainLayout, 800, 500);
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();
    }

    private void saveToTxtFile(String content, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            //writer.write(content + "\n\n");

            // Menambahkan informasi peminjaman ke dalam file
            writer.write("Informasi Peminjaman:\n");
            Book borrowedBook = tableView.getSelectionModel().getSelectedItem();
            Borrower borrower = borrowedBook.getBorrower();
            if (borrowedBook != null && borrower != null) {
                writer.write("Judul Buku: " + borrowedBook.getTitle() + "\n");
                writer.write("Mahasiswa: " + borrower.getName() + "\n");
                writer.write("NIM Mahasiswa: " + borrower.getNim() + "\n");
                writer.write("Program Studi: " + borrower.getProdi() + "\n");
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showInfoPopup(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private boolean isNumeric(String str) {
        return str.matches("\\d+");
    }

    public static class Book implements Serializable {
        private String title;
        private String author;
        private boolean available;
        private Borrower borrower;

        public Book(String title, String author, boolean available) {
            this.title = title;
            this.author = author;
            this.available = available;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        public boolean isAvailable() {
            return available;
        }

        public void setAvailable(boolean available) {
            this.available = available;
        }

        public Borrower getBorrower() {
            return borrower;
        }

        public void setBorrower(Borrower borrower) {
            this.borrower = borrower;
        }

        public StringProperty titleProperty() {
            return new SimpleStringProperty(title);
        }

        public StringProperty authorProperty() {
            return new SimpleStringProperty(author);
        }

        public StringProperty availableProperty() {
            return new SimpleStringProperty(available ? "Tersedia" : "Dipinjam");
        }
    }

    public static class Borrower implements Serializable {
        private String name;
        private String nim;
        private String prodi;

        public Borrower(String name, String nim, String prodi) {
            this.name = name;
            this.nim = nim;
            this.prodi = prodi;
        }

        public String getName() {
            return name;
        }

        public String getNim() {
            return nim;
        }

        public String getProdi() {
            return prodi;
        }

        public StringProperty nameProperty() {
            return new SimpleStringProperty(name);
        }
        public StringProperty nimProperty() {
            return new SimpleStringProperty(nim);
        }

        public StringProperty prodiProperty() {
            return new SimpleStringProperty(prodi);
        }
    }
}
