// backend/Item.java
package backend;

import java.util.List;

public class Item {
    public int itemId;
    public String tipo; // tipo de ítem: opcion_multiple o verdadero_falso
    public List<Pregunta> preguntas;
}