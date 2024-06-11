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

        // Matr�cula
        add(new JLabel("Matr�cula:"));
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

        // Hor�rio
        add(new JLabel("Hor�rio:"));
        timeComboBox = new JComboBox<>(getTimeSlots());
        add(timeComboBox);

        // Bot�o de Agendamento
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

            // Valida��o b�sica
            if (matricula.isEmpty() || name.isEmpty() || cpf.isEmpty() || date.isEmpty() || time.isEmpty()) {
                JOptionPane.showMessageDialog(SchedulingPanel.this, "Por favor, preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String url = "jdbc:sqlite:academia.db";
            String sql = "INSERT INTO TB_CONSULTA(horario_consulta, id_cliente, id_colaborador) VALUES(?, ?, ?)";

            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                // Para simplificar, estamos usando valores fixos para id_cliente e id_colaborador.
                // Em uma aplica��o real, esses valores viriam de uma l�gica de sele��o/lookup adequada.
                pstmt.setString(1, date + " " + time); // Combinando data e hor�rio
                pstmt.setInt(2, 1); // id_cliente, deve ser obtido de uma consulta real
                pstmt.setInt(3, 1); // id_colaborador, deve ser obtido de uma consulta real
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(SchedulingPanel.this, "Consulta agendada com sucesso!");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(SchedulingPanel.this, "Erro ao agendar consulta: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }

            // Exibir tela de confirma��o
            JFrame confirmationFrame = new JFrame("Consulta Agendada");
            confirmationFrame.setSize(300, 200);
            confirmationFrame.add(new ConfirmationPanel(name, date, time));
            confirmationFrame.setVisible(true);
        }
    }
}
