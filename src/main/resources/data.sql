-- Inserção de Autocarros (Exemplos de Dados)
INSERT INTO Viaturas (matricula, ano_fabricacao, tipo_servico, latitude, longitude, velocidade, ocupacao, nivel_energia, status_operacional) VALUES
('34-AB-56', 2020, 'Regular', 41.55032, -8.42005, 45.0, 40, 75.5, 'Em serviço'),
('12-CD-34', 2019, 'Noturno', 41.54567, -8.42678, 38.5, 25, 60.0, 'Manutenção'),
('99-ZZ-01', 2022, 'Expresso', 41.55123, -8.42256, 80.0, 50, 90.0, 'Fora de serviço'),
('78-XY-45', 2018, 'Turístico', 41.54321, -8.41987, 50.0, 30, NULL, 'Em serviço'), 
('55-YY-77', 2023, 'Regular', 41.55234, -8.43056, 60.0, 45, 85.0, 'Avariado');

-- Inserção de Vias (Exemplos de Dados)
INSERT INTO Vias (nome, sentido, limite_velocidade, fluxo_trafego, evento_trafego, condicao_meteorologica) VALUES
('Avenida Central', 'Norte-Sul', 50, 'Congestionado', 'Acidente', 'Chuva'),
('Rua da Universidade', 'Leste-Oeste', 30, 'Livre', NULL, 'Sol'),
('Circular Externa', 'Duplo Sentido', 80, 'Moderado', NULL, 'Nublado'),
('Via Rápida Braga', 'Norte-Sul', 100, 'Livre', NULL, 'Sol');

-- Inserção de Estações (Exemplos de Dados)
INSERT INTO Estacoes (nome, localizacao, capacidade, tempo_medio_espera, ocupacao, sensor_temperatura) VALUES
('Estação Central', 'Braga Centro', 300, 10, 150, 25.5),
('Terminal Norte', 'Universidade do Minho', 200, 5, 100, 23.0),
('Paragem Avenida', 'Avenida da Liberdade', 80, 7, 50, 22.0),
('Estação Sul', 'Rua de São Vicente', 150, 12, 90, 24.5);
