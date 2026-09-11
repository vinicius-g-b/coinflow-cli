package br.com.coinflow.service;

import br.com.coinflow.model.Categoria;
import br.com.coinflow.model.TipoTransacao;
import br.com.coinflow.model.Transacao;
import br.com.coinflow.repository.TransacaoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GerenciadorFinanceiroService {
    
    // O service não precisa saber SE é CSV ou Banco de Dados, 
    // ele só precisa da Interface. Isso é Injeção de Dependência.
    private final TransacaoRepository repository;

    public GerenciadorFinanceiroService(TransacaoRepository repository) {
        this.repository = repository;
    }

    public void adicionarTransacao(LocalDate data, String descricao, double valor, Categoria categoria, TipoTransacao tipo) {
        Transacao novaTransacao = new Transacao(data, descricao, valor, categoria, tipo);
        repository.salvar(novaTransacao);
    }

    public List<Transacao> listarTodas() {
        return repository.listarTodas();
    }

    // --- APLICANDO A STREAM API ---

    /**
     * Calcula o saldo total (Receitas - Despesas)
     */
    public double calcularSaldo() {
        List<Transacao> transacoes = repository.listarTodas();

        double totalReceitas = transacoes.stream()
                .filter(t -> t.getTipo() == TipoTransacao.RECEITA)
                .mapToDouble(Transacao::getValor) // Converte a Stream de Objetos para uma Stream de números (double)
                .sum(); // Soma tudo de forma elegante

        double totalDespesas = transacoes.stream()
                .filter(t -> t.getTipo() == TipoTransacao.DESPESA)
                .mapToDouble(Transacao::getValor)
                .sum();

        return totalReceitas - totalDespesas;
    }

    /**
     * Filtra transações por um mês e ano específicos
     */
    public List<Transacao> filtrarPorMesEAno(int mes, int ano) {
        return repository.listarTodas().stream()
                .filter(t -> t.getData().getMonthValue() == mes && t.getData().getYear() == ano)
                .collect(Collectors.toList()); // Coleta o resultado do filtro de volta para uma Lista
    }

    /**
     * Relatório Avançado: Agrupa a soma de gastos por categoria
     * Retorna um Map onde a Chave é a Categoria e o Valor é a soma total gasta nela.
     */
    public Map<Categoria, Double> relatorioGastosPorCategoria() {
        return repository.listarTodas().stream()
                .filter(t -> t.getTipo() == TipoTransacao.DESPESA) // Só queremos analisar gastos
                .collect(Collectors.groupingBy(
                        Transacao::getCategoria, // Agrupa pela categoria
                        Collectors.summingDouble(Transacao::getValor) // Soma o valor de cada transação daquela categoria
                ));
    }
}