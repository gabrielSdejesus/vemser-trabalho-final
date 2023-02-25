package entities.view;

import entities.model.Conta;
import entities.service.*;

public class TelaAdministrador extends Tela {
    public static void exibirTelaAdministrador(){
        System.out.println("Você está na Tela de Administrador");
        TelaAdministrador.tratarInput();
    }

    public static void tratarInput() {
        int input = pedirInput("[1] -> Cadastrar um novo CLIENTE com CONTA\n[2] -> Deletar CONTA e CLIENTE\n[3] -> Alterar Cliente\n" +
                "[4] -> Exibir todas as transferências\n[5] -> Exibir todos os clientes\n[6] -> Exibir todas as contas\n[0] -> Voltar para a Tela Principal");
        ClienteService clienteService = new ClienteService();
        ContaService contaService = new ContaService();
        TransferenciaService transferenciaService = new TransferenciaService();
        ContatoService contatoService = new ContatoService();
        EnderecoService enderecoService = new EnderecoService();
        switch(input){
            case 1 ->{
                if(Tela.loginAdm()){
                    Conta conta = contaService.adicionar();
                    contatoService.adicionarContato(conta);
                    enderecoService.adicionarEndereco(conta);
                }else{
                    System.err.println("Senha administrativa inválida!\n");
                }
                exibirTelaAdministrador();
            }
            case 2 -> {
                if(Tela.loginAdm()){
                    contaService.removerConta();
                }else{
                    System.err.println("Senha administrativa inválida!");
                }
                exibirTelaAdministrador();
            }
            case 3 -> {
                if(Tela.loginAdm()){
                    clienteService.alterarCliente();
                }else{
                    System.err.println("Senha administrativa inválida!");
                }
                exibirTelaAdministrador();
            }
            case 4 -> {
                if(Tela.loginAdm()){
                    transferenciaService.listarTransferencias();
                }else{
                    System.err.println("Senha administrativa inválida!");
                }
                exibirTelaAdministrador();
            }
            case 5 -> {
                if(Tela.loginAdm()){
                    clienteService.listarClientes();
                }else{
                    System.err.println("Senha administrativa inválida!");
                }
                exibirTelaAdministrador();
            }
            case 6 -> {
                if(Tela.loginAdm()){
                    contaService.listar();
                }else{
                    System.err.println("Senha administrativa inválida!");
                }
                exibirTelaAdministrador();
            }
            case 0 -> Tela.redirecionarParaTela(1);
            default -> {
                System.out.println("Opção inválida!");
                exibirTelaAdministrador();
            }
        }
    }
}
