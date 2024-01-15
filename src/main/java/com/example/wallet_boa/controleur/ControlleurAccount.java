package com.example.wallet_boa.controleur;

import com.example.wallet_boa.HelloApplication;
import com.example.wallet_boa.modele.Blockchaine;
import com.example.wallet_boa.modele.Investor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

public class ControlleurAccount {

    private Investor investor;
    private Blockchaine blockchain;

    @FXML
    Label label_name;
    @FXML
    ImageView imageView;

    @FXML
    TextField txt_name;
    @FXML
    TextField txt_surname;
    @FXML
    TextField txt_email;
    @FXML
    TextField txt_phone_number;
    @FXML
    Button btn_edit;
    @FXML
    Button btn_submit;
    @FXML
    Button btn_password;
    @FXML
    VBox box_npw;
    @FXML
    VBox box_profil;
    @FXML
    PasswordField txt_p_n_a;
    @FXML
    PasswordField txt_p_n;
    @FXML
    PasswordField txt_p_actu;
    @FXML
    Label label_solde;
    @FXML
    Label label_erreur;

    /*
        Toutes les fonctions commencant par l_
        Permettent la redirection vers une autre page
    */


    public void l_logout() throws IOException {
        IntefaceFeatures.log_out();
    }
    public void l_accueil() throws Exception{
        IntefaceFeatures.layout_accueil(investor, blockchain);
    }
    public void l_help() throws Exception{
        IntefaceFeatures.layout_help(investor, blockchain);
    }
    public void l_wallet() throws Exception{
        IntefaceFeatures.layout_wallet(investor, blockchain);
    }
    public void l_action() throws Exception{
        IntefaceFeatures.layout_stock(investor, blockchain);
    }
    public void l_transaction() throws Exception{
        IntefaceFeatures.layout_transaction(investor, blockchain);
    }
    public void l_crytpo() throws Exception{
        IntefaceFeatures.layout_crypto(investor, blockchain);
    }


    public void initialize_investor(){
        /*
            Cette fonction permet d'ajouter dans les champs les valeurs de l'investisseur
         */

        txt_name.setText(investor.getName());
        txt_surname.setText(investor.getSurname());
        txt_phone_number.setText(investor.getPhone_number());
        txt_email.setText(investor.getEmail());
    }

    @FXML
    public void edit_fields () {
        /*
            Cette fonction permet de modifier l'affichage de la page pour modifier un champ
         */

        btn_submit.setVisible(true);
        txt_name.setText("");
        txt_surname.setText("");
        txt_email.setText("");
        txt_phone_number.setText("");
        txt_phone_number.setEditable(true);
        txt_email.setEditable(true);
        txt_name.setEditable(true);
        txt_surname.setEditable(true);
        txt_name.setStyle("-fx-background-color: white;");
        txt_surname.setStyle("-fx-background-color: white;");
        txt_email.setStyle("-fx-background-color: white;");
        txt_phone_number.setStyle("-fx-background-color: white;");
        btn_password.setVisible(false);

    }


    public void edit_fields2 () {
        /*
            Cette fonction permet de modifier l'affichage de la page pour ne pas modifier les champs
         */
        btn_submit.setVisible(false);
        txt_phone_number.setEditable(false);
        txt_email.setEditable(false);
        txt_name.setEditable(false);
        txt_surname.setEditable(false);
        txt_name.setStyle("-fx-background-color: #CCCCCC;");
        txt_surname.setStyle("-fx-background-color: #CCCCCC;");
        txt_email.setStyle("-fx-background-color: #CCCCCC;");
        txt_phone_number.setStyle("-fx-background-color: #CCCCCC;");
        btn_password.setVisible(true);
    }

    @FXML
    public void update_field(){
        /*
            Cette fonction permet de modifier les données d'un investisseur dans la base de données
         */

        String name = txt_name.getText();
        String surname = txt_surname.getText();
        String email = txt_email.getText();
        String phone_number = txt_phone_number.getText();

        StringBuilder requeteBuilder = new StringBuilder("UPDATE investor SET ");
        List<String> values = new ArrayList<>();

        boolean first = false;
        if(!name.isEmpty()){
            requeteBuilder.append("name = ?");
            investor.setName(name);
            txt_name.setText(name);
            values.add(name);
            first = true;
        }else{
            txt_name.setText(investor.getName());
        }
        if(!surname.isEmpty()){
            if(first) requeteBuilder.append(", ");
            requeteBuilder.append("surname = ?");
            investor.setSurname(surname);
            txt_surname.setText(surname);
            values.add(surname);
            first = true;
        }else{
            txt_surname.setText(investor.getSurname());
        }
        if(!email.isEmpty()){
            if(IntefaceFeatures.isValidEmail(email)){
                if(IntefaceFeatures.isEmailUnique(email)){
                    if(first) requeteBuilder.append(", ");
                    requeteBuilder.append("email = ?");
                    investor.setEmail(email);
                    txt_email.setText(email);
                    values.add(email);
                    first = true;
                }else{
                    label_erreur.setText("L'email est déjà utilisé !");
                }
            }else{
                label_erreur.setText("Format de l'email ne correspond pas ! Ex : java@boa.fr");
            }

        }else{
            txt_email.setText(investor.getEmail());
        }
        if(!phone_number.isEmpty() ){
            if(IntefaceFeatures.isValidPhone(phone_number)){
                if(first) requeteBuilder.append(", ");
                requeteBuilder.append("phone_number = ?");
                investor.setPhone_number(phone_number);
                txt_phone_number.setText(phone_number);
                values.add(phone_number);
                first = true;
            }else{
                label_erreur.setText("Le format du numéro de téléphone est incorrecte ! Ex : 0728213087");
            }

        }else{
            txt_phone_number.setText(investor.getPhone_number());
        }


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Update field");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            if(!first){
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Confirmation");
                alert2.setHeaderText(null);
                alert2.setContentText("no fields modified\n");
                alert2.showAndWait();
            }
        } else {
            first = false;

        }
        if(first){
            requeteBuilder.append(" WHERE id_investor = ?");
            values.add(String.valueOf(investor.getId()));

            String requete = requeteBuilder.toString();
            String url = "jdbc:mysql://localhost:3306/database_boa_java?serverTimezone=UTC&useSSL=false";
            try (
                    Connection connection = DriverManager.getConnection(url, IntefaceFeatures.NAME_DB, IntefaceFeatures.MDP_DB);
                    PreparedStatement statement = connection.prepareStatement(requete);)
            {
                for(int i = 0; i < values.size(); i++) {
                    statement.setString(i + 1, values.get(i));
                }

                int affectedRows = statement.executeUpdate();
                if(affectedRows > 0) {

                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        edit_fields2();
    }

    @FXML
    public void change_password() {
        box_npw.setVisible(true);
        box_profil.setVisible(false);
    }
    @FXML
    public void submit_password() throws Exception {
         /*
            Cette fonction permet de modifier le mot de passe d'un utilisateur en base
         */
        String txt_mdp1 = txt_p_actu.getText();
        String txt_mdp2 = txt_p_n_a.getText();
        String txt_mdp3 = txt_p_n.getText();

        if(txt_mdp2.equals("") || txt_mdp3.equals("") || txt_mdp1.equals("")){
            label_erreur.setText("Veuillez remplir tous les champs !");
        }else{
            if(!txt_mdp2.equals(txt_mdp3)){
                label_erreur.setText("Les deux nouveaux mots de passe sont différents");
            }else{
                if(!IntefaceFeatures.isValidPassword(txt_mdp2)){
                    label_erreur.setText("Le format du nouveau mot de passe est incorrect");
                }else{
                    String id = String.valueOf(investor.getId());
                    txt_mdp1 = IntefaceFeatures.encryptPassword(txt_p_actu.getText());

                    String query = "SELECT mdp FROM investor WHERE id_investor = " + id + " ;";
                    String url = "jdbc:mysql://localhost:3306/database_boa_java?serverTimezone=UTC&useSSL=false";

                    try (
                            Connection connection = DriverManager.getConnection(url, IntefaceFeatures.NAME_DB, IntefaceFeatures.MDP_DB);                PreparedStatement preparedStatement = connection.prepareStatement(query);
                    ) {
                        ResultSet resultSet = preparedStatement.executeQuery();
                        if (resultSet.next()) {
                            String ps = resultSet.getString("mdp");

                            if(ps.equals(txt_mdp1)){

                                String mdp = IntefaceFeatures.encryptPassword(txt_mdp2);
                                String updateQuery = "UPDATE investor SET mdp = ? WHERE id_investor = ?;";
                                try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                                    updateStmt.setString(1, mdp);
                                    updateStmt.setString(2, id);

                                    int rowsAffected = updateStmt.executeUpdate();
                                    if(rowsAffected > 0) {
                                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                                        alert2.setTitle("Confirmation");
                                        alert2.setHeaderText(null);
                                        alert2.setContentText("Password update \n");
                                        alert2.showAndWait();

                                    }
                                }
                            }else{
                                label_erreur.setText("Votre mot de passe actuel est faux !");
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void setInvestor(Investor investor, Blockchaine blockchaine) {
        /*
            Affection d'un objet Investor
         */

        Image image = new Image(new File("src/main/resources/galerie/logo.png").toURI().toString());
        imageView.setImage(image);

        String solde = "Solde : " + IntefaceFeatures.compter_montant(investor) + " $";
        label_solde.setText(solde);
        this.investor = investor;
        this.blockchain = blockchaine;
        initialize_investor();
        label_name.setText(investor.getName());
    }

}
