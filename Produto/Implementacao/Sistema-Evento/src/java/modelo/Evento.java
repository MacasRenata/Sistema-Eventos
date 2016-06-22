
package modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


@Entity
@Table(name="eventos")  //alteração para ficar igual a tabela sql
@SQLDelete(sql = "update eventos set ativo = 0 where id_evento = ?")
@Where(clause = "ativo = 1")
public class Evento implements Serializable {
    @Id
    @GeneratedValue
    private int id_evento;
    private String titulo;
    
    @ManyToOne
    @JoinColumn(name="id_categoria")
    private Categoria categoria;
    
    @OneToMany(mappedBy="evento")
    private List<Inscricao> inscricoesEvt;
            
    private String local_evento;
    @Temporal(TemporalType.DATE)
    private Date data_inicial;
    @Temporal(TemporalType.DATE)
    private Date data_final;
    @Temporal(TemporalType.TIME)
    private Date horario_inicio; 
    @Temporal(TemporalType.TIME)
    private Date horario_fim;
    private String categoria_evento;
    private String area_evento; 
    private boolean inscricoes;
    private String descricao_evento;
    @Temporal(TemporalType.DATE)
    private Date data_inicial_inscricao;
    @Temporal(TemporalType.DATE)
    private Date data_final_inscricao;
    private boolean limite_inscricoes;//limitar o num de participantes no evento - Alterado para Booleano
    private boolean submissao;//para permitir o upload de arquivo na inscrição do usuário no evento
    private String nome_organizador;
    private String email_organizador;
    private String senha_organizador;
    private int quantidade_inscritos;
    private String imagem;
    private Boolean ativo = true;

       public int getId_evento() {
        return id_evento;
    }

  
    public void setId_evento(int id_evento) {
        this.id_evento = id_evento;
    }


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
    
    public Date getHorario_inicio() {
        return horario_inicio;
    }

    public void setHorario_inicio(Date horario_inicio) {
        this.horario_inicio = horario_inicio;
    }
     public Date getHorario_fim() {
        return horario_fim;
    }

    public void setHorario_fim(Date horario_fim) {
        this.horario_fim = horario_fim;
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

    public boolean isInscricoes() {
        return inscricoes;
    }

    public void setInscricoes(boolean inscricoes) {
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


    public boolean isSubmissao() {
        return submissao;
    }

    public void setSubmissao(boolean submissao) {
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

  
    public String getSenha_organizador() {
        return senha_organizador;
    }

 
    public void setSenha_organizador(String senha_organizador) {
        this.senha_organizador = senha_organizador;
    }

   
    public boolean isLimite_inscricoes() {
        return limite_inscricoes;
    }

    public void setLimite_inscricoes(boolean limite_inscricoes) {
        this.limite_inscricoes = limite_inscricoes;
    }

  
    public int getQuantidade_inscritos() {
        return quantidade_inscritos;
    }

  
    public void setQuantidade_inscritos(int quantidade_inscritos) {
        this.quantidade_inscritos = quantidade_inscritos;
    }
    
      public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
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

    public List<Inscricao> getInscricoesEvt() {
        return inscricoesEvt;
    }

    public void setInscricoesEvt(List<Inscricao> inscricoesEvt) {
        this.inscricoesEvt = inscricoesEvt;
    }
    
    public String getNomeCategoria() {
        return categoria.getId() + " - " + categoria.getNome();
    }

    /**
     * @return the imagem
     */
    public String getImagem() {
        return imagem;
    }

    /**
     * @param imagem the imagem to set
     */
    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
                
    
}
