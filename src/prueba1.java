import java.io.*;
import java.util.Arrays;

    public class prueba1 {
        // Matriz de adyacencia que representa las distancias entre nodos
        static int[][] adjMatrix;

        public static void main(String[] args) {
            // Cargar matriz de adyacencia desde archivo
            cargarMatriz("nombre_del_archivo.txt");

            // Calcular las distancias más cortas
            calcularCaminos();

            // Ejemplo de cómo mostrar la distancia y ruta entre dos nodos
            int nodoInicio = 0; // Cambia el valor de acuerdo a tus pruebas
            int nodoFin = 3;    // Cambia el valor de acuerdo a tus pruebas
            mostrarRuta(nodoInicio, nodoFin);
        }

        /**
         * Lee la matriz de adyacencia desde un archivo de texto.
         * @param archivo Nombre del archivo que contiene la matriz de adyacencia
         */
        static void cargarMatriz(String archivo) {
            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                // Lee el tamaño de la matriz desde la primera línea del archivo
                int size = Integer.parseInt(br.readLine().trim());
                adjMatrix = new int[size][size];

                // Inicializar la matriz con valores infinitos para distancias desconocidas
                for (int[] row : adjMatrix) {
                    Arrays.fill(row, Integer.MAX_VALUE);
                }

                // Leer cada línea y llenar la matriz de adyacencia
                for (int i = 0; i < size; i++) {
                    String[] values = br.readLine().trim().split(" ");
                    for (int j = 0; j < size; j++) {
                        adjMatrix[i][j] = values[j].equals("∞") ? Integer.MAX_VALUE : Integer.parseInt(values[j]);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * Calcula la distancia más corta entre todos los pares de nodos usando el algoritmo de Floyd-Warshall.
         */
        static void calcularCaminos() {
            int n = adjMatrix.length;

            // Aplicar el algoritmo de Floyd-Warshall
            for (int k = 0; k < n; k++) {
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (adjMatrix[i][k] != Integer.MAX_VALUE && adjMatrix[k][j] != Integer.MAX_VALUE) {
                            adjMatrix[i][j] = Math.min(adjMatrix[i][j], adjMatrix[i][k] + adjMatrix[k][j]);
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
            if (adjMatrix[inicio][fin] == Integer.MAX_VALUE) {
                System.out.println("No hay ruta entre el nodo " + inicio + " y el nodo " + fin);
            } else {
                System.out.println("La distancia más corta entre " + inicio + " y " + fin + " es " + adjMatrix[inicio][fin]);
                // Nota: No implementamos aquí los nodos intermedios en la ruta
            }
        }
    }


