package com.mycompany.testerodrigo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSetup {

    public static void main(String[] args) {
        String url = "jdbc:sqlite:academia.db";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            // Criação das tabelas
            String[] createTableStatements = {
                // Comandos de criação das tabelas
                "CREATE TABLE IF NOT EXISTS TB_PLANO (" +
                "id_plano INTEGER PRIMARY KEY NOT NULL UNIQUE," +
                "descricao TEXT NOT NULL," +
                "valor REAL NOT NULL" +
                ");",

                "CREATE TABLE IF NOT EXISTS TB_FUNCAO (" +
                "id_funcao INTEGER PRIMARY KEY NOT NULL UNIQUE," +
                "descricao TEXT NOT NULL" +
                ");",

                "CREATE TABLE IF NOT EXISTS TB_HORARIO_TRABALHO (" +
                "id_horario INTEGER PRIMARY KEY NOT NULL UNIQUE," +
                "hora_inicio TEXT NOT NULL," +
                "hora_fim TEXT NOT NULL" +
                ");",

                "CREATE TABLE IF NOT EXISTS TB_CLIENTE (" +
                "id_cliente INTEGER PRIMARY KEY NOT NULL UNIQUE," +
                "nome_cliente TEXT NOT NULL," +
                "cpf_cliente TEXT NOT NULL UNIQUE," +
                "nascimento_cliente TEXT NOT NULL," +
                "sexo_cliente TEXT NOT NULL," +
                "contato_cliente TEXT NOT NULL," +
                "email_cliente TEXT NOT NULL," +
                "endereco_cliente TEXT NOT NULL," +
                "id_plano INTEGER NOT NULL," +
                "FOREIGN KEY(id_plano) REFERENCES TB_PLANO(id_plano)" +
                ");",

                "CREATE TABLE IF NOT EXISTS TB_COLABORADOR (" +
                "id_colaborador INTEGER PRIMARY KEY NOT NULL UNIQUE," +
                "nome_colaborador TEXT NOT NULL," +
                "cpf_colaborador TEXT NOT NULL UNIQUE," +
                "nascimento_colaborador TEXT NOT NULL," +
                "sexo_colaborador TEXT NOT NULL," +
                "contato_colaborador TEXT NOT NULL," +
                "email_colaborador TEXT NOT NULL," +
                "endereco_colaborador TEXT NOT NULL," +
                "id_funcao INTEGER NOT NULL," +
                "id_horario INTEGER NOT NULL," +
                "FOREIGN KEY(id_funcao) REFERENCES TB_FUNCAO(id_funcao)," +
                "FOREIGN KEY(id_horario) REFERENCES TB_HORARIO_TRABALHO(id_horario)" +
                ");",

                "CREATE TABLE IF NOT EXISTS TB_CONSULTA (" +
                "id_consulta INTEGER PRIMARY KEY NOT NULL UNIQUE," +
                "horario_consulta TEXT NOT NULL," +
                "id_cliente INTEGER NOT NULL," +
                "id_colaborador INTEGER NOT NULL," +
                "FOREIGN KEY(id_cliente) REFERENCES TB_CLIENTE(id_cliente)," +
                "FOREIGN KEY(id_colaborador) REFERENCES TB_COLABORADOR(id_colaborador)" +
                ");"
            };

            for (String sql : createTableStatements) {
                stmt.executeUpdate(sql);
            }

            // Verifica se a tabela está populada
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM TB_PLANO");
            rs.next();
            int count = rs.getInt(1);

            if (count == 0) {
                // Tabela está vazia, então popule-a
                populateTables(stmt);
                System.out.println("Tabelas criadas e populadas com sucesso.");
            } else {
                System.out.println("Tabelas já existem e estão populadas.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Método para popular as tabelas
    private static void populateTables(Statement stmt) throws SQLException {
        // Inserir dados nas tabelas
        String[] sqlStatements = {
            // Comandos de inserção aqui
            "INSERT INTO TB_PLANO (id_plano, descricao, valor) VALUES (1, 'Plano Básico', 50.0), " +
            "(2, 'Plano Intermediário', 100.0), (3, 'Plano Premium', 200.0);",
            "INSERT INTO TB_FUNCAO (id_funcao, descricao) VALUES (1, 'Nutricionista'), " +
            "(2, 'Fisioterapeuta'), (3, 'Personal Trainer'), (4, 'Estagiário');",
            "INSERT INTO TB_HORARIO_TRABALHO (id_horario, hora_inicio, hora_fim) VALUES " +
            "(1, '08:00', '14:00'), (2, '14:00', '20:00'), (3, '20:00', '02:00'), (4, '02:00', '08:00');",
            "INSERT INTO TB_CLIENTE (id_cliente, nome_cliente, cpf_cliente, nascimento_cliente, sexo_cliente, contato_cliente, email_cliente, endereco_cliente, id_plano) VALUES " +
            "(1, 'Ana Maria Braga', '123.456.789-00', '1949-04-01', 'F', '21987654321', 'ana@exemplo.com', 'Rua das Flores, 123', 1)," +
            "(2, 'Fausto Silva', '123.456.789-01', '1950-05-02', 'M', '21987654322', 'fausto@exemplo.com', 'Avenida Brasil, 456', 2)," +
            "(3, 'Ivete Sangalo', '123.456.789-02', '1972-05-27', 'F', '21987654323', 'ivete@exemplo.com', 'Rua das Palmeiras, 789', 3)," +
            "(4, 'William Bonner', '123.456.789-03', '1963-11-16', 'M', '21987654324', 'william@exemplo.com', 'Rua do Comércio, 101', 1)," +
            "(5, 'Fátima Bernardes', '123.456.789-04', '1962-09-17', 'F', '21987654325', 'fatima@exemplo.com', 'Rua da Paz, 202', 2)," +
            "(6, 'Rodrigo Faro', '123.456.789-05', '1973-10-20', 'M', '21987654326', 'rodrigo@exemplo.com', 'Avenida Central, 303', 3)," +
            "(7, 'Eliana Michaelichen', '123.456.789-06', '1973-11-22', 'F', '21987654327', 'eliana@exemplo.com', 'Rua Nova, 404', 1)," +
            "(8, 'Gugu Liberato', '123.456.789-07', '1959-04-10', 'M', '21987654328', 'gugu@exemplo.com', 'Avenida Paulista, 505', 2)," +
            "(9, 'Sabrina Sato', '123.456.789-08', '1981-02-04', 'F', '21987654329', 'sabrina@exemplo.com', 'Rua das Américas, 606', 3)," +
            "(10, 'Silvio Santos', '123.456.789-09', '1930-12-12', 'M', '21987654330', 'silvio@exemplo.com', 'Avenida dos Estados, 707', 1)," +
            "(11, 'Xuxa Meneghel', '123.456.789-10', '1963-03-27', 'F', '21987654331', 'xuxa@exemplo.com', 'Rua dos Artistas, 808', 2)," +
            "(12, 'Luciano Huck', '123.456.789-11', '1971-09-03', 'M', '21987654332', 'luciano@exemplo.com', 'Rua das Estrelas, 909', 3)," +
            "(13, 'Angélica Ksyvickis', '123.456.789-12', '1973-11-30', 'F', '21987654333', 'angelica@exemplo.com', 'Rua Nova, 1010', 1)," +
            "(14, 'Sandy Leah', '123.456.789-13', '1983-01-28', 'F', '21987654334', 'sandy@exemplo.com', 'Rua das Oliveiras, 111', 2)," +
            "(15, 'Junior Lima', '123.456.789-14', '1984-04-11', 'M', '21987654335', 'junior@exemplo.com', 'Avenida do Progresso, 1212', 3)," +
            "(16, 'Marcos Mion', '123.456.789-15', '1979-06-20', 'M', '21987654336', 'marcos@exemplo.com', 'Rua das Palmeiras, 1313', 1)," +
            "(17, 'Ana Paula Arósio', '123.456.789-16', '1975-07-16', 'F', '21987654337', 'ana.paula@exemplo.com', 'Avenida do Contorno, 1414', 2)," +
            "(18, 'Fernanda Lima', '123.456.789-17', '1977-06-25', 'F', '21987654338', 'fernanda@exemplo.com', 'Rua das Laranjeiras, 1515', 3)," +
            "(19, 'Thiago Lacerda', '123.456.789-18', '1978-01-19', 'M', '21987654339', 'thiago@exemplo.com', 'Avenida do Trabalhador, 1616', 1)," +
            "(20, 'Deborah Secco', '123.456.789-19', '1979-11-26', 'F', '21987654340', 'deborah@exemplo.com', 'Rua da Glória, 1717', 2);",
            "INSERT INTO TB_COLABORADOR (id_colaborador, nome_colaborador, cpf_colaborador, nascimento_colaborador, sexo_colaborador, contato_colaborador, email_colaborador, endereco_colaborador, id_funcao, id_horario) VALUES " +
            "(1, 'Pedro Bial', '223.456.789-00', '1958-03-29', 'M', '21987654341', 'pedro@exemplo.com', 'Rua da Televisão, 111', 1, 1)," +
            "(2, 'Glória Maria', '223.456.789-01', '1949-08-15', 'F', '21987654342', 'gloria@exemplo.com', 'Avenida da Notícia, 222', 2, 2)," +
            "(3, 'Fernanda Montenegro', '223.456.789-02', '1929-10-16', 'F', '21987654343', 'fernanda@exemplo.com', 'Rua do Teatro, 333', 3, 3)," +
            "(4, 'Chico Buarque', '223.456.789-03', '1944-06-19', 'M', '21987654344', 'chico@exemplo.com', 'Avenida da Música, 444', 4, 4)," +
            "(5, 'Neymar Jr', '223.456.789-04', '1992-02-05', 'M', '21987654345', 'neymar@exemplo.com', 'Rua do Futebol, 555', 1, 1)," +
            "(6, 'Anitta', '223.456.789-05', '1993-03-30', 'F', '21987654346', 'anitta@exemplo.com', 'Avenida do Funk, 666', 2, 2)," +
            "(7, 'Lázaro Ramos', '223.456.789-06', '1978-11-01', 'M', '21987654347', 'lazaro@exemplo.com', 'Rua do Ator, 777', 3, 3)," +
            "(8, 'Camila Pitanga', '223.456.789-07', '1977-06-14', 'F', '21987654348', 'camila@exemplo.com', 'Avenida da Atriz, 888', 4, 4)," +
            "(9, 'Jô Soares', '223.456.789-08', '1938-01-16', 'M', '21987654349', 'jo@exemplo.com', 'Rua do Humor, 999', 1, 1)," +
            "(10, 'Gisele Bündchen', '223.456.789-09', '1980-07-20', 'F', '21987654350', 'gisele@exemplo.com', 'Avenida da Moda, 1010', 2, 2)," +
            "(11, 'Marina Silva', '223.456.789-10', '1958-02-08', 'F', '21987654351', 'marina@exemplo.com', 'Rua da Política, 1111', 3, 3)," +
            "(12, 'Caetano Veloso', '223.456.789-11', '1942-08-07', 'M', '21987654352', 'caetano@exemplo.com', 'Avenida da Tropicália, 1212', 4, 4)," +
            "(13, 'Zezé Di Camargo', '223.456.789-12', '1962-08-17', 'M', '21987654353', 'zeze@exemplo.com', 'Rua do Sertanejo, 1313', 1, 1)," +
            "(14, 'Ivete Sangalo', '223.456.789-13', '1972-05-27', 'F', '21987654354', 'ivete@exemplo.com', 'Avenida da Bahia, 1414', 2, 2)," +
            "(15, 'Rita Lee', '223.456.789-14', '1947-12-31', 'F', '21987654355', 'rita@exemplo.com', 'Rua do Rock, 1515', 3, 3)," +
            "(16, 'Tony Ramos', '223.456.789-15', '1948-08-25', 'M', '21987654356', 'tony@exemplo.com', 'Avenida do Cinema, 1616', 4, 4)," +
            "(17, 'Paulo Coelho', '223.456.789-16', '1947-08-24', 'M', '21987654357', 'paulo@exemplo.com', 'Rua do Escritor, 1717', 1, 1)," +
            "(18, 'Adriana Esteves', '223.456.789-17', '1969-12-15', 'F', '21987654358', 'adriana@exemplo.com', 'Avenida da Atriz, 1818', 2, 2)," +
            "(19, 'Djavan', '223.456.789-18', '1949-01-27', 'M', '21987654359', 'djavan@exemplo.com', 'Rua da Música, 1919', 3, 3)," +
            "(20, 'Gal Costa', '223.456.789-19', '1945-09-26', 'F', '21987654360', 'gal@exemplo.com', 'Avenida da Tropicália, 2020', 4, 4);"
        };

        for (String sql : sqlStatements) {
            stmt.executeUpdate(sql);
        }
    }
}

