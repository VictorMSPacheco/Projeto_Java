package com.mycompany.testerodrigo;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        // Criar a janela principal do aplicativo
        JFrame frame = new JFrame("Agendamento de Consulta de Bioimpedância");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        
        // Adicionar o painel de agendamento à janela
        frame.add(new SchedulingPanel());
        
        // Tornar a janela visível
        frame.setVisible(true);
    }
}
