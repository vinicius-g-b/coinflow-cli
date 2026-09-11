package br.com.coinflow;

import br.com.coinflow.cli.MenuConsole;
import br.com.coinflow.repository.ArquivoCsvRepository;
import br.com.coinflow.repository.TransacaoRepository;
import br.com.coinflow.service.GerenciadorFinanceiroService;

public class Main {
    public static void main(String[] args) {
        // 1. Instancia o repositório (quem acessa o CSV)
        TransacaoRepository repository = new ArquivoCsvRepository();
        
        // 2. Instancia o Service injetando o repositório
        GerenciadorFinanceiroService service = new GerenciadorFinanceiroService(repository);
        
        // 3. Instancia a interface do console injetando o Service
        MenuConsole menu = new MenuConsole(service);

        // 4. Inicia a aplicação
        System.out.println("Bem-vindo ao CoinFlow CLI, Vinicius!");
        menu.iniciar();
    }
}