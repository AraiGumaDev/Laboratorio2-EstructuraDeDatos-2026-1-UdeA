public class Usuario {
    private String nombre;
    private String cedula;
    private String correo;

    public Usuario(String nombre, String cedula, String correo) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.correo = correo;
    }

    public String getNombre() { return nombre; }
    public String getCedula() { return cedula; }
    public String getCorreo()  { return correo; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String toCSV() {
        return nombre + ";" + cedula + ";" + correo;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre +
                "\nCC: " + cedula +
                "\nCorreo: " + correo;
    }
}