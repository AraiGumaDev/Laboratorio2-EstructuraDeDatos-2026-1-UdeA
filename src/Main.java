import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        BusquedaSecuencial sec = new BusquedaSecuencial();
        ExtendibleHash hash = new ExtendibleHash(2);

        int opcion;

        do {
            System.out.println("\n===== MENÚ =====");
            System.out.println("1. Registrar usuario");
            System.out.println("2. Buscar usuario");
            System.out.println("3. Salir");
            System.out.print("Seleccione: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {

                case 1:
                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();

                    System.out.print("Cédula: ");
                    String cedula = sc.nextLine();

                    System.out.print("Correo: ");
                    String correo = sc.nextLine();

                    Usuario u = new Usuario(nombre, cedula, correo);

                    // Aquí controlamos los duplicados
                    if (!hash.existe(cedula)) {
                        hash.insertar(u);
                        sec.insertar(u);
                        System.out.println("Usuario registrado correctamente.");
                    } else {
                        System.out.println("Error: cédula duplicada -> " + cedula);
                    }

                    break;

                case 2:
                    System.out.print("Ingrese cédula a buscar: ");
                    String cedulaBuscar = sc.nextLine();

                    long inicioHash = System.nanoTime();
                    Usuario u1 = hash.buscar(cedulaBuscar);
                    long finHash = System.nanoTime();

                    long inicioSec = System.nanoTime();
                    Usuario u2 = sec.buscar(cedulaBuscar);
                    long finSec = System.nanoTime();

                    if (u1 != null) {
                        System.out.println("\nUsuario encontrado:");
                        System.out.println(u1);
                    } else {
                        System.out.println("Usuario no encontrado");
                    }

                    double tiempoHash = (finHash - inicioHash) / 1_000_000.0;
                    double tiempoSec = (finSec - inicioSec) / 1_000_000.0;

                    System.out.println("\nTiempo Hash: " + tiempoHash + " ms");
                    System.out.println("Tiempo Secuencial: " + tiempoSec + " ms");

                    break;

                case 3:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opción inválida");
            }

        } while (opcion != 3);

        sc.close();
    }
}