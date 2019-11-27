package sample;

import Records.Record;
import Records.RecordsList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.util.converter.IntegerStringConverter;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

public class Controller implements Initializable {

    @FXML public Button searchByDate;
    @FXML public TextField dateAddText;
    @FXML public TableColumn<Record, String>dateColumn;
    @FXML private TextField commentAddText;
    @FXML private TextField visitorsAddText;
    @FXML private TableView<Record> table;
    @FXML private TableColumn<Record, Integer> sumColumn;
    @FXML private TableColumn<Record, String> commentColumn;
    @FXML private TableView<Record> searchTable;
    @FXML private TableColumn<Record, Integer> visitorsSearchColumn;
    @FXML private TableColumn<Record, String> commentSearchColumn;

    @FXML private Button searchByWord;

    @FXML private TextField searchTextField;

    private ObservableList<Record> recordObservableList;
    private ObservableList<Record> recordObservableSearchList;
    private RecordsList records;


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        exhibitionListInit(Main.sourceFileName);
        tableInit();
        table.setEditable(true);

    }



    private void exhibitionListInit(String sourceFilename){
        records = new RecordsList();
        records.read(sourceFilename);
        recordObservableList = FXCollections.observableArrayList( records.getRecords());
    }

    private void tableInit(){


        table.setItems(recordObservableList);
        table.getColumns().clear();

        sumColumn.setCellValueFactory(new PropertyValueFactory<>("sum"));
        sumColumn.setOnEditCommit(t ->
                ((Record) t.getTableView().getItems().get(t.getTablePosition().getRow())).
                        setSum(t.getNewValue()));

        sumColumn.setCellFactory(TextFieldTableCell.<Record, Integer>forTableColumn(new IntegerStringConverter()));
        sumColumn.setOnEditCommit(t ->
                ((Record) t.getTableView().getItems().get(t.getTablePosition().getRow())).
                        setSum(t.getNewValue()));

        commentColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));
        commentColumn.setOnEditCommit(t ->
                ((Record) t.getTableView().getItems().get(t.getTablePosition().getRow())).
                        setComment(t.getNewValue()));

        commentColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        commentColumn.setOnEditCommit(t ->
                ((Record) t.getTableView().getItems().get(t.getTablePosition().getRow())).
                        setComment(t.getNewValue()));

        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateColumn.setOnEditCommit(t ->
                ((Record) t.getTableView().getItems().get(t.getTablePosition().getRow())).
                        setDate(t.getNewValue()));

        commentColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        dateColumn.setOnEditCommit(t ->
                ((Record) t.getTableView().getItems().get(t.getTablePosition().getRow())).
                        setDate(t.getNewValue()));

        table.getColumns().add(dateColumn);
        table.getColumns().add(sumColumn);
        table.getColumns().add(commentColumn);
    }

    @FXML
    private void search(){
        searchTable.getColumns().clear();
        recordObservableSearchList = FXCollections.observableArrayList(records.daysWithDate(searchTextField.getText()));
        searchTable.setItems(recordObservableSearchList);
        visitorsSearchColumn.setCellValueFactory(new PropertyValueFactory<>("sum"));
        commentSearchColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));
        searchTable.getColumns().add(visitorsSearchColumn);
        searchTable.getColumns().add(commentSearchColumn);
    }

    public void dellButtonPressed(ActionEvent actionEvent) {
        ObservableList<Record> allRecords, selectedRecords;
        allRecords = table.getItems();
        selectedRecords = table.getSelectionModel().getSelectedItems();



        selectedRecords.forEach(allRecords::remove);
        records.getRecords().clear();
        for (Object d : allRecords.toArray()){
            records.addRecord((Record) d);
        }
    }

    public void addButtonPressed(ActionEvent actionEvent) {
        try{
            if(!commentAddText.getText().isEmpty() && !visitorsAddText.getText().isEmpty() && !dateAddText.getText().isEmpty()){
                String date = dateAddText.getText();
                StringTokenizer tokenizer = new StringTokenizer(date);
                int day = Integer.parseInt(tokenizer.nextToken("."));
                int month = Integer.parseInt(tokenizer.nextToken("."));
                int year = Integer.parseInt(tokenizer.nextToken("."));
                if(!(day < 0 || day > 31 || month < 1 || month > 12 || year < 1900)){
                    recordObservableList.add(new Record(date, Integer.parseInt( visitorsAddText.getText()), commentAddText.getText()));
                    records.addRecord(new Record(dateAddText.getText(), Integer.parseInt(visitorsAddText.getText()), commentAddText.getText()));
                    dateAddText.clear();
                    commentAddText.clear();
                    visitorsAddText.clear();
                }
                else{
                    dateAddText.clear();
                }
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void infoClecked(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Personal finance manager");
        alert.setHeaderText("Author: Macksimka, https://github.com/EpicWasa");
        alert.setContentText("Version 1.0");
        alert.showAndWait();
    }

    public void saveToStandardFileClicked(ActionEvent actionEvent) {
        records.write(Main.sourceFileName);
    }

    public void saveToCustomFileClicked(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        File file;

        if ((file = chooser.showOpenDialog(null)) != null) {
            records.write(file.getAbsolutePath());
        }
    }

    public void loadFromFileClicked(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        File file;

        if ((file = chooser.showOpenDialog(null)) != null) {
            records.read(file.getAbsolutePath());
            recordObservableList = FXCollections.observableArrayList( records.getRecords());
            tableInit();


        }


    }

    public void newDataSetClicked(ActionEvent actionEvent) {
        records =new RecordsList();
        recordObservableList = FXCollections.observableArrayList(records.getRecords());
        tableInit();

    }
}
