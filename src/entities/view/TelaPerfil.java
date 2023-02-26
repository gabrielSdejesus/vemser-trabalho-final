package entities.view;

import entities.model.Conta;
import entities.service.*;

public class TelaPerfil extends Tela {
    public static void exibirTelaPerfil(){
        Service.tempoParaExibir(500);
        System.out.println("Você está na Tela de Perfil");
        TelaPerfil.tratarInput();
    }

    public static void tratarInput() {
        int input = pedirInput("[1] -> Exibir dados pessoais\n" +
                "[2] -> Exibir dados da conta\n" +
                "[3] -> Alterar contato\n" +
                "[4] -> Alterar endereço\n" +
                "[5] -> Deletar contato\n" +
                "[6] -> Deletar endereço\n" +
                "[7] -> Cadastrar novo contato\n" +
                "[8] -> Cadastrar novo endereço\n" +
                "[9] -> Alterar minha senha\n" +
                "[0] -> Voltar para a Tela Principal");
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
                    System.err.println("Login mal-sucedido");
                }
                exibirTelaPerfil();
            }
            case 4 -> {
                login = Tela.login();
                if(login != null){
                    enderecoService.alterarEndereco(login);
                }else{
                    System.err.println("Login mal-sucedido");
                }
                exibirTelaPerfil();
            }
            case 5 -> {
                login = Tela.login();
                if(login != null) {
                    contatoService.deletarContato(login);
                }else{
                    System.err.println("Login mal-sucedido");
                }
                exibirTelaPerfil();
            }
            case 6 -> {
                login = Tela.login();
                if(login != null) {
                    enderecoService.deletarEndereco(login);
                }else{
                    System.err.println("Login mal-sucedido");
                }
                exibirTelaPerfil();
            }
            case 7 -> {
                login = Tela.login();
                if(login != null) {
                    contatoService.adicionarContato(login);
                }else{
                    System.err.println("Login mal-sucedido");
                }
                exibirTelaPerfil();
            }
            case 8 -> {
                login = Tela.login();
                if(login != null) {
                    enderecoService.adicionarEndereco(login);
                }else{
                    System.err.println("Login mal-sucedido");
                }
                exibirTelaPerfil();
            }
            case 9 ->{
                login = Tela.login();
                if(login != null) {
                    contaService.alterarSenha(login);
                }else{
                    System.err.println("Login mal-sucedido");
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
