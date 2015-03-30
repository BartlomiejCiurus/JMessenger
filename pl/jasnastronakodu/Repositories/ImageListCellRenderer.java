package pl.jasnastronakodu.Repositories;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

/**
 * @author Alvin Alexander
 * @author Bartlomiej Ciurus
 */
public class ImageListCellRenderer implements ListCellRenderer {

    public Component getListCellRendererComponent(JList jlist,
                                                  Object value,
                                                  int cellIndex,
                                                  boolean isSelected,
                                                  boolean cellHasFocus) {

        if (value instanceof JPanel) {
            Component component = (Component) value;
            component.setForeground(Color.white);
            component.setBackground(isSelected ? Color.blue : Color.white);
            return component;
        } else {
            ImageIcon image = null;
            try {
                image = new ImageIcon(ImageIO.read(new URL("http://code-side.byethost17.com/images/user.png")));
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
}
