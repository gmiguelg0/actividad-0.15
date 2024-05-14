package com.empresa.javafx_sp;

import java.sql.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private TextField txt_nombre;
    @FXML
    private TextField txt_ciudad;
    @FXML
    private TextField txt_facturacion;

    @FXML
    protected void onHelloButtonClick() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/test";
            conn = DriverManager.getConnection(url,"root","");
            String sp = "{CALL sp_add_clientes(?, ?, ?)}";
            CallableStatement cs = conn.prepareCall(sp);
            cs.setString(1, txt_nombre.getText());
            cs.setString(2, txt_ciudad.getText());
            cs.setDouble(3, Double.parseDouble(txt_facturacion.getText()));
            cs.executeUpdate();
            welcomeText.setText("Cliente guardado");
        } catch (ClassNotFoundException | SQLException e) {
            welcomeText.setText("Error al guardar el cliente: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    welcomeText.setText("Error al cerrar la conexión: " + e.getMessage());
                }
            }
        }
    }
}