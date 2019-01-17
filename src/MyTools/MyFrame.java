package MyTools;

import javax.swing.JFrame;

public class MyFrame extends JFrame {

    public MyFrame() {
        // Konstruktor mit String-Argument aufrufen
        this("Kein Titel");
    }

    public MyFrame(String title) {
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public MyFrame(String title, boolean exitOnClose) {
        this.setTitle(title);
        if (exitOnClose) {
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

    }

    public void show(int width, int height) {
        setVisible(true);
        pack();
        setSize(width, height);
        setLocationRelativeTo(null);
    }

}
