import java.util.ArrayList;

public class BusquedaSecuencial {

    private ArrayList<Usuario> lista = new ArrayList<>();

    // Insertar usuario
    public void insertar(Usuario u) {
        lista.add(u);
    }

    // Buscar por cédula
    public Usuario buscar(String cedula) {
        for (Usuario u : lista) {
            if (u.getCedula().equals(cedula)) {
                return u;
            }
        }
        return null;
    }

    public ArrayList<Usuario> getLista() {
        return lista;
    }
}