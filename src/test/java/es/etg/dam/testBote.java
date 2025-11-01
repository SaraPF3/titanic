package es.etg.dam;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class testBote {
    
    @Test
    void testCrear() {
        Bote bote = Bote.crear("B01");

        assertEquals("B01", bote.getId());
        assertTrue(bote.getTotal() >= Bote.NUM_MIN && bote.getTotal() <= Bote.NUM_MAX);
        assertEquals(bote.getTotal(), bote.getMujeres() + bote.getVarones() + bote.getNinos());
    }

    @Test
    void testEjecutar() {
        List<Bote> lista = Bote.ejecutar();
        assertEquals(Bote.TOT_BOT, lista.size());

        for (int i = 0; i < lista.size(); i++) {
            String expectedId = String.format(Bote.FORMATO, i);
            assertEquals(expectedId, lista.get(i).getId());
        }
    }
}
