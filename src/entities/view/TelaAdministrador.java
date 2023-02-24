package entities.view;

import entities.service.ClienteService;
import entities.service.ContaService;
import entities.service.TransferenciaService;

public class TelaAdministrador extends Tela {
    public static void exibirTelaAdministrador(){
        System.out.println("Você está na Tela de Administrador");
        TelaAdministrador.tratarInput();
    }

    public static void tratarInput() {
        int input = pedirInput("[1] -> Cadastrar um novo CLIENTE com CONTA\n[2] -> Deletar CLIENTE\n[3] -> Deletar CONTA\n[4] -> Alterar Cliente\n[5] -> Exibir todas as transferências\n[6] -> Voltar para a Tela Principal");
        ClienteService clienteService = new ClienteService();
        ContaService contaService = new ContaService();
        TransferenciaService transferenciaService = new TransferenciaService();
        switch(input){
            case 1 ->{
                if(Tela.loginAdm()){
                    clienteService.exibirCliente(clienteService.adicionarCliente().getIdCliente());
                }else{
                    System.err.println("Senha administrativa inválida!\n");
                }
                exibirTelaAdministrador();
            }
            case 2 -> {
                if(Tela.loginAdm()){
                    clienteService.deletarCliente();
                }else{
                    System.err.println("Senha administrativa inválida!");
                }
                exibirTelaAdministrador();
            }
            case 3 -> {
                if(Tela.loginAdm()){
                    contaService.deletarConta();
                }else{
                    System.err.println("Senha administrativa inválida!");
                }
                exibirTelaAdministrador();
            }
            case 4 -> {
                if(Tela.loginAdm()){
                    clienteService.alterarCliente();
                }else{
                    System.err.println("Senha administrativa inválida!");
                }
                exibirTelaAdministrador();
            }
            case 5 -> {
                if(Tela.loginAdm()){
                    transferenciaService.listarTransferencias();
                }else{
                    System.err.println("Senha administrativa inválida!");
                }
                exibirTelaAdministrador();
            }
            case 6 -> Tela.redirecionarParaTela(1);
            default -> {
                System.out.println("Opção inválida!");
                exibirTelaAdministrador();
            }
        }
    }
}
