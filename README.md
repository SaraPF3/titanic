# Titanic

Hecho por Sara Pérez

> Link del repositorio: <https://github.com/SaraPF3/titanic>

Haz clic aquí para ir a la documentación del proyecto: [Documentación](doc/Documentacion.md)

## Indice

- [Titanic](#titanic)
  - [Indice](#indice)
  - [Clases](#clases)
    - [ServicioEmergencias](#servicioemergencias)
    - [Bote](#bote)
    - [ExtensionValida](#extensionvalida)
    - [InformeMarkdown](#informemarkdown)
    - [InformeHtml](#informehtml)
    - [Escritor](#escritor)
  - [Test](#test)
    - [TestBote](#testbote)
    - [TestServicioEmergencias](#testservicioemergencias)
    - [TestInformeMarkdown](#testinformemarkdown)

## Clases

### ServicioEmergencias

En **ServicioEmergencias** se obtiene la información que proporciona **Bote**, que es utilizada para generar el informe con la extensión que se le haya proporcionado:

```java
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
```

### Bote

En **Bote** se encuentran los métodos para crear los botes con el número de personas que hay en cada uno (**generar()** y **personas()**) y posteriormente el método **ejecutar()** que genera los botes con la información que va a ser enviada a **ServicioEmergencias**:

```java
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
```

### ExtensionValida

En **ExtensiónVálida** se encuentran los métodos que comprueban que la extensión deseada sea soportada por el programa y, si es el caso, devuelve la clase utilizada para generar el tipo de infrome deseado:

```java
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
                return new InformeHTML();
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
```

### InformeMarkdown

La clase **InformeMarkdown** se utiliza para generar un informe con extensión md a través del método **escribir()**. En caso de querer generar informes con otras extensiones se debería crear una clase nueva con los elementos adecuados para el informe y posteriormente introducirla en el case del método **comprobar()** encontrado en **ExtensiónVálida**:

```java
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
```

### InformeHtml

La clase **InformeHtml** no se ha implementado, por lo que en vez de devolver un informe devuelve un mensaje informando al usuario de este hecho:

```java
public class InformeHtml implements Escritor {
    
    public static final String EXT_NO_IMPL = "Esta extensión todavía no se ha implementado: ";

    @Override
    public void escribir(String ruta, List<Bote> listBotes){
        throw new UnsupportedOperationException(EXT_NO_IMPL + ruta);
    }
}
```

### Escritor

Para llamar a las clases de los distintos tipos de informe y a **ExtensionValida** se utiliza la interfaz **Escritor**:

```java
public interface Escritor {
    void escribir(String ruta, List<Bote> listBotes);
}
```

## Test

### TestBote

- **TestBote** tiene dos test, **testCrear()** que comprueba que el método **crear()** funcione correctamente y **testEjecutar** que comprueba que al ejecutar el método se crean la cantidad de botes deseada:

```java
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
```

### TestServicioEmergencias

- **TestServicioEmergencias** tiene un test que comprueba que el informe se genera correctamente:
  
```java
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
```

### TestInformeMarkdown

- **TestInformeMarkdown** tiene un test que comprueba que el método **escribir()** funciona correctamente en la clase **InformeMarkdown**:
  
```java
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
```
