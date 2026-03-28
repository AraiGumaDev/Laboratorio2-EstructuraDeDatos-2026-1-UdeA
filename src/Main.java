import java.io.*;
import java.util.*;

public class Main {

    private static final String ARCHIVO = resolverRutaArchivo("usuarios.csv");

    private static String resolverRutaArchivo(String nombreArchivo) {
        if (new File(nombreArchivo).exists()) return nombreArchivo;
        String subir = "../" + nombreArchivo;
        if (new File(subir).exists()) return subir;
        return nombreArchivo;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BusquedaSecuencial sec = new BusquedaSecuencial();
        ExtendibleHash hash = new ExtendibleHash(4);

        cargarDesdeArchivo(hash, sec);

        int opcion;
        do {
            System.out.println("\n========== MENÚ CRUD ==========");
            System.out.println("1. Registrar usuario");
            System.out.println("2. Buscar usuario");
            System.out.println("3. Actualizar usuario");
            System.out.println("4. Eliminar usuario");
            System.out.println("5. Listar todos los usuarios");
            System.out.println("6. Salir");
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

                    if (hash.existe(cedula)) {
                        System.out.println("Error: ya existe un usuario con la cédula " + cedula);
                    } else {
                        Usuario u = new Usuario(nombre, cedula, correo);
                        hash.insertar(u);
                        sec.insertar(u);
                        guardarEnArchivo(sec);
                        System.out.println("Usuario registrado correctamente.");
                    }
                    break;

                case 2:
                    System.out.print("Ingrese cédula a buscar: ");
                    String cedulaBuscar = sc.nextLine();

                    long inicioHash = System.nanoTime();
                    Usuario encontradoHash = hash.buscar(cedulaBuscar);
                    long finHash = System.nanoTime();

                    long inicioSec = System.nanoTime();
                    Usuario encontradoSec = sec.buscar(cedulaBuscar);
                    long finSec = System.nanoTime();

                    if (encontradoHash != null) {
                        System.out.println("\nUsuario encontrado:");
                        System.out.println(encontradoHash);
                    } else {
                        System.out.println("Usuario no encontrado.");
                    }

                    System.out.printf("Tiempo búsqueda (Hashing): %.4f ms%n", (finHash - inicioHash) / 1_000_000.0);
                    System.out.printf("Tiempo búsqueda (Secuencial): %.4f ms%n", (finSec - inicioSec) / 1_000_000.0);
                    break;

                case 3:
                    System.out.print("Cédula del usuario a actualizar: ");
                    String cedulaAct = sc.nextLine();

                    if (!hash.existe(cedulaAct)) {
                        System.out.println("No existe un usuario con esa cédula.");
                        break;
                    }

                    System.out.println("Usuario actual:");
                    System.out.println(hash.buscar(cedulaAct));

                    System.out.print("Nuevo nombre: ");
                    String nuevoNombre = sc.nextLine();
                    System.out.print("Nuevo correo: ");
                    String nuevoCorreo = sc.nextLine();

                    hash.actualizar(cedulaAct, nuevoNombre, nuevoCorreo);
                    sec.actualizar(cedulaAct, nuevoNombre, nuevoCorreo);
                    guardarEnArchivo(sec);
                    System.out.println("Usuario actualizado correctamente.");
                    break;

                case 4:
                    System.out.print("Cédula del usuario a eliminar: ");
                    String cedulaDel = sc.nextLine();

                    if (hash.eliminar(cedulaDel) && sec.eliminar(cedulaDel)) {
                        guardarEnArchivo(sec);
                        System.out.println("Usuario eliminado correctamente.");
                    } else {
                        System.out.println("No existe un usuario con esa cédula.");
                    }
                    break;

                case 5:
                    ArrayList<Usuario> todos = sec.getLista();
                    if (todos.isEmpty()) {
                        System.out.println("No hay usuarios registrados.");
                    } else {
                        System.out.println("\n===== Lista de usuarios (" + todos.size() + ") =====");
                        for (int i = 0; i < todos.size(); i++) {
                            System.out.println("\n[" + (i + 1) + "]");
                            System.out.println(todos.get(i));
                        }
                    }
                    break;

                case 6:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 6);

        sc.close();
    }

    private static void cargarDesdeArchivo(ExtendibleHash hash, BusquedaSecuencial sec) {
        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) {
            System.out.println("Archivo '" + ARCHIVO + "' no encontrado. Se iniciará sin datos previos.");
            return;
        }

        int cargados = 0;
        int duplicados = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;

                String[] partes = linea.split(";");
                if (partes.length != 3) continue;

                String nombre = partes[0].trim();
                String cedula = partes[1].trim();
                String correo = partes[2].trim();

                if (hash.existe(cedula)) {
                    duplicados++;
                } else {
                    Usuario u = new Usuario(nombre, cedula, correo);
                    hash.insertar(u);
                    sec.insertar(u);
                    cargados++;
                }
            }
            System.out.println("Datos cargados desde '" + ARCHIVO + "': " + cargados + " usuarios.");
            if (duplicados > 0) {
                System.out.println("Advertencia: se ignoraron " + duplicados + " registros duplicados.");
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    private static void guardarEnArchivo(BusquedaSecuencial sec) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO))) {
            for (Usuario u : sec.getLista()) {
                bw.write(u.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo: " + e.getMessage());
        }
    }
}
