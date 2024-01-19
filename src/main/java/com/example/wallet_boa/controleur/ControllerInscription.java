package com.example.wallet_boa.controleur;

import com.example.wallet_boa.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.Base64;
import java.util.Properties;


public class ControllerInscription {

    @FXML
    Button btn_back;
    @FXML
    TextField i_email;
    @FXML
    TextField i_phone;
    @FXML
    TextField i_name;
    @FXML
    TextField i_surname;
    @FXML
    PasswordField i_mdp;
    @FXML
    PasswordField i_mdp2;
    @FXML
    ImageView imageView;
    @FXML
    Label label_erreur;
    @FXML
    Label area_mdp;


    /*
        Toutes les fonctions commencant par l_
        Permettent la redirection vers une autre page
    */

    public void initialize() {
        /*
        Cette fonction permet de charger une video automatiquement au chargement de la page
         */

        String errorMessage = "Your password format!\n" +
                "  - minimum 8 characters\n" +
                "  - minimum 1 uppercase letter\n" +
                "  - minimum 1 lowercase letter\n" +
                "  - minimum 1 special character\n" +
                "  - minimum 1 digit";


        area_mdp.setText(errorMessage);
        Image image = new Image(new File("src/main/resources/galerie/logo.png").toURI().toString());
        imageView.setImage(image);

    }

    @FXML
    public void l_connection() throws Exception {
        IntefaceFeatures.layout_connexion();
    }

    @FXML
    public void insert_inscription () throws Exception {
        /*
            Cette fonction permet de d'insérer un nouvelle utilisateur dans la base de données
         */

        String email = i_email.getText();

        String phone = i_phone.getText();
        String surname = i_surname.getText();
        String name = i_name.getText();

        String mdp_no = i_mdp.getText();

        String mdp = IntefaceFeatures.encryptPassword(i_mdp.getText());
        String mdp_ = IntefaceFeatures.encryptPassword(i_mdp2.getText());


        if(!mdp.equals(mdp_)){
            label_erreur.setText("Les deux mots de passe sont différents");
            i_mdp2.setText("");
            i_mdp.setText("");

        }else{
            if(email.equals("") || phone.equals("") || surname.equals("") || name.equals("") || mdp.equals("") || mdp_.equals("")){
                label_erreur.setText("Veuillez remplir tous les champs");
            }else{
                if(!IntefaceFeatures.isValidEmail(email)){
                    label_erreur.setText("Format de l'email ne correspond pas ! Ex : java@boa.fr");
                    i_email.setText("");

                }else{
                    if(!IntefaceFeatures.isValidPassword(mdp_no)){
                        label_erreur.setText("Format mot de passe ne correspond pas !");
                        i_mdp2.setText("");
                        i_mdp.setText("");
                    }else{
                        if(!IntefaceFeatures.isValidPhone(phone)){
                            label_erreur.setText("Le format du numéro de téléphone est incorrecte !");
                            i_phone.setText("");

                        }else{
                            if(!IntefaceFeatures.isEmailUnique(email)){
                                label_erreur.setText("L'email est déjà utilisé !");
                                i_email.setText("");

                            }else{
                                String query = "INSERT INTO investor (name, surname, email, mdp, phone_number) VALUES (?, ?, ?, ?, ?)";
                                String url = "jdbc:mysql://localhost:3306/database_boa_java?serverTimezone=UTC&useSSL=false";
                                try (
                                        Connection connection = DriverManager.getConnection(url, IntefaceFeatures.NAME_DB, IntefaceFeatures.MDP_DB);
                                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                                ) {
                                    preparedStatement.setString(1, name);
                                    preparedStatement.setString(2, surname);
                                    preparedStatement.setString(3, email);
                                    preparedStatement.setString(4, mdp);
                                    preparedStatement.setString(5, phone);

                                    int rowsAffected = preparedStatement.executeUpdate();

                                    if (rowsAffected > 0) {
                                        //envoiemail(email);
                                        IntefaceFeatures.layout_connexion();
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }


    }

    public void envoiemail(String toEmail) throws Exception{
        final String username = "boa75000@outlook.com";
        final String password = "boa12345";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp-mail.outlook.com"); // Utilisez le serveur SMTP d'Outlook
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Subject: Bienvenue sur notre plateforme de crypto-monnaies et d'actions !");
            message.setText("Cher(e) [Prénom],\n\n"
                    + "Nous sommes ravis de vous accueillir sur notre plateforme dédiée aux crypto-monnaies et aux actions. "
                    + "Vous avez désormais accès à un monde passionnant d'investissements et de trading.\n\n"
                    + "Que vous soyez débutant ou investisseur chevronné, notre plateforme est conçue pour répondre à vos besoins. "
                    + "Explorez nos outils, analyses et ressources éducatives pour prendre des décisions éclairées.\n\n"
                    + "Notre équipe est là pour vous accompagner. N'hésitez pas à nous contacter si vous avez des questions ou "
                    + "si vous avez besoin d'aide pour démarrer.\n\n"
                    + "Nous croyons en l'importance de la connaissance dans le domaine des crypto-monnaies et des actions. "
                    + "Restez curieux, apprenez continuellement et élargissez vos horizons financiers avec nous.\n\n"
                    + "Nous vous souhaitons une excellente aventure financière et sommes impatients de vous aider "
                    + "à atteindre vos objectifs.\n\n"
                    + "Investissez judicieusement et construisons ensemble un avenir financier solide.\n\n"
                    + "Bienvenue dans la famille de notre site dédié aux crypto-monnaies et aux actions !");

            Transport.send(message);

    /*              Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Inscription");
                    alert.setHeaderText(null);
                    alert.setContentText("Un email de confirmation vous a été envoyé !");
                    alert.showAndWait();

     */

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }




}
