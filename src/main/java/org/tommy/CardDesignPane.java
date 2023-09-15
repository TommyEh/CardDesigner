package org.tommy;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.scene.transform.Scale;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class CardDesignPane extends Pane {
    private ImageView templateView;
    private ImageView artworkView;
    private TextFlow cardNameTextFlow;
    private TextFlow cardEffectTextFlow;
    private TextFlow cardTextTextFlow;
    private Font customCardTextFont;
    private Font customFont;
    private Font customItalicFont;

    private double desiredWidth = 362.0;

    private static final double CARDNAME_ORIGINAL_X = 140;
    private static final double CARDNAME_ORIGINAL_Y = 39;
    private static final double CARDNAME_ORIGINAL_WIDTH = 487;
    private static final double CARDNAME_ORIGINAL_HEIGHT = 48;

    private static final double ARTWORK_ORIGINAL_X = 47;
    private static final double ARTWORK_ORIGINAL_Y = 122;
    private static final double ARTWORK_ORIGINAL_WIDTH = 631;
    private static final double ARTWORK_ORIGINAL_HEIGHT = 459;

    private static final double CARDEFFECT_ORIGINAL_X = 50;
    private static final double CARDEFFECT_ORIGINAL_Y = 640;
    private static final double CARDEFFECT_ORIGINAL_WIDTH = 620;
    private static final double CARDEFFECT_ORIGINAL_HEIGHT = 260;

    private static final double CARDTEXT_ORIGINAL_X = 44;
    private static final double CARDTEXT_ORIGINAL_Y = 930;
    private static final double CARDTEXT_ORIGINAL_WIDTH = 364;
    private static final double CARDTEXT_ORIGINAL_HEIGHT = 77;

    public CardDesignPane() {
        initialize();
    }

    private void initialize() {
        Image template = new Image("C:\\Users\\tommy\\Documents\\CardDesigner\\src\\res\\BaseCard.png");
        templateView = new ImageView(template);
        templateView.setPreserveRatio(true);
        templateView.setFitWidth(desiredWidth);

        double desiredHeight = desiredWidth * (template.getHeight() / template.getWidth());
        templateView.setFitHeight(desiredHeight);

        double scaleX = templateView.getFitWidth() / templateView.getImage().getWidth();
        double scaleY = templateView.getFitHeight() / templateView.getImage().getHeight();

        Font.loadFont(getClass().getResourceAsStream("/AtkinsonHyperlegible-Regular.ttf"), 22.5);
        customFont = Font.loadFont(getClass().getResourceAsStream("/Calluna Bold.otf"), 20);

        double scaledCardNameX = CARDNAME_ORIGINAL_X * scaleX;
        double scaledCardNameY = CARDNAME_ORIGINAL_Y * scaleY;

        cardNameTextFlow = new TextFlow();
        cardNameTextFlow.setLayoutX(scaledCardNameX);
        cardNameTextFlow.setLayoutY(scaledCardNameY);

        customItalicFont = Font.loadFont(getClass().getResourceAsStream("/Calluna Italic.otf"), 22);

        double scaledCardEffectX = CARDEFFECT_ORIGINAL_X * scaleX;
        double scaledCardEffectY = CARDEFFECT_ORIGINAL_Y * scaleY;

        cardEffectTextFlow = new TextFlow();
        cardEffectTextFlow.setLayoutX(scaledCardEffectX);
        cardEffectTextFlow.setLayoutY(scaledCardEffectY);
        cardEffectTextFlow.setPrefWidth(CARDEFFECT_ORIGINAL_WIDTH * scaleX);
        cardEffectTextFlow.setPrefHeight(CARDEFFECT_ORIGINAL_HEIGHT * scaleY);
        cardEffectTextFlow.setLineSpacing(5);

        double scaledCardTextX = CARDTEXT_ORIGINAL_X * scaleX;
        double scaledCardTextY = CARDTEXT_ORIGINAL_Y * scaleY;

        cardTextTextFlow = new TextFlow();
        cardTextTextFlow.setLayoutX(scaledCardTextX);
        cardTextTextFlow.setLayoutY(scaledCardTextY);
        cardTextTextFlow.setPrefWidth(CARDTEXT_ORIGINAL_WIDTH * scaleX);
        cardTextTextFlow.setPrefHeight(CARDTEXT_ORIGINAL_HEIGHT * scaleY);
        cardTextTextFlow.setLineSpacing(0.1);

        customCardTextFont = Font.font("Arial", FontPosture.ITALIC, 12);


        double scaledArtworkX = ARTWORK_ORIGINAL_X * scaleX;
        double scaledArtworkY = ARTWORK_ORIGINAL_Y * scaleY;
        double scaledArtworkWidth = ARTWORK_ORIGINAL_WIDTH * scaleX;
        double scaledArtworkHeight = ARTWORK_ORIGINAL_HEIGHT * scaleY;

        artworkView = new ImageView();
        artworkView.setFitWidth(scaledArtworkWidth);
        artworkView.setFitHeight(scaledArtworkHeight);
        artworkView.setLayoutX(scaledArtworkX);
        artworkView.setLayoutY(scaledArtworkY);
        artworkView.setPreserveRatio(true);
        artworkView.setImage(new WritableImage((int) scaledArtworkWidth, (int) scaledArtworkHeight));

        getChildren().addAll(artworkView, templateView, cardNameTextFlow, cardEffectTextFlow, cardTextTextFlow);
    }

    public void uploadArtwork() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Artwork");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            Image artwork = new Image(selectedFile.toURI().toString());
            Image resizedArtwork = resizeImage(artwork, 631, 459);
            artworkView.setImage(resizedArtwork);
        }
    }

    private Image resizeImage(Image source, int targetWidth, int targetHeight) {
        ImageView imageView = new ImageView(source);
        imageView.setFitWidth(targetWidth);
        imageView.setFitHeight(targetHeight);
        imageView.setPreserveRatio(false);

        Canvas canvas = new Canvas(targetWidth, targetHeight);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.drawImage(imageView.getImage(), 0, 0, targetWidth, targetHeight);
        return canvas.snapshot(null, null);
    }


    public void setCardName(String name) {
        cardNameTextFlow.getChildren().clear();

        String[] words = name.toUpperCase().split(" ");
        for (String word : words) {
            if (word.length() > 0) {
                Text firstLetter = new Text(word.substring(0, 1));
                firstLetter.setFont(new Font(customFont.getName(), customFont.getSize() * 1.5)); // 1.5 times bigger

                Text remainingLetters = new Text(word.substring(1) + " ");
                remainingLetters.setFont(customFont);

                cardNameTextFlow.getChildren().addAll(firstLetter, remainingLetters);
            }
        }
    }

    public void setCardNameFont(Font font) {
        customFont = font;
    }

    public void setCardNameColor(String color) {
        for (javafx.scene.Node node : cardNameTextFlow.getChildren()) {
            if (node instanceof Text) {
                ((Text) node).setFill(Color.web(color));
            }
        }
    }

    public void setCardEffect(String effect) {
        cardEffectTextFlow.getChildren().clear();
        Text effectText = new Text(effect);
        effectText.setFont(customItalicFont);
        cardEffectTextFlow.getChildren().add(effectText);
    }

    public TextFlow getCardEffectField() {
        return cardEffectTextFlow;
    }

    public Font getCustomItalicFont() {
        if (customItalicFont == null) {
            customItalicFont = new Font("/Calluna Italic.otf", 20);
        }
        return customItalicFont;
    }

    public void setCardEffectFont(Font font) {
        customItalicFont = font;
    }

    public void setCardEffectColor(String color) {
        for (javafx.scene.Node node : cardEffectTextFlow.getChildren()) {
            if (node instanceof Text) {
                ((Text) node).setFill(Color.web(color));
            }
        }
    }

    public TextFlow getCardNameField() {
        return cardNameTextFlow;
    }

    public void setCardText(String text) {
        cardTextTextFlow.getChildren().clear();
        Text textNode = new Text(text);
        textNode.setFont(customCardTextFont);
        cardTextTextFlow.getChildren().add(textNode);
    }

    public TextFlow getCardTextField() {
        return cardTextTextFlow;
    }

    public void setCustomCardTextFont(Font font) {
        customCardTextFont = font;
    }

    public void setCardTextColor(String color) {
        for (javafx.scene.Node node : cardTextTextFlow.getChildren()) {
            if (node instanceof Text) {
                ((Text) node).setFill(Color.web(color));
            }
        }
    }

    public Font getCustomCardTextFont() {
        return customCardTextFont;
    }

    public Font getCustomFont() {
        if (customFont == null) {
            customFont = new Font("/Calluna Bold.otf", 20);
        }
        return customFont;
    }

    public void downloadCard() {
        SnapshotParameters sp = new SnapshotParameters();

        Scale scale = new Scale(725 / this.getWidth(), 1099 / this.getHeight());
        sp.setTransform(scale);

        sp.setFill(Color.TRANSPARENT);

        WritableImage image = this.snapshot(sp, new WritableImage(725, 1099));

        if (image == null || image.getWidth() <= 0 || image.getHeight() <= 0) {
            System.out.println("Failed to capture a valid image.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setSelectedExtensionFilter(extFilter);

        File file = fileChooser.showSaveDialog(this.getScene().getWindow());
        if (file != null) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

}
