package org.tommy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.embed.swing.SwingFXUtils;


import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class CardController {

    @FXML
    private TextField cardNameField;

    @FXML
    private CardDesignPane cardDesignPane;

    @FXML
    private TextField cardEffectField;

    @FXML
    private TextField cardTextField;

    @FXML
    private Button uploadArtworkButton;

    @FXML
    private Button downloadButton;

    @FXML
    private void handleUploadArtwork(ActionEvent event) {
        cardDesignPane.uploadArtwork();
    }

    @FXML
    private void handleDownloadCard(ActionEvent event) {
        cardDesignPane.downloadCard();
    }

    public void initialize() {
        cardNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            cardDesignPane.setCardName(newValue);

            Font callunaBold = cardDesignPane.getCustomFont();
            cardDesignPane.setCardNameFont(callunaBold);

            String customColor = "#FFFFFF";
            cardDesignPane.setCardNameColor(customColor);
        });

        cardEffectField.textProperty().addListener((observable, oldValue, newValue) -> {
            cardDesignPane.setCardEffect(newValue);

            Font callunaItalic = cardDesignPane.getCustomItalicFont();
            cardDesignPane.setCardEffectFont(callunaItalic);

            String customColor1 = "#FFFFFF";
            cardDesignPane.setCardEffectColor(customColor1);
        });

        cardTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            cardDesignPane.setCardText(newValue);

            Font arialCursive = cardDesignPane.getCustomCardTextFont();
            cardDesignPane.setCustomCardTextFont(arialCursive);
            cardDesignPane.setCardTextColor("#FFFFFF");
        });

    }

    public String getCardName() {
        return cardNameField.getText();
    }
}
