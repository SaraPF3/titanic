package es.etg.dam.informes;

import java.util.List;

import es.etg.dam.Bote;
import es.etg.dam.informes.interfaz.Escritor;

public class InformeHtml implements Escritor {
    
    public static final String EXT_NO_IMPL = "Esta extensión todavía no se ha implementado: ";

    @Override
    public void escribir(String ruta, List<Bote> listBotes){
        throw new UnsupportedOperationException(EXT_NO_IMPL + ruta);
    }
}
