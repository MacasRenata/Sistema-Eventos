/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;



@Entity
@Table(name="usuario")  //mudança para conectar com a tabela correta
@SQLDelete(sql = "update Usuario set ativo = 0 where id_user = ?")
@Where(clause = "ativo = 1")
public class Usuario implements Serializable{

  
    @Id
    @GeneratedValue
    private int id_user;
    private String nome;
    private String email;
    private String senha;
    private String senhaNova; 
    private String sexo;
    @Temporal(TemporalType.DATE)
    @Past
    private Date data_nascimento;
    @Length(min = 9, max = 9)
    private String cep;
    @Length(min = 6, max = 40)
    private String logradouro;
    @NotNull
    private int numero;
    private String complemento = "";
    @Length(min = 3, max = 40)
    private String bairro;
    @Length(min = 6, max = 40)
    private String cidade;
    private String estado;
    private String instituicao;
    private String telefone;
    private Boolean admin = false;
    private Boolean ativo = true;
    private Boolean trocasenha = false;

    @OneToMany(mappedBy="usuario")
    private List<Inscricao> inscricoesEvt;
    
    /**
     * @return the id_user
     */
    public int getId_user() {
        return id_user;
    }

    /**
     * @param id_user the id_user to set
     */
    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * @param senha the senha to set
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * @return the sexo
     */
    public String getSexo() {
        return sexo;
    }

    /**
     * @param sexo the sexo to set
     */
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    /**
     * @return the data_nascimento
     */
    public Date getData_nascimento() {
        return data_nascimento;
    }

    /**
     * @param data_nascimento the data_nascimento to set
     */
    public void setData_nascimento(Date data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    /**
     * @return the cep
     */
    public String getCep() {
        return cep;
    }

    /**
     * @param cep the cep to set
     */
    public void setCep(String cep) {
        this.cep = cep;
    }

    /**
     * @return the cidade
     */
    public String getCidade() {
        return cidade;
    }

    /**
     * @param cidade the cidade to set
     */
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the instituicao
     */
    public String getInstituicao() {
        return instituicao;
    }

    /**
     * @param instituicao the instituicao to set
     */
    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    /**
     * @return the telefone
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * @param telefone the telefone to set
     */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /**
     * @return the ativo
     */
    public Boolean getAtivo() {
        return ativo;
    }

    /**
     * @param ativo the ativo to set
     */
    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }


    /**
     * @return the senhaNova
     */
    public String getSenhaNova() {
        return senhaNova;
    }

    /**
     * @param senhaNova the senhaNova to set
     */
    public void setSenhaNova(String senhaNova) {
        this.senhaNova = senhaNova;
    }
   
    /**
     * @return the admin
     */
    public Boolean getAdmin() {
        return admin;
    }

    /**
     * @param admin the admin to set
     */
    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    /**
     * @return the trocasenha
     */
    public Boolean getTrocasenha() {
        return trocasenha;
    }

    /**
     * @param trocasenha the trocasenha to set
     */
    public void setTrocasenha(Boolean trocasenha) {
        this.trocasenha = trocasenha;
    }

    public List<Inscricao> getInscricoesEvt() {
        return inscricoesEvt;
    }

    public void setInscricoesEvt(List<Inscricao> inscricoesEvt) {
        this.inscricoesEvt = inscricoesEvt;
    }

    /**
     * @return the logradouro
     */
    public String getLogradouro() {
        return logradouro;
    }

    /**
     * @param logradouro the logradouro to set
     */
    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    /**
     * @return the numero
     */
    public int getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * @return the complemento
     */
    public String getComplemento() {
        return complemento;
    }

    /**
     * @param complemento the complemento to set
     */
    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    /**
     * @return the bairro
     */
    public String getBairro() {
        return bairro;
    }

    /**
     * @param bairro the bairro to set
     */
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }


    }
