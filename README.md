# Agendamento de Consulta de Bioimpedância

## Descrição

Este projeto é uma aplicação Java para agendamento de consultas de bioimpedância em uma academia. O sistema permite que clientes agendem consultas com colaboradores disponíveis e fornece uma interface de login para colaboradores visualizarem suas consultas agendadas.

## Estrutura do Projeto

- **Main.java**: Classe principal que inicializa a aplicação, criando as janelas de agendamento e login.
- **DatabaseSetup.java**: Configura e popula o banco de dados SQLite com as tabelas necessárias para a aplicação.
- **ConfirmationPanel.java**: Painel que exibe a confirmação de agendamento de consulta.
- **SchedulingPanel.java**: Painel de agendamento de consultas, permitindo que clientes insiram seus dados e escolham data e horário.
- **LoginPanel.java**: Painel de login para colaboradores.
- **ConsultationViewPanel.java**: Painel para visualização das consultas agendadas pelos colaboradores.

## Configuração e Execução

### Requisitos

- Java JDK
- SQLite

### Execução

Basta rodar o projeto que ele ja inicializará toda a aplicação, incluindo criação e população do banco de dados (se necessário).

## Uso

### Agendamento de Consultas

A janela principal permite que os clientes agendem suas consultas fornecendo matrícula, nome, CPF, data e horário desejados.
Após o agendamento, uma tela de confirmação exibe os detalhes da consulta e dicas para o dia.

### Login de Colaboradores

A janela de login permite que colaboradores insiram seu ID para visualizar as consultas agendadas.
