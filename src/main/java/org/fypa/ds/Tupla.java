package org.fypa.ds;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tupla<ID, T> {

    private ID id;

    private T dados;

    public Tupla(ID id, T dados) {
        this.id = id;
        this.dados = dados;
    }

}
