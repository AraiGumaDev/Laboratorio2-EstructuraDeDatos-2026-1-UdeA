import java.util.ArrayList;

public class ExtendibleHash {

    private int profundidadGlobal;
    private int capacidadBucket;
    private ArrayList<Bucket> directorio;

    public ExtendibleHash(int capacidadBucket) {
        this.capacidadBucket = capacidadBucket;
        this.profundidadGlobal = 1;

        directorio = new ArrayList<>();

        // Inicialmente 2 buckets
        directorio.add(new Bucket(capacidadBucket, 1));
        directorio.add(new Bucket(capacidadBucket, 1));
    }

    // Función hash simple
    private int hash(String clave) {
        return Math.abs(clave.hashCode());
    }

    // Obtener índice usando bits
    private int obtenerIndice(String clave) {
        int h = hash(clave);
        return h & ((1 << profundidadGlobal) - 1);
    }
    
    // NUEVO: verificar duplicados
    public boolean existe(String cedula) {
        return buscar(cedula) != null;
    }

    // Insertar
    public void insertar(Usuario u) {
        if (existe(u.getCedula())) {
            
            return;
        }
        int indice = obtenerIndice(u.getCedula());
        Bucket bucket = directorio.get(indice);

        if (!bucket.estaLleno()) {
            bucket.insertar(u);
        } else {
            dividirBucket(indice);
            insertar(u); // reintentar
        }
    }

    // Dividir bucket
    private void dividirBucket(int indice) {
        Bucket viejo = directorio.get(indice);
        viejo.incrementarProfundidad();

        // Si profundidad local supera global → duplicar directorio
        if (viejo.getProfundidadLocal() > profundidadGlobal) {
            profundidadGlobal++;

            int size = directorio.size();
            for (int i = 0; i < size; i++) {
                directorio.add(directorio.get(i));
            }
        }

        // Crear nuevo bucket
        Bucket nuevo = new Bucket(capacidadBucket, viejo.getProfundidadLocal());

        // Reubicar elementos
        ArrayList<Usuario> temp = new ArrayList<>(viejo.getUsuarios());
        viejo.getUsuarios().clear();

        for (Usuario u : temp) {
            int nuevoIndice = obtenerIndice(u.getCedula());
            if ((nuevoIndice & (1 << (viejo.getProfundidadLocal() - 1))) != 0) {
                nuevo.insertar(u);
            } else {
                viejo.insertar(u);
            }
        }

        // Actualizar referencias en directorio
        for (int i = 0; i < directorio.size(); i++) {
            if (directorio.get(i) == viejo) {
                if ((i & (1 << (viejo.getProfundidadLocal() - 1))) != 0) {
                    directorio.set(i, nuevo);
                }
            }
        }
    }

    // Buscar
    public Usuario buscar(String cedula) {
        int indice = obtenerIndice(cedula);
        Bucket bucket = directorio.get(indice);
        return bucket.buscar(cedula);
    }
}