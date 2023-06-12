import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Square_Model implements ActionListener {
    int index;
    public JButton button;
    int original_x, original_y;
    int actual_x, actual_y;

    public Square_Model() {

    }
    public Square_Model(Square_Model s) {
        this.index = s.index;
        this.button = s.button;
        this.original_x = s.original_x;
        this.original_y = s.original_y;
        this.actual_x = s.actual_x;
        this.actual_y = s.actual_y;
    }


    public Square_Model(int index) {
        this.index = index;
        String image_path = "image" + Integer.toString(index + 1) + ".jpg";
        System.out.println(image_path);
        if (index != 8) {

            ImageIcon imageIcon = new ImageIcon(image_path);
            Image resizedImage = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            button = new JButton(Integer.toString(index), new ImageIcon(resizedImage));
        } else {
            button = new JButton(Integer.toString(index));
        }
        button.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {

        // Ação para o botão de randomize
        if (e.getSource() == button) {
            System.out.println("ACTUAL X: " + actual_x);
            System.out.println("ACTUAL Y: " + actual_y);
            System.out.println("ORIGINAL X: " + original_x);
            System.out.println("ORIGINAL Y: " + original_y);
        }

    }
}
