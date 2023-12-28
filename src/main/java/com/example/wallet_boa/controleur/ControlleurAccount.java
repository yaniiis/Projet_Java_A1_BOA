package com.example.wallet_boa.controleur;

import com.example.wallet_boa.HelloApplication;
import com.example.wallet_boa.modele.Investor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ControlleurAccount {

    private Investor investor;

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




    public void initialize_investor(Investor investor){
        this.investor = investor;
        txt_name.setText(investor.getName());
        txt_surname.setText(investor.getSurname());
        txt_phone_number.setText(investor.getPhone_number());
        txt_email.setText(investor.getEmail());
    }


    @FXML
    public void layout_wallet() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("wallet.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = HelloApplication.getPrimaryStage();
        stage.setTitle("Wallet");
        stage.setScene(new Scene(root, 900, 600));
    }


    @FXML
    public void layout_transaction() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("transactions.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = HelloApplication.getPrimaryStage();
        stage.setTitle("Transactions");
        stage.setScene(new Scene(root, 900, 600));
    }

    @FXML
    public void layout_crypto() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("crypto.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = HelloApplication.getPrimaryStage();
        stage.setTitle("Cryptocurrency");
        stage.setScene(new Scene(root, 900, 600));
    }

    @FXML
    public void layout_stock() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("action.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = HelloApplication.getPrimaryStage();
        stage.setTitle("Stock");
        stage.setScene(new Scene(root, 900, 600));
    }

    @FXML
    public void layout_help() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("help.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = HelloApplication.getPrimaryStage();
        stage.setTitle("Help");
        stage.setScene(new Scene(root, 900, 600));
    }

    @FXML
    public void layout_accueil() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("accueil.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = HelloApplication.getPrimaryStage();
        stage.setTitle("Accueil");
        stage.setScene(new Scene(root, 900, 600));
    }

    @FXML
    public void edit_fields () {
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
            requeteBuilder.append("surnme = ?");
            investor.setSurname(surname);
            txt_surname.setText(surname);
            values.add(surname);
            first = true;
        }else{
            txt_surname.setText(investor.getSurname());
        }
        if(!email.isEmpty()){
            if(first) requeteBuilder.append(", ");
            requeteBuilder.append("email = ?");
            investor.setEmail(email);
            txt_email.setText(email);
            values.add(email);
            first = true;
        }else{
            txt_email.setText(investor.getEmail());
        }
        if(!phone_number.isEmpty()){
            if(first) requeteBuilder.append(", ");
            requeteBuilder.append("phone_number = ?");
            investor.setPhone_number(phone_number);
            txt_phone_number.setText(phone_number);
            values.add(phone_number);
            first = true;
        }else{
            txt_phone_number.setText(investor.getPhone_number());
        }

        if(first){
            requeteBuilder.append(" WHERE id_investor = ?");
            values.add(String.valueOf(investor.getId()));

            String requete = requeteBuilder.toString();
            String url = "jdbc:mysql://localhost:3306/boa_database?serverTimezone=UTC&useSSL=false";
            try (
                    Connection connection = DriverManager.getConnection(url, "root", "equipe_BOA3");
                    PreparedStatement statement = connection.prepareStatement(requete);)
            {
                for(int i = 0; i < values.size(); i++) {
                    statement.setString(i + 1, values.get(i));
                }

                int affectedRows = statement.executeUpdate();
                if(affectedRows > 0) {
                    System.out.println("Mise à jour réussie.");
                } else {
                    System.out.println("Aucune ligne affectée.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else{
            System.out.println("Aucun champ modifié.");
        }
        edit_fields2();
    }

    @FXML
    public void change_password() {
        box_npw.setVisible(true);
        box_profil.setVisible(false);
    }
    @FXML
    public void submit_password(){

        String txt_mdp1 = txt_p_actu.getText();
        String txt_mdp2 = txt_p_n_a.getText();
        String txt_mdp3 = txt_p_n.getText();

        String id = String.valueOf(investor.getId());

        String query = "SELECT mdp FROM investor WHERE id_investor = " + id + " ;";
        String url = "jdbc:mysql://localhost:3306/boa_database?serverTimezone=UTC&useSSL=false";

        try (
                Connection connection = DriverManager.getConnection(url, "root", "equipe_BOA3");
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String ps = resultSet.getString("mdp");

                if(ps.equals(txt_mdp1)){
                    if(txt_mdp2.equals(txt_mdp3)){

                        String updateQuery = "UPDATE investor SET mdp = ? WHERE id_investor = ?;";
                        try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                            updateStmt.setString(1, txt_mdp2); // Nouveau mot de passe
                            updateStmt.setString(2, id); // ID de l'utilisateur

                            int rowsAffected = updateStmt.executeUpdate();
                            if(rowsAffected > 0) {
                                System.out.println("Le mot de passe a été changé avec succès!");
                            } else {
                                System.out.println("Erreur lors de la mise à jour du mot de passe.");
                            }
                        }
                    }else{
                        System.out.println("Les deux nouveaux mots de passe sont différents !");
                    }
                }else{
                    System.out.println("Votre mot de passe actuel est faux !");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
