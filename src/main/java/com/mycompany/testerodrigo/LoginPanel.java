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

public class LoginPanel extends JPanel {
    private JTextField matriculaField;
    private JButton loginButton;

    public LoginPanel() {
        setLayout(new GridLayout(3, 2));

        // Matrícula
        add(new JLabel("Matrícula:"));
        matriculaField = new JTextField();
        add(matriculaField);

        // Botão de Login
        loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginButtonListener());
        add(loginButton);
    }

    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String matricula = matriculaField.getText();

            // Validação básica
            if (matricula.isEmpty()) {
                JOptionPane.showMessageDialog(LoginPanel.this, "Por favor, insira a matrícula.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String url = "jdbc:sqlite:academia.db";
            String sql = "SELECT * FROM TB_COLABORADOR WHERE id_colaborador = ?";

            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, matricula);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    String nomeColaborador = rs.getString("nome_colaborador");
                    String idHorario = rs.getString("id_horario");

                    // Abrir a tela de visualização de consultas
                    JFrame consultationFrame = new JFrame("Consultas Agendadas");
                    consultationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    consultationFrame.setSize(600, 400);
                    consultationFrame.add(new ConsultationViewPanel(nomeColaborador, idHorario));
                    consultationFrame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(LoginPanel.this, "Matrícula não encontrada.", "Erro", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(LoginPanel.this, "Erro ao verificar matrícula: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
