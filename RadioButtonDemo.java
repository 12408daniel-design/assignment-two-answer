import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;

/**
 * A small Swing demonstration of mutually exclusive pet radio buttons.
 *
 * Place optional images in an "images" directory next to this source file /
 * application, using the names bird.jpg, cat.jpg, dog.jpg, rabbit.jpg, pig.jpg.
 * The panel displays a friendly text fallback if an image is unavailable.
 */
public class RadioButtonDemo extends JFrame {
    private static final String[] PETS = {"Bird", "Cat", "Dog", "Rabbit", "Pig"};

    private final PetImagePanel imagePanel = new PetImagePanel();

    public RadioButtonDemo() {
        super("RadioButtonDemo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(520, 340));
        setSize(700, 430);
        setLocationByPlatform(true);

        add(createPetSelector(), BorderLayout.WEST);
        add(imagePanel, BorderLayout.CENTER);

        // Pig is the required initial selection. Showing it does not trigger a
        // selection popup; popups appear when the user changes the selection.
        showPetImage("Pig");
    }

    /** Creates and groups the five mutually exclusive radio buttons. */
    private JPanel createPetSelector() {
        JPanel selector = new JPanel();
        selector.setLayout(new javax.swing.BoxLayout(selector, javax.swing.BoxLayout.Y_AXIS));
        selector.setBorder(BorderFactory.createEmptyBorder(20, 18, 20, 18));

        JLabel heading = new JLabel("Choose a pet");
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 16f));
        heading.setAlignmentX(LEFT_ALIGNMENT);
        selector.add(heading);
        selector.add(javax.swing.Box.createVerticalStrut(12));

        ButtonGroup group = new ButtonGroup();
        ActionListener selectionListener = event -> {
            JRadioButton selectedButton = (JRadioButton) event.getSource();
            String pet = selectedButton.getText();
            showPetImage(pet);
            JOptionPane.showMessageDialog(
                    this,
                    "You selected: " + pet,
                    "Pet selected",
                    JOptionPane.INFORMATION_MESSAGE);
        };

        for (String pet : PETS) {
            JRadioButton button = new JRadioButton(pet);
            button.setActionCommand(pet);
            button.setAlignmentX(LEFT_ALIGNMENT);
            button.addActionListener(selectionListener);
            group.add(button);
            selector.add(button);
            selector.add(javax.swing.Box.createVerticalStrut(5));

            if ("Pig".equals(pet)) {
                button.setSelected(true);
            }
        }
        return selector;
    }

    /** Loads a pet's image, or prepares a visible fallback when it is missing. */
    private void showPetImage(String pet) {
        String imageName = pet.toLowerCase() + ".jpg";
        BufferedImage image = loadImage(imageName);
        imagePanel.setImage(image, imageName);
    }

    /**
     * Looks first for an image file under ./images and then for a classpath
     * resource at /images. Image loading failures are treated as missing images.
     */
    private BufferedImage loadImage(String imageName) {
        Path imagePath = Paths.get("images", imageName);
        if (Files.isRegularFile(imagePath)) {
            try {
                return ImageIO.read(imagePath.toFile());
            } catch (IOException ignored) {
                // The fallback message below also covers unreadable/corrupt files.
            }
        }

        try (InputStream resource = RadioButtonDemo.class.getResourceAsStream("/images/" + imageName)) {
            if (resource != null) {
                return ImageIO.read(resource);
            }
        } catch (IOException ignored) {
            // Keep the application usable when an optional image cannot be read.
        }
        return null;
    }

    /** Image area that scales a loaded image while preserving its aspect ratio. */
    private static final class PetImagePanel extends JPanel {
        private BufferedImage image;
        private String missingImageName = "";

        PetImagePanel() {
            setBackground(new Color(245, 247, 250));
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createEmptyBorder(14, 8, 14, 14),
                    BorderFactory.createLineBorder(new Color(210, 216, 224))));
            setPreferredSize(new Dimension(420, 340));
            setToolTipText("Selected pet image");
        }

        void setImage(BufferedImage image, String imageName) {
            this.image = image;
            this.missingImageName = imageName;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            Graphics2D g2 = (Graphics2D) graphics.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                if (image == null) {
                    String message = "Image not found: " + missingImageName;
                    g2.setColor(new Color(75, 85, 99));
                    g2.setFont(getFont().deriveFont(Font.PLAIN, 16f));
                    int textWidth = g2.getFontMetrics().stringWidth(message);
                    g2.drawString(message, Math.max(12, (getWidth() - textWidth) / 2), getHeight() / 2);
                    return;
                }

                int availableWidth = Math.max(1, getWidth() - 28);
                int availableHeight = Math.max(1, getHeight() - 28);
                double scale = Math.min(
                        (double) availableWidth / image.getWidth(),
                        (double) availableHeight / image.getHeight());
                int drawWidth = Math.max(1, (int) Math.round(image.getWidth() * scale));
                int drawHeight = Math.max(1, (int) Math.round(image.getHeight() * scale));
                int x = (getWidth() - drawWidth) / 2;
                int y = (getHeight() - drawHeight) / 2;
                g2.drawImage(image, x, y, drawWidth, drawHeight, this);
            } finally {
                g2.dispose();
            }
        }
    }

    /** Starts Swing on the event-dispatch thread. */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RadioButtonDemo().setVisible(true));
    }
}
