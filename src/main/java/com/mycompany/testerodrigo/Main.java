package com.mycompany.testerodrigo;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        // Criar a janela principal de agendamento
        JFrame schedulingFrame = new JFrame("Agendamento de Consulta de Bioimpedância");
        schedulingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        schedulingFrame.setSize(400, 400);
        schedulingFrame.add(new SchedulingPanel());
        schedulingFrame.setVisible(true);

        // Criar a janela de login do colaborador
        JFrame loginFrame = new JFrame("Login do Colaborador");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(400, 200);
        loginFrame.add(new LoginPanel());
        loginFrame.setVisible(true);
    }
}
