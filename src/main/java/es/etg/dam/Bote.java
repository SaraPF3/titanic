package es.etg.dam;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Bote {

    public static final String MSG_ERROR = "Se ha producido un error: ";
    public static final int NUM_MIN = 1;
    public static final int NUM_MAX = 100;
    public static final int TIEM_MIN = 2000;
    public static final int TIEM_MAX = 4001;

    public static final String FORMATO = "B%02d";
    public static final int TOT_BOT = 20;
    public static final int NUM0 = 0;

    public static List<Bote> ejecutar() {

        List<Bote> listBotes = new ArrayList<>();

        for (int i = NUM0; i < TOT_BOT; i++) {
            String id = String.format(FORMATO, i);
            Bote bote = Bote.crear(id);
            listBotes.add(bote);
        }
        return listBotes;
    }

    public String id;
    public int total;
    public int mujeres;
    public int varones;
    public int ninos;

    public Bote(String id) {
        this.id = id;
    }

    public static Bote crear(String id) {
        Bote bote = new Bote(id);
        bote.generarPersonas();
        return bote;
    }

    public void generarPersonas() {
        Random rand = new Random();
        total = rand.nextInt(NUM_MAX) + NUM_MIN;
        mujeres = rand.nextInt(total);
        varones = rand.nextInt(total - mujeres);
        ninos = total - mujeres - varones;

        int time = rand.nextInt(TIEM_MAX) + TIEM_MIN;
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            System.err.println(MSG_ERROR + e);
        }
    }
}
