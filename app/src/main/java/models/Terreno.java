package models;

public class Terreno {
    private String id;
    private String nombre;
    private String detalles;

    public Terreno() {
        // Required empty constructor for Firestore
    }

    public Terreno(String id, String nombre, String detalles) {
        this.id = id;
        this.nombre = nombre;
        this.detalles = detalles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }
}