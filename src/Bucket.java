import java.util.ArrayList;

public class Bucket {

    private int capacidad;
    private int profundidadLocal;
    private ArrayList<Usuario> usuarios;

    public Bucket(int capacidad, int profundidadLocal) {
        this.capacidad = capacidad;
        this.profundidadLocal = profundidadLocal;
        this.usuarios = new ArrayList<>();
    }

    public boolean estaLleno() {
        return usuarios.size() >= capacidad;
    }

    public void insertar(Usuario u) {
        usuarios.add(u);
    }

    public Usuario buscar(String cedula) {
        for (Usuario u : usuarios) {
            if (u.getCedula().equals(cedula)) {
                return u;
            }
        }
        return null;
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public int getProfundidadLocal() {
        return profundidadLocal;
    }

    public void incrementarProfundidad() {
        profundidadLocal++;
    }
}