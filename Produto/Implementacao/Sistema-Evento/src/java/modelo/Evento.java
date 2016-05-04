
package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="eventos")  //alteração para ficar igual a tabela sql
public class Evento implements Serializable {
    @Id
    @GeneratedValue
    private int id_evento;//mudar para idEvento
    private String titulo;
    private String local_evento; //local palavra reservada do SQL - mudar nome do atributo
    @Temporal(TemporalType.DATE)
    private Date data_inicial;
    @Temporal(TemporalType.DATE)
    private Date data_final;
    private String categoria_evento;
    private String area_evento; 
    private Boolean inscricoes;
    private String descricao_evento;
    @Temporal(TemporalType.DATE)
    private Date data_inicial_inscricao;
    @Temporal(TemporalType.DATE)
    private Date data_final_inscricao;
    private Boolean limite_inscricoes;//limitar o num de participantes no evento - Alterado para Booleano
    private Boolean submissao;//para permitir o upload de arquivo na inscrição do usuário no evento
    private String nome_organizador;
    private String email_organizador;
    private String senha_organizador;
    private int quantidade_inscritos;

    

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLocal_evento() {
        return local_evento;
    }

    public void setLocal_evento(String local_evento) {
        this.local_evento = local_evento;
    }

    public Date getData_inicial() {
        return data_inicial;
    }

    public void setData_inicial(Date data_inicial) {
        this.data_inicial = data_inicial;
    }

    public Date getData_final() {
        return data_final;
    }

    public void setData_final(Date data_final) {
        this.data_final = data_final;
    }

    public String getCategoria_evento() {
        return categoria_evento;
    }

    public void setCategoria_evento(String categoria_evento) {
        this.categoria_evento = categoria_evento;
    }

    public String getArea_evento() {
        return area_evento;
    }

    public void setArea_evento(String area_evento) {
        this.area_evento = area_evento;
    }

    public Boolean getInscricoes() {
        return inscricoes;
    }

    public void setInscricoes(Boolean inscricoes) {
        this.inscricoes = inscricoes;
    }

    public Date getData_inicial_inscricao() {
        return data_inicial_inscricao;
    }

    public void setData_inicial_inscricao(Date data_inicial_inscricao) {
        this.data_inicial_inscricao = data_inicial_inscricao;
    }

    public Date getData_final_inscricao() {
        return data_final_inscricao;
    }

    public void setData_final_inscricao(Date data_final_inscricao) {
        this.data_final_inscricao = data_final_inscricao;
    }


    public Boolean getSubmissao() {
        return submissao;
    }

    public void setSubmissao(Boolean submissao) {
        this.submissao = submissao;
    }

    public String getNome_organizador() {
        return nome_organizador;
    }

    public void setNome_organizador(String nome_organizador) {
        this.nome_organizador = nome_organizador;
    }

    public String getEmail_organizador() {
        return email_organizador;
    }

    public void setEmail_organizador(String email_organizador) {
        this.email_organizador = email_organizador;
    }


    public String getDescricao_evento() {
        return descricao_evento;
    }

  
    public void setDescricao_evento(String descricao_evento) {
        this.descricao_evento = descricao_evento;
    }

    /**
     * @return the id_evento
     */
    public int getId_evento() {
        return id_evento;
    }

    /**
     * @param id_evento the id_evento to set
     */
    public void setId_evento(int id_evento) {
        this.id_evento = id_evento;
    }

    /**
     * @return the senha_organizador
     */
    public String getSenha_organizador() {
        return senha_organizador;
    }

    /**
     * @param senha_organizador the senha_organizador to set
     */
    public void setSenha_organizador(String senha_organizador) {
        this.senha_organizador = senha_organizador;
    }

    /**
     * @return the limite_inscricoes
     */
    public Boolean getLimite_inscricoes() {
        return limite_inscricoes;
    }

    /**
     * @param limite_inscricoes the limite_inscricoes to set
     */
    public void setLimite_inscricoes(Boolean limite_inscricoes) {
        this.limite_inscricoes = limite_inscricoes;
    }

    /**
     * @return the quantidade_inscritos
     */
    public int getQuantidade_inscritos() {
        return quantidade_inscritos;
    }

    /**
     * @param quantidade_inscritos the quantidade_inscritos to set
     */
    public void setQuantidade_inscritos(int quantidade_inscritos) {
        this.quantidade_inscritos = quantidade_inscritos;
    }
    
    
}
