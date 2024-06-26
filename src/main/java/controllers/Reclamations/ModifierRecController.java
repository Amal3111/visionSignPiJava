package controllers.Reclamations;


import State.MainNavigations;
import entities.Publications;
import entities.Reclamations;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import services.Reclamations.ReclamationsServices;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;

public class ModifierRecController extends AjouterRecController {
    @FXML
    public AnchorPane rootId;
    public AnchorPane getRootBox(){
        return  this.rootId ;
    }
    private int usserId=State.UserSessionManager.getInstance().getCurrentUser().getId();

    @FXML
    private TextField desFx;

    @FXML
    private Button forumBtn;

    @FXML
    private AnchorPane listepubid;

    @FXML
    private Button modPubBtn;

    @FXML
    private VBox myposts;

    @FXML
    private TextField searchField1;

    @FXML
    private TextField statFx;

    @FXML
    private ComboBox<String> typeCombo;
    private ReclamationsServices reclamationsServices;
    private int recId;
    ReclamationsServices pubs = new ReclamationsServices();



    public void setRecId(int recId) {
        this.recId = recId;
        loadPublicationDetails();
    }


    @FXML
    void handleModifier(ActionEvent event) {
        String typeText = typeCombo.getValue();
        String descriptionText=desFx.getText();
        String statusText=statFx.getText();

        try {
            if (descriptionText != null && !descriptionText.isEmpty() && statFx != null && !statusText.isEmpty()) {

                //TODO userId=1

                pubs.modifier(new Reclamations(recId,typeText,descriptionText,statusText,usserId));

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success!");
                alert.setContentText("Reclamation updated successfully!");
                alert.showAndWait();

                MainNavigations.getInstance().openReclamationPage();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error!");
                alert.setContentText("Title and content cannot be empty!");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }


    @FXML
    void initialize() {
        typeCombo.getItems().addAll("cours", "note", "certificat", "autre");
        try {
            //TODO userid=1
            mypub = pubs.getReclamationsById(usserId);
            if (this.mypub == null || this.mypub.isEmpty()) {
                Text emptyText = new Text("Vous n'avez pas encore publié!");
                emptyText.setFont(new Font("System", 15));
                emptyText.setFill(Color.GRAY);
                emptyText.setLayoutX(19);
                emptyText.setLayoutY(172);
                listepubid.getChildren().add(emptyText);
                listepubid.setPrefHeight(100);
            } else {
                for (int i = 0; i < mypub.size(); i++) {
                    Pane pane = createPublicationPane(mypub.get(i), i, false);
                    listepubid.getChildren().add(pane);
                }
                listepubid.setPrefHeight(mypub.size() * 85);
            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public void loadPublicationDetails() {
        try {
            Reclamations getPub = pubs.getReclamationsById2(recId);
            if (getPub != null) {
                typeCombo.setValue(getPub.getType());
                statFx.setText(getPub.getStatus());
                desFx.setText(getPub.getDescription());
            } else {
                System.out.println("No publication found with ID: " + recId);
            }
        } catch (SQLException e) {
            System.err.println("SQL error when trying to load publication details: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error when trying to load publication details: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

//import entities.Reclamations;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.scene.control.ComboBox;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.Pane;
//
//import services.ReclamationsServices;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.Arrays;
//import java.util.List;
//import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
//import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
//
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Cursor;
//import javafx.scene.Parent;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.Pane;
//import javafx.scene.paint.Color;
//import javafx.scene.text.Font;
//import javafx.scene.text.Text;
//
//
//import java.io.IOException;
//import java.net.URL;
//import java.sql.SQLException;
//import java.util.Date;
//import java.util.List;
//import java.util.ResourceBundle;
//
//public class ModifierRecController {
//    @FXML
//    public AnchorPane listepubid;
//    List<Reclamations> mypub;
//    @FXML
//    private ComboBox<String> typeComboBox;
//    @FXML
//    private TextField descriptionField, statusField;
//    @FXML
//    private Button updateButton;
//    @FXML
//    private Button forumBtn;
//    private Reclamations reclamations;
//    private ReclamationsServices reclamationsServices;
//
//    public void initialize() {
//        // Initialize services
//        reclamationsServices = new ReclamationsServices();
//        pubs = new ReclamationsServices();
//        typeComboBox = new ComboBox<>();
//        typeComboBox.getItems().addAll(Arrays.asList(ReclamationsServices.TYPES));
//
//        // Load details of the complaint to be modified
//        loadComplaintDetails();
//    }
//
//    public void setReclamation(Reclamations reclamation) {
//        this.reclamations = reclamation;
//    }
//
//    private void loadComplaintDetails() {
//        // Check if complaint is not null
//        if (reclamations != null) {
//            // Set details to fields
//            typeComboBox.setValue(reclamations.getType().toString());
//            descriptionField.setText(reclamations.getDescription());
//            statusField.setText(reclamations.getStatus());
//        }
//    }
//
//    @FXML
////    void handleUpdate(ActionEvent event) {
////        // Get updated details from fields
////        String type = typeComboBox.getValue();
////        String description = descriptionField.getText();
////        String status = statusField.getText();
////
////        try {
////            // Update the complaint
////            reclamations.setType(type);
////            reclamations.setDescription(description);
////            reclamations.setStatus(status);
////
////            reclamationsServices.modifier(reclamations);
////
////            // Show success message
////            Alert alert = new Alert(Alert.AlertType.INFORMATION);
////            alert.setTitle("Success");
////            alert.setHeaderText(null);
////            alert.setContentText("Complaint updated successfully");
////            alert.showAndWait();
////        } catch (SQLException e) {
////            // Show error message
////            Alert alert = new Alert(Alert.AlertType.ERROR);
////            alert.setTitle("Error");
////            alert.setHeaderText(null);
////            alert.setContentText("Error updating complaint: " + e.getMessage());
////            alert.showAndWait();
////        }
////    }
////
//
//    public void searchPubByTitle1(ActionEvent actionEvent) {
//    }
//
//    public void handleForum(ActionEvent actionEvent) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reclamtions/AjouterRec.fxml"));
//            Parent root = loader.load();
//            forumBtn.getScene().setRoot(root);
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    private int pubId;
//    ReclamationsServices pubs = new ReclamationsServices();
//    public void setIdProduitUpdate(int pubId)
//    {
//        this.pubId = pubId;
//        ChargerProductDetails();
//    }
//
//    private void ChargerProductDetails()
//    {
//        try {
//            ReclamationsServices recl = new ReclamationsServices();
//
//  System.out.println("Loading details for pubId: " + pubId);
//
//            Reclamations p = recl.getReclmationsById(pubId);
//
//            String categorie = p.getType();
//            typeComboBox.setValue(categorie);
//            descriptionField.setText(p.getDescription());
//            statusField.setText(String.valueOf(p.getStatus()));
//
//
//        } catch (SQLException e) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Erreur");
//            alert.setHeaderText("Erreur lors du chargement des détails du produit");
//            alert.setContentText("Une erreur s'est produite lors du chargement des détails du produit.");
//            alert.showAndWait();
//        }
//    }
//
//    public void handleUpdate(ActionEvent actionEvent)
//    {
//        try {
//            ReclamationsServices produitService = new ReclamationsServices();
//            String name = statusField.getText();
//            String description = descriptionField.getText();
//            String categorie = typeComboBox.getValue();
//
//
//            Reclamations updatedProduct = new Reclamations(categorie,  description,name );
//
//            setIdProduitUpdate(pubId);
//            produitService.modifier(updatedProduct);
//
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Information");
//            alert.setContentText("Produit modifie avec succes");
//            alert.showAndWait();
//
//
//
//
//        } catch (SQLException e) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error");
//            alert.setContentText(e.getMessage());
//            alert.showAndWait();
//        }
//    }
//
//    public void setPubId(int pubId) {
//        this.pubId = pubId;
//        ChargerProductDetails();
//    }
////    private void loadPublicationDetails() {
////        System.out.println("Loading details for pubId: " + pubId);
////        try {
////            String typeReclamation = typeComboBox.getValue();
////
////            Reclamations getPub = pubs.getReclmationsById(pubId);
////            if (getPub != null) {
////                //typeComboBox.setItems(getPub.getType());
////                ObservableList<String> typeList = FXCollections.observableArrayList(getPub.getType());
////                typeComboBox.setItems(typeList);
////                descriptionField.setText(getPub.getDescription());
////                statusField.setText(getPub.getStatus());
////                System.out.println("Loaded publication: " + getPub);
////            } else {
////                System.out.println("No publication found with ID: " + pubId);
////            }
////        } catch (SQLException e) {
////            System.err.println("SQL error when trying to load publication details: " + e.getMessage());
////            e.printStackTrace();
////        } catch (Exception e) {
////            System.err.println("Unexpected error when trying to load publication details: " + e.getMessage());
////            e.printStackTrace();
////        }
////    }
//private Pane createPublicationPane(Reclamations publication, int index) {
//        Pane pane = new Pane();
//        pane.setPrefSize(271, 75);
//        pane.setLayoutX(-1);
//        pane.setLayoutY(7 + index * (75));
//        pane.setStyle("-fx-background-color: white; -fx-border-color: #ECECEC;");
//
//        Text titreText = new Text(publication.getType().toString());
//        titreText.setLayoutX(14);
//        titreText.setLayoutY(28);
//        titreText.setFont(new Font("System Bold", 12));
//        titreText.setFill(Color.web("#494949"));
//
//        Text dateText = new Text(publication.getDate().toString());
//        dateText.setLayoutX(14);
//        dateText.setLayoutY(54);
//        dateText.setFill(Color.web("#a5a5a5"));
//
//
//        Button modifyButton = new Button();
//        modifyButton.setLayoutX(202);
//        modifyButton.setLayoutY(37);
//        modifyButton.setPrefSize(28, 25);
//        modifyButton.setStyle("-fx-background-color: #FFFFFF;");
//        FontAwesomeIconView modifyIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL);
//        modifyIcon.setFill(Color.web("#2447f9"));
//        modifyIcon.setSize("15");
//        modifyButton.setGraphic(modifyIcon);
//        modifyButton.setCursor(Cursor.HAND);
//        modifyButton.setOnAction(new EventHandler<ActionEvent>() {
//@Override
//public void handle(ActionEvent event) {
//        try {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifyAvis.fxml"));
//        Parent root = loader.load();
//        ModifierRecController modifyController = loader.getController();
//        modifyController.setPubId(mypub.get(index).getId_reclamation());
//        System.out.println(mypub.get(index).getId_reclamation());
//        forumBtn.getScene().setRoot(root);
//        } catch (IOException e) {
//        e.printStackTrace();
//        }
//        }
//        });
//
//        Button deleteButton = new Button();
//        deleteButton.setLayoutX(228);
//        deleteButton.setLayoutY(37);
//        deleteButton.setPrefSize(28, 25);
//        deleteButton.setStyle("-fx-background-color: #FFFFFF;");
//        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
//        deleteIcon.setFill(Color.web("#2447f9"));
//        deleteIcon.setSize("15");
//        deleteButton.setGraphic(deleteIcon);
//        deleteButton.setCursor(Cursor.HAND);
//        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
//@Override
//public void handle(ActionEvent event) {
//        try {
//        pubs.supprimer(mypub.get(index).getId_reclamation());
//        System.out.println("deleted!");
//        listepubid.getChildren().remove(pane);
//        mypub.remove(index);
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("Succes!");
//        alert.setContentText("Publication supprimé!");
//        alert.showAndWait();
//        } catch (SQLException e) {
//        Alert alert = new Alert(Alert.AlertType.ERROR);
//        alert.setTitle("ERROR!");
//        alert.setContentText(e.getMessage());
//        alert.showAndWait();
//        }
//        }
//        });
//
//        pane.getChildren().addAll(titreText, dateText, modifyButton, deleteButton);
//
//
//        return pane;
//        }
//
//    public void ajouter(ActionEvent actionEvent) {
//    }
//
//    public void back(ActionEvent actionEvent) {
//    }
//}