package org.fypa.ds;

import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

@Getter
@Setter
public class Pagina {

    private ArrayList<Tupla> dadosPagina;

    private Integer tamanhoPagina;

    public Pagina(Integer tamanhoPagina) {
        this.tamanhoPagina = tamanhoPagina;
        this.dadosPagina = new ArrayList<>();
    }

    public static ArrayList<Pagina> carregarPaginas(File arquivoParaLer, Integer tamanhoPagina) throws Exception {

        BufferedReader br = new BufferedReader(new FileReader(arquivoParaLer));
        String dados = "";

        Pagina pagina = new Pagina(tamanhoPagina);
        Integer paginaIdx = 0;
        ArrayList<Pagina> paginas = new ArrayList<>();

        while (dados != null) {
            while (pagina.isNotCheia()) {
                Tupla registro = new Tupla(paginaIdx, dados);
                pagina.adicionarRegistro(registro);
                dados = br.readLine();
                if (dados == null)
                    break;
            }
            paginas.add(pagina);
            pagina = new Pagina(tamanhoPagina);
            paginaIdx++;
        }
        br.close();
        return paginas;
    }

    public void adicionarRegistro(Tupla registro) throws Exception {
        if (this.isNotCheia()) {
            this.dadosPagina.add(registro);
        } else {
            throw new Exception("Overflow! Caso ocorrido ao tentar inserir o dado: " + registro.getDados() + ".");
        }
    }

    public boolean isNotCheia() {
        return this.dadosPagina.size() < tamanhoPagina;
    }

}
