package org.fypa.ds;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Hashtable;

@Getter
@Setter
public class Bucket {

    private ArrayList<Tupla> registros;

    public static final Integer MAX_TAMANHO_BUCKET = 10;

    public static Integer MAX_QTD_BUCKETS;

    public Bucket() {
        this.registros = new ArrayList<>();
    }

    public static ArrayList<Bucket> carregarBucket(ArrayList<Pagina> paginas, Integer qtdLinhas) throws Exception {
        ArrayList<Bucket> buckets = new ArrayList<>();

        MAX_QTD_BUCKETS = (int) (double) (qtdLinhas / MAX_TAMANHO_BUCKET);

        for (int i = 0; i < MAX_QTD_BUCKETS; i++)
            buckets.add(new Bucket());

        int colisoes = 0;

        for (Pagina pagina : paginas) {
            for (Tupla tupla : pagina.getDadosPagina()) {

                int hash = Bucket.hash(tupla.getDados());

                if (buckets.get(hash).isNotCheio()) {
                    buckets.get(hash).getRegistros().add(tupla);
                } else {
                    colisoes += 1;
                }

            }
        }

        System.out.println("Porcentagem de colisão no preenchimento do Bucket: " + calcularPorcentagem(colisoes, qtdLinhas));
        return buckets;
    }

    private static Integer hash(String dado) {
        int soma = 0;
        for (int i = 0; i < dado.length(); i++) {
            soma = (31 * soma + dado.charAt(i)) % MAX_QTD_BUCKETS;
        }
        return soma;
    }

    public static double calcularPorcentagem(int parte, int total) {
        if (total == 0) {
            throw new IllegalArgumentException("Total não pode ser zero!");
        }
        return ((double) parte / total) * 100;
    }

    public boolean isNotCheio() {
        return this.registros.size() < MAX_TAMANHO_BUCKET;
    }

}
