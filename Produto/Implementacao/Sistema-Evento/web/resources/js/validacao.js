/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*

    if (txtTitulo.length < 2) {
      alert('Digite um Títulopara o evento');
      formDados.txtTitulo.focus();
     return false;
     }

    //o campo e-mail precisa de conter: "@", "." e não pode estar vazio
    if(frm.email_organizador.value.indexOf("@") == -1 ||
      frm.email_organizador.valueOf.indexOf(".") == -1 ||
      frm.email_organizador.value == "" ||
      frm.email_organizador.value == null) {
        alert("Por favor, indique um e-mail válido.");
        frm.email.focus();
        return false;
    }
    
    if (senha != rep_senha) {
      alert('Senhas diferentes');
      formDados.senha.focus();
     return false;
     }
    // O utilizador necessita de selecionar um dos dois
    //radio buttons: sim e não.
    inscricoesEvento = -1; //valor negativo default (padrão) que significa que nada foi escolhido ainda.
    //No bloco de código abaixo foi criado um ciclo entre
    //os radios button com o mesmo nome (inscricoes)
    for(x = frm.inscricoes.lenght -1; x > -1; x--) {
        /*
        x = frm.inscricoes.lenght -1 é a mesma coisa que: x = 2-
        1, que resulta em 1.
        x > -1 significa que o valor de x não pode ser igual a -1 e
        sim maior, porque -1 significa que nada foi escolhido.
        x-- significa que há um decremento no valor x, é algo como:
        x = 1, x= 0 e pára pois x não pode ser -1.
        */
       /*
        if(frm.inscricoes[x].checked) { //checked quer dizer selecionado,
           //então verifica se o primeiro (0) ou o
           //segundo (1) radio button foi selecionado (checked).
           inscricoesEvento = x; //atribui à variável inscricoesEvento o valor X.
        }
    }
    //se nenhuma das opções (sim ou não) forem
    //escolhidas, mostra um alerta e cancela o envio.
    if(inscricoesEvento == -1) {
        alert("será realizada inscrições?");
        frm.inscricoes[0].focus();
        return false;
    }
    /* valida a categoria:
    */
   
    

