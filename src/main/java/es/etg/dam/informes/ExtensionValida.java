package es.etg.dam.informes;

import es.etg.dam.informes.interfaz.Escritor;

public class ExtensionValida {

    public static final String MD = "md";
    public static final String HTML = "html";

    public static final String TXT_GEN = "Se va a generar el informe ";
    public static final String ERR_EXT = "La extensión indicada no es soportada por el programa: ";
    public static final String ERR_POS = "No se ha podido encontrar la extensión";
    public static final String PUNT = ".";

    public static final int NUM0 = 0;
    public static final int NUM1 = 1;

    public static Escritor comprobar(String ruta) {

        String extension = obtener(ruta);

        switch (extension) {
            case MD -> {
                return new InformeMarkdown();
            }
            case HTML -> {
                return new InformeHtml();
            }
            default ->
                throw new UnsupportedOperationException(ERR_EXT + extension);
        }
    }

    public static String obtener(String ruta) {
        int posi = ruta.lastIndexOf(PUNT);

        if (posi < NUM0) {
            return ERR_POS;
        }
        return ruta.substring(posi + NUM1);
    }
}
