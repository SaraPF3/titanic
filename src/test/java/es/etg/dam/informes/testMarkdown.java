package es.etg.dam.informes;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import es.etg.dam.Bote;

public class testMarkdown {

    @Test
    void testEscribir() throws Exception {

        List<Bote> listaBotes = List.of(
                new Bote("B00", 10, 3, 4, 3),
                new Bote("B01", 5, 2, 2, 1)
        );

        File fichTemp = File.createTempFile("InformeTest", ".md");
        fichTemp.deleteOnExit();

        InformeMarkdown md = new InformeMarkdown();
        md.escribir(fichTemp.getAbsolutePath(), listaBotes);

        assertTrue(fichTemp.exists());

        String contenido = Files.readString(fichTemp.toPath());

        assertTrue(contenido.contains("Bote B00"));
        assertTrue(contenido.contains("Bote B01"));

        assertTrue(contenido.contains("Total Salvados 10"));
        assertTrue(contenido.contains("Total Salvados 5"));

        int total = 10 + 5;
        assertTrue(contenido.contains("Total Salvados " + total));
    }
}
