import java.util.*;
public class Main {

    static int[][] matrizAdyacencia; // Matriz de adyacencia
    static int[][] dist; // Matriz de distancias para el algoritmo de Floyd-Warshall
    static int[][] siguiente; // Matriz para rastrear los nodos en el camino más corto
    static int numNodos; // Número de nodos en el grafo

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Leer el número de nodos
        System.out.print("Ingrese el número de nodos: ");
        numNodos = scanner.nextInt();
        matrizAdyacencia = new int[numNodos][numNodos];

        // Leer la matriz de adyacencia
        System.out.println("Ingrese la matriz de adyacencia (use un número grande para ∞):");
        for (int i = 0; i < numNodos; i++) {
            for (int j = 0; j < numNodos; j++) {
                int valor = scanner.nextInt();
                matrizAdyacencia[i][j] = (valor >= 9999) ? Integer.MAX_VALUE : valor; // Usar un número grande como infinito
            }
        }

        // Inicializar las matrices dist y siguiente para Floyd-Warshall
        inicializarRutaMasCorta();

        // Calcular las distancias más cortas entre todos los pares de nodos
        algoritmoFloydWarshall();

        // Definir nodos de inicio y fin para calcular la ruta más corta
        System.out.print("Ingrese el nodo de inicio: ");
        int nodoInicio = scanner.nextInt();
        System.out.print("Ingrese el nodo de destino: ");
        int nodoFin = scanner.nextInt();

        // Obtener la distancia y la ruta más corta entre los nodos
        int distancia = obtenerDistancia(nodoInicio, nodoFin);
        List<Integer> ruta = obtenerRuta(nodoInicio, nodoFin);

        // Mostrar resultados
        if (distancia == Integer.MAX_VALUE) {
            System.out.println("No hay camino disponible entre los nodos.");
        } else {
            System.out.println("Distancia más corta entre " + nodoInicio + " y " + nodoFin + ": " + distancia);
            System.out.println("Ruta: " + ruta);
        }

        scanner.close();
    }

    // Inicializar las matrices para el algoritmo de Floyd-Warshall
    static void inicializarRutaMasCorta() {
        dist = new int[numNodos][numNodos];
        siguiente = new int[numNodos][numNodos];

        for (int i = 0; i < numNodos; i++) {
            for (int j = 0; j < numNodos; j++) {
                if (matrizAdyacencia[i][j] != Integer.MAX_VALUE) {
                    dist[i][j] = matrizAdyacencia[i][j];
                    siguiente[i][j] = j;
                } else {
                    dist[i][j] = Integer.MAX_VALUE;
                    siguiente[i][j] = -1;
                }
            }
        }
    }

    // Algoritmo de Floyd-Warshall para calcular la distancia mínima entre todos los pares de nodos
    static void algoritmoFloydWarshall() {
        for (int k = 0; k < numNodos; k++) {
            for (int i = 0; i < numNodos; i++) {
                for (int j = 0; j < numNodos; j++) {
                    if (dist[i][k] != Integer.MAX_VALUE && dist[k][j] != Integer.MAX_VALUE &&
                            dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        siguiente[i][j] = siguiente[i][k];
                    }
                }
            }
        }
    }

    // Obtener la distancia más corta entre dos nodos específi
    static int obtenerDistancia(int inicio, int fin) {
        return dist[inicio][fin];
    }

    // Obtener la secuencia de nodos en la ruta más corta entre dos nodos
    static List<Integer> obtenerRuta(int inicio, int fin) {
        List<Integer> ruta = new ArrayList<>();
        if (siguiente[inicio][fin] == -1) return ruta;

        int actual = inicio;
        while (actual != fin) {
            ruta.add(actual);
            actual = siguiente[actual][fin];
        }
        ruta.add(fin);
        return ruta;
    }
}


