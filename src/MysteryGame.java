import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

//FIRST SCREEN
public class MysteryGame {
    private static JFrame frame;
    private static JPanel backgroundPanel;
    private static String playerName = "";
    private static String playerCharacter = "";
    private static final Color DARK_RED = new Color(139, 0, 0);
    private static final Font TITLE_FONT = new Font("Algerian", Font.BOLD, 48);
    private static final Font BUTTON_FONT = new Font("Algerian", Font.BOLD, 24);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        // Create the main frame
        frame = new JFrame("Relic Hunter: The Stolen Oracle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 640);
        frame.setResizable(false);

        try {
            // Load the background image from URL
            URL imageUrl = new URL("https://www.relyonhorror.com/wp-content/uploads/2018/12/uncanny-valley-screen-05-ps4-us-10jan17-1024x640.jpg");
            ImageIcon backgroundImage = new ImageIcon(imageUrl);

            // Create a panel with the background image
            backgroundPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            };
            backgroundPanel.setLayout(new GridBagLayout());

            // Create the title label
            JLabel titleLabel = new JLabel("Relic Hunter: The Stolen Oracle");
            titleLabel.setFont(TITLE_FONT);
            titleLabel.setForeground(DARK_RED);
            titleLabel.setHorizontalAlignment(JLabel.CENTER);

            // Create the start button
            JButton startButton = createStyledButton("Begin Story");
            startButton.addActionListener(e -> showCharacterSelectionScreen());

            // Add components to the panel with constraints
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.insets = new Insets(0, 0, 50, 0); // Bottom padding

            backgroundPanel.add(titleLabel, gbc);
            backgroundPanel.add(startButton);

            // Add the panel to the frame
            frame.add(backgroundPanel);

            // Center the frame on screen
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading background image", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setBackground(Color.BLACK);
        button.setForeground(DARK_RED);
        button.setBorder(BorderFactory.createLineBorder(DARK_RED, 2));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Add hover effects
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(30, 30, 30));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.BLACK);
            }
        });

        return button;
    }

    private static void showCharacterSelectionScreen() {
        try {
            // Remove the current content
            frame.getContentPane().removeAll();

            // Load the new background image
            URL newBgUrl = new URL("https://preview.redd.it/yz0731vlop981.png?auto=webp&s=461ed58358de70ee717c7c023769dfa051af262d");
            ImageIcon newBackground = new ImageIcon(newBgUrl);

            // Create panel with new background
            JPanel selectionPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(newBackground.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            };
            selectionPanel.setLayout(new GridBagLayout());

            // Create title for character selection
            JLabel selectLabel = new JLabel("Select Your Detective");
            selectLabel.setFont(TITLE_FONT);
            selectLabel.setForeground(DARK_RED);
            selectLabel.setHorizontalAlignment(JLabel.CENTER);

            // Create a container panel for the characters
            JPanel charactersPanel = new JPanel(new GridLayout(1, 2, 50, 0));
            charactersPanel.setOpaque(false);

            // Create male character panel
            JPanel malePanel = createCharacterPanel(
                    "C:\\Users\\15862\\Desktop\\Detective You (1).png",
                    "Detective Sir",
                    "Choose Detective Sir"
            );

            // Create female character panel
            JPanel femalePanel = createCharacterPanel(
                    "C:\\Users\\15862\\Desktop\\Detective Laura.png",
                    "Detective Ma'am",
                    "Choose Detective Ma'am"
            );

            // Add character panels to the container
            charactersPanel.add(malePanel);
            charactersPanel.add(femalePanel);

            // Add components to the main panel
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.insets = new Insets(0, 0, 50, 0);

            selectionPanel.add(selectLabel, gbc);
            selectionPanel.add(charactersPanel, gbc);

            // Update the frame
            frame.add(selectionPanel);
            frame.revalidate();
            frame.repaint();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading character selection screen", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static JPanel createCharacterPanel(String imagePath, String characterType, String buttonText) {
        ImageIcon icon = new ImageIcon(imagePath);
        JLabel imageLabel = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));

        JButton button = createStyledButton(buttonText);
        button.setFont(new Font("Algerian", Font.BOLD, 20));
        button.addActionListener(e -> {
            playerCharacter = characterType;
            showNameInputDialog();
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.add(imageLabel, BorderLayout.CENTER);
        panel.add(button, BorderLayout.SOUTH);

        return panel;
    }

    private static void showNameInputDialog() {
        // Create a custom panel for the input dialog
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.BLACK);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Create and style the label
        JLabel label = new JLabel("Enter your detective's name:");
        label.setFont(new Font("Algerian", Font.BOLD, 18));
        label.setForeground(DARK_RED);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label, BorderLayout.NORTH);

        // Create and style the text field
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Arial", Font.PLAIN, 16));
        textField.setBackground(Color.BLACK);
        textField.setForeground(Color.WHITE);
        textField.setCaretColor(Color.WHITE);
        textField.setBorder(BorderFactory.createLineBorder(DARK_RED, 2));
        panel.add(textField, BorderLayout.CENTER);

        // Show the dialog
        int result = JOptionPane.showOptionDialog(
                frame,
                panel,
                "Name Your Detective",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                new Object[]{"Confirm", "Cancel"},
                "Confirm"
        );

        // Process the result
        if (result == JOptionPane.OK_OPTION) {
            playerName = textField.getText().trim();
            if (!playerName.isEmpty()) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Welcome Detective " + playerName + "!\nYou have a crime to solve. Good Luck...",
                        "Character Selected",
                        JOptionPane.INFORMATION_MESSAGE
                );
                showCuratorIntroduction();
            } else {
                JOptionPane.showMessageDialog(
                        frame,
                        "A great detective needs a name!\nPlease enter your name.",
                        "Name Required",
                        JOptionPane.WARNING_MESSAGE
                );
                showNameInputDialog(); // Show the dialog again
            }
        }
    }

    private static void showCuratorIntroduction() {
        try {
            // Remove the current content
            frame.getContentPane().removeAll();

            // Load the new background image
            URL bgUrl = new URL("https://pixeljoint.com/files/icons/full/re2demake3.png");
            ImageIcon backgroundImage = new ImageIcon(bgUrl);

            // Create panel with new background
            JPanel curatorPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            };
            curatorPanel.setLayout(new BorderLayout());

            // Create a content panel with semi-transparent background
            JPanel contentPanel = new JPanel(new BorderLayout(0, -20));
            contentPanel.setOpaque(false);
            contentPanel.setBorder(BorderFactory.createEmptyBorder(300, 100, -500, 20));

            // Load and add curator image
            URL curatorUrl = new URL("file:///C:/Users/15862/Desktop/portrait%20(1).png");
            ImageIcon curatorIcon = new ImageIcon(curatorUrl);
            Image scaledCurator = curatorIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            JLabel curatorImage = new JLabel(new ImageIcon(scaledCurator));

            // Create panel for curator image and title
            JPanel leftPanel = new JPanel();
            leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
            leftPanel.setOpaque(false);
            leftPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

            // Add curator title above the image
            JLabel curatorTitle = new JLabel("<html><center>Museum Curator<br>The person who reported the theft</center></html>");
            curatorTitle.setFont(new Font("Algerian", Font.BOLD, 18));
            curatorTitle.setForeground(Color.WHITE);
            curatorTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
            leftPanel.add(curatorTitle);
            leftPanel.add(Box.createVerticalStrut(10));
            leftPanel.add(curatorImage);

            // Create dialogue panel
            JPanel dialoguePanel = new JPanel(new BorderLayout());
            dialoguePanel.setOpaque(false);
            dialoguePanel.setBorder(BorderFactory.createEmptyBorder(50, 0, -100, 0));

            // Create dialogue text
            JTextArea dialogueText = new JTextArea(
                    "\"Detective, you have to understand—this isn't just any artifact! " +
                            "The Oracle of Delphi fragment was one of our most prized possessions! " +
                            "And now—poof—gone! Just as the cameras decided to conveniently malfunction!\""
            );
            dialogueText.setFont(new Font("Press Start 2P", Font.PLAIN, 15));
            dialogueText.setForeground(Color.WHITE);
            dialogueText.setBackground(new Color(0, 0, 0, 150));
            dialogueText.setLineWrap(true);
            dialogueText.setWrapStyleWord(true);
            dialogueText.setEditable(false);
            dialogueText.setBorder(BorderFactory.createEmptyBorder(10, 20, 50, 0));

            // Create continue button and position it in top right
            JButton continueButton = createStyledButton("Continue Investigation");
            continueButton.addActionListener(e -> {
                showDetectiveResponse();  // This will transition to the detective's response
            });

            // Create a panel for the button with FlowLayout to right-align it
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.setOpaque(false);
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 10));
            buttonPanel.add(continueButton);

            // Add components to dialogue panel
            dialoguePanel.add(dialogueText, BorderLayout.CENTER);

            // Add components to content panel
            contentPanel.add(leftPanel, BorderLayout.WEST);
            contentPanel.add(dialoguePanel, BorderLayout.CENTER);

            // Add content panel and button panel to main panel
            curatorPanel.add(buttonPanel, BorderLayout.NORTH);
            curatorPanel.add(contentPanel, BorderLayout.CENTER);

            // Update the frame
            frame.add(curatorPanel);
            frame.revalidate();
            frame.repaint();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading curator introduction screen", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void showDetectiveResponse() {
        try {
            // Remove the current content
            frame.getContentPane().removeAll();

            // Load the same background image as before
            URL bgUrl = new URL("https://pixeljoint.com/files/icons/full/re2demake3.png");
            ImageIcon backgroundImage = new ImageIcon(bgUrl);

            // Create panel with background
            JPanel detectivePanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            };
            detectivePanel.setLayout(new BorderLayout());

            // Create a content panel with semi-transparent background
            JPanel contentPanel = new JPanel(new BorderLayout(0, -20));
            contentPanel.setOpaque(false);
            contentPanel.setBorder(BorderFactory.createEmptyBorder(300, 100, -500, 20));

            // Load and add detective image (based on chosen character)
            String detectiveImagePath = playerCharacter.equals("Detective Sir") ?
                    "C:\\Users\\15862\\Desktop\\Detective You (1).png" :
                    "C:\\Users\\15862\\Desktop\\Detective Laura.png";

            ImageIcon detectiveIcon = new ImageIcon(detectiveImagePath);
            Image scaledDetective = detectiveIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            JLabel detectiveImage = new JLabel(new ImageIcon(scaledDetective));

            // Create panel for detective image and title
            JPanel leftPanel = new JPanel();
            leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
            leftPanel.setOpaque(false);
            leftPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

            // Add detective title above the image
            JLabel detectiveTitle = new JLabel("<html><center>" + playerCharacter + "<br>" + playerName + "</center></html>");
            detectiveTitle.setFont(new Font("Algerian", Font.BOLD, 18));
            detectiveTitle.setForeground(Color.WHITE);
            detectiveTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
            leftPanel.add(detectiveTitle);
            leftPanel.add(Box.createVerticalStrut(10));
            leftPanel.add(detectiveImage);

            // Create dialogue panel
            JPanel dialoguePanel = new JPanel(new BorderLayout());
            dialoguePanel.setOpaque(false);
            dialoguePanel.setBorder(BorderFactory.createEmptyBorder(50, 0, -100, 0));

            // Create dialogue text
            JTextArea dialogueText = new JTextArea(
                    "\"I understand sir. The Oracle of Delphi fragment is indeed priceless. " +
                            "Can you tell me more about the security measures that were in place? " +
                            "And who had access to the artifact besides museum staff?\""
            );
            dialogueText.setFont(new Font("Press Start 2P", Font.PLAIN, 15));
            dialogueText.setForeground(Color.WHITE);
            dialogueText.setBackground(new Color(0, 0, 0, 150));
            dialogueText.setLineWrap(true);
            dialogueText.setWrapStyleWord(true);
            dialogueText.setEditable(false);
            dialogueText.setBorder(BorderFactory.createEmptyBorder(10, 20, 50, 0));

            // Create continue button
            JButton continueButton = createStyledButton("Continue");
            continueButton.addActionListener(e -> {
                // This would show the curator's response in the next scene
                showCuratorResponse();
            });

            // Create a panel for the button with FlowLayout to right-align it
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.setOpaque(false);
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 10));
            buttonPanel.add(continueButton);

            // Add components to dialogue panel
            dialoguePanel.add(dialogueText, BorderLayout.CENTER);

            // Add components to content panel
            contentPanel.add(leftPanel, BorderLayout.WEST);
            contentPanel.add(dialoguePanel, BorderLayout.CENTER);

            // Add content panel and button panel to main panel
            detectivePanel.add(buttonPanel, BorderLayout.NORTH);
            detectivePanel.add(contentPanel, BorderLayout.CENTER);

            // Update the frame
            frame.add(detectivePanel);
            frame.revalidate();
            frame.repaint();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading detective response screen", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void showCuratorResponse() {
        try {
            // Remove the current content
            frame.getContentPane().removeAll();

            // Load the background image
            URL bgUrl = new URL("https://pixeljoint.com/files/icons/full/re2demake3.png");
            ImageIcon backgroundImage = new ImageIcon(bgUrl);

            // Create panel with background
            JPanel responsePanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            };
            responsePanel.setLayout(new BorderLayout());

            // Create a content panel with semi-transparent background
            JPanel contentPanel = new JPanel(new BorderLayout(0, -20));
            contentPanel.setOpaque(false);
            contentPanel.setBorder(BorderFactory.createEmptyBorder(300, 100, -500, 20));

            // Load and add curator image
            URL curatorUrl = new URL("file:///C:/Users/15862/Desktop/portrait%20(1).png");
            ImageIcon curatorIcon = new ImageIcon(curatorUrl);
            Image scaledCurator = curatorIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            JLabel curatorImage = new JLabel(new ImageIcon(scaledCurator));

            // Create panel for curator image and title
            JPanel leftPanel = new JPanel();
            leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
            leftPanel.setOpaque(false);
            leftPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

            // Add curator title above the image
            JLabel curatorTitle = new JLabel("<html><center>Museum Curator<br>The person who reported the theft</center></html>");
            curatorTitle.setFont(new Font("Algerian", Font.BOLD, 18));
            curatorTitle.setForeground(Color.WHITE);
            curatorTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
            leftPanel.add(curatorTitle);
            leftPanel.add(Box.createVerticalStrut(10));
            leftPanel.add(curatorImage);

            // Create dialogue panel
            JPanel dialoguePanel = new JPanel(new BorderLayout());
            dialoguePanel.setOpaque(false);
            dialoguePanel.setBorder(BorderFactory.createEmptyBorder(50, 0, -100, 0));

            // Create dialogue text
            JTextArea dialogueText = new JTextArea(
                    "\"Of course, detective. We had state-of-the-art security - motion sensors, " +
                            "temperature controls, and a biometric lock. Only three people had access: " +
                            "myself, the head of security, and... well, the museum's major donor, " +
                            "Mr. Blackwood. But he would never... I mean, why would he?\""
            );
            dialogueText.setFont(new Font("Press Start 2P", Font.PLAIN, 15));
            dialogueText.setForeground(Color.WHITE);
            dialogueText.setBackground(new Color(0, 0, 0, 150));
            dialogueText.setLineWrap(true);
            dialogueText.setWrapStyleWord(true);
            dialogueText.setEditable(false);
            dialogueText.setBorder(BorderFactory.createEmptyBorder(10, 20, 50, 0));

            // Create decision buttons
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.setOpaque(false);
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 10));

            JButton option1Button = createStyledButton("Investigate Blackwood");
            option1Button.addActionListener(e -> {
                // This would lead to investigating Blackwood
                JOptionPane.showMessageDialog(frame,
                        "You decide to investigate Mr. Blackwood, the mysterious donor...",
                        "Investigation Path",
                        JOptionPane.INFORMATION_MESSAGE);
            });

            JButton option2Button = createStyledButton("Examine Security System");
            option2Button.addActionListener(e -> {
                // This would lead to examining the security system
                JOptionPane.showMessageDialog(frame,
                        "You decide to examine the security system for weaknesses...",
                        "Investigation Path",
                        JOptionPane.INFORMATION_MESSAGE);
            });

            buttonPanel.add(option1Button);
            buttonPanel.add(option2Button);

            // Add components to dialogue panel
            dialoguePanel.add(dialogueText, BorderLayout.CENTER);

            // Add components to content panel
            contentPanel.add(leftPanel, BorderLayout.WEST);
            contentPanel.add(dialoguePanel, BorderLayout.CENTER);

            // Add content panel and button panel to main panel
            responsePanel.add(buttonPanel, BorderLayout.NORTH);
            responsePanel.add(contentPanel, BorderLayout.CENTER);

            // Update the frame
            frame.add(responsePanel);
            frame.revalidate();
            frame.repaint();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading curator response screen", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}