package library.management.ui.controllers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import library.management.data.DAO.DocumentDAO;
import library.management.data.entity.Document;

import java.util.Optional;

import static library.management.alert.AlertMaker.showAlertConfirmation;

public class DocumentController {
    private final MainController controller;
    private final ObservableList<Document> list = FXCollections.observableArrayList();
    private final ObservableList<BooleanProperty> checkBoxStatusList = FXCollections.observableArrayList();

    public DocumentController(MainController controller) {
        this.controller = controller;
    }

    public void initDocumentView() {
        controller.checkDocView.setCellValueFactory(cellData -> {
            int index = controller.docView.getItems().indexOf(cellData.getValue());
            return checkBoxStatusList.get(index);
        });
        controller.checkDocView.setCellFactory(CheckBoxTableCell.forTableColumn(controller.checkDocView));
        controller.docIDDocView.setCellValueFactory(new PropertyValueFactory<>("documentID"));
        controller.docISBNDocView.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        controller.docNameDocView.setCellValueFactory(new PropertyValueFactory<>("title"));
        controller.docAuthorDocView.setCellValueFactory(new PropertyValueFactory<>("author"));
        controller.docPublisherDocView.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        controller.categoryDocView.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCategory())
        );
        controller.quantityDocView.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        controller.remainingDocsDocView.setCellValueFactory(new PropertyValueFactory<>("availableCopies"));
        controller.availabilityDocView.setCellValueFactory(new PropertyValueFactory<>("availability"));
        controller.availabilityDocView.setCellValueFactory(new PropertyValueFactory<>("availability"));
        controller.availabilityDocView.setCellFactory(_ -> new TableCell<>() {
            private final Button button = new Button();

            @Override
            protected void updateItem(String availability, boolean empty) {
                super.updateItem(availability, empty);

                if (empty || availability == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    button.setText(availability.equals("available") ? "Available" : "Unavailable");
                    button.setStyle("-fx-background-color: " + (availability.equals("available") ? "green" : "red") + "; -fx-text-fill: white;");

                    button.setOnAction(_ -> {
                        Document document = getTableRow().getItem();
                        Optional<ButtonType> result = showAlertConfirmation(
                                "Change availability of document",
                                "Are you sure you want to change availability of this document: " + document.getTitle() + "?");
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            document.setAvailability(availability.equals("available") ? "unavailable" : "available");
                            DocumentDAO.getInstance().update(document);
                            controller.docView.refresh();
                        }
                    });

                    setGraphic(button);
                }
            }
        });
        controller.allDocs.setText(String.valueOf(DocumentDAO.getInstance().getTotalQuantity()));
        controller.remainDocs.setText(String.valueOf(DocumentDAO.getInstance().getTotalAvailableCopies()));
    }

    public void loadDocumentData() {
        list.clear();
        list.addAll(DocumentDAO.getInstance().getBookList());
        controller.docView.setItems(list);
        initializeCheckBox();
    }

    public void handleSearchDocTField() {
        list.clear();
        list.addAll(DocumentDAO.getInstance().searchDocuments(controller.searchDocTField.getText().trim().toLowerCase()));
        controller.docView.setItems(list);
        initializeCheckBox();
    }

    private void initializeCheckBox() {
        this.checkBoxStatusList.clear();
        for (int i = 0; i < list.size(); i++) {
            this.checkBoxStatusList.add(new SimpleBooleanProperty(false));
        }
        for (BooleanProperty checkBoxStatus : checkBoxStatusList) {
            checkBoxStatus.addListener((_, _, newValue) -> {
                if (!newValue) {
                    controller.checkAllDocsView.setSelected(false);
                }
            });
        }
    }

    public void checkAllDocs() {
        boolean isSelected = controller.checkAllDocsView.isSelected();
        for (BooleanProperty checkBoxStatus : checkBoxStatusList) {
            checkBoxStatus.set(isSelected);
        }
    }

    public void handleDeleteDocHyperlink() {
        Optional<ButtonType> result = showAlertConfirmation(
                "Delete document",
                "Are you sure you want to delete these documents?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            for (int i = 0; i < list.size(); i++) {
                if (checkBoxStatusList.get(i).getValue()) {
                    DocumentDAO.getInstance().delete(list.get(i));
                }
            }
            loadDocumentData();
        }
    }

    public void DeleteBook() {
        Document document = controller.docView.getSelectionModel().getSelectedItem();
        if (document != null) {
            showAlertConfirmation("Delete document", "Are you sure you want to delete this document?");
            DocumentDAO.getInstance().delete(document);
        }
        loadDocumentData();
    }
}
