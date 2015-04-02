package pl.jasnastronakodu.Repositories;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;


public class ImageListCellRenderer implements ListCellRenderer {

    public Component getListCellRendererComponent(JList jlist,
                                                  Object value,
                                                  int cellIndex,
                                                  boolean isSelected,
                                                  boolean cellHasFocus) {

            ImageIcon image = null;
            try {
                image = new ImageIcon(ImageIO.read(new URL("http://jasnastronakodu.pl/images/user.png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            JLabel imageLabel = new JLabel(value.toString(), image, JLabel.LEFT);
            JPanel panel = new JPanel(new BorderLayout());
            panel.add(imageLabel, BorderLayout.WEST);
            panel.setForeground(Color.white);
            panel.setBackground(isSelected ? Color.cyan : Color.white);
            return panel;
        }
    }
