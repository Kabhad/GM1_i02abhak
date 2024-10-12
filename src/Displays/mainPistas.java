package Displays;

import java.util.List;
import java.util.Scanner;

import Clases.Pista;
import Clases.Material;
import Clases.tamanioPista;
import Clases.TipoMaterial;
import Clases.EstadoMaterial;
import Gestores.GestorPistas;

public class mainPistas {
    public static void imprimirMenu() {
        System.out.println("=====================================");
        System.out.println("        GESTOR DE PISTAS");
        System.out.println("=====================================");
        System.out.println("1. Crear pista");
        System.out.println("2. Crear Material");
        System.out.println("3. Asociar material a pista disponible");
        System.out.println("4. Listar las pistas no disponibles");
        System.out.println("5. Buscar pistas disponibles");
        System.out.println("0. Volver al menú principal");
        System.out.println("=====================================");
        System.out.print("Seleccione una opción: ");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GestorPistas gestor = new GestorPistas();  // Instancia del gestor de pistas

        int opcion;
      
        do {
            imprimirMenu();
            opcion = scanner.nextInt();
            scanner.nextLine();  // Consumir la nueva línea

            switch (opcion) {
                case 1:
                    System.out.println("Crear nueva pista");
                    System.out.print("Nombre de la pista: ");
                    String nombrePista = scanner.nextLine();
                    System.out.print("¿Está disponible (true/false)? ");
                    boolean disponible = scanner.nextBoolean();
                    System.out.print("¿Es exterior (true/false)? ");
                    boolean exterior = scanner.nextBoolean();
                    System.out.println("Tipo de pista (1: MINIBASKET, 2: ADULTOS, 3: 3VS3): ");
                    int tipoPista = scanner.nextInt();
                    tamanioPista tamanio = tipoPista == 1 ? tamanioPista.MINIBASKET
                            : tipoPista == 2 ? tamanioPista.ADULTOS : tamanioPista._3VS3;
                    System.out.print("Máximo número de jugadores: ");
                    int maxJugadores = scanner.nextInt();
                    gestor.crearPista(nombrePista, disponible, exterior, tamanio, maxJugadores);
                    System.out.println("Pista creada con éxito.");
                    break;

                case 2:
                    System.out.println("Crear nuevo material");
                    System.out.print("ID del material: ");
                    int idMaterial = scanner.nextInt();
                    System.out.println("Tipo de material (1: PELOTAS, 2: CANASTAS, 3: CONOS): ");
                    int tipoMaterial = scanner.nextInt();
                    TipoMaterial tipo = tipoMaterial == 1 ? TipoMaterial.PELOTAS
                            : tipoMaterial == 2 ? TipoMaterial.CANASTAS : TipoMaterial.CONOS;
                    System.out.print("¿Es para uso exterior (true/false)? ");
                    boolean usoExterior = scanner.nextBoolean();
                    System.out.println("Estado del material (1: DISPONIBLE, 2: RESERVADO, 3: MAL_ESTADO): ");
                    int estadoMaterial = scanner.nextInt();
                    EstadoMaterial estado = estadoMaterial == 1 ? EstadoMaterial.DISPONIBLE
                            : estadoMaterial == 2 ? EstadoMaterial.RESERVADO : EstadoMaterial.MAL_ESTADO;
                    gestor.crearMaterial(idMaterial, tipo, usoExterior, estado);
                    System.out.println("Material creado con éxito.");
                    break;

                case 3:
                    System.out.println("Asociar material a pista disponible");
                    System.out.print("Nombre de la pista: ");
                    String nombreAsociarPista = scanner.nextLine();
                    System.out.print("ID del material a asociar: ");
                    int idMaterialAsociar = scanner.nextInt();
                    boolean resultado = gestor.asociarMaterialAPista(nombreAsociarPista, idMaterialAsociar);
                    if (resultado) {
                        System.out.println("Material asociado con éxito.");
                    } else {
                        System.out.println("No se pudo asociar el material.");
                    }
                    break;

                case 4:
                    System.out.println("Listar pistas no disponibles:");
                    List<Pista> pistasNoDisponibles = gestor.listarPistasNoDisponibles();
                    if (pistasNoDisponibles.isEmpty()) {
                        System.out.println("No hay pistas no disponibles.");
                    } else {
                        for (Pista pista : pistasNoDisponibles) {
                            System.out.println(pista.toString());
                        }
                    }
                    break;

                case 5:
                    System.out.println("Buscar pistas disponibles");
                    System.out.print("Número de jugadores: ");
                    int numJugadores = scanner.nextInt();
                    System.out.println("Tipo de pista (1: MINIBASKET, 2: ADULTOS, 3: 3VS3): ");
                    int tipoPistaBuscar = scanner.nextInt();
                    tamanioPista tipoPistaEnum = tipoPistaBuscar == 1 ? tamanioPista.MINIBASKET
                            : tipoPistaBuscar == 2 ? tamanioPista.ADULTOS : tamanioPista._3VS3;
                    List<Pista> pistasDisponibles = gestor.buscarPistasDisponibles(numJugadores, tipoPistaEnum);
                    if (pistasDisponibles.isEmpty()) {
                        System.out.println("No hay pistas disponibles.");
                    } else {
                        for (Pista pista : pistasDisponibles) {
                            System.out.println(pista.toString());
                        }
                    }
                    break;

                case 0:
                	// Volver al menú principal
                    System.out.println("Volviendo al menú principal...");
                    break;

                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
                    break;
            }
        } while (opcion != 0);

        scanner.close();
    }
}

