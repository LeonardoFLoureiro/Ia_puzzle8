import java.util.*;

public class AStar_Manager {
    Square_Model original_squares[];
    double last_heuristic;
    List<Integer> pos_to_switch;
    List<Square_Model[]> decisions;
    Square_Model result[];
    public AStar_Manager(Square_Model sq[],double last_heuristic) {
this.last_heuristic = last_heuristic;
        // 0,2,6,8 == 2
        // 1,3,5,7 == 3
        // 4 == 4
        original_squares = sq;
        int pos_vazia = 8;
        pos_to_switch = new ArrayList<Integer>();
        decisions = new ArrayList<Square_Model[]>();

        // Descobrir qual a posição vazia
        for (int i = 0; i < sq.length; i++) {
            if (sq[i].index == 8) {
                pos_vazia = i;
            }
        }

        // Descobrir quais posições podem ser trocadas
        if (pos_vazia > 2) {

            add_decision(pos_vazia, (pos_vazia - 3));

        }
        if (pos_vazia != 2 && pos_vazia != 5 && pos_vazia != 8) {
            add_decision(pos_vazia, (pos_vazia + 1));

        }
        if (pos_vazia != 0 && pos_vazia != 3 && pos_vazia != 6) {
            add_decision(pos_vazia, (pos_vazia - 1));

        }
        if (pos_vazia < 6) {
            add_decision(pos_vazia, (pos_vazia + 3));

        }

        search_AStar();
        
    }
    public void search_AStar2() {
        Map<Integer, List<int[]>> graph = new HashMap<>();
        graph.put(0, Arrays.asList(new int[]{1, 1}, new int[]{2, 1}, new int[]{3, 1}));
        graph.put(1, Arrays.asList(new int[]{6, 1}));
        graph.put(2, Arrays.asList(new int[]{6, 1}));
        graph.put(3, Arrays.asList(new int[]{6, 1}));
        graph.put(6, Collections.emptyList());
        

        Map<Integer, Double> heuristic = new HashMap<>();
        heuristic.put(0, calculate_heuristic_decision(original_squares));//3
        heuristic.put(1, calculate_heuristic_decision(decisions.get(0)));//4
        heuristic.put(2, calculate_heuristic_decision(decisions.get(1)));//2
        heuristic.put(3, calculate_heuristic_decision(decisions.get(2)));//4
        heuristic.put(6, 0.0);//4
        
        
        int startNode = 0;
        int goalNode = 6;
        Busca_AStar busca = new Busca_AStar();
        List<Integer> path = busca.aStar(graph, startNode, goalNode, heuristic);
        result = decisions.get(path.get(1)-1);
    }
    public void search_AStar() {
        
        Map<Integer, List<int[]>> graph = new HashMap<>();
        Map<Integer, Double> heuristic = new HashMap<>();

        List<int[]> nodes = new ArrayList<int[]>();

        heuristic.put(0,calculate_heuristic_decision(original_squares));
        for (int i = 0; i < decisions.size(); i++) {

            nodes.add(new int[] { i + 1, 1 });
            graph.put(i + 1, Arrays.asList(new int[]{decisions.size()+1, 1}));
            
            if(calculate_heuristic_decision(decisions.get(i)) == last_heuristic){
                heuristic.put(i+1,999999999.9999 );
            }else{
                heuristic.put(i+1,calculate_heuristic_decision(decisions.get(i)) );
            }
            //System.out.println(calculate_heuristic_decision(decisions.get(i)));

        }
        heuristic.put(decisions.size()+1, 0.0);//4
        graph.put(decisions.size()+1, Collections.emptyList());
        graph.put(0, nodes);
        Busca_AStar busca = new Busca_AStar();
        
        List<Integer> path = busca.aStar(graph, 0, decisions.size()+1, heuristic);
        
        System.out.println("HEURISTICAS");
        for(int i = 0;i< heuristic.size();i++){
            System.out.println(heuristic.get(i));
        }
        result = decisions.get(path.get(1)-1);
        last_heuristic = calculate_heuristic_decision(decisions.get(path.get(1)));

    }

    public double calculate_heuristic_decision(Square_Model sq[]) {
        double heuristic_value = 0;

        for (int i = 0; i < sq.length; i++) {
            heuristic_value += calculate_heuristic(sq[i]);
        }

        return heuristic_value;

    }

    public double calculate_heuristic(Square_Model sqr) {// Distancia Euclidiana

        //return Math.sqrt(Math.pow(sqr.original_x - sqr.actual_x, 2) + Math.pow(sqr.original_y - sqr.actual_y, 2));
        return Math.abs(sqr.original_x - sqr.actual_x) + Math.abs(sqr.original_y - sqr.actual_y);
    }

    public void add_decision(int pos_vazia, int change_pos) {

        Square_Model temp[] = new Square_Model[original_squares.length];
        for (int i = 0; i < original_squares.length; i++) {
            temp[i] = new Square_Model(original_squares[i]);

        }

        int tempx = temp[change_pos].actual_x;
        int tempy = temp[change_pos].actual_y;
        temp[change_pos].actual_x = temp[pos_vazia].actual_x;
        temp[change_pos].actual_y = temp[pos_vazia].actual_y;
        temp[pos_vazia].actual_x = tempx;
        temp[pos_vazia].actual_y = tempy;

        decisions.add(temp);
    }
}
