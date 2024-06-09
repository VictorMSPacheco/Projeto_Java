package com.mycompany.testerodrigo;

import javax.swing.*;
import java.awt.*;

public class ConfirmationPanel extends JPanel {
    public ConfirmationPanel(String name, String date, String time) {
        setLayout(new GridLayout(9, 1));

        add(new JLabel("Consulta Agendada com Sucesso!"));
        add(new JLabel("Nome: " + name));
        add(new JLabel("Data: " + date));
        add(new JLabel("Horário: " + time));

        // Dicas
        add(new JLabel("Dicas para o dia da consulta:"));
        add(new JLabel("1. Chegue 15 minutos antes do horário."));
        add(new JLabel("2. Evite comer 2 horas antes da consulta."));
        add(new JLabel("3. Beba bastante água."));
        add(new JLabel("4. Evite atividades físicas intensas no dia."));
    }
}
