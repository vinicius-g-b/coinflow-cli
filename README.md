# 💰 CoinFlow CLI

Um sistema de gerenciamento de finanças pessoais executado via terminal, desenvolvido em Java. Este é um projeto de portfólio criado para aplicar conceitos modernos da linguagem e arquitetura de software.

## 🚀 Tecnologias e Práticas Utilizadas
* **Java 8+:** Uso de Stream API, Lambdas e `java.time` (LocalDate).
* **NIO.2:** Manipulação de arquivos para leitura e escrita do banco de dados em formato `.csv`.
* **Arquitetura em Camadas:** Separação clara entre Modelo, Repositório, Serviço e Interface do Usuário (CLI).
* **Boas Práticas:** Injeção de dependências, tratamento de exceções e encapsulamento.

## ⚙️ Funcionalidades
- [x] Cadastro de transações (Receitas e Despesas).
- [x] Persistência automática de dados em arquivo `.csv`.
- [x] Cálculo de saldo em tempo real.
- [x] Relatório avançado de agrupamento de gastos por categoria.

## 👨‍💻 Como executar
1. Clone este repositório.
2. Compile e rode a classe `Main.java` localizada no pacote `br.com.coinflow`.