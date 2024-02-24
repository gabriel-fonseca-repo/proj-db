package org.fypa.ds;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

@Getter
@Setter
public class Bucket<ID, T> {

    private ArrayList<Pagina<ID, T>> paginas;

    private Integer tamanhoPagina;

    private Integer qtdPaginas;

    public Bucket(File arquivoParaLer, Integer tamanhoPagina, Integer qtdPaginas) throws FileNotFoundException {
        this.tamanhoPagina = tamanhoPagina;
        this.qtdPaginas = qtdPaginas;
        this.carregarPaginas(arquivoParaLer);
    }

    private void carregarPaginas(File arquivoParaLer) throws FileNotFoundException {
        Scanner sc = new Scanner(arquivoParaLer);
        while (sc.hasNextLine()) {
            String prox = sc.nextLine();
            Integer hash = hash(prox);
            if (this.paginas.get(hash) == null) {
                // this.paginas.set(hash);
            }
        }
    }

    private Integer hash(String dado) {
        int soma = 0;
        for (int i = 0; i < dado.length(); i++) {
            soma += (int) dado.charAt(i);
        }
        return (soma * 92821) % this.qtdPaginas;
    }

}
