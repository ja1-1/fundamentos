import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

    public class proyectofinal {

        static int[][] matrizAdyacencia; // Matriz de adyacencia para distancias
        static int[][] predecesores; // Matriz para registrar los caminos
        static int numNodos; // Número de nodos en el grafo

        /**
         * Inicializa la matriz de adyacencia y la matriz de predecesores con el número de nodos especificado.
         * @param nodos Número de nodos en el grafo.
         */
        public static void inicializarGrafo(int nodos) {
            numNodos = nodos;
            matrizAdyacencia = new int[numNodos][numNodos];
            predecesores = new int[numNodos][numNodos];

            for (int i = 0; i < numNodos; i++) {
                for (int j = 0; j < numNodos; j++) {
                    if (i == j) {
                        matrizAdyacencia[i][j] = 0;
                    } else {
                        matrizAdyacencia[i][j] = Integer.MAX_VALUE; // Representa infinito
                    }
                    predecesores[i][j] = i; // Iniciar predecesores con el nodo propio
                }
            }
        }

        /**
         * Carga el grafo desde un archivo de texto.
         * Cada línea representa una fila de la matriz de adyacencia.
         * @param nombreArchivo nombre del archivo de entrada.
         */
        public static void cargarGrafoDesdeArchivo(String nombreArchivo) {
            try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
                String linea;
                int fila = 0;
                while ((linea = br.readLine()) != null) {
                    String[] valores = linea.split(" ");
                    for (int columna = 0; columna < valores.length; columna++) {
                        int peso = Integer.parseInt(valores[columna]);
                        matrizAdyacencia[fila][columna] = peso;
                        matrizAdyacencia[columna][fila] = peso; // Simetría para grafo no dirigido
                    }
                    fila++;
                }
            } catch (IOException e) {
                System.out.println("Error al leer el archivo: " + e.getMessage());
            }
        }

        /**
         * Aplica el algoritmo de Floyd-Warshall para encontrar las distancias más cortas entre todos los pares de nodos.
         */
        public static void calcularRutaCorta() {
            for (int k = 0; k < numNodos; k++) {
                for (int i = 0; i < numNodos; i++) {
                    for (int j = 0; j < numNodos; j++) {
                        if (matrizAdyacencia[i][k] != Integer.MAX_VALUE && matrizAdyacencia[k][j] != Integer.MAX_VALUE) {
                            int nuevaDistancia = matrizAdyacencia[i][k] + matrizAdyacencia[k][j];
                            if (nuevaDistancia < matrizAdyacencia[i][j]) {
                                matrizAdyacencia[i][j] = nuevaDistancia;
                                predecesores[i][j] = predecesores[k][j];
                            }
                        }
                    }
                }
            }
        }

        /**
         * Imprime la ruta más corta entre dos nodos, incluyendo los nodos visitados.
         * @param origen nodo inicial.
         * @param destino nodo final.
         */
        public static void mostrarRuta(int origen, int destino) {
            if (matrizAdyacencia[origen][destino] == Integer.MAX_VALUE) {
                System.out.println("No hay camino entre los nodos " + origen + " y " + destino);
            } else {
                System.out.print("Ruta más corta de " + origen + " a " + destino + ": ");
                imprimirCamino(origen, destino);
                System.out.println("\nDistancia: " + matrizAdyacencia[origen][destino]);
            }
        }

        /**
         * Método auxiliar para imprimir el camino entre dos nodos usando la matriz de predecesores.
         * @param origen nodo inicial del camino.
         * @param destino nodo final del camino.
         */
        public static void imprimirCamino(int origen, int destino) {
            if (origen != destino) {
                imprimirCamino(origen, predecesores[origen][destino]);
            }
            System.out.print(destino + " ");
        }

        public static void main(String[] args) {
            inicializarGrafo(4); // Ajusta el número de nodos aquí

            // Cargar el grafo desde un archivo de texto
            cargarGrafoDesdeArchivo("grafo.txt");

            // Calcular las rutas más cortas
            calcularRutaCorta();

            // Mostrar la ruta más corta entre dos nodos
            mostrarRuta(0, 3); // Ajusta los nodos según tu prueba
        }
    }

}
