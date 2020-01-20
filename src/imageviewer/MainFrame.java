package imageviewer;

import imageviewer.control.Command;
import imageviewer.persistance.ImageLoader;
import imageviewer.ui.ImageDisplay;
import imageviewer.ui.swing.SwingImageDisplay;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainFrame extends JFrame{
    private ImageDisplay imageDisplay;
    private final ImageLoader imageLoader;
    private final Map<String, Command> commands = new HashMap<>();
    private final Map<String, String> labels = new HashMap<>();
    private final JLabel jlabel;

    public MainFrame(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
        this.jlabel = new JLabel();
        this.setTitle("ImageViewer");
        this.setSize(800,600);
        this.setMinimumSize(new Dimension(500,400));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        initializer();
        this.setVisible(true);
    }
    
    private void initializer(){
        initLabels();
        
        this.add(image(), BorderLayout.CENTER);
        this.add(button("prev"), BorderLayout.WEST);
        this.add(button("next"), BorderLayout.EAST);
        
        JPanel panel = new JPanel();
        jlabel.setText(imageLoader.getFileName());
        jlabel.setForeground(Color.white);
        panel.add(jlabel);
        panel.setBackground(Color.BLACK);
        this.add(panel, BorderLayout.SOUTH);
        
        JPanel upPanel = new JPanel();
        upPanel.add(new JLabel("\n"));
        upPanel.setBackground(Color.black);
        this.add(upPanel, BorderLayout.NORTH);
        
        this.setBackground(Color.BLACK);
    }
    
    public void add(Command command){
        commands.put(command.name(), command);
    }
    
    public ImageDisplay getImageDisplay() {
        return imageDisplay;
    }

    private Component image() {
        SwingImageDisplay display = new SwingImageDisplay();
        imageDisplay = display;
        return display;
    }
    
    private JPanel button(String name) {
        JButton button = new JButton(labels.get(name));
        button.addActionListener(execute(name));
        button.setBackground(Color.BLACK);
        button.setForeground(Color.white);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(button);
        panel.setBackground(Color.BLACK);
        
        return panel;
    }

    private ActionListener execute(String name) {
        return new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                commands.get(name).execute();
                jlabel.setText(imageLoader.getFileName());
            }
        };
    }
    
    private void initLabels() {
        labels.put("prev", "<");
        labels.put("next", ">");
    }
}
