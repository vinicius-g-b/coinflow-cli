package br.com.coinflow.model;

import java.time.LocalDate;
import java.util.UUID;

public class Transacao {
    private String id;
    private LocalDate data;
    private String descricao;
    private double valor;
    private Categoria categoria;
    private TipoTransacao tipo;

    // Construtor 1: Para criar uma NOVA transação (Gera um ID aleatório)
    public Transacao(LocalDate data, String descricao, double valor, Categoria categoria, TipoTransacao tipo) {
        this.id = UUID.randomUUID().toString(); // Gera um ID único, ex: "f47ac10b-58cc-4372-a567-0e02b2c3d479"
        this.data = data;
        this.descricao = descricao;
        this.valor = valor;
        this.categoria = categoria;
        this.tipo = tipo;
    }

    // Construtor 2: Para ler transações do arquivo CSV (Usa o ID que já estava salvo)
    public Transacao(String id, LocalDate data, String descricao, double valor, Categoria categoria, TipoTransacao tipo) {
        this.id = id;
        this.data = data;
        this.descricao = descricao;
        this.valor = valor;
        this.categoria = categoria;
        this.tipo = tipo;
    }

    // Getters (para acessar os dados)
    public String getId() { return id; }
    public LocalDate getData() { return data; }
    public String getDescricao() { return descricao; }
    public double getValor() { return valor; }
    public Categoria getCategoria() { return categoria; }
    public TipoTransacao getTipo() { return tipo; }

    // Sobrescrita do toString para facilitar a impressão no terminal depois
    @Override
    public String toString() {
        return String.format("[%s] %s | %s | R$ %.2f | %s", 
                data, tipo, descricao, valor, categoria);
    }
}
