import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ImageEditorApp extends JFrame {

    private BufferedImage originalImage;
    private JLabel imageLabel;

    public ImageEditorApp() {
        super("Basic Java Image Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        imageLabel = new JLabel("Load an image using the File menu.");
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        JScrollPane scrollPane = new JScrollPane(imageLabel);
        add(scrollPane, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        JMenuItem openItem = new JMenuItem("Open Image...");
        openItem.addActionListener(new OpenImageListener());
        fileMenu.add(openItem);
        menuBar.add(fileMenu);

        JMenu filterMenu = new JMenu("Filter");
        JMenuItem grayscaleItem = new JMenuItem("Grayscale");
        grayscaleItem.addActionListener(new GrayscaleFilterListener());
        filterMenu.add(grayscaleItem);
        
        JMenuItem negativeItem = new JMenuItem("Negative");
        negativeItem.addActionListener(new NegativeFilterListener());
        filterMenu.add(negativeItem);
        
        menuBar.add(filterMenu);

        setVisible(true);
    }

    private void displayImage(BufferedImage image) {
        ImageIcon icon = new ImageIcon(image);
        imageLabel.setIcon(icon);
        imageLabel.setText(null);
        pack();
        setSize(800, 600);
    }
    
    class OpenImageListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            int result = fileChooser.showOpenDialog(ImageEditorApp.this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    originalImage = ImageIO.read(selectedFile);
                    if (originalImage != null) {
                        displayImage(originalImage);
                    } else {
                        JOptionPane.showMessageDialog(ImageEditorApp.this, "Could not read image file.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(ImageEditorApp.this, "An error occurred while loading the image.", "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        }
    }

    class GrayscaleFilterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (originalImage == null) {
                JOptionPane.showMessageDialog(ImageEditorApp.this, "Please open an image first.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                BufferedImage processedImage = ImageProcessor.toGrayscale(originalImage);
                displayImage(processedImage);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(ImageEditorApp.this, "Error applying filter.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    class NegativeFilterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (originalImage == null) {
                JOptionPane.showMessageDialog(ImageEditorApp.this, "Please open an image first.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                BufferedImage processedImage = ImageProcessor.toNegative(originalImage);
                displayImage(processedImage);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(ImageEditorApp.this, "Error applying filter.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ImageEditorApp());
    }
}
