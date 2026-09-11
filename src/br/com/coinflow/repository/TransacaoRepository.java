package br.com.coinflow.repository;

import br.com.coinflow.model.Transacao;
import java.util.List;

public interface TransacaoRepository {
    void salvar(Transacao transacao);
    List<Transacao> listarTodas();
}