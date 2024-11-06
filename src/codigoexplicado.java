import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class codigoexplicado {
    // Matriz de adyacencia que representa las distancias entre nodos
    static int[][] adjMatrix;
    // Matriz para rastrear el camino más corto entre nodos
    static int[][] camino;

    public static void main(String[] args) {
        // Cargar matriz de adyacencia desde archivo
        cargarMatriz("nombre_del_archivo.txt");

        // Calcular las distancias más cortas utilizando el algoritmo de Floyd-Warshall
        calcularCaminos();

        // Crear un objeto Scanner para permitir al usuario ingresar nodos de inicio y fin
        Scanner scanner = new Scanner(System.in);

        // Pedir al usuario el nodo de inicio para la ruta
        System.out.print("Ingrese el nodo de inicio: ");
        int nodoInicio = scanner.nextInt();

        // Pedir al usuario el nodo de fin para la ruta
        System.out.print("Ingrese el nodo de fin: ");
        int nodoFin = scanner.nextInt();

        // Mostrar la distancia y la ruta más corta entre los nodos ingresados por el usuario
        mostrarRuta(nodoInicio, nodoFin);

        // Cerrar el objeto Scanner para liberar recursos
        scanner.close();
    }

    /**
     * Lee la matriz de adyacencia desde un archivo de texto.
     * @param archivo Nombre del archivo que contiene la matriz de adyacencia
     */
    static void cargarMatriz(String archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            // Leer la primera línea del archivo para obtener el tamaño de la matriz
            int size = Integer.parseInt(br.readLine().trim());

            // Inicializar la matriz de adyacencia con el tamaño leído
            adjMatrix = new int[size][size];
            // Inicializar la matriz para rastrear los caminos más cortos
            camino = new int[size][size];

            // Llenar la matriz de adyacencia con valores "infinitos" (representados por Integer.MAX_VALUE)
            // Esto significa que al principio, asumimos que no hay rutas directas entre nodos
            for (int[] row : adjMatrix) {
                Arrays.fill(row, Integer.MAX_VALUE);
            }

            // Leer el archivo línea por línea para llenar la matriz de adyacencia
            for (int i = 0; i < size; i++) {
                // Dividir cada línea en valores individuales (separados por espacios)
                String[] values = br.readLine().trim().split(" ");
                for (int j = 0; j < size; j++) {
                    // Si el valor es "∞", representa una distancia infinita (sin conexión directa)
                    // De lo contrario, se convierte el valor a un entero y se guarda en la matriz
                    adjMatrix[i][j] = values[j].equals("∞") ? Integer.MAX_VALUE : Integer.parseInt(values[j]);
                    // Inicializar la matriz de camino; si hay una conexión directa, establecemos el destino
                    camino[i][j] = (i != j && adjMatrix[i][j] != Integer.MAX_VALUE) ? j : -1;
                }
            }
        } catch (IOException e) {
            // Capturar cualquier error de entrada/salida y mostrar el error
            e.printStackTrace();
        }
    }

    /**
     * Calcula la distancia más corta entre todos los pares de nodos usando el algoritmo de Floyd-Warshall.
     */
    static void calcularCaminos() {
        int n = adjMatrix.length;

        // Implementación del algoritmo de Floyd-Warshall para encontrar distancias más cortas
        for (int k = 0; k < n; k++) { // k representa el nodo intermedio actual
            for (int i = 0; i < n; i++) { // i representa el nodo de origen
                for (int j = 0; j < n; j++) { // j representa el nodo de destino
                    // Si existe una ruta de i a k y de k a j, entonces intentamos actualizar la distancia i->j
                    if (adjMatrix[i][k] != Integer.MAX_VALUE && adjMatrix[k][j] != Integer.MAX_VALUE) {
                        // Si la ruta pasando por el nodo k es más corta, actualizamos adjMatrix[i][j]
                        if (adjMatrix[i][j] > adjMatrix[i][k] + adjMatrix[k][j]) {
                            adjMatrix[i][j] = adjMatrix[i][k] + adjMatrix[k][j];
                            // Guardamos el nodo intermedio en la matriz camino para reconstruir la ruta después
                            camino[i][j] = camino[i][k];
                        }
                    }
                }
            }
        }
    }

    /**
     * Muestra la distancia más corta y la ruta entre dos nodos.
     * @param inicio Nodo inicial
     * @param fin Nodo final
     */
    static void mostrarRuta(int inicio, int fin) {
        // Verificar si hay una ruta entre los nodos de inicio y fin
        if (adjMatrix[inicio][fin] == Integer.MAX_VALUE) {
            System.out.println("No hay ruta entre el nodo " + inicio + " y el nodo " + fin);
        } else {
            // Mostrar la distancia más corta entre los dos nodos
            System.out.println("La distancia más corta entre " + inicio + " y " + fin + " es " + adjMatrix[inicio][fin]);
            // Imprimir la ruta desde el nodo de inicio hasta el nodo de fin
            System.out.print("Ruta: " + inicio);
            imprimirCamino(inicio, fin);
            System.out.println(" -> " + fin);
        }
    }

    /**
     * Imprime los nodos intermedios en la ruta más corta entre dos nodos.
     * Utiliza la matriz camino para reconstruir la ruta.
     * @param inicio Nodo inicial
     * @param fin Nodo final
     */
    static void imprimirCamino(int inicio, int fin) {
        // Si no hay nodo intermedio, termina la recursión
        if (camino[inicio][fin] == -1) return;

        // Obtener el nodo intermedio entre inicio y fin
        int intermedio = camino[inicio][fin];

        // Llamada recursiva para construir el camino desde inicio hasta intermedio
        imprimirCamino(inicio, intermedio);

        // Imprimir el nodo intermedio como parte de la ruta
        System.out.print(" -> " + intermedio);
    }
}
// Variables y Estructuras de Datos
//
//adjMatrix: Una matriz bidimensional que representa las distancias entre nodos. Se usa para almacenar la "matriz de adyacencia", donde cada elemento [i][j] representa la distancia directa entre los nodos i y j.
//camino: Otra matriz bidimensional que almacena los nodos intermedios para la ruta más corta. Esto permite reconstruir la ruta completa una vez que se calculan las distancias.
//Método cargarMatriz
//
//Este método lee el archivo que contiene la matriz de adyacencia. Cada línea representa una fila de la matriz.
//Si un valor es "∞", se considera como "sin conexión directa" (distancia infinita).
//También se inicializa la matriz camino, donde cada par directo de nodos se almacena para poder reconstruir el camino.
//Método calcularCaminos
//
//Implementa el algoritmo de Floyd-Warshall, que es un algoritmo de programación dinámica para calcular las distancias más cortas entre todos los pares de nodos.
//La estructura for triple representa el uso de nodos intermedios (k) para mejorar las distancias directas entre nodos i y j.
//Método mostrarRuta
//
//Verifica si hay una ruta válida entre los nodos inicio y fin.
//Si existe una ruta, muestra la distancia más corta y llama al método imprimirCamino para detallar la ruta exacta.
//Método imprimirCamino
//
//Utiliza la matriz camino para imprimir los nodos intermedios en la ruta más corta.
//Emplea recursión para recorrer todos los nodos intermedios entre el nodo de inicio y el de fin, permitiendo construir la ruta de forma completa.
