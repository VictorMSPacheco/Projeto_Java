package com.mycompany.testerodrigo;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConsultationViewPanel extends JPanel {
    public ConsultationViewPanel(String nomeColaborador, String idHorario) {
        setLayout(new BorderLayout());

        // Boas-vindas ao colaborador
        JLabel welcomeLabel = new JLabel("Bem-vindo, " + nomeColaborador + "!");
        add(welcomeLabel, BorderLayout.NORTH);

        // Tabela de consultas
        String[] columnNames = {"ID Consulta", "Horário", "Nome Cliente"};
        Object[][] data = getConsultationsData(idHorario);

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    private Object[][] getConsultationsData(String idHorario) {
        String url = "jdbc:sqlite:academia.db";
        String sql = "SELECT c.id_consulta, c.horario_consulta, cl.nome_cliente " +
                     "FROM TB_CONSULTA c " +
                     "JOIN TB_CLIENTE cl ON c.id_cliente = cl.id_cliente " +
                     "JOIN TB_COLABORADOR co ON c.id_colaborador = co.id_colaborador " +
                     "WHERE co.id_horario = " + idHorario;

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            rs.last();
            int rowCount = rs.getRow();
            rs.beforeFirst();

            Object[][] data = new Object[rowCount][3];
            int i = 0;
            while (rs.next()) {
                data[i][0] = rs.getInt("id_consulta");
                data[i][1] = rs.getString("horario_consulta");
                data[i][2] = rs.getString("nome_cliente");
                i++;
            }
            return data;

        } catch (SQLException e) {
            e.printStackTrace();
            return new Object[0][0];
        }
    }
}
