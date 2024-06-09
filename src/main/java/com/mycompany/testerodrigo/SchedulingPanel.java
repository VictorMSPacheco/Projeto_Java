package com.mycompany.testerodrigo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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

            // Salvar no banco de dados
            String url = "jdbc:sqlite:agendamento.db";
            String sql = "INSERT INTO agendamentos(matricula, nome, cpf, data, horario) VALUES(?, ?, ?, ?, ?)";

            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, matricula);
                pstmt.setString(2, name);
                pstmt.setString(3, cpf);
                pstmt.setString(4, date);
                pstmt.setString(5, time);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(SchedulingPanel.this, "Consulta agendada com sucesso!");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(SchedulingPanel.this, "Erro ao agendar consulta: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }

            // Exibir tela de confirmação
            JFrame confirmationFrame = new JFrame("Consulta Agendada");
            confirmationFrame.setSize(300, 200);
            confirmationFrame.add(new ConfirmationPanel(name, date, time));
            confirmationFrame.setVisible(true);
        }
    }
}
