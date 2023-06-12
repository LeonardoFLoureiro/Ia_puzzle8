
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class Screen_Home extends JFrame implements ActionListener {
    Square_Model squares[];
    JButton randomize_button;
    JButton AStarButton;
    JPanel gridPanel;
    JPanel ButtomPanel;
    double last_heuristic;

    public Screen_Home() {
        squares = new Square_Model[9];

        // Criar uma janela
        this.setTitle("Exemplo de Interface Gráfica");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 200);
        // Centralizar a janela na tela
        this.setLocationRelativeTo(null);

        // --------------------------------------------------------------------------
        // Definir um Layout

        GridLayout layout = new GridLayout(2, 1);
        gridPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        ButtomPanel = new JPanel(new GridLayout(1, 1, 10, 10));
        ButtomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Criar um botão para randomizar os botões
        randomize_button = new JButton("RANDOMIZAR");
        randomize_button.addActionListener(this);
        // Criar um botão para rodar a ia
        AStarButton = new JButton("A Estrela");
        AStarButton.addActionListener(this);

        // Criar e adicionar os Botões ao painel da grade

        for (int i = 0; i < squares.length; i++) {

            squares[i] = new Square_Model(i);
            gridPanel.add(squares[i].button);

        }

        // Adicionar o botao na janela
        ButtomPanel.add(randomize_button);
        ButtomPanel.add(AStarButton);

        // Adiciona os Paineis a tela
        this.getContentPane().setLayout(layout);
        this.getContentPane().add(ButtomPanel);
        this.getContentPane().add(gridPanel);

        // Ajustar o tamanho da janela de acordo com o conteúdo
        this.pack();

        // Salvar a posição original no array de pos
        save_array_pos();
        // Exibir a janela
        this.setVisible(true);
    }

    public void save_array_pos() { // Funcao que salva no array de pos a pos dos botoes
        for (int i = 0; i < squares.length; i++) {

            Point pos = squares[i].button.getLocation();
            squares[i].original_x = pos.x;
            squares[i].original_y = pos.y;
            squares[i].actual_x = pos.x;
            squares[i].actual_y = pos.y;

        }
    }

    public void randomize_grid() {

        // Passa a matriz para um Arraylist
        ArrayList<Square_Model> values = new ArrayList<>();
        for (int i = 0; i < squares.length; i++) {

            values.add(squares[i]);
            gridPanel.remove(0);

        }
        // Randomiza o Arraylist
        Collections.shuffle(values);

        // Readiciona os itens no Painel
        for (int i = 0; i < squares.length; i++) {

            squares[i] = values.remove(0);
            gridPanel.add(squares[i].button);

        }

        // Recarrega a tela
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        // Ação para o botão de randomize
        if (e.getSource() == randomize_button) {
            randomize_grid();// Chama a funcao de randomizar o grid

            // Salva a posição atual dos Squares
            update_actual_pos();
        }
        if (e.getSource() == AStarButton) {

            // switch_squares(0,8);
            // update_actual_pos();

            AStar_Manager as = new AStar_Manager(squares,last_heuristic);
            last_heuristic = as.last_heuristic;
            Square_Model new_squares[] = new Square_Model[9];
            
            for (int i = 0; i < squares.length; i++) {

                squares[i].index = as.result[i].index;
                squares[i].original_x = as.result[i].original_x;
                squares[i].original_y = as.result[i].original_y;
                squares[i].actual_x = as.result[i].actual_x;
                squares[i].actual_y = as.result[i].actual_y;
                int nova_pos = 0;

                if (squares[i].actual_x == 10) {

                } else if (squares[i].actual_x == 164) {
                    nova_pos++;
                } else if (squares[i].actual_x == 318) {
                    nova_pos += 2;
                }

                if (squares[i].actual_y == 10) {

                } else if (squares[i].actual_y == 130) {
                    nova_pos += 3;
                } else if (squares[i].actual_y == 250) {
                    nova_pos += 6;
                }
                new_squares[nova_pos] = squares[i];

                // gridPanel.remove(nova_pos);

                // squares[i] = new Square_Model(as.result[i]);
                // squares[i].button.setLocation(as.result[i].actual_x, as.result[i].actual_y);

            }
            gridPanel.remove(0);
            gridPanel.remove(0);
            gridPanel.remove(0);
            gridPanel.remove(0);
            gridPanel.remove(0);
            gridPanel.remove(0);
            gridPanel.remove(0);
            gridPanel.remove(0);
            gridPanel.remove(0);
            for(int i = 0;i<9;i++){
                squares[i] = new Square_Model(new_squares[i]);
                //squares[i] = new_squares[i];
                gridPanel.add(squares[i].button);
            }
            this.revalidate();
            this.repaint();
            update_actual_pos();

        }
    }

    public void switch_squares(int square1, int square2) {
        if (square1 > square2) {
            int aux = square1;
            square1 = square2;
            square2 = aux;
        }
        Component component1 = gridPanel.getComponent(square1);
        Component component2 = gridPanel.getComponent(square2);

        gridPanel.remove(component1);
        gridPanel.remove(component2);

        gridPanel.add(component2, square1);
        gridPanel.add(component1, square2);

        this.revalidate();
        this.repaint();

    }

    public void update_actual_pos() {
        for (int i = 0; i < squares.length; i++) {
            Point pos = squares[i].button.getLocation();
            squares[i].actual_x = pos.x;
            squares[i].actual_y = pos.y;
        }
    }

}
