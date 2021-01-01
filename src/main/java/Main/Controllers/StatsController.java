package Main.Controllers;

import Main.DAO.Interfaces.IToyRepository;
import Main.DAO.SingletonConnection;
import Main.DAO.ToyRepository;
import Main.Model.SalesByPerson;
import Main.Model.Toy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class StatsController implements Initializable {
@FXML
TextField hardWorker ;
@FXML
DatePicker startDatepicker ;
@FXML
DatePicker endDatepicker ;

    @FXML
    BarChart<String,Double> toybarchart ;
    IToyRepository toyRepository = new ToyRepository();

    //    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    java.sql.Date startDate ;
    java.sql.Date endDate ;
    ObservableList<SalesByPerson> salesByPersonObservableList ;
    XYChart.Series<String,Double> series;
    XYChart.Series<String,Double> series2;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        salesByPersonObservableList = FXCollections.observableArrayList(toyRepository.GetSalesByPerson(startDate,endDate));
        series = new XYChart.Series();


//        series.getData().add(new XYChart.Data<String, Number>("Hamdi",120));
//        series.getData().add(new XYChart.Data<String, Number>("3mirat",20));
//        series.getData().add(new XYChart.Data<String, Number>("Jozef",70));
        for (SalesByPerson s : toyRepository.GetSalesByPerson(startDate,endDate)) {
            series.getData().add(new XYChart.Data<String,Double>(String.valueOf(s.getId()),s.getCv()));

        }

        series.setName("Ventes");
        toybarchart.getData().add(series);

        hardWorker.setText(String.valueOf(toyRepository.GetMaxSalesByPerson(startDate,endDate).get(0).getId()));
    }

    @FXML
    public void getByDate(ActionEvent actionEvent) throws IOException {
        toybarchart.getData().removeAll();
        salesByPersonObservableList.removeAll();
        series.getData().removeAll();

        toybarchart.getData().clear();
        salesByPersonObservableList.clear();
        series.getData().clear();

        LocalDate sd =startDatepicker.getValue();
       LocalDate ed= endDatepicker.getValue();
//
//        System.out.println(sd);
//        System.out.println(ed);

        java.sql.Date startDate = java.sql.Date.valueOf(sd);
        java.sql.Date endDate = java.sql.Date.valueOf(ed);

        salesByPersonObservableList = FXCollections.observableArrayList(toyRepository.GetSalesByPerson(startDate,endDate));
        series2 = new XYChart.Series();

//        series.getData().add(new XYChart.Data<String, Number>("Hamdi",120));
//        series.getData().add(new XYChart.Data<String, Number>("3mirat",20));
//        series.getData().add(new XYChart.Data<String, Number>("Jozef",70));
        for (SalesByPerson s : toyRepository.GetSalesByPerson(startDate,endDate)) {
            series2.getData().add(new XYChart.Data<String,Double>(String.valueOf(s.getId()),s.getCv()));

        }

        series2.setName("Ventes");
        toybarchart.getData().add(series2);
   hardWorker.setText(String.valueOf(toyRepository.GetMaxSalesByPerson(startDate,endDate).get(0).getId()));


    }


        public void logout(ActionEvent actionEvent) throws IOException {
        ButtonType yes = new ButtonType("Oui", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"",yes,no);

        alert.setTitle("Confimer la déconnection");
        alert.setHeaderText("Voulez vous vraimenet déconnecter ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == yes ){
            Parent parent = FXMLLoader.load(getClass().getResource("/FXML/login.fxml"));
            Scene scene = new Scene(parent);
            Stage homeStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            homeStage.setScene(scene);
            homeStage.centerOnScreen();

            homeStage.show();
        } else {
            alert.close();        }



    }


}
