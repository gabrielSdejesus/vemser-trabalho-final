package br.com.dbc.vemser.financeiro.view;


import br.com.dbc.vemser.financeiro.model.Conta;
import br.com.dbc.vemser.financeiro.model.TipoCartao;
import br.com.dbc.vemser.financeiro.service.*;

public class TelaAdministrador extends Tela {
    public static void exibirTelaAdministrador(){
        Servico.tempoParaExibir(500);
        System.out.println("Você está na Tela de Administrador");
        TelaAdministrador.tratarInput();
    }

    public static void tratarInput() {
        int input = pedirInput("""
                [1] -> Cadastrar um novo cliente e conta
                [2] -> Deletar cliente e conta
                [3] -> Alterar cliente
                [4] -> Exibir todas as transferências
                [5] -> Exibir todos os clientes
                [6] -> Exibir todas as contas
                [7] -> Reativar cliente e conta
                [0] -> Voltar para a Tela Principal""");
        ClienteService clienteService = new ClienteService();
        ContaService contaService = new ContaService();
        TransferenciaService transferenciaService = new TransferenciaService();
        ContatoService contatoService = new ContatoService();
        EnderecoService enderecoService = new EnderecoService();
        CartaoService cartaoService = new CartaoService();
        switch(input){
            case 1 ->{
                if(Tela.loginAdm()){
                    Conta conta = contaService.adicionar();
                    contatoService.adicionarContato(conta);
                    enderecoService.adicionarEndereco(conta);
                    cartaoService.cadastrarCartao(conta, TipoCartao.DEBITO);
                }else{
                    System.err.println("\nSenha administrativa inválida!");
                }
                exibirTelaAdministrador();
            }
            case 2 -> {
                if(Tela.loginAdm()){
                    contaService.removerConta();
                }else{
                    System.err.println("\nSenha administrativa inválida!");
                }
                exibirTelaAdministrador();
            }
            case 3 -> {
                if(Tela.loginAdm()){
                    clienteService.alterarCliente();
                }else{
                    System.err.println("\nSenha administrativa inválida!");
                }
                exibirTelaAdministrador();
            }
            case 4 -> {
                if(Tela.loginAdm()){
                    transferenciaService.listarTransferencias();
                }else{
                    System.err.println("\nSenha administrativa inválida!");
                }
                exibirTelaAdministrador();
            }
            case 5 -> {
                if(Tela.loginAdm()){
                    clienteService.listarClientes();
                }else{
                    System.err.println("\nSenha administrativa inválida!");
                }
                exibirTelaAdministrador();
            }
            case 6 -> {
                if(Tela.loginAdm()){
                    contaService.listar();
                }else{
                    System.err.println("\nSenha administrativa inválida!");
                }
                exibirTelaAdministrador();
            }
            case 7 -> {
                if(Tela.loginAdm()){
                    contaService.reativarConta();
                }else{
                    System.err.println("\nSenha administrativa inválida!");
                }
                exibirTelaAdministrador();
            }
            case 0 -> Tela.redirecionarParaTela(1);
            default -> {
                System.err.println("\nOpção inválida!");
                exibirTelaAdministrador();
            }
        }
    }
}
