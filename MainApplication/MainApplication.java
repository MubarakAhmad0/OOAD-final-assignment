package MainApplication;


import Components.MainApplicationWindow;

import java.awt.*;

public class MainApplication{
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            MainApplicationWindow frame = new MainApplicationWindow();
            frame.setVisible(true);
        });
    }
}
