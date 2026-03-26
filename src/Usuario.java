public class Usuario {
    private String nombre;
    private String cedula;
    private String correo;

    public Usuario(String nombre, String cedula, String correo) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.correo = correo;
    }

    public String getCedula() {
        return cedula;
    }

    public String toString() {
        return "Nombre: " + nombre +
                "\nCC: " + cedula +
                "\nCorreo: " + correo;
    }
}