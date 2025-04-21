import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class MysteryGame {
    private static JFrame frame;
    private static JPanel backgroundPanel;
    private static String playerName = "";
    private static String playerCharacter = "";
    private static final Color DARK_RED = new Color(139, 0, 0);
    private static final Font TITLE_FONT = new Font("Algerian", Font.BOLD, 48);
    private static final Font BUTTON_FONT = new Font("Algerian", Font.BOLD, 25);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        frame = new JFrame("Relic Hunter: The Stolen Oracle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 640);
        frame.setResizable(false);

        try {
            URL imageUrl = new URL("https://www.relyonhorror.com/wp-content/uploads/2018/12/uncanny-valley-screen-05-ps4-us-10jan17-1024x640.jpg");
            ImageIcon backgroundImage = new ImageIcon(imageUrl);

            backgroundPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            };
            backgroundPanel.setLayout(new GridBagLayout());

            JLabel titleLabel = new JLabel("Relic Hunter: The Stolen Oracle");
            titleLabel.setFont(TITLE_FONT);
            titleLabel.setForeground(DARK_RED);
            titleLabel.setHorizontalAlignment(JLabel.CENTER);

            JButton startButton = createStyledButton("Begin Story");
            startButton.addActionListener(e -> showCharacterSelectionScreen());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.insets = new Insets(0, 0, 50, 0);

            backgroundPanel.add(titleLabel, gbc);
            backgroundPanel.add(startButton);

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

            JLabel selectLabel = new JLabel("Select Your Detective");
            selectLabel.setFont(TITLE_FONT);
            selectLabel.setForeground(DARK_RED);
            selectLabel.setHorizontalAlignment(JLabel.CENTER);

            JPanel charactersPanel = new JPanel(new GridLayout(1, 2, 50, 0));
            charactersPanel.setOpaque(false);

            JPanel malePanel = createCharacterPanel(
                    "C:\\Users\\15862\\Desktop\\Detective You (1).png",
                    "Detective Sir",
                    "Choose Detective Sir"
            );

            JPanel femalePanel = createCharacterPanel(
                    "C:\\Users\\15862\\Desktop\\Detective Laura.png",
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
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.BLACK);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel label = new JLabel("Enter your detective's name:");
        label.setFont(new Font("Algerian", Font.BOLD, 18));
        label.setForeground(DARK_RED);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label, BorderLayout.NORTH);

        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Arial", Font.PLAIN, 16));
        textField.setBackground(Color.BLACK);
        textField.setForeground(Color.WHITE);
        textField.setCaretColor(Color.WHITE);
        textField.setBorder(BorderFactory.createLineBorder(DARK_RED, 2));
        panel.add(textField, BorderLayout.CENTER);

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

            URL curatorUrl = new URL("file:///C:/Users/15862/Desktop/portrait%20(1).png");
            ImageIcon curatorIcon = new ImageIcon(curatorUrl);
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

            String detectiveImagePath = playerCharacter.equals("Detective Sir") ?
                    "C:\\Users\\15862\\Desktop\\Detective You (1).png" :
                    "C:\\Users\\15862\\Desktop\\Detective Laura.png";

            ImageIcon detectiveIcon = new ImageIcon(detectiveImagePath);
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

            URL curatorUrl = new URL("file:///C:/Users/15862/Desktop/portrait%20(1).png");
            ImageIcon curatorIcon = new ImageIcon(curatorUrl);
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

            JButton option1Button = createStyledButton("Investigate Blackwood");
            option1Button.addActionListener(e -> showBlackwoodInvestigation());

            JButton option2Button = createStyledButton("Examine Security System");
            option2Button.addActionListener(e -> {
                JOptionPane.showMessageDialog(frame,
                        "You decide to examine the security system for weaknesses...",
                        "Investigation Path",
                        JOptionPane.INFORMATION_MESSAGE);
            });

            buttonPanel.add(option1Button);
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
            URL curatorUrl = new URL("file:///C:/Users/15862/Desktop/portrait%20(1).png");
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

            // Question Buttons - FIXED LAYOUT
            JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10));
            buttonPanel.setOpaque(false);
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

            JButton question1Button = createStyledButton("\"Did Blackwood seem unusually interested in the Oracle?\"");
            question1Button.setFont(new Font("Algerian", Font.PLAIN, 18)); // override font size here
            question1Button.addActionListener(e -> showBlackwoodInterest());

            JButton question2Button = createStyledButton("\"Where can I find Mr. Blackwood?\"");
            question2Button.addActionListener(e -> showBlackwoodLocation());

            buttonPanel.add(question1Button);
            buttonPanel.add(question2Button);

            // FIXED COMPONENT ASSEMBLY
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

            JPanel contentPanel = new JPanel(new BorderLayout(0, -20));
            contentPanel.setOpaque(false);
            contentPanel.setBorder(BorderFactory.createEmptyBorder(300, 100, -500, 20));

            URL curatorUrl = new URL("file:///C:/Users/15862/Desktop/portrait%20(1).png");
            ImageIcon curatorIcon = new ImageIcon(curatorUrl);
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
                    "\"Now that you mention it... he kept asking about its 'true power,' not just its history. " +
                            "Almost like he believed the legends.\""
            );
            dialogueText.setFont(new Font("Press Start 2P", Font.PLAIN, 15));
            dialogueText.setForeground(Color.WHITE);
            dialogueText.setBackground(new Color(0, 0, 0, 150));
            dialogueText.setLineWrap(true);
            dialogueText.setWrapStyleWord(true);
            dialogueText.setEditable(false);
            dialogueText.setBorder(BorderFactory.createEmptyBorder(10, 20, 50, 0));

            JButton continueButton = createStyledButton("Continue to Blackwood Estate");
            continueButton.addActionListener(e -> showBlackwoodEstate());

            dialoguePanel.add(dialogueText, BorderLayout.CENTER);
            dialoguePanel.add(continueButton, BorderLayout.SOUTH);
            contentPanel.add(leftPanel, BorderLayout.WEST);
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

            JPanel contentPanel = new JPanel(new BorderLayout(0, -20));
            contentPanel.setOpaque(false);
            contentPanel.setBorder(BorderFactory.createEmptyBorder(300, 100, -500, 20));

            URL curatorUrl = new URL("file:///C:/Users/15862/Desktop/portrait%20(1).png");
            ImageIcon curatorIcon = new ImageIcon(curatorUrl);
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
                    "\"His estate on the outskirts of town. But detective, he's a powerful man—be careful.\""
            );
            dialogueText.setFont(new Font("Press Start 2P", Font.PLAIN, 15));
            dialogueText.setForeground(Color.WHITE);
            dialogueText.setBackground(new Color(0, 0, 0, 150));
            dialogueText.setLineWrap(true);
            dialogueText.setWrapStyleWord(true);
            dialogueText.setEditable(false);
            dialogueText.setBorder(BorderFactory.createEmptyBorder(10, 20, 50, 0));

            JButton continueButton = createStyledButton("Continue to Blackwood Estate");
            continueButton.addActionListener(e -> showBlackwoodEstate());

            dialoguePanel.add(dialogueText, BorderLayout.CENTER);
            dialoguePanel.add(continueButton, BorderLayout.SOUTH);
            contentPanel.add(leftPanel, BorderLayout.WEST);
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
        JOptionPane.showMessageDialog(frame,
                "This will be the estate scene where you confront Blackwood",
                "Next Step",
                JOptionPane.INFORMATION_MESSAGE);
    }
}