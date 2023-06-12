import java.util.*;

public class Busca_AStar {
    static class Node {
        int id;
        double gScore;
        double fScore;

        Node(int id, double gScore, double fScore) {
            this.id = id;
            this.gScore = gScore;
            this.fScore = fScore;
        }
    }

    public static List<Integer> aStar(Map<Integer, List<int[]>> graph, int start, int goal, Map<Integer, Double> heuristic) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(node -> node.fScore));
        Set<Integer> closedSet = new HashSet<>();
        Map<Integer, Double> gScore = new HashMap<>();
        Map<Integer, Integer> cameFrom = new HashMap<>();

        gScore.put(start, 0.0);
        openSet.add(new Node(start, 0.0, heuristic.get(start)));

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.id == goal) {
                return reconstructPath(cameFrom, current.id);
            }

            closedSet.add(current.id);

            List<int[]> neighbors = graph.get(current.id);
            if (neighbors != null) {
                for (int[] neighbor : neighbors) {
                    int neighborId = neighbor[0];
                    double edgeCost = neighbor[1];
                    double tentativeGScore = gScore.get(current.id) + edgeCost;

                    if (closedSet.contains(neighborId)) {
                        continue;
                    }

                    if (!gScore.containsKey(neighborId) || tentativeGScore < gScore.get(neighborId)) {
                        cameFrom.put(neighborId, current.id);
                        gScore.put(neighborId, tentativeGScore);
                        double fScore = tentativeGScore + heuristic.get(neighborId);
                        openSet.add(new Node(neighborId, tentativeGScore, fScore));
                    }
                }
            }
        }

        return null;
    }

    public static List<Integer> reconstructPath(Map<Integer, Integer> cameFrom, int current) {
        List<Integer> path = new ArrayList<>();
        path.add(current);

        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            path.add(0, current);
        }

        return path;
    }

    public static void printGraph(Map<Integer, List<int[]>> graph) {
        for (Map.Entry<Integer, List<int[]>> entry : graph.entrySet()) {
            int node = entry.getKey();
            List<int[]> neighbors = entry.getValue();

            System.out.print("Node " + node + " -> ");
            for (int[] neighbor : neighbors) {
                System.out.print("(" + neighbor[0] + ", " + neighbor[1] + ") ");
            }
            System.out.println();
        }
    }

    public static void Astar_test() {
        Map<Integer, List<int[]>> graph = new HashMap<>();
        graph.put(1, Arrays.asList(new int[]{2, 1}, new int[]{3, 3}));
        graph.put(2, Arrays.asList(new int[]{4, 5}, new int[]{5, 2}));
        graph.put(3, Arrays.asList(new int[]{6, 1}));
        graph.put(4, Collections.emptyList());
        graph.put(5, Arrays.asList(new int[]{6, 2}));
        graph.put(6, Collections.emptyList());

        Map<Integer, Double> heuristic = new HashMap<>();
        heuristic.put(1, 1.0);//3
        heuristic.put(2, 1.0);//2
        heuristic.put(3, 1.0);//4
        heuristic.put(4, 1.0);//5
        heuristic.put(5, 1.0);//1
        heuristic.put(6, 1.0);//0

        System.out.println("Graph:");
        printGraph(graph);

        int startNode = 1;
        int goalNode = 6;
        System.out.println("\nA* Path do vertice " + startNode + " para o vertice " + goalNode + ":");
        List<Integer> path = aStar(graph, startNode, goalNode, heuristic);
        if (path != null) {
            for (int node : path) {
                System.out.print(node + " ");
            }
            System.out.println();
        } else {
            System.out.println("No path found.");
        }
    }
}
