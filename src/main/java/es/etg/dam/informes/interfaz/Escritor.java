package es.etg.dam.informes.interfaz;

import java.util.List;

import es.etg.dam.Bote;

public interface Escritor {
    void escribir(String ruta, List<Bote> listBotes);
}
