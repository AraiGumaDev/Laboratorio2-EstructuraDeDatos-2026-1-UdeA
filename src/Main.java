public class Main {

    public static void main(String[] args) {

        BusquedaSecuencial sec = new BusquedaSecuencial();
        ExtendibleHash hash = new ExtendibleHash(2);

        // Datos de prueba
        for (int i = 1; i <= 10000; i++) {
            Usuario u = new Usuario("User" + i, String.valueOf(i), "correo" + i + "@test.com");
            sec.insertar(u);
            hash.insertar(u);
        }

        String cedulaBuscar = "9999";

        // Hash
        long inicioHash = System.nanoTime();
        Usuario u1 = hash.buscar(cedulaBuscar);
        long finHash = System.nanoTime();

        // Secuencial
        long inicioSec = System.nanoTime();
        Usuario u2 = sec.buscar(cedulaBuscar);
        long finSec = System.nanoTime();

        if (u1 != null) {
            System.out.println("Usuario encontrado:");
            System.out.println(u1);
        }

        System.out.println("\nTiempo Hash: " + (finHash - inicioHash) + " ns");
        System.out.println("Tiempo Secuencial: " + (finSec - inicioSec) + " ns");
    }
}