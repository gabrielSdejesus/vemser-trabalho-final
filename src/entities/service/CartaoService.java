package entities.service;

import entities.model.*;

import java.util.List;

public class CartaoService extends Service{
    public void exibirExtrato(Conta conta, TipoCartao tipo){
        List<Cartao> cartoes = this.returnCartoes(conta);
        int cartao = -1;

        for(int i=0;i<cartoes.size();i++){
            if(cartoes.get(i).getTipo() == tipo){
                cartao = i;
                System.out.println("\t\nExibindo dados do cartão ["+(i+1)+"]:");
                System.out.println(
                        "Tipo do cartão: "+((tipo == TipoCartao.DEBITO)? "DÉBITO":"CRÉDITO")+
                        "Número da conta do cartão: "+cartoes.get(i).getConta().getNumeroConta()+
                        "Número do cartão: "+cartoes.get(i).getNumeroCartao()+
                        "Vencimento do cartão: "+cartoes.get(i).getVencimento()+
                        "Código de segurança do cartão: "+cartoes.get(i).getCodigoSeguranca()+
                        "Data de expedição: "+cartoes.get(i).getDataExpedicao());
                CompraService compraService = new CompraService();
                compraService.exibirComprasCartao(cartoes.get(i));
                break;
            }
        }
        if (cartao == -1) {
            System.out.println("\tVocê não possui nenhum cartão de "+((tipo == TipoCartao.DEBITO)? "DÉBITO":"CRÉDITO")+"\n");
        }
    }

    public void cadastrarCartao(Conta conta){
        int contador = 0;
        CartaoService cartaoService = new CartaoService();
        for(Cartao cartao: cartaoService.returnCartoes(conta)){
            if(cartao != null){
                contador++;
            }
        }
        if(contador == 2){
            System.err.println("Você não pode ADICIONAR mais CARTÕES, só é possível ter no MÁXIMO 2 CARTÕES");
        }else{
            int tipoCartao;

            System.out.println("Insira o tipo do cartão:");
            System.out.println("[1] CRÉDITO");
            System.out.println("[2] DÉBITO");
            System.out.println("[3] CANCELAR");

            tipoCartao = Integer.parseInt(SCANNER.nextLine());

            String senha;

            if (tipoCartao < 1 || tipoCartao >= 3){
                System.out.println("Operação cancelada!");
            }else{
                System.out.println("Digite novamente sua senha:");
                senha = SCANNER.nextLine();
                switch(tipoCartao){
                    case 1 -> {
                        CartaoDeCredito cartaoDeCredito = new CartaoDeCredito(conta);
                        if(conta.adicionarCartao(cartaoDeCredito, senha)){
                            System.out.println("Novo CARTÃO de CRÉDITO adicionado com sucesso!");
                            System.out.println("\tDados do CARTÃO:");
                            cartaoDeCredito.exibirDadosCartao();
                        }else{
                            System.err.println("Senha incorreta!");
                        }
                    }
                    case 2 -> {
                        CartaoDeDebito cartaoDeDebito = new CartaoDeDebito(conta);
                        if(conta.adicionarCartao(cartaoDeDebito, senha)){
                            System.out.println("Novo CARTÃO de DÉBITO adicionado com sucesso!");
                            System.out.println("\tDados do CARTÃO:");
                            cartaoDeDebito.exibirDadosCartao();
                        }else{
                            System.err.println("Senha incorreta!");
                        }
                    }
                    default -> System.err.println("Erro bizarro!");
                }
            }
        }
    }
    public void deletarCartao(Conta conta){
        int contador = 0;
        for(Cartao cartao: conta.getCartoes()){
            if(cartao != null){
                contador++;
            }
        }
        if(contador == 1){
            System.err.println("Você não pode REMOVER mais CARTÕES, é NECESSÁRIO ter no MÍNIMO 1 CARTÃO");
        }else{
            Cartao[] cartoes = conta.getCartoes();
            int cartao;

            System.out.println("Selecione o CARTÃO para REMOVER:");
            for(int i=0;i<cartoes.length;i++){
                if(cartoes[i] != null){
                    System.out.printf("Cartão [%d] -> %s\n", (i+1), (cartoes[i].getTipo() == TipoCartao.DEBITO ? "Débito":"Crédito"));
                }
            }
            cartao = Integer.parseInt(SCANNER.nextLine())-1;

            String senha;
            System.out.println("Digite novamente sua senha:");
            senha = SCANNER.nextLine();

            if(conta.removerCartao(cartao, senha)){
                System.err.println("CARTÃO removido com sucesso!");
            }else{
                if(conta.verificarSenha(senha)){
                    System.err.println("CARTÃO de CRÉDITO usado, pague a fatura para poder excluí-lo!");
                }else{
                    System.err.println("Senha incorreta!");
                }
            }
        }
    }

    public List<Cartao> returnCartoes(Conta conta) {
        //traz os cartões que tem o numero_conta igual o da conta do parâmetro
        return null;
    }
}
