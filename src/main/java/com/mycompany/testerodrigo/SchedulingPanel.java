package com.mycompany.testerodrigo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class SchedulingPanel extends JPanel {
    private JTextField matriculaField;
    private JTextField nameField;
    private JTextField cpfField;
    private JComboBox<String> dateComboBox;
    private JComboBox<String> timeComboBox;
    private JButton scheduleButton;

    public SchedulingPanel() {
        setLayout(new GridLayout(6, 2));

        // Matrícula
        add(new JLabel("Matrícula:"));
        matriculaField = new JTextField();
        add(matriculaField);

        // Nome
        add(new JLabel("Nome:"));
        nameField = new JTextField();
        add(nameField);

        // CPF
        add(new JLabel("CPF:"));
        cpfField = new JTextField();
        add(cpfField);

        // Data
        add(new JLabel("Data:"));
        dateComboBox = new JComboBox<>(getNext7Days());
        add(dateComboBox);

        // Horário
        add(new JLabel("Horário:"));
        timeComboBox = new JComboBox<>(getTimeSlots());
        add(timeComboBox);

        // Botão de Agendamento
        scheduleButton = new JButton("Agendar Consulta");
        scheduleButton.addActionListener(new ScheduleButtonListener());
        add(scheduleButton);
    }

    private String[] getNext7Days() {
        String[] days = new String[7];
        LocalDate today = LocalDate.now();
        for (int i = 0; i < 7; i++) {
            days[i] = today.plusDays(i).toString();
        }
        return days;
    }

    private String[] getTimeSlots() {
        return new String[]{"08:00", "09:00", "10:00", "11:00", "14:00", "15:00", "16:00", "17:00"};
    }

    private class ScheduleButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String matricula = matriculaField.getText();
            String name = nameField.getText();
            String cpf = cpfField.getText();
            String date = (String) dateComboBox.getSelectedItem();
            String time = (String) timeComboBox.getSelectedItem();

            // Validação básica
            if (matricula.isEmpty() || name.isEmpty() || cpf.isEmpty() || date.isEmpty() || time.isEmpty()) {
                JOptionPane.showMessageDialog(SchedulingPanel.this, "Por favor, preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String url = "jdbc:sqlite:academia.db";
            String sqlFindClientById = "SELECT id_cliente FROM TB_CLIENTE WHERE id_cliente = ?";
            String sqlFindAvailableCollaborator = 
                "SELECT id_colaborador FROM TB_COLABORADOR WHERE id_colaborador NOT IN " +
                "(SELECT id_colaborador FROM TB_CONSULTA WHERE horario_consulta = ?) LIMIT 1";
            String sqlInsertConsulta = "INSERT INTO TB_CONSULTA(horario_consulta, id_cliente, id_colaborador) VALUES(?, ?, ?)";

            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement pstmtFindClient = conn.prepareStatement(sqlFindClientById);
                 PreparedStatement pstmtFindCollaborator = conn.prepareStatement(sqlFindAvailableCollaborator);
                 PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsertConsulta)) {

                // Encontrar o ID do cliente baseado na matrícula
                pstmtFindClient.setString(1, matricula);
                ResultSet rsClient = pstmtFindClient.executeQuery();

                if (rsClient.next()) {
                    int idCliente = rsClient.getInt("id_cliente");

                    // Encontrar um colaborador disponível
                    pstmtFindCollaborator.setString(1, date + " " + time);
                    ResultSet rsCollaborator = pstmtFindCollaborator.executeQuery();

                    if (rsCollaborator.next()) {
                        int idColaborador = rsCollaborator.getInt("id_colaborador");

                        // Inserir a consulta
                        pstmtInsert.setString(1, date + " " + time); // Combinando data e horário
                        pstmtInsert.setInt(2, idCliente); // ID do cliente encontrado
                        pstmtInsert.setInt(3, idColaborador); // ID do colaborador disponível
                        pstmtInsert.executeUpdate();

                        JOptionPane.showMessageDialog(SchedulingPanel.this, "Consulta agendada com sucesso!");

                        // Exibir tela de confirmação
                        JFrame confirmationFrame = new JFrame("Consulta Agendada");
                        confirmationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        confirmationFrame.setSize(300, 200);
                        confirmationFrame.add(new ConfirmationPanel(name, date, time));
                        confirmationFrame.setLocationRelativeTo(null); // Centralizar na tela
                        confirmationFrame.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(SchedulingPanel.this, "Não há colaboradores disponíveis para o horário selecionado.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(SchedulingPanel.this, "Cliente não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(SchedulingPanel.this, "Erro ao agendar consulta: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}