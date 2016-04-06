
package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author luis
 */
@Entity
@Table(name="Eventos")
public class Evento implements Serializable {
    @Id
    @GeneratedValue
    private int id;//mudar para idEvento
    private String titulo;
    private String local; //mudar nome do atributo
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
    private int limite_inscricoes;//limitar o num de participantes no evento
    private Boolean submissao;//para permitir o upload de arquivo na inscrição do usuário no evento
    private String nome_organizador;
    private String email_organizador;  

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
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

    public int getLimite_inscricoes() {
        return limite_inscricoes;
    }

    public void setLimite_inscricoes(int limite_inscricoes) {
        this.limite_inscricoes = limite_inscricoes;
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
    
}
