/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ptux;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ProgressMonitor;
import javax.swing.SwingWorker;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Vasileios Gerodimos A.M. 2025200900014
 *
 * @author Vasileios Stamos A.M. 2025200900070
 */
public class Frame extends JFrame {

    private JPanel cardPanel, fourthCardPanel, southPanel0, southPanel1, southPanel2, southPanel3, southPanel4;
    private CardLayout cdL;
    private JLabel bottomLabel5, bottomIcon5, middleLabel4;
    private JRadioButton choice1, choice2, choice3, choice4, choice5, choice6, choice7;
    private JRadioButton moveRB, copyRB, newStartButton, keepLatestButton;
    private ButtonGroup buttonGroup2;
    private JButton edit, nextButton0, backButton1, nextButton1, backButton2, nextButton2, backButton3, nextButton3, backButton4, nextButton4;
    private JButton startPauseButton, pauseButton, stopButton;
    private JTextField sourceFolderTF, destinationFolderTF, sourceLabel2, destinationLabel2, keepLatestMethodTextField, keepLatestDestinationTextField;
    private JTextField moveCopyLabel2, sourceRightLabel, destinationRightLabel;
    private JTextArea sortingLabel2;
    private Dialog3b dialog = null;
    private JProgressBar progressBar;
    private ProgressMonitor progressMonitor;
    private BufferedImage image = null;
    private File file[] = null, file2 = null;
    private JFileChooser fc;
    private ArrayList<File> dir = null, fil = null;
    private ArrayList<String> originalList2, yearList, monthNumberList, monthCharList, dateNumberList, dateCharList, locationList, dimensionsList;
    private ArrayList<String> manufacturerList, modelList;
    private String sortingMethodDialogText, stringForSelectedRB, helpText = "";
    private Task task;
    private int ctrForProgress = 0, flagForSPButton = 0;
    private PausableSwingWorker<Void, String> worker;
    private JMenuItem newSortItem, startItem, pauseItem, stopItem, exitItem;
    private boolean locationTagSelected = false;

    public Frame() {
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Organize your photos");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/gab-lab.png")));

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);

//        setMinimumSize(getSize());
        setResizable(false);

        //dilwnoume to cardLayout
        cardPanel = new JPanel();
        cdL = new CardLayout();
        cardPanel.setLayout(cdL);

        ////////////////////////////////////////////////////////////////////////
        //mideniko parathuro tou cardLayout
        JPanel zeroCardPanel = new JPanel();
        BorderLayout b0 = new BorderLayout();
        zeroCardPanel.setLayout(b0);

        JPanel box0 = new JPanel();
        box0.setLayout(new BoxLayout(box0, BoxLayout.Y_AXIS));

        box0.add(Box.createVerticalGlue());
        JPanel centerPanel0 = new JPanel(new FlowLayout());

        JPanel ssf0 = new JPanel();
        ssf0.setLayout(new BoxLayout(ssf0, BoxLayout.Y_AXIS));
        ssf0.setBorder(new TitledBorder("Settings:"));

        ssf0.add(Box.createRigidArea(new Dimension(0, 0)));
        JPanel radioButtonPanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel radioButtonPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        newStartButton = new JRadioButton("Create your own");
        newStartButton.setSelected(true);
        keepLatestButton = new JRadioButton("Keep the latest");

        ButtonGroup startGroup = new ButtonGroup();
        startGroup.add(newStartButton);
        startGroup.add(keepLatestButton);

        radioButtonPanel1.add(newStartButton);
        radioButtonPanel2.add(keepLatestButton);

        ssf0.add(Box.createRigidArea(new Dimension(0, 5)));

        ssf0.add(radioButtonPanel1);
        ssf0.add(radioButtonPanel2);

        JPanel keepLatestMethod = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel keepLatestMethodLabel = new JLabel("Sorting Method: ");
        keepLatestMethodLabel.setForeground(Color.gray);
        keepLatestMethodTextField = new JTextField(30);
        keepLatestMethodTextField.setEditable(false);
        keepLatestMethod.add(keepLatestMethodLabel);
        keepLatestMethod.add(keepLatestMethodTextField);

        JPanel keepLatestDestination = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel keepLatestDestinationLabel = new JLabel("Destination Folder: ");
        keepLatestDestinationLabel.setForeground(Color.gray);
        keepLatestDestinationTextField = new JTextField(30);
        keepLatestDestinationTextField.setEditable(false);
        keepLatestDestination.add(keepLatestDestinationLabel);
        keepLatestDestination.add(keepLatestDestinationTextField);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(System.getProperty("java.io.tmpdir") + "latestsettings.txt")), "UTF-8"))) {
//        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src\\ptux\\settings\\latestsettings.txt"), "UTF-8"))) {
//        try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("settings/latestsettings.txt"), "UTF-8"))) {
//         try(InputStream stream = this.getClass().getResourceAsStream("settings/latestsettings.txt")){ 

            String line = br.readLine();
            if (line != null) {
                keepLatestDestinationTextField.setText(line);
                System.out.println(line);
                line = br.readLine();
                keepLatestMethodTextField.setText(line);
                System.out.println(line);
            } else {
                keepLatestButton.setEnabled(false);
            }
        } catch (Exception eee) {
            System.out.println("To arxeio den vrethike.");
            keepLatestButton.setEnabled(false);
        }

        ssf0.add(keepLatestDestination);
        ssf0.add(keepLatestMethod);

        ssf0.add(Box.createRigidArea(new Dimension(0, 15)));

        centerPanel0.add(ssf0);
        box0.add(centerPanel0);

        box0.add(Box.createVerticalGlue());

        southPanel0 = new JPanel();
        southPanel0.setLayout(new BoxLayout(southPanel0, BoxLayout.X_AXIS));
        southPanel0.add(Box.createHorizontalGlue());

        nextButton0 = new JButton(new ImageIcon(getClass().getResource("images/right.png")));

        nextButton0.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (newStartButton.isSelected()) {
                    cdL.next(cardPanel);
                } else if (keepLatestButton.isSelected()) {
//                    try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("settings/latestsettings.txt"), "UTF-8"))) {
//                    try (BufferedReader br = new BufferedReader(new FileReader(new File("src\\ptux\\settings\\latestsettings.txt")))) {
//                    try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src\\ptux\\settings\\latestsettings.txt"), "UTF-8"))) {
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(System.getProperty("java.io.tmpdir") + "latestsettings.txt")), "UTF-8"))) {
                        String line = br.readLine();

                        while (line != null) {
                            System.out.println(line);

                            line = br.readLine();

                        }

                    } catch (Exception eee) {

                    }
                    cdL.next(cardPanel);
                }
            }
        });

        southPanel0.add(nextButton0);
        southPanel0.add(Box.createRigidArea(new Dimension(5, 0)));

        box0.add(southPanel0, BorderLayout.SOUTH);

        zeroCardPanel.add(box0, BorderLayout.CENTER);

        ////////////////////////////////////////////////////////////////////////
        //prwto parathuro tou cardLayout
        JPanel firstCardPanel = new JPanel();
        BorderLayout b1 = new BorderLayout();
        firstCardPanel.setLayout(b1);

        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(1, 4));

        p1.setBorder(new LineBorder(Color.DARK_GRAY, 2));

        JPanel p11 = new JPanel(new FlowLayout());
        JPanel p12 = new JPanel(new FlowLayout());
        JPanel p13 = new JPanel(new FlowLayout());
        JPanel p14 = new JPanel(new FlowLayout());

        JLabel l1 = new JLabel("Step 1");
        l1.setForeground(Color.CYAN);
        l1.setFont(new Font(getName(), Font.PLAIN, 20));
        JLabel l2 = new JLabel("Step 2");
        l2.setFont(new Font(getName(), Font.PLAIN, 20));
        JLabel l3 = new JLabel("Step 3");
        l3.setFont(new Font(getName(), Font.PLAIN, 20));
        JLabel l4 = new JLabel("Step 4");
        l4.setFont(new Font(getName(), Font.PLAIN, 20));

        //add labels into panels
        p11.add(l1);
        p12.add(l2);
        p13.add(l3);
        p14.add(l4);

        //add panels to topPanel
        p1.add(p11);
        p1.add(p12);
        p1.add(p13);
        p1.add(p14);

        //add topPanel to firstCardPanel
        firstCardPanel.add(p1, BorderLayout.NORTH);

        JPanel box1 = new JPanel();
        box1.setLayout(new BoxLayout(box1, BoxLayout.Y_AXIS));

        box1.add(Box.createVerticalGlue());
        JPanel centerPanel = new JPanel(new FlowLayout());

        JPanel ssf = new JPanel();
        ssf.setLayout(new BoxLayout(ssf, BoxLayout.Y_AXIS));
        ssf.setBorder(new TitledBorder("Select Source Folder"));

        ssf.add(Box.createRigidArea(new Dimension(0, 10)));
        JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sourceFolderTF = new JTextField("", 30);
        sourceFolderTF.setEditable(false);

        sourceFolderTF.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                checkSourceFolderTF();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkSourceFolderTF();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkSourceFolderTF();
            }

            private void checkSourceFolderTF() {
                String st = sourceFolderTF.getText();

                if (st.length() == 0) {
//                    nextButton1.setEnabled(false);
                } else {
//                    nextButton1.setEnabled(true);
                }
            }
        });

        textPanel.add(sourceFolderTF);

        ssf.add(Box.createRigidArea(new Dimension(0, 5)));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton selectButton = new JButton("Choose Source Folder");
        buttonPanel.add(selectButton);
        ssf.add(Box.createRigidArea(new Dimension(0, 5)));

        dir = new ArrayList<>();
        fil = new ArrayList<>();
        selectButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                fc.setMultiSelectionEnabled(true);

                //orizoume san arxiko fakelo pou tha emfanizetai me to anoigma tou JFileChooser to Desktop
                fc.setCurrentDirectory(new File(System.getProperty("user.home") + "\\Desktop"));

                sourceFolderTF.setText("Analyzing source folder...");
                nextButton1.setEnabled(false);

                int returnVal = fc.showOpenDialog(Frame.this);

                if (returnVal == JFileChooser.APPROVE_OPTION) {

                    try {
                        file = fc.getSelectedFiles();
                        System.out.println(file.length);

                        //image = ImageIO.read(file);
                        String sourceTX = "";
                        for (File f : file) {
                            sourceTX += f.toString() + ";";
                        }
                        helpText = sourceTX;
                        sourceFolderTF.setText(sourceTX);

                        dir = new ArrayList<>();
                        fil = new ArrayList<>();
                        for (int j = 0; j < file.length; j++) {
                            if (file[j].isDirectory()) {
                                File[] f = file[j].listFiles();

                                for (int i = 0; i < f.length; i++) {
                                    if (f[i].isDirectory()) {
                                        dir.add(f[i]);
                                    } else if (f[i].isFile()) {
                                        fil.add(f[i]);
                                    }
                                    System.out.println(f[i].getName());
                                }

                            } else if (file[j].isFile()) {
                                System.out.println("vrika arxeio");
                                File[] f = file[j].listFiles();

                                fil.add(file[j]);
                                System.out.println(file[j].getName());
                                nextButton1.setEnabled(true);
                            }
                        }
                        while (dir.isEmpty() == false) {
                            File[] f2 = dir.get(0).listFiles();
                            dir.remove(0);
                            for (int i = 0; i < f2.length; i++) {
                                if (f2[i].isDirectory()) {
                                    dir.add(f2[i]);
                                } else if (f2[i].isFile()) {
                                    fil.add(f2[i]);
                                }
                                System.out.println(f2[i].getName());
                            }
                        }

                    } catch (Exception eee) {
                        System.out.println("Error at source folder selection: " + eee.getMessage());
                    }

                } else {
                    sourceFolderTF.setText(helpText);
                }

                if (fil.isEmpty() && !helpText.isEmpty()) {
                    JOptionPane.showMessageDialog(Frame.this, "The selected folder is empty. Please choose another one!", "Empty Folder!!!", JOptionPane.WARNING_MESSAGE);
                    nextButton1.setEnabled(false);
                } else if (!fil.isEmpty()) {
                    nextButton1.setEnabled(true);
                }

            }

        });

        ssf.add(textPanel);
        ssf.add(buttonPanel);
        centerPanel.add(ssf);

        box1.add(centerPanel);

        box1.add(Box.createVerticalGlue());

        southPanel1 = new JPanel();
        southPanel1.setLayout(new BoxLayout(southPanel1, BoxLayout.X_AXIS));
        southPanel1.add(Box.createHorizontalGlue());

        backButton1 = new JButton(new ImageIcon(getClass().getResource("images/left.png")));

        nextButton1 = new JButton(new ImageIcon(getClass().getResource("images/right.png")));
        nextButton1.setEnabled(false);

        nextButton1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (keepLatestButton.isSelected()) {
                    cdL.show(cardPanel, "fourthCardPanel");

                    sourceLabel2.setText(sourceFolderTF.getText());

//                    try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("settings/latestsettings.txt"), "UTF-8"))) {
//                     try (BufferedReader br = new BufferedReader(new FileReader(new File("src\\ptux\\settings\\latestsettings.txt")))) {    
//                    try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src\\ptux\\settings\\latestsettings.txt"), "UTF-8"))) {
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(System.getProperty("java.io.tmpdir") + "latestsettings.txt")), "UTF-8"))) {

                        String line = br.readLine();
                        if (line != null) {
                            destinationLabel2.setText(line);
                            System.out.println(line);

                            line = br.readLine();
                            sortingLabel2.setText(destinationLabel2.getText() + "\\" + line);
                            System.out.println(line);

                            line = br.readLine();
                            moveCopyLabel2.setText(line);
                            System.out.println(line);
                        } else {
                        }
                    } catch (Exception eee) {
                        System.out.println("Sfalma sto arxeio");
                    }

                } else if (newStartButton.isSelected()) {
                    cdL.next(cardPanel);
                }
            }
        });
        backButton1.addActionListener(new backActionListener());

        southPanel1.add(backButton1);
        southPanel1.add(Box.createRigidArea(new Dimension(5, 0)));
        southPanel1.add(nextButton1);
        southPanel1.add(Box.createRigidArea(new Dimension(5, 0)));

        box1.add(southPanel1, BorderLayout.SOUTH);

        firstCardPanel.add(box1, BorderLayout.CENTER);
        ////////////////////////////////////////////////////////////////////////
        //deutero parathuro tou cardLayout
        JPanel secondCardPanel = new JPanel();
        BorderLayout b2 = new BorderLayout();
        secondCardPanel.setLayout(b2);

        JPanel p2 = new JPanel();
        p2.setLayout(new GridLayout(1, 4));

        p2.setBorder(new LineBorder(Color.DARK_GRAY, 2));

        JPanel p21 = new JPanel(new FlowLayout());
        JPanel p22 = new JPanel(new FlowLayout());
        JPanel p23 = new JPanel(new FlowLayout());
        JPanel p24 = new JPanel(new FlowLayout());

        JLabel l21 = new JLabel("Step 1");
        l21.setFont(new Font(getName(), Font.PLAIN, 20));
        JLabel l22 = new JLabel("Step 2");
        l22.setForeground(Color.CYAN);
        l22.setFont(new Font(getName(), Font.PLAIN, 20));
        JLabel l23 = new JLabel("Step 3");
        l23.setFont(new Font(getName(), Font.PLAIN, 20));
        JLabel l24 = new JLabel("Step 4");
        l24.setFont(new Font(getName(), Font.PLAIN, 20));

        //add labels into panels
        p21.add(l21);
        p22.add(l22);
        p23.add(l23);
        p24.add(l24);

        //add panels to topPanel
        p2.add(p21);
        p2.add(p22);
        p2.add(p23);
        p2.add(p24);

        //add topPanel to firstCardPanel
        secondCardPanel.add(p2, BorderLayout.NORTH);

        JPanel box2 = new JPanel();
        box2.setLayout(new BoxLayout(box2, BoxLayout.Y_AXIS));

        box2.add(Box.createVerticalGlue());

        JPanel centerPanel2 = new JPanel(new FlowLayout());

        JPanel ssf2 = new JPanel();
        ssf2.setLayout(new BoxLayout(ssf2, BoxLayout.Y_AXIS));
        ssf2.setBorder(new TitledBorder("Select Destination Folder"));

        ssf2.add(Box.createRigidArea(new Dimension(0, 10)));
        JPanel textPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        destinationFolderTF = new JTextField("", 30);
        destinationFolderTF.setEditable(false);

        destinationFolderTF.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                checkDestinationFolderTF();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkDestinationFolderTF();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkDestinationFolderTF();
            }

            private void checkDestinationFolderTF() {
                String st = destinationFolderTF.getText();

                if (st.length() == 0) {
                    nextButton2.setEnabled(false);
                } else {
                    nextButton2.setEnabled(true);
                }
            }

        });

        textPanel2.add(destinationFolderTF);

        ssf2.add(Box.createRigidArea(new Dimension(0, 5)));
        JPanel buttonPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton selectButton2 = new JButton("Choose Destination Folder");

        selectButton2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                fc.setMultiSelectionEnabled(false);
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fc.setSelectedFile(file[0]);
                int returnVal = fc.showSaveDialog(Frame.this);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    file2 = fc.getSelectedFile();

                    destinationFolderTF.setText(file2.toString());

                    System.out.println("pira:" + getDestinationFolderTF().getText());
                    System.out.println(file2.toString());
                } else {

                }

            }
        });

        buttonPanel2.add(selectButton2);
        ssf2.add(Box.createRigidArea(new Dimension(0, 5)));

        ssf2.add(textPanel2);
        ssf2.add(buttonPanel2);
        centerPanel2.add(ssf2);

        ////////////////////////////
        JPanel centerPanel22 = new JPanel(new FlowLayout());

        JPanel ssf22 = new JPanel();
        ssf22.setLayout(new BoxLayout(ssf22, BoxLayout.Y_AXIS));
        ssf22.setBorder(new TitledBorder("Move/Copy Your Photos"));

        ssf22.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel textPanel22 = new JPanel(new FlowLayout(FlowLayout.LEFT));

        textPanel22.setPreferredSize(new Dimension(345, 0));

        ButtonGroup buttonGroup1 = new ButtonGroup();

        moveRB = new JRadioButton("Move");
        copyRB = new JRadioButton("Copy");
        copyRB.setSelected(true);

        buttonGroup1.add(moveRB);
        buttonGroup1.add(copyRB);

        ssf22.add(textPanel22);
        ssf22.add(copyRB);
        ssf22.add(moveRB);
        centerPanel22.add(ssf22);

        ssf22.add(Box.createRigidArea(new Dimension(0, 10)));

        box2.add(centerPanel2);
        box2.add(centerPanel22);

        box2.add(Box.createVerticalGlue());

        southPanel2 = new JPanel();
        southPanel2.setLayout(new BoxLayout(southPanel2, BoxLayout.X_AXIS));
        southPanel2.add(Box.createHorizontalGlue());
        backButton2 = new JButton(new ImageIcon(getClass().getResource("images/left.png")));

        nextButton2 = new JButton(new ImageIcon(getClass().getResource("images/right.png")));
        nextButton2.setEnabled(false);

        nextButton2.addActionListener(new nextActionListener());
        backButton2.addActionListener(new backActionListener());

        southPanel2.add(backButton2);
        southPanel2.add(Box.createRigidArea(new Dimension(5, 0)));
        southPanel2.add(nextButton2);
        southPanel2.add(Box.createRigidArea(new Dimension(5, 0)));

        box2.add(southPanel2, BorderLayout.SOUTH);

        secondCardPanel.add(box2, BorderLayout.CENTER);

        ////////////////////////////////////////////////////////////////////////
        //trito parathuro tou cardLayout
        JPanel thirdCardPanel = new JPanel();
        BorderLayout b3 = new BorderLayout();
        thirdCardPanel.setLayout(b3);

        JPanel p3 = new JPanel();
        p3.setLayout(new GridLayout(1, 4));

        p3.setBorder(new LineBorder(Color.DARK_GRAY, 2));

        JPanel p31 = new JPanel(new FlowLayout());
        JPanel p32 = new JPanel(new FlowLayout());
        JPanel p33 = new JPanel(new FlowLayout());
        JPanel p34 = new JPanel(new FlowLayout());

        JLabel l31 = new JLabel("Step 1");
        l31.setFont(new Font(getName(), Font.PLAIN, 20));
        JLabel l32 = new JLabel("Step 2");
        l32.setFont(new Font(getName(), Font.PLAIN, 20));
        JLabel l33 = new JLabel("Step 3");
        l33.setForeground(Color.CYAN);
        l33.setFont(new Font(getName(), Font.PLAIN, 20));
        JLabel l34 = new JLabel("Step 4");
        l34.setFont(new Font(getName(), Font.PLAIN, 20));

        //add labels into panels
        p31.add(l31);
        p32.add(l32);
        p33.add(l33);
        p34.add(l34);

        //add panels to topPanel
        p3.add(p31);
        p3.add(p32);
        p3.add(p33);
        p3.add(p34);

        //add topPanel to firstCardPanel
        thirdCardPanel.add(p3, BorderLayout.NORTH);

        JPanel box3 = new JPanel();
        box3.setLayout(new BoxLayout(box3, BoxLayout.Y_AXIS));

        box3.add(Box.createVerticalGlue());

        ////////////////////////////
        JPanel centerPanel32 = new JPanel(new FlowLayout());

        JPanel ssf32 = new JPanel();
        //ssf32.setLayout(new BoxLayout(ssf32, BoxLayout.Y_AXIS));
        ssf32.setLayout(new GridLayout(5, 2));
        ssf32.setBorder(new TitledBorder("Choose Sorting Method"));

        ssf32.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel textPanel32 = new JPanel(new FlowLayout(FlowLayout.LEFT));

        textPanel32.setPreferredSize(new Dimension(245, 0));

        buttonGroup2 = new ButtonGroup();

        choice1 = new JRadioButton("Year\\Month(1,2,3 etc.)\\Day(1,2,3 etc.)");
        choice2 = new JRadioButton("Year\\Month(Jan,Feb,Mar etc.)");
        choice3 = new JRadioButton("Manufacturer\\Model");
        choice4 = new JRadioButton("Model\\Year\\Month(1,2,3 etc.)\\Day(1,2,3 etc.)");
        choice5 = new JRadioButton("Location");
        choice6 = new JRadioButton("Location\\Year");
        choice7 = new JRadioButton("Custom");
        buttonGroup2.add(choice1);
        buttonGroup2.add(choice5);
        buttonGroup2.add(choice2);
        buttonGroup2.add(choice6);
        buttonGroup2.add(choice3);
        buttonGroup2.add(choice7);
        buttonGroup2.add(choice4);

        JPanel c1 = new JPanel(new FlowLayout(FlowLayout.LEADING));
        c1.add(choice5);

        JPanel c2 = new JPanel(new FlowLayout(FlowLayout.LEADING));
        c2.add(choice6);

        JPanel custom = new JPanel(new FlowLayout(FlowLayout.LEADING));
        custom.add(choice7);
        edit = new JButton("Edit");
        edit.setEnabled(false);
        custom.add(edit);

        edit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (dialog == null) {
                    System.out.println("Creating new dialog");
                    dialog = new Dialog3b(Frame.this);
                }
                dialog.setVisible(true);
            }
        });

        ssf32.add(textPanel32);
        ssf32.add(choice1);
        ssf32.add(c1);
        ssf32.add(choice2);
        ssf32.add(c2);
        ssf32.add(choice3);
        ssf32.add(custom);
        ssf32.add(choice4);

        choice1.addActionListener(new radioButtonActionListener());
        choice2.addActionListener(new radioButtonActionListener());
        choice3.addActionListener(new radioButtonActionListener());
        choice4.addActionListener(new radioButtonActionListener());
        choice5.addActionListener(new radioButtonActionListener());
        choice6.addActionListener(new radioButtonActionListener());
        choice7.addActionListener(new radioButtonActionListener());

        centerPanel32.add(ssf32);

        ssf32.add(Box.createRigidArea(new Dimension(0, 10)));

        box3.add(centerPanel32);

        box3.add(Box.createVerticalGlue());

        southPanel3 = new JPanel();
        southPanel3.setLayout(new BoxLayout(southPanel3, BoxLayout.X_AXIS));
        southPanel3.add(Box.createHorizontalGlue());
        backButton3 = new JButton(new ImageIcon(getClass().getResource("images/left.png")));

        nextButton3 = new JButton(new ImageIcon(getClass().getResource("images/right.png")));
        nextButton3.setEnabled(false);

        originalList2 = new ArrayList<>();
        nextButton3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (choice7.isSelected()) {
                    if (dialog != null && dialog.isDone() == true) {
                        sourceLabel2.setText(sourceFolderTF.getText());
                        destinationLabel2.setText(destinationFolderTF.getText());
                        System.out.println("s=" + sortingMethodDialogText);
                        if (copyRB.isSelected()) {
                            moveCopyLabel2.setText("Copy");
                        } else if (moveRB.isSelected()) {
                            moveCopyLabel2.setText("Move");
                        }
                        sortingLabel2.setText(sortingMethodDialogText);
                        cdL.next(cardPanel);
                    }
                } else {
                    sourceLabel2.setText(sourceFolderTF.getText());
                    destinationLabel2.setText(destinationFolderTF.getText());

                    if (copyRB.isSelected()) {
                        moveCopyLabel2.setText("Copy");
                    } else if (moveRB.isSelected()) {
                        moveCopyLabel2.setText("Move");
                    }
                    System.out.println("str:" + stringForSelectedRB);
                    sortingLabel2.setText(destinationFolderTF.getText() + "\\" + stringForSelectedRB);

                    originalList2.clear();
                    String[] fString = stringForSelectedRB.split("\\\\");
                    for (String f : fString) {
                        originalList2.add(f);
                        originalList2.add("\\");
                    }

                    cdL.next(cardPanel);
                }

            }
        });
        backButton3.addActionListener(new backActionListener());

        southPanel3.add(backButton3);
        southPanel3.add(Box.createRigidArea(new Dimension(5, 0)));
        southPanel3.add(nextButton3);
        southPanel3.add(Box.createRigidArea(new Dimension(5, 0)));

        box3.add(southPanel3, BorderLayout.SOUTH);

        thirdCardPanel.add(box3, BorderLayout.CENTER);

        ////////////////////////////////////////////////////////////////////////
        //tetarto parathuro tou cardLayout
        fourthCardPanel = new JPanel();
        BorderLayout b4 = new BorderLayout();
        fourthCardPanel.setLayout(b4);

        JPanel p4 = new JPanel();
        p4.setLayout(new GridLayout(1, 4));

        p4.setBorder(new LineBorder(Color.DARK_GRAY, 2));

        JPanel p41 = new JPanel(new FlowLayout());
        JPanel p42 = new JPanel(new FlowLayout());
        JPanel p43 = new JPanel(new FlowLayout());
        JPanel p44 = new JPanel(new FlowLayout());

        JLabel l41 = new JLabel("Step 1");
        l41.setFont(new Font(getName(), Font.PLAIN, 20));
        JLabel l42 = new JLabel("Step 2");
        l42.setFont(new Font(getName(), Font.PLAIN, 20));
        JLabel l43 = new JLabel("Step 3");
        l43.setFont(new Font(getName(), Font.PLAIN, 20));
        JLabel l44 = new JLabel("Step 4");
        l44.setFont(new Font(getName(), Font.PLAIN, 20));
        l44.setForeground(Color.CYAN);

        //add labels into panels
        p41.add(l41);
        p42.add(l42);
        p43.add(l43);
        p44.add(l44);

        //add panels to topPanel
        p4.add(p41);
        p4.add(p42);
        p4.add(p43);
        p4.add(p44);

        //add topPanel to firstCardPanel
        fourthCardPanel.add(p4, BorderLayout.NORTH);

        JPanel box4 = new JPanel();
        box4.setLayout(new BoxLayout(box4, BoxLayout.Y_AXIS));

        box4.add(Box.createVerticalGlue());

        ////////////////////////////
        JPanel centerPanel42 = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JPanel ssf42 = new JPanel();
        ssf42.setLayout(new BoxLayout(ssf42, BoxLayout.Y_AXIS));

        JPanel sourcePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel sourceLabel1 = new JLabel("Your Source Folder is:");
        sourceLabel2 = new JTextField(30);
        sourceLabel2.setEditable(false);
        sourceLabel2.setForeground(Color.GRAY);

        sourcePanel.add(sourceLabel1);
        sourcePanel.add(sourceLabel2);

        ssf42.add(Box.createVerticalGlue());

        JPanel destinationPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel destinationLabel1 = new JLabel("Your Destination Folder is:");
        destinationLabel2 = new JTextField(30);
        destinationLabel2.setEditable(false);
        destinationLabel2.setForeground(Color.GRAY);

        destinationPanel.add(destinationLabel1);
        destinationPanel.add(destinationLabel2);

        ssf42.add(Box.createVerticalGlue());

        JPanel moveCopyPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel moveCopyLabel1 = new JLabel("You have chosen:");
        moveCopyLabel2 = new JTextField(30);
        moveCopyLabel2.setEditable(false);
        moveCopyLabel2.setForeground(Color.GRAY);

        moveCopyPanel.add(moveCopyLabel1);
        moveCopyPanel.add(moveCopyLabel2);

        ssf42.add(Box.createVerticalGlue());

        JPanel sortingPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel sortingLabel1 = new JLabel("Your sorting method is:");

        sortingLabel2 = new JTextArea(6, 28);
        sortingLabel2.setEditable(false);
        JScrollPane sortingScrollPane = new JScrollPane(sortingLabel2);
        sortingScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sortingScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        sortingLabel2.setColumns(destinationLabel2.getColumns() - 1);
        sortingLabel2.setLineWrap(true);

        sortingLabel2.setForeground(Color.GRAY);

        sortingPanel.add(sortingLabel1);
        sortingPanel.add(sortingScrollPane);

        ssf42.add(sourcePanel);
        ssf42.add(destinationPanel);
        ssf42.add(moveCopyPanel);
        ssf42.add(sortingPanel);

        centerPanel42.add(ssf42);

        box4.add(centerPanel42);

        box4.add(Box.createVerticalGlue());

        ssf42.add(Box.createRigidArea(new Dimension(0, 10)));

        southPanel4 = new JPanel();
        southPanel4.setLayout(new BoxLayout(southPanel4, BoxLayout.X_AXIS));

        backButton4 = new JButton("Change Settings");
        middleLabel4 = new JLabel("");
        nextButton4 = new JButton("SCAN");

        yearList = new ArrayList<>();
        monthNumberList = new ArrayList<>();
        monthCharList = new ArrayList<>();
        dateNumberList = new ArrayList<>();
        dateCharList = new ArrayList<>();
        locationList = new ArrayList<>();
        dimensionsList = new ArrayList<>();
        manufacturerList = new ArrayList<>();
        modelList = new ArrayList<>();

        nextButton4.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                progressBar.setString(fil.size() + " files processed");
                progressBar.setStringPainted(true);

                sourceRightLabel.setText(sourceFolderTF.getText());
                destinationRightLabel.setText(destinationLabel2.getText());
                progressMonitor = new ProgressMonitor(Frame.this, "Scanning files...", "", 0, 100);

                progressMonitor.setMillisToDecideToPopup(100);
                progressMonitor.setMillisToPopup(500);
                task = new Task();
                task.addPropertyChangeListener(new PropertyChangeListener() {

                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        if ("progress" == evt.getPropertyName()) {
                            int progress = (Integer) evt.getNewValue();

                            progressMonitor.setProgress(progress);
                            String message
                                    = String.format("Completed %d%%.\n", progress);
                            progressMonitor.setNote(message);
                            if (progressMonitor.isCanceled() || task.isDone()) {

                                Toolkit.getDefaultToolkit().beep();
                                if (progressMonitor.isCanceled()) {

                                    task.cancel(true);
                                } else {
                                    Frame.this.toFront();
                                    System.out.println("doneeeeeeee");
                                }
                            }
                        }
                    }
                });
                task.execute();

                if (newStartButton.isSelected()) {
                    try {
//                        PrintWriter out = new PrintWriter("src\\ptux\\settings\\latestsettings.txt");
//                        PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream("src\\ptux\\settings\\latestsettings.txt"), "UTF-8"));
                        PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(new File(System.getProperty("java.io.tmpdir") + "latestsettings.txt")), "UTF-8"));

                        out.println(destinationFolderTF.getText());
                        String s = "";
                        for (int i = 0; i < originalList2.size(); i++) {
                            s += originalList2.get(i);
                        }
                        out.println(s);
                        if (copyRB.isSelected()) {
                            out.println("Copy");
                        } else if (moveRB.isSelected()) {
                            out.println("Move");
                        }
                        out.close();
                    } catch (Exception ee) {

                    }
                    try {
//                        PrintWriter out = new PrintWriter(new File(System.getProperty("java.io.tmpdir")+"originalList.txt"));
//                        PrintWriter out = new PrintWriter("src\\ptux\\settings\\originalList.txt");
//                        PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream("src\\ptux\\settings\\originalList.txt"), "UTF-8"));
                        PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(new File(System.getProperty("java.io.tmpdir") + "originalList.txt")), "UTF-8"));
                        for (int i = 0; i < originalList2.size(); i++) {
                            out.println(originalList2.get(i));
                        }

                        out.close();
                    } catch (Exception eee) {
                        System.out.println("Den egrapsa sto arxeio");

                    }
                } else if (keepLatestButton.isSelected()) {
//                    try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("settings/originalList.txt"), "UTF-8"))) {
//                    try (BufferedReader br = new BufferedReader(new FileReader(new File("src\\ptux\\settings\\originalList.txt")))) {
//                    try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src\\ptux\\settings\\originalList.txt"), "UTF-8"))) {
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(System.getProperty("java.io.tmpdir") + "originalList.txt"))))) {

                        String line = br.readLine();
                        originalList2 = new ArrayList<>();
                        while (line != null) {
                            originalList2.add(line);
                            System.out.println("line2:" + line);
                            line = br.readLine();
                        }
                    } catch (Exception eee) {
                        System.out.println("Sfalma sto arxeio2");
                    }

//                    for (int i = 0; i < originalList2.size(); i++) {
//                        System.out.println("o=" + originalList2.get(i));
//                    }
                }

            }
        });
        backButton4.addActionListener(new firstCardActionListener());

        southPanel4.add(Box.createRigidArea(new Dimension(5, 0)));
        southPanel4.add(backButton4);
        southPanel4.add(Box.createHorizontalGlue());
        southPanel4.add(middleLabel4);
        southPanel4.add(Box.createHorizontalGlue());
        southPanel4.add(nextButton4);
        southPanel4.add(Box.createRigidArea(new Dimension(5, 0)));

        box4.add(southPanel4, BorderLayout.SOUTH);

        fourthCardPanel.add(box4, BorderLayout.CENTER);

        ////////////////////////////////////////////////////////////////////////
        //pempto parathuro tou cardLayout
        JPanel fifthCardPanel = new JPanel();
        BorderLayout b5 = new BorderLayout();
        fifthCardPanel.setLayout(b5);

        JMenu menu1 = new JMenu("File");
        JMenu menu2 = new JMenu("View");
        JMenu menu3 = new JMenu("About");

        newSortItem = new JMenuItem("New Sort");
        newSortItem.setIcon(new ImageIcon(getClass().getResource("images/organize1.jpg")));
        newSortItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int answer = JOptionPane.showConfirmDialog(Frame.this,
                        "Would you like to reset all of your settings\n"
                        + "and restart the program?",
                        "Restart the program",
                        JOptionPane.YES_NO_OPTION);
                if (answer == JOptionPane.YES_OPTION) {
                    dispose();
                    new Frame().setVisible(true);
                }
            }
        });

        startItem = new JMenuItem("Start/Resume");
        startItem.setIcon(new ImageIcon(getClass().getResource("images/button_black_play2.png")));
        startItem.addActionListener(new startResumeActionListener());

        pauseItem = new JMenuItem("Pause");
        pauseItem.setIcon(new ImageIcon(getClass().getResource("images/button_black_pause2.png")));
        pauseItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                startPauseButton.setEnabled(true);
                pauseButton.setEnabled(false);

                startItem.setEnabled(true);
                pauseItem.setEnabled(false);

                worker.pause();
            }
        });

        stopItem = new JMenuItem("Stop");
        stopItem.setIcon(new ImageIcon(getClass().getResource("images/button_black_stop2.png")));
        stopItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                startPauseButton.setEnabled(false);
                pauseButton.setEnabled(false);
                stopButton.setEnabled(false);

                startItem.setEnabled(false);
                pauseItem.setEnabled(false);
                stopItem.setEnabled(false);
                newSortItem.setEnabled(true);
                exitItem.setEnabled(true);

                worker.cancel(true);
            }
        });

        exitItem = new JMenuItem("Exit");
        exitItem.setIcon(new ImageIcon(getClass().getResource("images/button_red_exit2.png")));
        exitItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                System.exit(0);
            }
        });

        JMenuItem sourceFolderItem = new JMenuItem("Open Source Folder");
        sourceFolderItem.setIcon(new ImageIcon(getClass().getResource("images/folder_yellow_pics_2.png")));
        sourceFolderItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String[] temp = sourceRightLabel.getText().split(";");
                    for (String t : temp) {
                        Desktop.getDesktop().open(new File(t));
                    }
                } catch (IOException ioe) {
                    System.out.println("O fakelos den anoikse: " + ioe.getMessage());
                }
            }
        });

        JMenuItem destinationFolderItem = new JMenuItem("Open Destination Folder");
        destinationFolderItem.setIcon(new ImageIcon(getClass().getResource("images/folder_blue_pics_2.png")));
        destinationFolderItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().open(new File(destinationRightLabel.getText()));
                } catch (IOException ioe) {
                    System.out.println("O fakelos den anoikse: " + ioe.getMessage());
                }
            }
        });
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.setIcon(new ImageIcon(getClass().getResource("images/info2.png")));
        aboutItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Frame.this,
                        "Στάμος Βασίλειος | Γεροδήμος Βασίλειος\n"
                        + "2025200900070   | 2025200900014",
                        "Sort my pictures",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        menu1.add(newSortItem);
        menu1.addSeparator();
        menu1.add(startItem);
        menu1.add(pauseItem);
        menu1.add(stopItem);
        menu1.addSeparator();
        menu1.add(exitItem);

        menu2.add(sourceFolderItem);
        menu2.add(destinationFolderItem);

        menu3.add(aboutItem);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menu1);
        menuBar.add(menu2);
        menuBar.add(menu3);

        //topothetoume to menuBar sto pempto panel mono
        fifthCardPanel.add(menuBar, BorderLayout.NORTH);

        JPanel box5 = new JPanel();
        box5.setLayout(new BoxLayout(box5, BoxLayout.Y_AXIS));

        box5.add(Box.createVerticalGlue());
        JPanel centerPanel5 = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JPanel ssf5 = new JPanel();
        ssf5.setLayout(new BoxLayout(ssf5, BoxLayout.Y_AXIS));

        ssf5.add(Box.createRigidArea(new Dimension(0, 5)));

        JPanel sourcePanel5 = new JPanel();
        sourcePanel5.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JLabel sourceLeftLabel = new JLabel("Source Folder");
        sourceRightLabel = new JTextField("", 30);
        sourceRightLabel.setForeground(Color.gray);
        sourceRightLabel.setEditable(false);

        sourcePanel5.add(sourceLeftLabel);
        sourcePanel5.add(sourceRightLabel);

        JPanel destinationPanel5 = new JPanel();
        destinationPanel5.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JLabel destinationLeftLabel = new JLabel("Destination Folder");
        destinationRightLabel = new JTextField("", 30);
        destinationRightLabel.setForeground(Color.gray);
        destinationRightLabel.setEditable(false);

        destinationPanel5.add(destinationLeftLabel);
        destinationPanel5.add(destinationRightLabel);

        JPanel buttonsPanel5 = new JPanel();
        buttonsPanel5.setLayout(new BoxLayout(buttonsPanel5, BoxLayout.X_AXIS));

        startPauseButton = new JButton(new ImageIcon(getClass().getResource("images/button_black_play1.png")));
        flagForSPButton = 0;
        startPauseButton.addActionListener(new startResumeActionListener());

        stopButton = new JButton(new ImageIcon(getClass().getResource("images/button_black_stop1.png")));
        stopButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                startPauseButton.setEnabled(false);
                pauseButton.setEnabled(false);
                stopButton.setEnabled(false);

                newSortItem.setEnabled(true);
                startItem.setEnabled(false);
                pauseItem.setEnabled(false);
                stopItem.setEnabled(false);
                exitItem.setEnabled(true);

                bottomLabel5.setText("Canceled");
                bottomIcon5.setIcon(null);

                worker.cancel(true);
            }
        });
        pauseButton = new JButton(new ImageIcon(getClass().getResource("images/button_black_pause1.png")));
        pauseButton.setEnabled(false);
        pauseItem.setEnabled(false);
        stopItem.setEnabled(false);
        stopButton.setEnabled(false);
        pauseButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                startPauseButton.setEnabled(true);
                pauseButton.setEnabled(false);

                startItem.setEnabled(true);
                pauseItem.setEnabled(false);

                bottomLabel5.setText("");
                bottomIcon5.setIcon(new ImageIcon(getClass().getResource("images/stopped-loading.jpg")));

                worker.pause();
            }
        });

        buttonsPanel5.add(startPauseButton);
        buttonsPanel5.add(Box.createRigidArea(new Dimension(5, 0)));
        buttonsPanel5.add(pauseButton);
        buttonsPanel5.add(Box.createRigidArea(new Dimension(5, 0)));
        buttonsPanel5.add(stopButton);

        ssf5.add(sourcePanel5);
        ssf5.add(destinationPanel5);

        progressBar = new JProgressBar();
        progressBar.setValue(0);

        ssf5.add(Box.createRigidArea(new Dimension(0, 20)));
        ssf5.add(progressBar);

        ssf5.add(Box.createRigidArea(new Dimension(0, 50)));
        ssf5.add(buttonsPanel5);

        ssf5.add(Box.createRigidArea(new Dimension(0, 5)));

        centerPanel5.add(ssf5);
        box5.add(centerPanel5);

        box5.add(Box.createVerticalGlue());

        fifthCardPanel.add(box5, BorderLayout.CENTER);

        JPanel bottomPanel5 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel5.setBorder(new LineBorder(Color.GRAY, 1));
        bottomLabel5 = new JLabel("Ready to sort");

        bottomIcon5 = new JLabel("    ");
        bottomLabel5.setForeground(Color.DARK_GRAY);
        bottomPanel5.add(bottomLabel5);
        bottomPanel5.add(bottomIcon5);

        fifthCardPanel.add(bottomPanel5, BorderLayout.SOUTH);

        cardPanel.add(zeroCardPanel, "zeroCardPanel");
        cardPanel.add(firstCardPanel, "firstCardPanel");
        cardPanel.add(secondCardPanel, "secondCardPanel");
        cardPanel.add(thirdCardPanel, "thirdCardPanel");
        cardPanel.add(fourthCardPanel, "fourthCardPanel");
        cardPanel.add(fifthCardPanel, "fifthCardPanel");

        add(cardPanel, BorderLayout.CENTER);
    }

    public void setOriginalList(ArrayList<String> originalList2) {
        this.originalList2 = originalList2;
    }

    public void setSortingMethodDialogText(String sortingMethodDialogText) {
        System.out.println(sortingMethodDialogText);
        this.sortingMethodDialogText = sortingMethodDialogText;
    }

    public void setDestinationFolderTF(JTextField destinationFolderTF) {
        this.destinationFolderTF = destinationFolderTF;
    }

    public JTextField getDestinationFolderTF() {
        System.out.println(destinationFolderTF.getText());
        return destinationFolderTF;
    }

    public boolean isLocationTagSelected() {
        return locationTagSelected;
    }

    public JButton getNextButton3() {
        return nextButton3;
    }

    private class radioButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            stringForSelectedRB = "";

            if (choice7.isSelected() == true) {
                edit.setEnabled(true);
                if (dialog == null || dialog.isDone() == false) {
                    nextButton3.setEnabled(false);
                } else {
                    nextButton3.setEnabled(true);
                }
            } else if (choice1.isSelected()) {
                stringForSelectedRB = choice1.getText() + "\\";
                edit.setEnabled(false);
                nextButton3.setEnabled(true);
            } else if (choice2.isSelected()) {
                stringForSelectedRB = choice2.getText() + "\\";
                edit.setEnabled(false);
                nextButton3.setEnabled(true);
            } else if (choice3.isSelected()) {
                stringForSelectedRB = choice3.getText() + "\\";
                edit.setEnabled(false);
                nextButton3.setEnabled(true);
            } else if (choice4.isSelected()) {
                stringForSelectedRB = choice4.getText() + "\\";
                edit.setEnabled(false);
                nextButton3.setEnabled(true);
            } else if (choice5.isSelected()) {
                stringForSelectedRB = choice5.getText() + "\\";
                edit.setEnabled(false);
                nextButton3.setEnabled(true);
            } else if (choice6.isSelected()) {
                stringForSelectedRB = choice6.getText() + "\\";
                edit.setEnabled(false);
                nextButton3.setEnabled(true);
            }

        }
    }

    private class nextActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            cdL.next(cardPanel);
        }
    }

    private class backActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            cdL.previous(cardPanel);
        }
    }

    private class firstCardActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            cdL.first(cardPanel);
        }
    }

    private class startResumeActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (flagForSPButton == 1) {
                startPauseButton.setEnabled(false);
                pauseButton.setEnabled(true);
                stopButton.setEnabled(true);

                startItem.setEnabled(false);
                pauseItem.setEnabled(true);
                stopItem.setEnabled(true);

                startPauseButton.setIcon(new ImageIcon(getClass().getResource("images/button_black_repeat1.png")));

                bottomLabel5.setText("");
                bottomIcon5.setIcon(new ImageIcon(getClass().getResource("images/anigif2.gif")));

                worker.resume();
            }
            if (flagForSPButton == 0) {
                startPauseButton.setEnabled(false);
                pauseButton.setEnabled(true);
                stopButton.setEnabled(true);

                startItem.setEnabled(false);
                pauseItem.setEnabled(true);
                stopItem.setEnabled(true);
                exitItem.setEnabled(false);
                newSortItem.setEnabled(false);

                bottomLabel5.setText("");
                bottomIcon5.setIcon(new ImageIcon(getClass().getResource("images/anigif2.gif")));

                worker = new PausableSwingWorker<Void, String>() {
                    @Override
                    protected Void doInBackground() throws Exception {

                        //Initialize progress property.
                        setProgress(0);

                        if (startPauseButton.getIcon().toString().equals(getClass().getResource("images/button_black_play1.png"))) {
                            startPauseButton.setIcon(new ImageIcon(getClass().getResource("images/button_black_repeat1.png")));
                        }
                        String s = "";
                        File f;

                        setProgress(0);
                        progressBar.setValue(0);
                        progressBar.setString("0%");
                        progressBar.setMinimum(0);
                        progressBar.setMaximum(100);
                        progressBar.setStringPainted(true);

                        for (int j = 0; j < fil.size(); j++) {

                            if (isCancelled()) {
                                break;
                            }
                            if (!isPaused()) {

//                                try {
//                                    Thread.sleep(100);
//                                } catch (Exception ex) {
//
//                                }
                                if (newStartButton.isSelected()) {
                                    s = destinationFolderTF.getText() + "\\";
                                } else if (keepLatestButton.isSelected()) {
                                    s = keepLatestDestinationTextField.getText() + "\\";
                                }
//                                System.out.println(s);

                                if (keepLatestButton.isSelected()) {
                                    if (!new File(s.toString()).exists()) {
                                        new File(s.toString()).mkdirs();
                                    }
                                }

                                for (int i = 0; i < originalList2.size(); i++) {
                                    if (originalList2.get(i).equals("Year")) {
                                        s += yearList.get(j);
                                    } else if (originalList2.get(i).equals("Month(1,2,3 etc.)")) {
                                        s += monthNumberList.get(j);
                                    } else if (originalList2.get(i).equals("Month(Jan,Feb,Mar etc.)")) {
                                        s += monthCharList.get(j);
                                    } else if (originalList2.get(i).equals("Day(1,2,3 etc.)")) {
                                        s += dateNumberList.get(j);
                                    } else if (originalList2.get(i).equals("Day(Mon,Tue,Wed etc.)")) {
                                        s += dateCharList.get(j);
                                    } else if (originalList2.get(i).equals("Location")) {
                                        s += locationList.get(j);
                                    } else if (originalList2.get(i).equals("Manufacturer")) {
                                        s += manufacturerList.get(j);
                                    } else if (originalList2.get(i).equals("Model")) {
                                        s += modelList.get(j);
                                    } else if (originalList2.get(i).equals("Dimensions")) {
                                        s += dimensionsList.get(j);
                                    } else {
                                        s += originalList2.get(i);
                                    }

//                                    System.out.println(s);
                                    byte[] bytes = s.getBytes("UTF-8");
                                    String out = new String(bytes, "UTF-8");
                                    f = new File(out);

                                    if (originalList2.get(i).equals("\\")) {
                                        if (f.mkdir()) {
                                            System.out.println("Eftiaksa fakelo");
                                        } else {
                                            System.out.println("Den eftiaksa fakelo");
                                        }
                                    }

                                }

                                try {
                                    f = new File(s + "\\" + fil.get(j).getName());
                                    if (moveCopyLabel2.getText().equals("Copy")) {
                                        if (!f.exists()) {
                                            Files.copy(fil.get(j).toPath(), f.toPath(), StandardCopyOption.COPY_ATTRIBUTES);
                                        } else {
                                            int ctr = 1;
                                            String temp = "Copy " + ctr + "-";
                                            temp += fil.get(j).getName();
                                            f = new File(s + "\\" + temp);
                                            while (f.exists()) {
                                                ctr++;
                                                temp = "Copy " + ctr + "-";
                                                temp += fil.get(j).getName();
                                                f = new File(s + "\\" + temp);
                                            }
                                            Files.copy(fil.get(j).toPath(), f.toPath(), StandardCopyOption.COPY_ATTRIBUTES);
                                        }
                                    } else if (moveCopyLabel2.getText().equals("Move")) {
                                        if (!f.exists()) {
                                            Files.move(fil.get(j).toPath(), f.toPath(), StandardCopyOption.ATOMIC_MOVE);
                                        } else {
                                            int ctr = 1;
                                            String temp = "Copy " + ctr + "-";
                                            temp += fil.get(j).getName();
                                            f = new File(s + "\\" + temp);
                                            while (f.exists()) {
                                                ctr++;
                                                temp = "Copy " + ctr + "-";
                                                temp += fil.get(j).getName();
                                                f = new File(s + "\\" + temp);
                                            }
                                            Files.move(fil.get(j).toPath(), f.toPath(), StandardCopyOption.ATOMIC_MOVE);
                                        }

                                    }

                                } catch (IOException ee) {
                                    System.out.println("H metakinisi tou arxeiou apetuxe: " + ee.getMessage());
                                }
                                if (fil.get(j).getParentFile().list().length == 0) {
                                    fil.get(j).getParentFile().delete();
                                }
                                ctrForProgress = j + 1;
                                setProgress((ctrForProgress * 100) / fil.size());

//                                System.out.println("Pr22222222" + getProgress());
                            } else {
                                j--;

                                Thread.sleep(200);
                            }

                            if (ctrForProgress == fil.size()) {
                                startPauseButton.setIcon(new ImageIcon(getClass().getResource("images/button_black_play1.png")));
                            }
                        }

                        return null;
                    }

                    public void done() {
                        Toolkit.getDefaultToolkit().beep();

                        if (getProgress() == 100) {
                            startPauseButton.setEnabled(false);
                            pauseButton.setEnabled(false);
                            stopButton.setEnabled(false);

                            startItem.setEnabled(false);
                            pauseItem.setEnabled(false);
                            stopItem.setEnabled(false);
                            newSortItem.setEnabled(true);
                            exitItem.setEnabled(true);

                            bottomLabel5.setText("Done!");
                            bottomIcon5.setIcon(null);
                        }
//                        System.out.println("Pr3333333333" + getProgress());
                    }

                };
                worker.addPropertyChangeListener(new PropertyChangeListener() {
                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        if ("paused".equals(evt.getPropertyName())) {
                            String text = (Boolean) evt.getNewValue() ? "Paused..." : "Resumed...";
                            //textArea.append(String.format("%s%n", text));
                            System.out.println(String.format("%s%n", text));
                        } else if ("progress" == evt.getPropertyName()) {
                            int progress = (Integer) evt.getNewValue();

                            progressBar.setValue(progress);
                            String message
                                    = String.format("Completed %d%%.\n", progress);
                            progressBar.setString(message);
                        }
                    }
                });
                worker.execute();

                flagForSPButton = 1;
            }
        }
    }

    class Task extends SwingWorker<Void, Void> {

        @Override
        public Void doInBackground() {

            int progress = 0;
            setProgress(0);
            Frame.this.setEnabled(false);
            locationTagSelected = false;

            try {

                for (int x = 0; x < originalList2.size(); x++) {
                    if (originalList2.get(x).equals("Location")) {
                        locationTagSelected = true;
                    }
                }
//                System.out.println("loccccccccc:" + locationTagSelected);
                middleLabel4.setIcon(new ImageIcon(getClass().getResource("images/anigif2.gif")));

                ReverseGeoCode reverseGeoCode = null;
                if (locationTagSelected == true) {
                    middleLabel4.setText("Reading location text");
                    System.out.println("Reading txt with places");
                    reverseGeoCode = new ReverseGeoCode(getClass().getResourceAsStream("settings/cities1000.txt"), true);
                    ArrayList<GeoName> arPlaceNames;
                    arPlaceNames = reverseGeoCode.getArPlaceNames();
                }
                middleLabel4.setText("");

                for (int i = 0; i < fil.size(); i++) {

                    if (task.isCancelled()) {
                        yearList.clear();
                        monthNumberList.clear();
                        monthCharList.clear();
                        dateNumberList.clear();
                        dateCharList.clear();
                        locationList.clear();
                        manufacturerList.clear();
                        dimensionsList.clear();
                        modelList.clear();
                        setProgress(0);
                        cdL.show(cardPanel, "fourthCardPanel");
                        Frame.this.toFront();
                        middleLabel4.setIcon(null);
                        break;
                    }

                    TagGenerator t = new TagGenerator(fil.get(i).toString(), locationTagSelected, reverseGeoCode);
//                    System.out.println("loccccccccc:" + locationTagSelected);

                    yearList.add(t.getYear());
                    monthNumberList.add(t.getMonthNumber());
                    monthCharList.add(t.getMonthChar());
                    dateNumberList.add(t.getDateNumber());
                    dateCharList.add(t.getDateChar());
                    locationList.add(t.getLocation());
                    dimensionsList.add(t.getDimensions());
                    manufacturerList.add(t.getManufacturer());
                    modelList.add(t.getModel());

                    ctrForProgress = i + 1;

                    progress = (ctrForProgress * 100) / fil.size();

                    setProgress(Math.min(progress, 100));

                }

            } catch (Exception eeeeee) {

            }
//            for (int i = 0; i < fil.size(); i++) {
//                System.out.println("YearList:" + yearList.get(i));
//                System.out.println("monthNumberList:" + monthNumberList.get(i));
//                System.out.println("monthCharList:" + monthCharList.get(i));
//                System.out.println("dateNumberList:" + dateNumberList.get(i));
//                System.out.println("dateCharList:" + dateCharList.get(i));
//                System.out.println("locationList:" + locationList.get(i));
//                System.out.println("dimensionsList:" + dimensionsList.get(i));
//                System.out.println("manufacturerList:" + manufacturerList.get(i));
//                System.out.println("ModelList:" + modelList.get(i));
//
//            }

            //setProgress(100);
            return null;

        }

        @Override
        public void done() {
            Toolkit.getDefaultToolkit().beep();

            if (getProgress() == 100) {
                cdL.next(cardPanel);
            } else {
                cdL.show(cardPanel, "fourthCardPanel");
            }
            middleLabel4.setIcon(null);
            Frame.this.setEnabled(true);
            
        }
    }

    abstract class PausableSwingWorker<K, V> extends SwingWorker<K, V> {

        private volatile boolean isPaused;

        public final void pause() {
            if (!isPaused() && !isDone()) {
                isPaused = true;
                firePropertyChange("paused", false, true);
            }
        }

        public final void resume() {
            if (isPaused() && !isDone()) {
                isPaused = false;
                firePropertyChange("paused", true, false);
            }
        }

        public final boolean isPaused() {
            return isPaused;
        }
    }
}
