# Descripción del Programa de Evaluación MVC

Este programa implementa una aplicación de evaluación utilizando el patrón MVC (Modelo-Vista-Controlador). A continuación, se describen los componentes principales y su función dentro del programa:

## Componentes

### Modelo (Package `backend`)

- **Item**: Representa un conjunto de preguntas que forman un ítem de evaluación. Contiene una lista de preguntas y un atributo `tipo` que indica si el ítem es de selección múltiple (`opcion_multiple`) o de verdadero/falso (`verdadero_falso`).

- **Pregunta**: Representa una pregunta individual dentro de un ítem. Contiene el texto de la pregunta, las opciones de respuesta, un identificador único y el nivel cognitivo asociado (por ejemplo, recordar, aplicar, analizar).

### Vista (Package `view`)

- **EvaluacionView**: Es la interfaz gráfica del programa. Muestra los ítems y preguntas al usuario, permite navegar entre ellos, seleccionar respuestas y finalmente visualizar los resultados de la evaluación. Utiliza componentes Swing para la interfaz gráfica.

### Controlador (Package `controller`)

- **EvaluacionController**: Gestiona la lógica del programa. Se encarga de cargar la evaluación desde un archivo de texto, procesar las respuestas del usuario, evaluar las respuestas y generar los resultados. Sirve de intermediario entre la vista y el modelo.

---

## Flujo general del programa

1. El usuario selecciona un archivo de evaluación en formato texto mediante un diálogo de archivo.

2. El controlador carga los ítems y preguntas desde el archivo.

3. La vista muestra el primer ítem con sus preguntas y opciones, permitiendo al usuario seleccionar respuestas.

4. El usuario puede navegar entre los ítems usando botones "Anterior" y "Siguiente".

5. Al finalizar, el usuario entrega la evaluación y el controlador calcula el puntaje y muestra un resumen con los resultados.

---

## Requisitos para compilar y ejecutar

- Tener instalado Java Development Kit (JDK) 8 o superior.
- Estructura de carpetas:
  - `src/backend` para clases del modelo.
  - `src/controller` para el controlador.
  - `src/view` para la vista.

---

## Comandos para compilar y ejecutar

```bash
javac -d out src/backend/*.java src/controller/*.java src/view/*.java
java -cp out view.EvaluacionView

*si no funciona la ejecución

cd out
java Main

