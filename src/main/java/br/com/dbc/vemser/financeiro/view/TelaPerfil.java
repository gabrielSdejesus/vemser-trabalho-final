package br.com.dbc.vemser.financeiro.view;


import br.com.dbc.vemser.financeiro.model.Conta;
import br.com.dbc.vemser.financeiro.service.*;

public class TelaPerfil extends Tela {
    public static void exibirTelaPerfil(){
        Servico.tempoParaExibir(500);
        System.out.println("Você está na Tela de Perfil");
        TelaPerfil.tratarInput();
    }

    public static void tratarInput() {
        int input = pedirInput("""
                [1] -> Exibir dados pessoais
                [2] -> Exibir dados da conta
                [3] -> Alterar contato
                [4] -> Alterar endereço
                [5] -> Deletar contato
                [6] -> Deletar endereço
                [7] -> Cadastrar novo contato
                [8] -> Cadastrar novo endereço
                [9] -> Alterar minha senha
                [0] -> Voltar para a Tela Principal""");
        ClienteService clienteService = new ClienteService();
        ContaService contaService = new ContaService();
        EnderecoService enderecoService = new EnderecoService();
        ContatoService contatoService = new ContatoService();
        Conta login;
        switch(input){
            case 1 ->{
                login = Tela.login();
                if(login != null){
                    clienteService.exibirCliente(login.getCliente().getIdCliente());
                }else{
                    System.err.println("\nLogin mal-sucedido");
                }
                exibirTelaPerfil();
            }
            case 2 ->{
                login = Tela.login();
                if(login != null){
                    contaService.exibirConta(login);
                }else{
                    System.err.println("\nLogin mal-sucedido");
                }
                exibirTelaPerfil();
            }
            case 3 -> {
                login = Tela.login();
                if(login != null){
                    contatoService.alterarContato(login);
                }else{
                    System.err.println("\nLogin mal-sucedido");
                }
                exibirTelaPerfil();
            }
            case 4 -> {
                login = Tela.login();
                if(login != null){
                    enderecoService.alterarEndereco(login);
                }else{
                    System.err.println("\nLogin mal-sucedido");
                }
                exibirTelaPerfil();
            }
            case 5 -> {
                login = Tela.login();
                if(login != null) {
                    contatoService.deletarContato(login);
                }else{
                    System.err.println("\nLogin mal-sucedido");
                }
                exibirTelaPerfil();
            }
            case 6 -> {
                login = Tela.login();
                if(login != null) {
                    enderecoService.deletarEndereco(login);
                }else{
                    System.err.println("\nLogin mal-sucedido");
                }
                exibirTelaPerfil();
            }
            case 7 -> {
                login = Tela.login();
                if(login != null) {
                    contatoService.adicionarContato(login);
                }else{
                    System.err.println("\nLogin mal-sucedido");
                }
                exibirTelaPerfil();
            }
            case 8 -> {
                login = Tela.login();
                if(login != null) {
                    enderecoService.adicionarEndereco(login);
                }else{
                    System.err.println("\nLogin mal-sucedido");
                }
                exibirTelaPerfil();
            }
            case 9 ->{
                login = Tela.login();
                if(login != null) {
                    contaService.alterarSenha(login);
                }else{
                    System.err.println("\nLogin mal-sucedido");
                }
                exibirTelaPerfil();
            }
            case 0 -> Tela.redirecionarParaTela(1);
            default -> {
                System.err.println("Opção inválida!\n");
                exibirTelaPerfil();
            }
        }
    }
}
