package com.mycompany.testerodrigo;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Configuração da base de dados
        DatabaseSetup.main(null);

        // Iniciar a interface gráfica
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Agendamento de Consulta de Bioimpedância");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            frame.add(new SchedulingPanel());
            frame.setVisible(true);
        });
    }
}
