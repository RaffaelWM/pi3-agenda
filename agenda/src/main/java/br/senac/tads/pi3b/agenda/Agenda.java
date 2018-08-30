/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.pi3b.agenda;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author raffael.wmoraes
 */
public class Agenda {

    private Connection obterConexao() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        // Passo 1:Registrar driver JDBC;
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/agendabd",
                "root",
                "");
        return conn;
    }

    public void executar() {
        String querySql = "SELECT ID, NOME, DTNASCIMENTO FROM PESSOA";
        try (
                Connection conn = obterConexao();
                PreparedStatement stmt = conn.prepareStatement(querySql);
                ResultSet resultados = stmt.executeQuery()) {
            while (resultados.next()) {
                long id = resultados.getLong("ID");
                String nome = resultados.getString("NOME");
                Date dtNascimento = resultados.getDate("DTNASCIMENTO");
                System.out.println(id + " " + nome + " " + dtNascimento);
            }

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Agenda.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void inserir(String nome, String data) {
        String querySql = "INSERT INTO PESSOA (NOME, DTNASCIMENTO )VALUES (?,?)";
        try (
                Connection conn = obterConexao();
                PreparedStatement stmt = conn.prepareStatement(querySql);) {
            
            stmt.setString(1, nome);
            stmt.setString(2, data);
           
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Agenda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        Agenda a = new Agenda();
        Date data = new Date();
        a.inserir("Raffael", "1991-05-09");
        a.executar();
    }

}
