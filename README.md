## 1. Equipe

Nome da equipe: Java Tipagem Dinâmica
- Nícolas Canova Berton de Almeida
- Gabriel Santos de Jesus
- João Victor Lúcio do Santos

## 2. Metodologia
Para a realização do projeto, inicialmente, utilizamos o Lucidchart para a elaboração do diagrama de classes, onde todos participaram das tomadas de decisões, como definição de métodos, atributos, depedências etc. Posteriormente, começamos a criação do código, geralmente em conjunto -"call"- no Discord, pois dessa maneira foi possível realizar alinhamentos, sanar dúvidas, dar sugestões uns aos outros de uma forma mais dinâmica e realizar um Code Review mais assertivo com o compartilhamento de tela do Discord.

## 3. Sobre o Projeto
O Banco Commit Dinâmico é uma Fintech que traz soluções tecnológicas para a área financeira. Em nosso sistema não há diferenciação de tipo de conta, pois esta é considerada como uma junção de todos os tipos de contas bancárias e a relação do cliente com a conta é de 1 para 1, onde não é possível que um cliente (cpf) possua mais de uma conta. Possuímos um robusto sistema de telas em CLI (Inteface de Linha de Comando) onde o usuário pode navegar livremente pelas diversas funcionalidades do sistema. Sobre as principais funcionalidades do sistema:
* Cliente:
	* Cadastrar, excluir, visualizar dados do cliente, adicionar contato, adicionar endereço, alterar contato, alterar endereço, excluir contato, excluir endereço e alterar senha.
* Conta:
	* Cadastrar, excluir, visualizar dados da conta, sacar, depositar, transferir para outra conta dentro do sistema, adicionar cartão, excluir cartão, alterar senha, exibir transferências da conta e exibir todas as compras da conta.
* Cartão:
	* Cadastrar, excluir, visualizar dados do cartão, adicionar compra, exibir compras feitas no cartão, pagar uma conta externa ao banco e visualizar estrato do cartão de débito e/ou crédito.

O sistema exige que apenas um administrador cadastre ou exclua uma conta, após, o usuário estará livre para realizar as funcionalidades do sistema com seu login (número da conta) e sua senha escolhida durante o cadastro do cliente. Assim que uma conta é criada um cartão de débito é automaticamente gerado, sendo o cartão de crédito opcional e gerado pelo cliente. Também como regra de negócio da Fintech, cada conta pode ter no máximo 2 cartões, sejam os dois de crédito, débito ou 1 de cada tipo, mas é necessário que o cliente tenha no mínimo 1 cartão em sua conta para realizar suas transações.

## 4. Diagramas

1. Diagrama de classes: [Diagrama de Classes do projeto](https://lucid.app/lucidchart/17d33946-0d38-4544-8578-e02af91a9943/edit?invitationId=inv_0c6d8cd4-f4ac-44ed-8890-a31e96c56b54)
2. Diagrama de telas: [Diagrama de telas](https://lucid.app/lucidchart/d76289db-ddc4-47f1-9db6-11c56f979e26/edit?viewport_loc=-2332%2C-1759%2C3284%2C1998%2C0_0&invitationId=inv_a48637ca-1036-40cd-a5a3-01af67cee05a)
