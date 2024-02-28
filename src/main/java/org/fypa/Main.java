package org.fypa;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fypa.ds.Bucket;
import org.fypa.ds.Pagina;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Integer tamanhoPagina = 20;
            File arquivoParaLer = getFileFromRsc("words.txt");
            Integer qtdLinhas = lerQtdLinhas(arquivoParaLer);
            ArrayList<Pagina> paginas = Pagina.carregarPaginas(arquivoParaLer, tamanhoPagina);
            ArrayList<Bucket> buckets = Bucket.carregarBucket(paginas, qtdLinhas);
            String bucketJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(buckets);
            Files.writeString(Path.of("src/main/resources/out.json"), bucketJson);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static File getFileFromRsc(String file) {
        try {

            ClassLoader classLoader = Main.class.getClassLoader();
            URL resource = classLoader.getResource(file);

            if (resource == null) {
                throw new IllegalArgumentException("Arquivo '" + file + "'não encontrado!" + file);
            }

            return new File(resource.toURI());

        } catch (URISyntaxException ignored) {
            throw new RuntimeException("URL do arquivo " + file + " mal formatada!");
        }
    }

    public static void writeStrTofile(File arquivoPraEscrever, String str) {
        try (PrintWriter out = new PrintWriter(arquivoPraEscrever)) {
            if (!arquivoPraEscrever.exists()) {
                boolean newFile = arquivoPraEscrever.createNewFile();
                if (!newFile) {
                    throw new RuntimeException("Erro ao criar o arquivo no arquivo!");
                }
            }
            out.println(str);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao escrever no arquivo!");
        }
    }

    private static Integer lerQtdLinhas(File arquivoParaLer) throws FileNotFoundException {
        int linhas = 0;
        try (Scanner sc = new Scanner(arquivoParaLer)) {
            while (sc.hasNextLine()) {
                sc.nextLine();
                linhas++;
            }
        }
        return linhas;
    }

}