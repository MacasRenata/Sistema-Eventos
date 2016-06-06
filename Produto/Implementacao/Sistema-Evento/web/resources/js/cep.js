/*FUNÇÃO COMPLETA FORMULARIO SÓ COM CEP*/


$(document).ready(function () {

    //alert('oi');
    function limpa_formulário_cep() {
        // Limpa valores do formulário de cep.
        $("#txtLogradouro").val("");
        $("#txtBairro").val("");
        $("#txtCidade").val("");
        //$txtUF = $("select[name='skill_input'] option:selected").val();
        $("#txtUF").val("#txtUF");
        PF('selectWV').getSelectedValue();//gets the value
        PF('selectWV').getSelectedLabel();//gets the label
    }

    //Quando o campo cep perde o foco.
    $("#txtCEP").blur(function () {

        //Nova variável "cep" somente com dígitos.
        var cep = $(this).val().replace(/\D/g, '');

        //Verifica se campo cep possui valor informado.
        if (cep !== "") {

            //Expressão regular para validar o CEP.
            var validacep = /^[0-9]{8}$/;

            //Valida o formato do CEP.
            if (validacep.test(cep)) {

                //Preenche os campos com "..." enquanto consulta webservice.
                $("#txtLogradouro").val("Carregando...");
                $("#txtBairro").val("...");
                $("#txtCidade").val("...");
                $("#txtUF").val("...");
                //$("#ibge").val("...");

                //Consulta o webservice viacep.com.br/
                $.getJSON("//viacep.com.br/ws/" + cep + "/json/?callback=?", function (dados) {

                    if (!("erro" in dados)) {
                        //Atualiza os campos com os valores da consulta.
                        $("#txtLogradouro").val(dados.logradouro);
                        $("#txtBairro").val(dados.bairro);
                        $("#txtCidade").val(dados.localidade);
                        $("#txtUF").val(dados.uf);

                    } //end if.
                    else {
                        //CEP pesquisado não foi encontrado.
                        limpa_formulário_cep();
                        alert("CEP não encontrado.");
                    }
                });
            } //end if.
            else {
                //cep é inválido.
                limpa_formulário_cep();
                alert("Formato de CEP inválido.");
            }
        } //end if.
        else {
            //cep sem valor, limpa formulário.
            limpa_formulário_cep();
        }
    });
});







