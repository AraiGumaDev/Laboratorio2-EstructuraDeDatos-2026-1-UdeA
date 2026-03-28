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

    // Eliminar por cédula
    public boolean eliminar(String cedula) {
        return lista.removeIf(u -> u.getCedula().equals(cedula));
    }

    // Actualizar por cédula
    public boolean actualizar(String cedula, String nuevoNombre, String nuevoCorreo) {
        for (Usuario u : lista) {
            if (u.getCedula().equals(cedula)) {
                u.setNombre(nuevoNombre);
                u.setCorreo(nuevoCorreo);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Usuario> getLista() {
        return lista;
    }
}