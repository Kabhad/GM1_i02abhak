package es.uco.pw.displays.pistas;

import java.util.List;
import java.util.Scanner;

import es.uco.pw.classes.pista.Pista;
import es.uco.pw.classes.material.*;
import es.uco.pw.classes.pista.TamanoPista;
import es.uco.pw.gestores.pistas.GestorPistas;

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

    public static void main(Scanner sc) {
        GestorPistas gestorPistas = GestorPistas.getInstance();
        int opcion;

        do {
            imprimirMenu();
            opcion = sc.nextInt();
            sc.nextLine();  // Consumir la nueva línea

            switch (opcion) {
                case 1:
                    try {
                        System.out.println("Crear nueva pista");
                        System.out.print("Nombre de la pista: ");
                        String nombrePista = sc.nextLine();
                        System.out.print("¿Está disponible (true/false)? ");
                        boolean disponible = sc.nextBoolean();
                        System.out.print("¿Es exterior (true/false)? ");
                        boolean exterior = sc.nextBoolean();
                        System.out.println("Tipo de pista (1: MINIBASKET, 2: ADULTOS, 3: 3VS3): ");
                        int tipoPista = sc.nextInt();
                        TamanoPista tamanio = tipoPista == 1 ? TamanoPista.MINIBASKET
                                : tipoPista == 2 ? TamanoPista.ADULTOS : TamanoPista._3VS3;
                        System.out.print("Máximo número de jugadores: ");
                        int maxJugadores = sc.nextInt();
                        gestorPistas.crearPista(nombrePista, disponible, exterior, tamanio, maxJugadores);
                        System.out.println("Pista creada con éxito.");
                    } catch (Exception e) {
                        System.out.println("Error al crear la pista: " + e.getMessage());
                    }
                    break;

                case 2:
                    try {
                        System.out.println("Crear nuevo material");
                        System.out.print("ID del material: ");
                        int idMaterial = sc.nextInt();
                        System.out.println("Tipo de material (1: PELOTAS, 2: CANASTAS, 3: CONOS): ");
                        int tipoMaterial = sc.nextInt();
                        TipoMaterial tipo = tipoMaterial == 1 ? TipoMaterial.PELOTAS
                                : tipoMaterial == 2 ? TipoMaterial.CANASTAS : TipoMaterial.CONOS;
                        System.out.print("¿Es para uso exterior (true/false)? ");
                        boolean usoExterior = sc.nextBoolean();
                        System.out.println("Estado del material (1: DISPONIBLE, 2: RESERVADO, 3: MAL_ESTADO): ");
                        int estadoMaterial = sc.nextInt();
                        EstadoMaterial estado = estadoMaterial == 1 ? EstadoMaterial.DISPONIBLE
                                : estadoMaterial == 2 ? EstadoMaterial.RESERVADO : EstadoMaterial.MAL_ESTADO;
                        gestorPistas.crearMaterial(idMaterial, tipo, usoExterior, estado);
                        System.out.println("Material creado con éxito.");
                    } catch (Exception e) {
                        System.out.println("Error al crear el material: " + e.getMessage());
                    }
                    break;

                case 3:
                    try {
                        System.out.println("Asociar material a pista disponible");
                        System.out.print("Nombre de la pista: ");
                        String nombreAsociarPista = sc.nextLine();
                        System.out.print("ID del material a asociar: ");
                        int idMaterialAsociar = sc.nextInt();
                        boolean resultado = gestorPistas.asociarMaterialAPista(nombreAsociarPista, idMaterialAsociar);
                        if (resultado) {
                            System.out.println("Material asociado con éxito.");
                        } else {
                            System.out.println("No se pudo asociar el material.");
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 4:
                    System.out.println("Listar pistas no disponibles:");
                    List<Pista> pistasNoDisponibles = gestorPistas.listarPistasNoDisponibles();
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
                    int numJugadores = sc.nextInt();
                    System.out.println("Tipo de pista (1: MINIBASKET, 2: ADULTOS, 3: 3VS3): ");
                    int tipoPistaBuscar = sc.nextInt();
                    TamanoPista tipoPistaEnum = tipoPistaBuscar == 1 ? TamanoPista.MINIBASKET
                            : tipoPistaBuscar == 2 ? TamanoPista.ADULTOS : TamanoPista._3VS3;
                    List<Pista> pistasDisponibles = gestorPistas.buscarPistasDisponibles(numJugadores, tipoPistaEnum);
                    if (pistasDisponibles.isEmpty()) {
                        System.out.println("No hay pistas disponibles.");
                    } else {
                        for (Pista pista : pistasDisponibles) {
                            System.out.println(pista.toString());
                        }
                    }
                    break;

                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;

                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
                    break;
            }
        } while (opcion != 0);
    }
}
