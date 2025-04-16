-- Tabela de Autocarros (Viaturas)
CREATE TABLE IF NOT EXISTS Viaturas (
    id IDENTITY PRIMARY KEY,
    matricula VARCHAR(50) NOT NULL UNIQUE, -- Identificação única do autocarro
    ano_fabricacao INT NOT NULL CHECK (ano_fabricacao BETWEEN 1990 AND 2026), -- Restrição de ano válido
    tipo_servico VARCHAR(50) NOT NULL, -- Exemplo: Regular, Expresso, Noturno
    latitude DECIMAL(9,6),  -- Localização do autocarro
    longitude DECIMAL(9,6),
    velocidade DECIMAL(5,2) CHECK (velocidade BETWEEN 0 AND 100), -- Respeitando os limites legais
    ocupacao INT CHECK (ocupacao BETWEEN 0 AND 120), -- Limite máximo de passageiros
    nivel_energia DECIMAL(5,2) CHECK (nivel_energia BETWEEN 0 AND 100) NULL, -- Estado da bateria (0 a 100%)
    status_operacional VARCHAR(20) CHECK (status_operacional IN ('Em serviço', 'Fora de serviço', 'Manutenção', 'Avariado'))
);


-- Tabela das Vias (Ruas e Estradas)
CREATE TABLE IF NOT EXISTS Vias (
    id IDENTITY PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE, -- Evita nomes duplicados de vias
    sentido VARCHAR(20) CHECK (sentido IN ('Norte-Sul', 'Leste-Oeste', 'Duplo Sentido')), -- Direções válidas para a organização do trânsito
    limite_velocidade INT CHECK (limite_velocidade BETWEEN 30 AND 100), -- Limites legais em Portugal para autocarros
    fluxo_trafego VARCHAR(20), -- Estado do tráfego (Exemplo: "Congestionado", "Livre")
    evento_trafego VARCHAR(50), -- Eventos na via (Exemplo: "Acidente", "Obras")
    condicao_meteorologica VARCHAR(30) -- Condições meteorológicas (Exemplo: "Chuva", "Sol", "Nublado")
);

-- Tabela das Estações (Paragens e Terminais)
CREATE TABLE IF NOT EXISTS Estacoes (
    id IDENTITY PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE, -- Evita nomes repetidos de estações
    localizacao VARCHAR(255), -- Descrição da localização da estação
    capacidade INT CHECK (capacidade BETWEEN 50 AND 500), -- Capacidade realista entre 50 e 500 passageiros
    tempo_medio_espera INT CHECK (tempo_medio_espera BETWEEN 5 AND 60), -- Tempo médio de espera realista, entre 5 e 60 minutos
    ocupacao INT CHECK (ocupacao BETWEEN 0 AND 500), -- Número de passageiros na estação (até 500)
    sensor_temperatura DECIMAL(5,2) -- Temperatura ambiente da estação
);


/*
=================================================================
|                   EXPLICAÇÃO DOS VALORES                      |
=================================================================


 TABELA: autocarros (Viaturas)
---------------------------------
- **matricula VARCHAR(50) UNIQUE** → Garante que não existam duas viaturas com a mesma matrícula.
- **ano_fabricacao INT CHECK (1990-2026)** → Apenas permite autocarros fabricados entre 1990 e 2026.
- **velocidade DECIMAL(5,2) CHECK (0-100)** → Máximo de 100 km/h, respeitando os limites legais para autocarros em Portugal.
- **ocupacao INT CHECK (0-120)** → Limite de 120 passageiros, compatível com autocarros articulados.
- **nivel_energia DECIMAL(5,2) CHECK (0-100) NULL** → Percentagem da bateria (0-100%) ou NULL para autocarros a diesel/gás.
- **status_operacional CHECK ('Em serviço', 'Fora de serviço', 'Manutenção', 'Avariado')** → Define o estado do autocarro.

 TABELA: vias (Ruas e Estradas)
----------------------------------
- **nome VARCHAR(100) UNIQUE** → Evita nomes duplicados de vias.
- **sentido CHECK ('Norte-Sul', 'Leste-Oeste', 'Duplo Sentido')** → Direções válidas para a organização do trânsito.
- **limite_velocidade INT CHECK (30-100)** → De acordo com os limites legais para vias urbanas e estradas rápidas.
- **fluxo_trafego VARCHAR(20)** → Identifica o estado do tráfego ("Congestionado", "Livre", etc.).
- **evento_trafego VARCHAR(50)** → Guarda incidentes nas vias ("Acidente", "Obras", etc.).
- **condicao_meteorologica VARCHAR(30)** → Regista as condições meteorológicas ("Chuva", "Sol", etc.).

 TABELA: estacoes (Paragens e Terminais)
-------------------------------------------
- **nome VARCHAR(100) UNIQUE** → Evita nomes repetidos de estações.
- **capacidade INT CHECK (50-500)** → Garante que as estações acomodam entre 50 e 500 passageiros.
- **tempo_medio_espera INT CHECK (5-60)** → Tempo médio de espera realista, entre 5 e 60 minutos.
- **ocupacao INT CHECK (0-500)** → Número de passageiros na estação (até 500).
- **sensor_temperatura DECIMAL(5,2)** → Guarda a temperatura ambiente da estação.


=================================================================
|   Estes limites foram definidos com base nas normas dos TUB   |
|   (Transportes Urbanos de Braga) e nas regras do trânsito PT. |
=================================================================
*/

