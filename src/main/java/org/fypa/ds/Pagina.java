package org.fypa.ds;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Pagina<ID, T> {

    private ArrayList<Tupla<ID, T>> dadosPagina;

    private Integer tamanhoPagina;

    public Pagina(Integer tamanhoPagina) {
        this.tamanhoPagina = tamanhoPagina;
    }

    public void adicionarRegistro(ID id, T dados) throws Exception {
        this.adicionarRegistro(new Tupla<>(id, dados));
    }

    public void adicionarRegistro(Tupla<ID, T> registro) throws Exception {
        if (!this.isPaginaCheia()) {
            this.dadosPagina.add(registro);
        } else {
            throw new Exception("Overflow! Caso ocorrido ao tentar inserir o dado: " + registro.getDados() + " de hash: " + registro.getId() + ".");
        }
    }

    public boolean isPaginaCheia() {
        return this.dadosPagina.size() >= tamanhoPagina;
    }

}
