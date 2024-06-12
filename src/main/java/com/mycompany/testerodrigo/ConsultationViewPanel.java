package com.mycompany.testerodrigo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConsultationViewPanel extends JPanel {
    private JTable table;

    public ConsultationViewPanel(int idColaborador) {
        setLayout(new BorderLayout());

        // Boas-vindas ao colaborador
        JLabel welcomeLabel = new JLabel("Consultas Agendadas para o Colaborador ID " + idColaborador);
        add(welcomeLabel, BorderLayout.NORTH);

        // Tabela de consultas
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Consulta");
        model.addColumn("Horário");
        model.addColumn("Nome Cliente");

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        fetchConsultations(idColaborador);
    }

    private void fetchConsultations(int idColaborador) {
        String url = "jdbc:sqlite:academia.db";
        String sql = "SELECT c.id_consulta, c.horario_consulta, cl.nome_cliente " +
                     "FROM TB_CONSULTA c " +
                     "JOIN TB_CLIENTE cl ON c.id_cliente = cl.id_cliente " +
                     "WHERE c.id_colaborador = " + idColaborador;

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int idConsulta = rs.getInt("id_consulta");
                String horarioConsulta = rs.getString("horario_consulta");
                String nomeCliente = rs.getString("nome_cliente");

                // Adicionar linha na tabela
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.addRow(new Object[]{idConsulta, horarioConsulta, nomeCliente});
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}