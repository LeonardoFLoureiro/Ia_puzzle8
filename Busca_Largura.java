import java.util.*;

public class Busca_Largura {
    public static void bfs(Map<Integer, List<Integer>> graph, int start) {
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
        
            int current = queue.poll();
            System.out.println(current); // Ação a ser realizada ao visitar o nó

            List<Integer> neighbors = graph.get(current);
            if (neighbors != null) {
                for (int neighbor : neighbors) {
                    if (!visited.contains(neighbor)) {
                        queue.add(neighbor);
                        visited.add(neighbor);
                    }
                }
            }
        }

    }
    public static void printGraph(Map<Integer, List<Integer>> graph) {
        for (Map.Entry<Integer, List<Integer>> entry : graph.entrySet()) {
            int node = entry.getKey();
            List<Integer> neighbors = entry.getValue();

            System.out.print("Node " + node + " -> ");
            for (int neighbor : neighbors) {
                System.out.print(neighbor + " ");
            }
            System.out.println();
        }
    }
    public void busca_largura_teste(){

        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, Arrays.asList(2, 3));
        graph.put(2, Arrays.asList(4, 5));
        graph.put(3, Arrays.asList(6));
        graph.put(4, Collections.emptyList());
        graph.put(5, Arrays.asList(6));
        graph.put(6, Collections.emptyList());

        System.out.println("Graph:");
        printGraph(graph);

        int startNode = 1;
        System.out.println("\nBFS traversal starting from node " + startNode + ":");
        bfs(graph, startNode);
    }
}