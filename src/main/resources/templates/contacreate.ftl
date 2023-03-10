<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Bem-vindo(a) ao nosso banco</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            margin: 0;
            padding: 0;
        }
        .container {
            width: 90%;
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }
        .logo {
            width: 150px;
            height: auto;
        }
        header {
            margin-bottom: 40px;
            text-align: center;
        }
        header h4 {
            font-size: 38px;
            font-weight: bold;
            margin-bottom: 20px;
            color: #1a5276;
            text-shadow: 2px 2px 0 rgba(255, 255, 255, 0.5);
            border-bottom: 2px solid #1a5276;
            padding-bottom: 10px;
        }
        header img {
            display: block;
            margin: 0 auto;
        }
        h3 {
            font-size: 20px;
            font-weight: bold;
            color: #1a5276;
            margin-bottom: 20px;
        }
        p, p span {
            font-size: 16px;
            line-height: 1.5;
            color: #6b6b6b;
        }

        p b, span b{
            color: #1a5276;
        }

        footer {
            text-align: center;
            margin-top: 40px;
        }
        footer p {
            font-size: 16px;
            color: #a4b0be;
            margin-bottom: 0;
        }

    </style>
</head>
<body>
<div class="container">
    <header>
        <h4>SEJA BEM-VINDO(A) AO NOSSO BANCO</h4>
        <img class="logo" src="http://atlas-content-cdn.pixelsquid.com/stock-images/gold-icon-bank-computer-o0KLdv7-600.jpg" alt="Logo do Banco" width="300">
    </header>
    <main>
        <h3>Olá <b>${nome}</b>,</h3>
        <p>Estamos felizes em ter você em nosso banco :)<br>
            Seu cadastro foi realizado com sucesso. <br><br>Seguem abaixo as informações de sua conta:<br>
            Número da conta: <b>${numero_conta}</b><br>
            Agência: <b>${agencia}</b><br><br>
            <span>As informações do seu novo cartão foram geradas com sucesso e agora estão disponíveis para você:</span><br>
            Numero do cartão: <b>${numero_cartao}</b><br>
            Data de expedição: <b>${data_expedicao}</b><br>
            Código de segurança: <b>${codigo_seguranca}</b><br>
            Tipo do cartão: <b>${tipo_cartao}</b><br>
            Data de vencimento: <b>${data_vencimento}</b><br>
        </p><br>
        <span>Qualquer dúvida é só contatar o suporte pelo e-mail <b>${email}</b>.</span>
    </main>
    <footer>
        <p>Att, Sis &copy; 2023</p>
    </footer>
</div>
</body>
</html>


