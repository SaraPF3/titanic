package es.etg.dam;

import java.util.List;

import es.etg.dam.informes.ExtensionValida;
import es.etg.dam.informes.interfaz.Escritor;

public class ServicioEmergencias {

    public static final String RUTA = "doc/Informe.md";
    public static final String LIST_VAC = "La lista de los botes está vacía";
    public static final String BOTES_OBT = "Se ha obtenido la información de los botes";
    public static final String MSG_INF_CREAD = "Se ha creado el informe correctamente en la ruta: " + RUTA;

    public static void main(String[] args) {

        List<Bote> listaBotes = Bote.ejecutar();

        if (listaBotes.isEmpty()) {
            System.out.println(LIST_VAC);
            return;
        }
        
        System.out.println(BOTES_OBT);

        Escritor inf = ExtensionValida.comprobar(RUTA);

        inf.escribir(RUTA, listaBotes);

        System.out.println(MSG_INF_CREAD);

    }
}
