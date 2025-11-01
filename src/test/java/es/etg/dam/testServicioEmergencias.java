package es.etg.dam;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import es.etg.dam.informes.ExtensionValida;
import es.etg.dam.informes.interfaz.Escritor;

public class testServicioEmergencias {
    
    @Test
    void testInformeGenerado() throws Exception {

        List<Bote> listaBotes = Bote.ejecutar();

        Escritor mockEscritor = mock(Escritor.class);

        try (var mockedStatic = Mockito.mockStatic(ExtensionValida.class)) {
            mockedStatic.when(() -> ExtensionValida.comprobar(ServicioEmergencias.RUTA)).thenReturn(mockEscritor);

            Escritor inf = ExtensionValida.comprobar(ServicioEmergencias.RUTA);
            inf.escribir(ServicioEmergencias.RUTA, listaBotes);

            verify(mockEscritor, times(1)).escribir(ServicioEmergencias.RUTA, listaBotes);
        }
    }
}
