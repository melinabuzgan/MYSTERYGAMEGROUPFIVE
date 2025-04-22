import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.awt.image.BufferedImage;

public class MysteryGame {
    // Main application window
    private static JFrame frame;

    // Background panel that holds other components
    private static JPanel backgroundPanel;

    // Player information
    private static String playerName = "";
    private static String playerCharacter = "";

    // Custom colors and fonts
    private static final Color DARK_RED = new Color(139, 0, 0);
    private static final Font TITLE_FONT = new Font("Algerian", Font.BOLD, 48);
    private static final Font BUTTON_FONT = new Font("Algerian", Font.BOLD, 25);

    public static void main(String[] args) {
        // Use SwingUtilities to ensure GUI creation happens on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        // Set up main window
        frame = new JFrame("Relic Hunter: The Stolen Oracle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 640);
        frame.setResizable(false);

        try {
            // Load background image from URL
            URL imageUrl = new URL("https://www.relyonhorror.com/wp-content/uploads/2018/12/uncanny-valley-screen-05-ps4-us-10jan17-1024x640.jpg");
            ImageIcon backgroundImage = new ImageIcon(imageUrl);

            // Create panel with background image
            backgroundPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            };
            backgroundPanel.setLayout(new GridBagLayout());

            // Create title label
            JLabel titleLabel = new JLabel("Relic Hunter: The Stolen Oracle");
            titleLabel.setFont(TITLE_FONT);
            titleLabel.setForeground(DARK_RED);
            titleLabel.setHorizontalAlignment(JLabel.CENTER);

            // Create start button
            JButton startButton = createStyledButton("Begin Story");
            startButton.addActionListener(e -> showCharacterSelectionScreen());

            // Layout constraints
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.insets = new Insets(0, 0, 50, 0);

            // Add components to panel
            backgroundPanel.add(titleLabel, gbc);
            backgroundPanel.add(startButton, gbc);

            // Finalize frame
            frame.add(backgroundPanel);
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

        // Hover effects
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

    private static void showFinalDecisionScreen() {
        // Remove the try block since we're not actually doing anything that throws exceptions
        // Clear the current screen
        frame.getContentPane().removeAll();

        // Create main panel with background
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Add title
        JLabel titleLabel = new JLabel("FINAL DECISION");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(DARK_RED);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Create scrollable clues panel
        JPanel cluesPanel = new JPanel();
        cluesPanel.setLayout(new BoxLayout(cluesPanel, BoxLayout.Y_AXIS));
        cluesPanel.setBackground(new Color(30, 30, 30));

        // Add all clues
        String[] allClues = {
                "The cameras malfunctioned during the theft",
                "Mr. Blackwood had special access privileges",
                "Jenkins' coffee alibi doesn't hold up",
                "Blackwood suddenly started using his access last week",
                "Blackwood is obsessed with the Oracle's power",
                "Blackwood admitted to bribing Jenkins"
        };

        for (String clue : allClues) {
            JTextArea clueText = new JTextArea("• " + clue);
            clueText.setFont(new Font("Press Start 2P", Font.PLAIN, 14));
            clueText.setForeground(Color.WHITE);
            clueText.setBackground(new Color(50, 50, 50));
            clueText.setLineWrap(true);
            clueText.setWrapStyleWord(true);
            clueText.setEditable(false);
            clueText.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

            cluesPanel.add(clueText);
            cluesPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        JScrollPane scrollPane = new JScrollPane(cluesPanel);
        scrollPane.setBorder(BorderFactory.createLineBorder(DARK_RED, 2));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Add suspect selection
        JLabel questionLabel = new JLabel("WHO STOLE THE ORACLE?");
        questionLabel.setFont(new Font("Algerian", Font.BOLD, 28));
        questionLabel.setForeground(DARK_RED);
        questionLabel.setHorizontalAlignment(JLabel.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 10, 50));
        buttonPanel.setOpaque(false);

        // Create suspect buttons
        JButton curatorBtn = createStyledButton("The Curator");
        curatorBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame,
                    "Wrong! He was just negligent.\nThe thief gets away!",
                    "Case Closed",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        });

        JButton jenkinsBtn = createStyledButton("Security Jenkins");
        jenkinsBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame,
                    "Partial truth - she was bribed,\nbut Blackwood escapes!",
                    "Case Closed",
                    JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        });

        JButton blackwoodBtn = createStyledButton("Mr. Blackwood");
        blackwoodBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame,
                    "CORRECT! He wanted the Oracle's power for himself.\nCONGRATULATIONS DETECTIVE!",
                    "Case Solved",
                    JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        });

        buttonPanel.add(curatorBtn);
        buttonPanel.add(jenkinsBtn);
        buttonPanel.add(blackwoodBtn);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.add(questionLabel, BorderLayout.NORTH);
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Show the screen
        frame.add(mainPanel);
        frame.revalidate();
        frame.repaint();
    }

    private static void showCharacterSelectionScreen() {
        try {
            frame.getContentPane().removeAll();
            URL newBgUrl = new URL("https://preview.redd.it/yz0731vlop981.png?auto=webp&s=461ed58358de70ee717c7c023769dfa051af262d");
            ImageIcon newBackground = new ImageIcon(newBgUrl);

            JPanel selectionPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(newBackground.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            };
            selectionPanel.setLayout(new GridBagLayout());

            // Title label
            JLabel selectLabel = new JLabel("Select Your Detective");
            selectLabel.setFont(TITLE_FONT);
            selectLabel.setForeground(DARK_RED);
            selectLabel.setHorizontalAlignment(JLabel.CENTER);

            // Character selection panel
            JPanel charactersPanel = new JPanel(new GridLayout(1, 2, 50, 0));
            charactersPanel.setOpaque(false);

            // Male character panel
            JPanel malePanel = createCharacterPanel(
                    "/images/Detective You (1).png",
                    "Detective Sir",
                    "Choose Detective Sir"
            );

            // Female character panel
            JPanel femalePanel = createCharacterPanel(
                    "/images/Detective You.png",
                    "Detective Ma'am",
                    "Choose Detective Ma'am"
            );

            charactersPanel.add(malePanel);
            charactersPanel.add(femalePanel);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.insets = new Insets(0, 0, 50, 0);

            selectionPanel.add(selectLabel, gbc);
            selectionPanel.add(charactersPanel, gbc);

            frame.add(selectionPanel);
            frame.revalidate();
            frame.repaint();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading character selection screen", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static JPanel createCharacterPanel(String resourcePath, String characterType, String buttonText) {
        System.out.println("Attempting to load: " + resourcePath);
        ImageIcon icon = null;
        try {
            // Try loading from resources first
            URL imageUrl = MysteryGame.class.getResource(resourcePath);
            System.out.println("Resource URL: " + imageUrl);

            if (imageUrl == null) {
                System.out.println("Trying absolute path for: " + resourcePath);
                icon = new ImageIcon(resourcePath);
            } else {
                icon = new ImageIcon(imageUrl);
            }

            // Fallback if image still not found
            if (icon.getImage() == null) {
                System.err.println("Failed to load image: " + resourcePath);
                BufferedImage placeholder = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = placeholder.createGraphics();
                g2d.setColor(Color.RED);
                g2d.fillRect(0, 0, 200, 200);
                g2d.dispose();
                icon = new ImageIcon(placeholder);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create image label
        JLabel imageLabel = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));

        // Create selection button
        JButton button = createStyledButton(buttonText);
        button.setFont(new Font("Algerian", Font.BOLD, 20));
        button.addActionListener(e -> {
            playerCharacter = characterType;
            showNameInputDialog();
        });

        // Assemble panel
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.add(imageLabel, BorderLayout.CENTER);
        panel.add(button, BorderLayout.SOUTH);

        return panel;
    }

    private static void showNameInputDialog() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.BLACK);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Instruction label
        JLabel label = new JLabel("Enter your detective's name:");
        label.setFont(new Font("Algerian", Font.BOLD, 18));
        label.setForeground(DARK_RED);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label, BorderLayout.NORTH);

        // Name input field
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Arial", Font.PLAIN, 16));
        textField.setBackground(Color.BLACK);
        textField.setForeground(Color.WHITE);
        textField.setCaretColor(Color.WHITE);
        textField.setBorder(BorderFactory.createLineBorder(DARK_RED, 2));
        panel.add(textField, BorderLayout.CENTER);

        // Show dialog
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

        // Handle result
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
                showNameInputDialog();
            }
        }
    }

    private static void showCuratorIntroduction() {
        try {
            frame.getContentPane().removeAll();
            URL bgUrl = new URL("https://pixeljoint.com/files/icons/full/re2demake3.png");
            ImageIcon backgroundImage = new ImageIcon(bgUrl);

            JPanel curatorPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            };
            curatorPanel.setLayout(new BorderLayout());

            JPanel contentPanel = new JPanel(new BorderLayout(0, -20));
            contentPanel.setOpaque(false);
            contentPanel.setBorder(BorderFactory.createEmptyBorder(300, 100, -500, 20));

            // Load curator image with fallback
            ImageIcon curatorIcon;
            try {
                URL curatorUrl = MysteryGame.class.getResource("/images/portrait (1).png");
                if (curatorUrl != null) {
                    curatorIcon = new ImageIcon(curatorUrl);
                } else {
                    BufferedImage placeholder = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2d = placeholder.createGraphics();
                    g2d.setColor(Color.RED);
                    g2d.fillRect(0, 0, 200, 200);
                    g2d.dispose();
                    curatorIcon = new ImageIcon(placeholder);
                }
            } catch (Exception e) {
                BufferedImage placeholder = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = placeholder.createGraphics();
                g2d.setColor(Color.RED);
                g2d.fillRect(0, 0, 200, 200);
                g2d.dispose();
                curatorIcon = new ImageIcon(placeholder);
            }

            Image scaledCurator = curatorIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            JLabel curatorImage = new JLabel(new ImageIcon(scaledCurator));

            JPanel leftPanel = new JPanel();
            leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
            leftPanel.setOpaque(false);
            leftPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

            JLabel curatorTitle = new JLabel("<html><center>Museum Curator<br>The person who reported the theft</center></html>");
            curatorTitle.setFont(new Font("Algerian", Font.BOLD, 18));
            curatorTitle.setForeground(Color.WHITE);
            curatorTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
            leftPanel.add(curatorTitle);
            leftPanel.add(Box.createVerticalStrut(10));
            leftPanel.add(curatorImage);

            JPanel dialoguePanel = new JPanel(new BorderLayout());
            dialoguePanel.setOpaque(false);
            dialoguePanel.setBorder(BorderFactory.createEmptyBorder(50, 0, -100, 0));

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

            JButton continueButton = createStyledButton("Continue Investigation");
            continueButton.addActionListener(e -> showDetectiveResponse());

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.setOpaque(false);
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 10));
            buttonPanel.add(continueButton);

            dialoguePanel.add(dialogueText, BorderLayout.CENTER);
            contentPanel.add(leftPanel, BorderLayout.WEST);
            contentPanel.add(dialoguePanel, BorderLayout.CENTER);
            curatorPanel.add(buttonPanel, BorderLayout.NORTH);
            curatorPanel.add(contentPanel, BorderLayout.CENTER);

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
            frame.getContentPane().removeAll();
            URL bgUrl = new URL("https://pixeljoint.com/files/icons/full/re2demake3.png");
            ImageIcon backgroundImage = new ImageIcon(bgUrl);

            JPanel detectivePanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            };
            detectivePanel.setLayout(new BorderLayout());

            JPanel contentPanel = new JPanel(new BorderLayout(0, -20));
            contentPanel.setOpaque(false);
            contentPanel.setBorder(BorderFactory.createEmptyBorder(300, 100, -500, 20));

            // Load detective image based on selected character
            String detectiveImagePath = playerCharacter.equals("Detective Sir") ?
                    "/images/Detective You (1).png" :
                    "/images/Detective You.png";

            ImageIcon detectiveIcon;
            try {
                URL detectiveUrl = MysteryGame.class.getResource(detectiveImagePath);
                if (detectiveUrl != null) {
                    detectiveIcon = new ImageIcon(detectiveUrl);
                } else {
                    BufferedImage placeholder = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2d = placeholder.createGraphics();
                    g2d.setColor(Color.BLUE);
                    g2d.fillRect(0, 0, 200, 200);
                    g2d.dispose();
                    detectiveIcon = new ImageIcon(placeholder);
                }
            } catch (Exception e) {
                BufferedImage placeholder = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = placeholder.createGraphics();
                g2d.setColor(Color.BLUE);
                g2d.fillRect(0, 0, 200, 200);
                g2d.dispose();
                detectiveIcon = new ImageIcon(placeholder);
            }

            Image scaledDetective = detectiveIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            JLabel detectiveImage = new JLabel(new ImageIcon(scaledDetective));

            JPanel leftPanel = new JPanel();
            leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
            leftPanel.setOpaque(false);
            leftPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

            JLabel detectiveTitle = new JLabel("<html><center>" + playerCharacter + "<br>" + playerName + "</center></html>");
            detectiveTitle.setFont(new Font("Algerian", Font.BOLD, 18));
            detectiveTitle.setForeground(Color.WHITE);
            detectiveTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
            leftPanel.add(detectiveTitle);
            leftPanel.add(Box.createVerticalStrut(10));
            leftPanel.add(detectiveImage);

            JPanel dialoguePanel = new JPanel(new BorderLayout());
            dialoguePanel.setOpaque(false);
            dialoguePanel.setBorder(BorderFactory.createEmptyBorder(50, 0, -100, 0));

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

            JButton continueButton = createStyledButton("Continue");
            continueButton.addActionListener(e -> showCuratorResponse());

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.setOpaque(false);
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 10));
            buttonPanel.add(continueButton);

            dialoguePanel.add(dialogueText, BorderLayout.CENTER);
            contentPanel.add(leftPanel, BorderLayout.WEST);
            contentPanel.add(dialoguePanel, BorderLayout.CENTER);
            detectivePanel.add(buttonPanel, BorderLayout.NORTH);
            detectivePanel.add(contentPanel, BorderLayout.CENTER);

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
            frame.getContentPane().removeAll();
            URL bgUrl = new URL("https://pixeljoint.com/files/icons/full/re2demake3.png");
            ImageIcon backgroundImage = new ImageIcon(bgUrl);

            JPanel responsePanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            };
            responsePanel.setLayout(new BorderLayout());

            JPanel contentPanel = new JPanel(new BorderLayout(0, -20));
            contentPanel.setOpaque(false);
            contentPanel.setBorder(BorderFactory.createEmptyBorder(300, 100, -500, 20));

            // Load curator image
            ImageIcon curatorIcon;
            try {
                URL curatorUrl = MysteryGame.class.getResource("/images/portrait (1).png");
                if (curatorUrl != null) {
                    curatorIcon = new ImageIcon(curatorUrl);
                } else {
                    BufferedImage placeholder = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2d = placeholder.createGraphics();
                    g2d.setColor(Color.RED);
                    g2d.fillRect(0, 0, 200, 200);
                    g2d.dispose();
                    curatorIcon = new ImageIcon(placeholder);
                }
            } catch (Exception e) {
                BufferedImage placeholder = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = placeholder.createGraphics();
                g2d.setColor(Color.RED);
                g2d.fillRect(0, 0, 200, 200);
                g2d.dispose();
                curatorIcon = new ImageIcon(placeholder);
            }

            Image scaledCurator = curatorIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            JLabel curatorImage = new JLabel(new ImageIcon(scaledCurator));

            JPanel leftPanel = new JPanel();
            leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
            leftPanel.setOpaque(false);
            leftPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

            JLabel curatorTitle = new JLabel("<html><center>Museum Curator<br>The person who reported the theft</center></html>");
            curatorTitle.setFont(new Font("Algerian", Font.BOLD, 18));
            curatorTitle.setForeground(Color.WHITE);
            curatorTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
            leftPanel.add(curatorTitle);
            leftPanel.add(Box.createVerticalStrut(10));
            leftPanel.add(curatorImage);

            JPanel dialoguePanel = new JPanel(new BorderLayout());
            dialoguePanel.setOpaque(false);
            dialoguePanel.setBorder(BorderFactory.createEmptyBorder(50, 0, -100, 0));

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

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.setOpaque(false);
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 10));


            JButton option2Button = createStyledButton("Examine Security System");
            option2Button.addActionListener(e -> examineSecuritySystem());

            buttonPanel.add(option2Button);
            dialoguePanel.add(dialogueText, BorderLayout.CENTER);
            contentPanel.add(leftPanel, BorderLayout.WEST);
            contentPanel.add(dialoguePanel, BorderLayout.CENTER);
            responsePanel.add(buttonPanel, BorderLayout.NORTH);
            responsePanel.add(contentPanel, BorderLayout.CENTER);

            frame.add(responsePanel);
            frame.revalidate();
            frame.repaint();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading curator response screen", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void examineSecuritySystem() {
        try {
            frame.getContentPane().removeAll();
            URL bgUrl = new URL("https://pixeljoint.com/files/icons/full/re2demake3.png");
            ImageIcon backgroundImage = new ImageIcon(bgUrl);

            JPanel securityPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            };
            securityPanel.setLayout(new BorderLayout());

            JPanel contentPanel = new JPanel(new BorderLayout(0, -20));
            contentPanel.setOpaque(false);
            contentPanel.setBorder(BorderFactory.createEmptyBorder(300, 100, -500, 20));

            // Load Jenkins image
            ImageIcon jenkinsIcon;
            try {
                URL jenkinsUrl = MysteryGame.class.getResource("/images/Jenkins.png");
                if (jenkinsUrl != null) {
                    jenkinsIcon = new ImageIcon(jenkinsUrl);
                } else {
                    BufferedImage placeholder = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2d = placeholder.createGraphics();
                    g2d.setColor(Color.GRAY);
                    g2d.fillRect(0, 0, 200, 200);
                    g2d.dispose();
                    jenkinsIcon = new ImageIcon(placeholder);
                }
            } catch (Exception e) {
                BufferedImage placeholder = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = placeholder.createGraphics();
                g2d.setColor(Color.GRAY);
                g2d.fillRect(0, 0, 200, 200);
                g2d.dispose();
                jenkinsIcon = new ImageIcon(placeholder);
            }

            Image scaledJenkins = jenkinsIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            JLabel jenkinsImage = new JLabel(new ImageIcon(scaledJenkins));

            JPanel leftPanel = new JPanel();
            leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
            leftPanel.setOpaque(false);
            leftPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

            JLabel jenkinsTitle = new JLabel("<html><center>Security Guard<br>Jenkins</center></html>");
            jenkinsTitle.setFont(new Font("Algerian", Font.BOLD, 18));
            jenkinsTitle.setForeground(Color.WHITE);
            jenkinsTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
            leftPanel.add(jenkinsTitle);
            leftPanel.add(Box.createVerticalStrut(10));
            leftPanel.add(jenkinsImage);

            JPanel dialoguePanel = new JPanel(new BorderLayout());
            dialoguePanel.setOpaque(false);
            dialoguePanel.setBorder(BorderFactory.createEmptyBorder(50, 0, -100, 0));

            JTextArea dialogueText = new JTextArea(
                    "JENKINS: \"I told the cops already—I saw nothing! The cameras glitched, and I was on my rounds.\"\n\n" +
                            "DETECTIVE: \"Your alibi for 2:17 AM?\"\n\n" +
                            "JENKINS: \"I was… uh… in the break room! Yeah, getting coffee!\"\n\n" +
                            "CLUE: No coffee stains in break room logbook.\n\n" +
                            "DETECTIVE: \"The curator says you borrow his keycard sometimes. True?\"\n\n" +
                            "JENKINS: (sweating): \"That's a lie! Wait—unless… did Blackwood put her up to this?\"\n\n" +
                            "CLUE: Guard suspects Blackwood is framing him."
            );
            dialogueText.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
            dialogueText.setForeground(Color.WHITE);
            dialogueText.setBackground(new Color(0, 0, 0, 150));
            dialogueText.setLineWrap(true);
            dialogueText.setWrapStyleWord(true);
            dialogueText.setEditable(false);
            dialogueText.setBorder(BorderFactory.createEmptyBorder(10, 20, 50, 0));

            JButton investigateButton = createStyledButton("Investigate Blackwood");
            investigateButton.addActionListener(e -> showBlackwoodInvestigation());

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.setOpaque(false);
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 10));
            buttonPanel.add(investigateButton);

            dialoguePanel.add(dialogueText, BorderLayout.CENTER);
            contentPanel.add(leftPanel, BorderLayout.WEST);
            contentPanel.add(dialoguePanel, BorderLayout.CENTER);
            securityPanel.add(buttonPanel, BorderLayout.NORTH);
            securityPanel.add(contentPanel, BorderLayout.CENTER);

            frame.add(securityPanel);
            frame.revalidate();
            frame.repaint();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading security room scene", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void showBlackwoodInvestigation() {
        try {
            frame.getContentPane().removeAll();
            URL bgUrl = new URL("https://pixeljoint.com/files/icons/full/re2demake3.png");
            ImageIcon backgroundImage = new ImageIcon(bgUrl);

            JPanel blackwoodPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            };
            blackwoodPanel.setLayout(new BorderLayout());

            // Content Panel Setup
            JPanel contentPanel = new JPanel(new BorderLayout());
            contentPanel.setOpaque(false);
            contentPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

            // Curator Image Setup
            URL curatorUrl = MysteryGame.class.getResource("/images/portrait (1).png");
            ImageIcon curatorIcon = new ImageIcon(curatorUrl);
            Image scaledCurator = curatorIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            JLabel curatorImage = new JLabel(new ImageIcon(scaledCurator));

            JPanel leftPanel = new JPanel(new BorderLayout());
            leftPanel.setOpaque(false);

            JLabel curatorTitle = new JLabel("<html><center>Museum Curator<br>The person who reported the theft</center></html>");
            curatorTitle.setFont(new Font("Algerian", Font.BOLD, 18));
            curatorTitle.setForeground(Color.WHITE);
            leftPanel.add(curatorTitle, BorderLayout.NORTH);
            leftPanel.add(curatorImage, BorderLayout.CENTER);

            // Dialogue Panel
            JPanel dialoguePanel = new JPanel(new BorderLayout());
            dialoguePanel.setOpaque(false);

            JTextArea dialogueText = new JTextArea(
                    "\"Mr. Blackwood funded the exhibit's security system. He insisted on access as a " +
                            "'patron privilege'—though he's never used it until last week. He came to... admire " +
                            "the artifact privately.\""
            );
            dialogueText.setFont(new Font("Press Start 2P", Font.PLAIN, 15));
            dialogueText.setForeground(Color.WHITE);
            dialogueText.setBackground(new Color(0, 0, 0, 150));
            dialogueText.setLineWrap(true);
            dialogueText.setWrapStyleWord(true);
            dialogueText.setEditable(false);
            dialogueText.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

            // Question Buttons Panel - Improved Layout
            JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10)); // Changed to 3 rows
            buttonPanel.setOpaque(false);
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

            // Question 1 Button
            JButton question1Button = createStyledButton("\"Did Blackwood seem unusually interested in the Oracle?\"");
            question1Button.setFont(new Font("Algerian", Font.PLAIN, 18));
            question1Button.addActionListener(e -> showBlackwoodInterest());

            // Question 2 Button
            JButton question2Button = createStyledButton("\"Where can I find Mr. Blackwood?\"");
            question2Button.addActionListener(e -> showBlackwoodLocation());

            // Back Button
            JButton backButton = createStyledButton("Back to Previous Questions");
            backButton.addActionListener(e -> showCuratorResponse()); // Goes back to previous screen

            buttonPanel.add(question1Button);
            buttonPanel.add(question2Button);
            buttonPanel.add(backButton); // Added back button

            // Component Assembly
            dialoguePanel.add(dialogueText, BorderLayout.CENTER);
            dialoguePanel.add(buttonPanel, BorderLayout.SOUTH);

            contentPanel.add(leftPanel, BorderLayout.WEST);
            contentPanel.add(dialoguePanel, BorderLayout.CENTER);

            blackwoodPanel.add(contentPanel, BorderLayout.CENTER);

            frame.add(blackwoodPanel);
            frame.revalidate();
            frame.repaint();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading Blackwood investigation screen", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void showBlackwoodInterest() {
        try {
            frame.getContentPane().removeAll();
            URL bgUrl = new URL("https://pixeljoint.com/files/icons/full/re2demake3.png");
            ImageIcon backgroundImage = new ImageIcon(bgUrl);

            JPanel interestPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            };
            interestPanel.setLayout(new BorderLayout());

            // Main content panel
            JPanel contentPanel = new JPanel(new BorderLayout());
            contentPanel.setOpaque(false);
            contentPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

            // Curator panel (left side)
            JPanel curatorPanel = new JPanel(new BorderLayout());
            curatorPanel.setOpaque(false);

            URL curatorUrl = MysteryGame.class.getResource("/images/portrait (1).png");
            ImageIcon curatorIcon = new ImageIcon(curatorUrl);
            Image scaledCurator = curatorIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            JLabel curatorImage = new JLabel(new ImageIcon(scaledCurator));

            JLabel curatorTitle = new JLabel("<html><center>Museum Curator<br>The person who reported the theft</center></html>");
            curatorTitle.setFont(new Font("Algerian", Font.BOLD, 18));
            curatorTitle.setForeground(Color.WHITE);
            curatorTitle.setHorizontalAlignment(JLabel.CENTER);

            curatorPanel.add(curatorTitle, BorderLayout.NORTH);
            curatorPanel.add(curatorImage, BorderLayout.CENTER);

            // Dialogue panel (center)
            JPanel dialoguePanel = new JPanel(new BorderLayout());
            dialoguePanel.setOpaque(false);

            JTextArea dialogueText = new JTextArea(
                    "\"Now that you mention it... he kept asking about its 'true power,' not just its history. " +
                            "Almost like he believed the legends.\""
            );
            dialogueText.setFont(new Font("Press Start 2P", Font.PLAIN, 15));
            dialogueText.setForeground(Color.WHITE);
            dialogueText.setBackground(new Color(0, 0, 0, 150));
            dialogueText.setLineWrap(true);
            dialogueText.setWrapStyleWord(true);
            dialogueText.setEditable(false);
            dialogueText.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

            // Action button
            JButton continueButton = createStyledButton("Continue to Blackwood Estate");
            continueButton.addActionListener(e -> showBlackwoodEstate());

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.setOpaque(false);
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
            buttonPanel.add(continueButton);

            // Assemble components
            dialoguePanel.add(dialogueText, BorderLayout.CENTER);
            dialoguePanel.add(buttonPanel, BorderLayout.SOUTH);

            contentPanel.add(curatorPanel, BorderLayout.WEST);
            contentPanel.add(dialoguePanel, BorderLayout.CENTER);

            interestPanel.add(contentPanel, BorderLayout.CENTER);

            frame.add(interestPanel);
            frame.revalidate();
            frame.repaint();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading Blackwood interest screen", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void showBlackwoodLocation() {
        try {
            frame.getContentPane().removeAll();
            URL bgUrl = new URL("https://pixeljoint.com/files/icons/full/re2demake3.png");
            ImageIcon backgroundImage = new ImageIcon(bgUrl);

            JPanel locationPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            };
            locationPanel.setLayout(new BorderLayout());

            // Main content panel
            JPanel contentPanel = new JPanel(new BorderLayout());
            contentPanel.setOpaque(false);
            contentPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

            // Curator panel (left side)
            JPanel curatorPanel = new JPanel(new BorderLayout());
            curatorPanel.setOpaque(false);

            URL curatorUrl = MysteryGame.class.getResource("/images/portrait (1).png");
            ImageIcon curatorIcon = new ImageIcon(curatorUrl);
            Image scaledCurator = curatorIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            JLabel curatorImage = new JLabel(new ImageIcon(scaledCurator));

            JLabel curatorTitle = new JLabel("<html><center>Museum Curator<br>The person who reported the theft</center></html>");
            curatorTitle.setFont(new Font("Algerian", Font.BOLD, 18));
            curatorTitle.setForeground(Color.WHITE);
            curatorTitle.setHorizontalAlignment(JLabel.CENTER);

            curatorPanel.add(curatorTitle, BorderLayout.NORTH);
            curatorPanel.add(curatorImage, BorderLayout.CENTER);

            // Dialogue panel (center)
            JPanel dialoguePanel = new JPanel(new BorderLayout());
            dialoguePanel.setOpaque(false);

            JTextArea dialogueText = new JTextArea(
                    "\"His estate on the outskirts of town. But detective, he's a powerful man—be careful.\""
            );
            dialogueText.setFont(new Font("Press Start 2P", Font.PLAIN, 15));
            dialogueText.setForeground(Color.WHITE);
            dialogueText.setBackground(new Color(0, 0, 0, 150));
            dialogueText.setLineWrap(true);
            dialogueText.setWrapStyleWord(true);
            dialogueText.setEditable(false);
            dialogueText.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

            // Action button
            JButton continueButton = createStyledButton("Continue to Blackwood Estate");
            continueButton.addActionListener(e -> showBlackwoodEstate());

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.setOpaque(false);
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
            buttonPanel.add(continueButton);

            // Assemble components
            dialoguePanel.add(dialogueText, BorderLayout.CENTER);
            dialoguePanel.add(buttonPanel, BorderLayout.SOUTH);

            contentPanel.add(curatorPanel, BorderLayout.WEST);
            contentPanel.add(dialoguePanel, BorderLayout.CENTER);

            locationPanel.add(contentPanel, BorderLayout.CENTER);

            frame.add(locationPanel);
            frame.revalidate();
            frame.repaint();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading Blackwood location screen", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void showBlackwoodEstate() {
        try {
            frame.getContentPane().removeAll();
            URL bgUrl = new URL("https://img.freepik.com/premium-photo/inside-library-pixel-art-style-maniac-mansion_923558-5562.jpg");
            ImageIcon backgroundImage = new ImageIcon(bgUrl);

            JPanel estatePanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            };
            estatePanel.setLayout(new BorderLayout());

            // Create a content panel
            JPanel contentPanel = new JPanel(new BorderLayout());
            contentPanel.setOpaque(false);
            contentPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

            // Create detective panel (left side)
            JPanel detectivePanel = new JPanel(new BorderLayout());
            detectivePanel.setOpaque(false);

            // Load and add detective image
            String detectiveImagePath = playerCharacter.equals("Detective Sir") ?
                    "/images/Detective You (1).png" :
                    "/images/Detective You.png";
            ImageIcon detectiveIcon = new ImageIcon(detectiveImagePath);
            Image scaledDetective = detectiveIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            JLabel detectiveImage = new JLabel(new ImageIcon(scaledDetective));

            // Add detective label
            JLabel detectiveLabel = new JLabel("<html><center>" + playerName + "<br>" + playerCharacter + "</center></html>");
            detectiveLabel.setFont(new Font("Algerian", Font.BOLD, 18));
            detectiveLabel.setForeground(Color.WHITE);
            detectiveLabel.setHorizontalAlignment(JLabel.CENTER);

            detectivePanel.add(detectiveLabel, BorderLayout.NORTH);
            detectivePanel.add(detectiveImage, BorderLayout.CENTER);

            // Create dialogue panel (center)
            JPanel dialoguePanel = new JPanel(new BorderLayout());
            dialoguePanel.setOpaque(false);

            // Create dialogue text
            JTextArea dialogueText = new JTextArea(
                    "\"Hmm... blueprints? Not just any, security blueprints. Oh? What's this? " +
                            "A shipping manifest for 'Delphi Project', but it appears to be a fake artifact label.\""
            );
            dialogueText.setFont(new Font("Press Start 2P", Font.PLAIN, 15));
            dialogueText.setForeground(Color.WHITE);
            dialogueText.setBackground(new Color(0, 0, 0, 150));
            dialogueText.setLineWrap(true);
            dialogueText.setWrapStyleWord(true);
            dialogueText.setEditable(false);
            dialogueText.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

            dialoguePanel.add(dialogueText, BorderLayout.CENTER);

            // Create Blackwood panel (right side)
            JPanel blackwoodPanel = new JPanel(new BorderLayout());
            blackwoodPanel.setOpaque(false);

            // Load and add Blackwood image
            URL blackwoodUrl = MysteryGame.class.getResource("/images/MRBLACKWOOD.png");
            ImageIcon blackwoodIcon = new ImageIcon(blackwoodUrl);
            Image scaledBlackwood = blackwoodIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            JLabel blackwoodImage = new JLabel(new ImageIcon(scaledBlackwood));

            // Add Blackwood label
            JLabel blackwoodLabel = new JLabel("<html><center>Mr. Blackwood<br>Museum Benefactor</center></html>");
            blackwoodLabel.setFont(new Font("Algerian", Font.BOLD, 18));
            blackwoodLabel.setForeground(Color.WHITE);
            blackwoodLabel.setHorizontalAlignment(JLabel.CENTER);

            blackwoodPanel.add(blackwoodLabel, BorderLayout.NORTH);
            blackwoodPanel.add(blackwoodImage, BorderLayout.CENTER);

            // Create Blackwood's dialogue
            JTextArea blackwoodDialogue = new JTextArea(
                    "\"Detective. I heard about the museum's... misfortune. " +
                            "But surely you don't suspect me? I'm a patron of the arts, not a thief.\""
            );
            blackwoodDialogue.setFont(new Font("Press Start 2P", Font.PLAIN, 15));
            blackwoodDialogue.setForeground(Color.WHITE);
            blackwoodDialogue.setBackground(new Color(0, 0, 0, 150));
            blackwoodDialogue.setLineWrap(true);
            blackwoodDialogue.setWrapStyleWord(true);
            blackwoodDialogue.setEditable(false);
            blackwoodDialogue.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

            dialoguePanel.add(blackwoodDialogue, BorderLayout.SOUTH);

            // Create action buttons panel
            JPanel actionPanel = new JPanel(new GridLayout(1, 2, 10, 10));
            actionPanel.setOpaque(false);
            actionPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));

            // Updated interrogation button to use showBlackwoodOption1()
            JButton interrogateButton = createStyledButton("Interrogate Blackwood");
            interrogateButton.addActionListener(e -> showBlackwoodOption1());

            JButton returnButton = createStyledButton("Return to Museum");
            returnButton.addActionListener(e -> showCuratorIntroduction());

            actionPanel.add(interrogateButton);
            actionPanel.add(returnButton);

            // Add components to content panel
            contentPanel.add(detectivePanel, BorderLayout.WEST);
            contentPanel.add(dialoguePanel, BorderLayout.CENTER);
            contentPanel.add(blackwoodPanel, BorderLayout.EAST);
            contentPanel.add(actionPanel, BorderLayout.SOUTH);

            // Add content panel to main panel
            estatePanel.add(contentPanel, BorderLayout.CENTER);

            // Update the frame
            frame.add(estatePanel);
            frame.revalidate();
            frame.repaint();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading Blackwood estate screen", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void showBlackwoodOption1() {
        try {
            // Remove the current content
            frame.getContentPane().removeAll();

            // Load the estate background image
            URL bgUrl = new URL("https://img.freepik.com/premium-photo/inside-library-pixel-art-style-maniac-mansion_923558-5562.jpg");
            ImageIcon backgroundImage = new ImageIcon(bgUrl);

            // Create panel with background
            JPanel optionPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            };
            optionPanel.setLayout(new BorderLayout());

            // Create a content panel
            JPanel contentPanel = new JPanel(new BorderLayout());
            contentPanel.setOpaque(false);
            contentPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

            // Create Blackwood panel (right side)
            JPanel blackwoodPanel = new JPanel(new BorderLayout());
            blackwoodPanel.setOpaque(false);

            // Load and add Blackwood image
            URL blackwoodUrl = MysteryGame.class.getResource("/images/MRBLACKWOOD.png");
            ImageIcon blackwoodIcon = new ImageIcon(blackwoodUrl);
            Image scaledBlackwood = blackwoodIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            JLabel blackwoodImage = new JLabel(new ImageIcon(scaledBlackwood));

            // Add Blackwood label
            JLabel blackwoodLabel = new JLabel("<html><center>Mr. Blackwood<br>Museum Benefactor</center></html>");
            blackwoodLabel.setFont(new Font("Algerian", Font.BOLD, 18));
            blackwoodLabel.setForeground(Color.WHITE);
            blackwoodLabel.setHorizontalAlignment(JLabel.CENTER);

            blackwoodPanel.add(blackwoodLabel, BorderLayout.NORTH);
            blackwoodPanel.add(blackwoodImage, BorderLayout.CENTER);

            // Create dialogue panel (center)
            JPanel dialoguePanel = new JPanel(new BorderLayout());
            dialoguePanel.setOpaque(false);

            // Create dialogue text
            JTextArea dialogueText = new JTextArea(
                    "\"Interest? Pah! It's history. Though... legends say the Oracle's fragments *whisper* " +
                            "if assembled. Superstition, of course.\" (Laughs too loudly.)"
            );
            dialogueText.setFont(new Font("Press Start 2P", Font.PLAIN, 15));
            dialogueText.setForeground(Color.WHITE);
            dialogueText.setBackground(new Color(0, 0, 0, 150));
            dialogueText.setLineWrap(true);
            dialogueText.setWrapStyleWord(true);
            dialogueText.setEditable(false);
            dialogueText.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

            dialoguePanel.add(dialogueText, BorderLayout.CENTER);

            // Create follow-up options
            JPanel optionsPanel = new JPanel(new GridLayout(1, 1, 10, 10));
            optionsPanel.setOpaque(false);
            optionsPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));

            JButton followUpButton = createStyledButton("\"Legends? Or a reason to steal it?\"");
            followUpButton.addActionListener(e -> {
                JTextArea followUpText = new JTextArea(
                        "\"Careful, detective. Accusations require evidence.\" (His hand trembles on the cigar.)"
                );
                followUpText.setFont(new Font("Press Start 2P", Font.PLAIN, 15));
                followUpText.setForeground(Color.WHITE);
                followUpText.setBackground(new Color(0, 0, 0, 150));
                followUpText.setLineWrap(true);
                followUpText.setWrapStyleWord(true);
                followUpText.setEditable(false);
                followUpText.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

                dialoguePanel.add(followUpText, BorderLayout.SOUTH);
                followUpButton.setEnabled(false);

                // Create the continue button here
                JButton continueButton = createStyledButton("Continue Investigation");
                continueButton.addActionListener(ev -> {
                    JOptionPane.showMessageDialog(frame,
                            "Clue Gained: Blackwood obsessed with the Oracle's \"whispers.\"",
                            "New Clue",
                            JOptionPane.INFORMATION_MESSAGE);
                    showBlackwoodConfrontation(); // Call the new confrontation screen
                });

                optionsPanel.removeAll();
                optionsPanel.add(continueButton);
                frame.revalidate();
                frame.repaint();
            });

            optionsPanel.add(followUpButton);

            // Add components to content panel
            contentPanel.add(blackwoodPanel, BorderLayout.EAST);
            contentPanel.add(dialoguePanel, BorderLayout.CENTER);
            contentPanel.add(optionsPanel, BorderLayout.SOUTH);

            // Add content panel to main panel
            optionPanel.add(contentPanel, BorderLayout.CENTER);

            // Update the frame
            frame.add(optionPanel);
            frame.revalidate();
            frame.repaint();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading Blackwood option 1 screen", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void showBlackwoodConfrontation() {
        try {
            frame.getContentPane().removeAll();

            // Load the estate background image
            URL bgUrl = new URL("https://img.freepik.com/premium-photo/inside-library-pixel-art-style-maniac-mansion_923558-5562.jpg");
            ImageIcon backgroundImage = new ImageIcon(bgUrl);

            // Create panel with background
            JPanel confrontationPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            };
            confrontationPanel.setLayout(new BorderLayout());

            // Main content panel
            JPanel contentPanel = new JPanel(new BorderLayout());
            contentPanel.setOpaque(false);
            contentPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

            // Left panel - Detective
            JPanel detectivePanel = new JPanel(new BorderLayout());
            detectivePanel.setOpaque(false);

            // Load detective image based on selected character
            String detectiveImagePath = playerCharacter.equals("Detective Sir") ?
                    "/images/Detective You (1).png" :
                    "/images/Detective You.png";

            ImageIcon detectiveIcon = new ImageIcon(MysteryGame.class.getResource(detectiveImagePath));
            Image scaledDetective = detectiveIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            JLabel detectiveImage = new JLabel(new ImageIcon(scaledDetective));

            JLabel detectiveLabel = new JLabel("<html><center>" + playerCharacter + "<br>" + playerName + "</center></html>");
            detectiveLabel.setFont(new Font("Algerian", Font.BOLD, 18));
            detectiveLabel.setForeground(Color.WHITE);
            detectiveLabel.setHorizontalAlignment(JLabel.CENTER);

            detectivePanel.add(detectiveLabel, BorderLayout.NORTH);
            detectivePanel.add(detectiveImage, BorderLayout.CENTER);

            // Right panel - Blackwood
            JPanel blackwoodPanel = new JPanel(new BorderLayout());
            blackwoodPanel.setOpaque(false);

            URL blackwoodUrl = MysteryGame.class.getResource("/images/MRBLACKWOOD.png");
            ImageIcon blackwoodIcon = new ImageIcon(blackwoodUrl);
            Image scaledBlackwood = blackwoodIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            JLabel blackwoodImage = new JLabel(new ImageIcon(scaledBlackwood));

            JLabel blackwoodLabel = new JLabel("<html><center>Mr. Blackwood<br>Museum Benefactor</center></html>");
            blackwoodLabel.setFont(new Font("Algerian", Font.BOLD, 18));
            blackwoodLabel.setForeground(Color.WHITE);
            blackwoodLabel.setHorizontalAlignment(JLabel.CENTER);

            blackwoodPanel.add(blackwoodLabel, BorderLayout.NORTH);
            blackwoodPanel.add(blackwoodImage, BorderLayout.CENTER);

            // Dialogue panel
            JPanel dialoguePanel = new JPanel(new BorderLayout());
            dialoguePanel.setOpaque(false);
            dialoguePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            JTextArea dialogueText = new JTextArea(
                    "DETECTIVE: \"You have access to the security system. Convenient, isn't it? " +
                            "A witness saw someone of your description at the museum that night.\" (Bluff)\n\n" +
                            "BLACKWOOD: \"Witness? *Really?*\" (Eyes narrow.) \"Name them.\"\n\n" +
                            "DETECTIVE: \"The guard. She ID'd your cigar smoke.\" " +
                            "(If player has cigar clue, Blackwood panics.)\n\n" +
                            "BLACKWOOD: \"That drunkard Jenkins? I paid her enough to—\" (Catches himself.)\n\n" +
                            "*CLUE: Blackwood bribed the guard!*\n\n" +
                            "DETECTIVE: \"My mistake.\" (Blackwood smirks; no clue.)"
            );
            dialogueText.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
            dialogueText.setForeground(Color.WHITE);
            dialogueText.setBackground(new Color(0, 0, 0, 150));
            dialogueText.setLineWrap(true);
            dialogueText.setWrapStyleWord(true);
            dialogueText.setEditable(false);
            dialogueText.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

            dialoguePanel.add(dialogueText, BorderLayout.CENTER);

            // Options panel
            JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            optionsPanel.setOpaque(false);
            optionsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

            JButton finalDecisionButton = createStyledButton("Final Decision");
            finalDecisionButton.addActionListener(e -> {
                showFinalDecisionScreen();  // This will show the final decision screen
            });

            optionsPanel.add(finalDecisionButton);

            // Add components to content panel
            JPanel characterPanel = new JPanel(new GridLayout(1, 2));
            characterPanel.setOpaque(false);
            characterPanel.add(detectivePanel);
            characterPanel.add(blackwoodPanel);

            contentPanel.add(characterPanel, BorderLayout.NORTH);
            contentPanel.add(dialoguePanel, BorderLayout.CENTER);
            contentPanel.add(optionsPanel, BorderLayout.SOUTH);

            // Add content panel to main panel
            confrontationPanel.add(contentPanel, BorderLayout.CENTER);

            // Update the frame
            frame.add(confrontationPanel);
            frame.revalidate();
            frame.repaint();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame,
                    "Error loading confrontation screen",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}