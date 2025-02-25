public class Usuario {
    // Atributos
    public String nombre;
    public String direccion;
    public String id;
    public String correo;
    public String contraseña;
    public boolean estado; // Atributo estado como boolean

    // Constructor
    public Usuario(String nombre, String direccion, String id, String correo, String contraseña, boolean estado) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.id = id;
        this.correo = correo;
        this.contraseña = contraseña;
        this.estado = estado; // Inicializar el estado como boolean
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public boolean isEstado() { // Getter para el estado (boolean)
        return estado;
    }

    public void setEstado(boolean estado) { // Setter para el estado (boolean)
        this.estado = estado;
    }
}
