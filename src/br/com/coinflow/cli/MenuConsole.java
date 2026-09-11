package br.com.coinflow.cli;

import br.com.coinflow.model.Categoria;
import br.com.coinflow.model.TipoTransacao;
import br.com.coinflow.model.Transacao;
import br.com.coinflow.service.GerenciadorFinanceiroService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MenuConsole {
    private final GerenciadorFinanceiroService service;
    private final Scanner scanner;
    // Formatador para o padrão brasileiro
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public MenuConsole(GerenciadorFinanceiroService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        boolean rodando = true;

        while (rodando) {
            System.out.println("\n===== COINFLOW CLI =====");
            System.out.println("1. Adicionar Transação");
            System.out.println("2. Listar Todas as Transações");
            System.out.println("3. Ver Saldo Atual");
            System.out.println("4. Relatório de Gastos por Categoria");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");

            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1" -> adicionarTransacaoUI();
                case "2" -> listarTransacoesUI();
                case "3" -> verSaldoUI();
                case "4" -> relatorioUI();
                case "5" -> {
                    System.out.println("Salvando dados e encerrando o sistema... Até logo!");
                    rodando = false;
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void adicionarTransacaoUI() {
        try {
            System.out.print("Data (dd/MM/yyyy): ");
            LocalDate data = LocalDate.parse(scanner.nextLine(), formatter);

            System.out.print("Descrição (ex: Assinatura Alura, Conta de Luz): ");
            String descricao = scanner.nextLine();

            System.out.print("Valor (ex: 150.50): ");
            double valor = Double.parseDouble(scanner.nextLine());

            System.out.print("Categoria (ALIMENTACAO, TRANSPORTE, SALARIO, EDUCACAO, OUTROS): ");
            Categoria categoria = Categoria.valueOf(scanner.nextLine().toUpperCase());

            System.out.print("Tipo (RECEITA ou DESPESA): ");
            TipoTransacao tipo = TipoTransacao.valueOf(scanner.nextLine().toUpperCase());

            service.adicionarTransacao(data, descricao, valor, categoria, tipo);
            System.out.println("✅ Transação adicionada com sucesso!");

        } catch (DateTimeParseException e) {
            System.out.println("❌ Erro: Formato de data inválido. Use dd/MM/yyyy.");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Erro: Categoria ou Tipo inválido verifique a ortografia.");
        } catch (Exception e) {
            System.out.println("❌ Erro inesperado: " + e.getMessage());
        }
    }

    private void listarTransacoesUI() {
        List<Transacao> transacoes = service.listarTodas();
        if (transacoes.isEmpty()) {
            System.out.println("Nenhuma transação encontrada.");
            return;
        }
        System.out.println("\n--- Histórico de Transações ---");
        transacoes.forEach(System.out::println); // Uso elegante de Method Reference (Java 8+)
    }

    private void verSaldoUI() {
        double saldo = service.calcularSaldo();
        System.out.printf("\n💰 Saldo Atual: R$ %.2f\n", saldo);
    }

    private void relatorioUI() {
        Map<Categoria, Double> relatorio = service.relatorioGastosPorCategoria();
        if (relatorio.isEmpty()) {
            System.out.println("Nenhuma despesa registrada para gerar relatório.");
            return;
        }
        System.out.println("\n--- Gastos por Categoria ---");
        // Iterando sobre o Map usando lambda
        relatorio.forEach((categoria, valorTotal) -> 
            System.out.printf("%s: R$ %.2f\n", categoria, valorTotal)
        );
    }
}