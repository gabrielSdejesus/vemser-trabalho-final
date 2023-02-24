package entities.view;

import entities.model.Conta;
import entities.service.ClienteService;
import entities.service.ContaService;
import entities.service.ContatoService;
import entities.service.EnderecoService;

public class TelaPerfil extends Tela {
    public static void exibirTelaPerfil(){
        System.out.println("Você está na Tela de Perfil");
        TelaPerfil.tratarInput();
    }

    public static void tratarInput() {
        int input = pedirInput("[1] -> Insira seus dados de LOGIN para exibir seus dados de CLIENTE\n[2] -> Insira seus dados de LOGIN para exibir os dados da sua CONTA\n[3] -> Alterar CONTATO do CLIENTE\n[4] -> Alterar ENDEREÇO do CLIENTE\n[5] -> Deletar CONTATO do CLIENTE\n[6] -> Deletar ENDEREÇO do CLIENTE\n[7] -> Cadastrar novo CONTATO em CLIENTE\n[8] -> Cadastrar novo ENDEREÇO em CLIENTE\n[9] -> Alterar SENHA\n[10] -> Voltar para a Tela Principal");
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
                    System.err.println("Login mal-sucedido");
                }
                exibirTelaPerfil();
            }
            case 2 ->{
                login = Tela.login();
                if(login != null){
                    contaService.exibirConta(login);
                }else{
                    System.err.println("Login mal-sucedido");
                }
                exibirTelaPerfil();
            }
            case 3 -> {
                login = Tela.login();
                if(login != null){
                    contatoService.alterarContato(login);
                }else{
                    System.err.println("Login mal-sucedido\n");
                }
                exibirTelaPerfil();
            }
            case 4 -> {
                login = Tela.login();
                if(login != null){
                    enderecoService.alterarEndereco(login);
                }else{
                    System.out.println("Login mal-sucedido\n");
                }
                exibirTelaPerfil();
            }
            case 5 -> {
                login = Tela.login();
                if(login != null) {
                    contatoService.deletarContato(login);
                }else{
                    System.out.println("Login mal-sucedido\n");
                }
                exibirTelaPerfil();
            }
            case 6 -> {
                login = Tela.login();
                if(login != null) {
                    enderecoService.deletarEndereco(login);
                }else{
                    System.err.println("Login mal-sucedido\n");
                }
                exibirTelaPerfil();
            }
            case 7 -> {
                login = Tela.login();
                if(login != null) {
                    contatoService.adicionarContato(login);
                }else{
                    System.err.println("Login mal-sucedido\n");
                }
                exibirTelaPerfil();
            }
            case 8 -> {
                login = Tela.login();
                if(login != null) {
                    enderecoService.adicionarEndereco(login);
                }else{
                    System.err.println("Login mal-sucedido\n");
                }
                exibirTelaPerfil();
            }
            case 9 ->{
                contaService.alterarSenha();
                exibirTelaPerfil();
            }
            case 10 -> Tela.redirecionarParaTela(1);
            default -> {
                System.out.println("Opção inválida!");
                exibirTelaPerfil();
            }
        }
    }
}
