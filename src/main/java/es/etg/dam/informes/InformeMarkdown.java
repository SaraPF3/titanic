package es.etg.dam.informes;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import es.etg.dam.Bote;
import es.etg.dam.informes.interfaz.Escritor;

public class InformeMarkdown implements Escritor {

    public static final String N = "\n";
    public static final String HASH = "# ";
    public static final String DOBL_HASH = "## ";
    public static final String LIST = "- ";
    public static final String SUB_LIST = "  - ";

    public static final String TIEM_EJEC = "Ejecución realizada el día ";
    public static final String FECH = "dd-MM-yyyy";
    public static final String HORA = "HH:mm:ss";
    public static final String TXT = " y a las ";

    public static final String MSG_ERROR = "Se ha producido un error al generar el informe: ";
    public static final String TIT_BOTE = DOBL_HASH + "Bote ";
    public static final String TIT_TOTAL = DOBL_HASH + "Total";
    public static final String TIT_SE = HASH + "SERVICIO DE EMERGENCIAS";
    public static final String TOTAL = LIST + "Total Salvados ";
    public static final String MUJERES = SUB_LIST +"Mujeres ";
    public static final String VARONES = SUB_LIST +"Varones ";
    public static final String NINOS = SUB_LIST + "Niños ";
    
    @Override
    public void escribir(String ruta, List<Bote> listBotes) {
        File fich = new File(ruta);

        try (FileWriter writer = new FileWriter(fich)) {

            int total = 0;
            int tMujeres = 0;
            int tVarones = 0;
            int tNinos = 0;

            LocalDateTime ejecucion = LocalDateTime.now();
            DateTimeFormatter ftD = DateTimeFormatter.ofPattern(FECH);
            DateTimeFormatter ftT = DateTimeFormatter.ofPattern(HORA);
            String fech = ejecucion.format(ftD);
            String hor = ejecucion.format(ftT);
            writer.write(TIT_SE + N + N);
            writer.write(TIEM_EJEC + fech + TXT + hor + N + N);

            for (Bote listBote : listBotes) {

                writer.write(TIT_BOTE + listBote.getId() + N + N);
                writer.write(TOTAL + listBote.getTotal() + N);
                writer.write(MUJERES + listBote.getMujeres() + N);
                writer.write(VARONES + listBote.getVarones() + N);
                writer.write(NINOS + listBote.getNinos() + N + N);
                
                total += listBote.getTotal();
                tMujeres += listBote.getMujeres();
                tVarones += listBote.getVarones();
                tNinos += listBote.getNinos();
            }

            writer.write(TIT_TOTAL + N + N);
            writer.write(TOTAL + total + N);
            writer.write(MUJERES + tMujeres + N);
            writer.write(VARONES + tVarones + N);
            writer.write(NINOS + tNinos + N);

        } catch (Exception e) {
            System.err.println(MSG_ERROR + e);
        }
    }
}
