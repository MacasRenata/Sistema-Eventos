
package modelo;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "areaC")
public class AreaConhecimento implements Serializable {
    @Id
    @GeneratedValue
    private int id;
    private String nome;
    
    @ManyToMany(mappedBy="areasC")
    private Set<Evento> areasDoEvento;

    
    public int getId(){
        return id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public String getNome(){
        return nome;
    }
    
    public void setNome(String nome){
        this.nome = nome;
    }

    public Set<Evento> getAreasDoEvento() {
        return areasDoEvento;
    }

    public void setAreasDoEvento(Set<Evento> areasDoEvento) {
        this.areasDoEvento = areasDoEvento;
    }
   
}
