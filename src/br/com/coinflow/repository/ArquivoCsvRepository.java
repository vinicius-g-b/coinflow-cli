package br.com.coinflow.repository;

import br.com.coinflow.model.Categoria;
import br.com.coinflow.model.TipoTransacao;
import br.com.coinflow.model.Transacao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ArquivoCsvRepository implements TransacaoRepository {
    
    // Nome do arquivo que será criado na raiz do seu projeto
    private static final String CAMINHO_ARQUIVO = "transacoes.csv";
    private static final String CABECALHO = "ID,DATA,DESCRICAO,VALOR,CATEGORIA,TIPO";

    public ArquivoCsvRepository() {
        // Toda vez que instanciarmos esse repositório, ele verifica se o arquivo existe.
        // Se não existir, ele cria o arquivo e já escreve a primeira linha (o cabeçalho).
        File arquivo = new File(CAMINHO_ARQUIVO);
        if (!arquivo.exists()) {
            try {
                arquivo.createNewFile();
                Files.writeString(Paths.get(CAMINHO_ARQUIVO), CABECALHO + System.lineSeparator());
            } catch (IOException e) {
                System.err.println("Erro ao criar o arquivo CSV: " + e.getMessage());
            }
        }
    }

    @Override
    public void salvar(Transacao transacao) {
        // Monta a linha separada por vírgulas, com uma quebra de linha (%n) no final
        String linha = String.format("%s,%s,%s,%s,%s,%s%n",
                transacao.getId(),
                transacao.getData(),
                transacao.getDescricao(),
                transacao.getValor(),
                transacao.getCategoria(),
                transacao.getTipo());

        try {
            // O StandardOpenOption.APPEND garante que vamos ADICIONAR ao final do arquivo, sem apagar o que já existe
            Files.writeString(Paths.get(CAMINHO_ARQUIVO), linha, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Erro ao salvar a transação: " + e.getMessage());
        }
    }

    @Override
    public List<Transacao> listarTodas() {
        List<Transacao> transacoes = new ArrayList<>();
        
        try {
            // Lê todas as linhas do arquivo de uma só vez
            List<String> linhas = Files.readAllLines(Paths.get(CAMINHO_ARQUIVO));
            
            // O loop começa no '1' de propósito para pular a linha 0 (que é o cabeçalho)
            for (int i = 1; i < linhas.size(); i++) {
                String[] colunas = linhas.get(i).split(",");
                
                // Converte as Strings de volta para os tipos corretos
                String id = colunas[0];
                LocalDate data = LocalDate.parse(colunas[1]); // Formato padrão: YYYY-MM-DD
                String descricao = colunas[2];
                double valor = Double.parseDouble(colunas[3]);
                Categoria categoria = Categoria.valueOf(colunas[4]);
                TipoTransacao tipo = TipoTransacao.valueOf(colunas[5]);

                // Usa o Construtor 2 (que aceita ID) para recriar o objeto na memória
                Transacao t = new Transacao(id, data, descricao, valor, categoria, tipo);
                transacoes.add(t);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo CSV: " + e.getMessage());
        }
        
        return transacoes;
    }
}