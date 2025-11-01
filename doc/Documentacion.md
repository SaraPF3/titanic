# Titanic

Hecho por Sara Pérez Fernández

## Indice

- [Titanic](#titanic)
  - [Indice](#indice)
  - [Análisis del problema](#análisis-del-problema)
  - [Diseño de la solución del problema](#diseño-de-la-solución-del-problema)
    - [Arquitectura](#arquitectura)
    - [Componentes](#componentes)
    - [Protocolo de comunicación](#protocolo-de-comunicación)
    - [Plan de pruebas](#plan-de-pruebas)
  - [Manual de usuario](#manual-de-usuario)
  - [Elementos destacables del desarrollo](#elementos-destacables-del-desarrollo)
  - [Problemas encontrados](#problemas-encontrados)

## Análisis del problema

Se necesita un programa que gestione los datos de los botes salvavidas del Titanic. Los datos incluirán: el identificador de cada bote, las mujeres, los varones, los niños, y el total de personas por bote. Cuando se tengan los datos de todos los botes se hará un recuento total de la cantidad de mujeres, varones y niños que había en todos los botes y posteriormente de todas las personas salvadas. Dichos datos serán enviados al servicio de emergencias, que creará un informe en base a la información proporcionada, incluyendo la fecha y hora de creación del informe.

## Diseño de la solución del problema

### Arquitectura

La arquitectura es la siguiente:

![Arquitectura](/src/main/resources/arquitectura.jpg)

### Componentes

Los componentes son los siguientes:

![Arquitectura](/src/main/resources/componentes.jpg)

Para la implementación de la clase **Informe** se utiliza una interfaz, **Escritor**, que tiene el método **escribir()**. En éste caso el informe solo puede ser generado con extensión markdown ya que no se han implementado otras, pero es posible hacerlo.

### Protocolo de comunicación

El protocolo de comunicación es el siguiente:

![Arquitectura](/src/main/resources/protocoloComunicacion.jpg)

En el intercambio de información entre **ServicioEmergencias** y **ExtensionValida**, en caso de que la extenxión sea válida, también devuelve la clase que genera el tipo de extensión que se ha indicado.

### Plan de pruebas

Se van a realizar pruebas con JUnit y mockito:

- Test para **Bote**:
  - Comprobar que los barcos se generan correctamente. Se comprueba que el id del barco creado es correcto y que el número de personas generado está dentro del rango establecido (1 a 100).
  - Comprobar que se generan la cantidad de barcos indicada.
- Test para **ServicioEmergencias**:
  - Comprobar que se genera el informe correctamente. Para ello se utiliza mockito con la interfaz **Escritor** para que no se genere un informe cada vez que se ejecute el test.
- Test para los **informes** (en este caso solo para Markdown):
  - Comprobar que se escribe el informe correctamente y que los datos coinciden con los datos de los botes.

## Manual de usuario

Para que el programa funcione correctamente se debe ejecutar el **ServicioEmergencias**.

## Elementos destacables del desarrollo

- Implementación de una clase que permite generar el informe en varios formatos distintos.
- Separación de los procesos por clases para facilitar el entendimiento del código.
- Implementación de pruebas que permiten comprobar la funcionalidad del programa.

## Problemas encontrados

- Se encontraron dificultades a la hora de transmitir la información generada en **Bote** a **ServicioEmergencias**.
- Se encontraron dificultades a la hora de comprobar que la extensión del informe coincidiera con las permitidas en el proyecto.
