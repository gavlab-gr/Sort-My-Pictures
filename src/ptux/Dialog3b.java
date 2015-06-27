/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ptux;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Vasileios Gerodimos A.M. 2025200900014
 *
 * @author Vasileios Stamos A.M. 2025200900070
 */
public class Dialog3b extends JDialog {

    private DefaultListModel<String> leftListModel, rightListModel;
    private JList<String> leftList, rightList;
    private JButton folderButton, unionButton, deleteButton, cancelButton, doneButton, tickButton;
    private JTextField separatorTF, pathTF, leftTF;
    private int counter = 0;
    private String unionString = ",";
    private ArrayList<String> originalList = null, originalList2 = null;
    private ArrayList<Integer> counterList = null;
    private Frame parent1;
    private boolean done = false;

    public Dialog3b(Frame parent) {

        super(parent, true);
        parent1 = parent;

        setTitle("Custom settings");
        setSize(800, 500);
        setResizable(false);
        setLocationRelativeTo(parent);
        setMaximumSize(new Dimension(800, 500));
        setMinimumSize(new Dimension(800, 500));
        setLayout(new BorderLayout());

        //JPanel centerPanel = new JPanel(new FlowLayout());
        JPanel verticalBox = new JPanel();
        verticalBox.setLayout(new BoxLayout(verticalBox, BoxLayout.Y_AXIS));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

        JPanel leftTopPanel = new JPanel();
        leftTopPanel.setLayout(new BoxLayout(leftTopPanel, BoxLayout.X_AXIS));
        leftTopPanel.setPreferredSize(new Dimension(283, 25));
        leftTopPanel.setMaximumSize(new Dimension(283, 25));

        leftTF = new JTextField(5);
        leftTF.setText(",");
        leftTF.setHorizontalAlignment(JTextField.CENTER);

        leftTF.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                myChanges();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                myChanges();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                myChanges();
            }

            private void myChanges() {
                if (leftTF.getText().isEmpty()) {
                    tickButton.setEnabled(false);
                } else {
                    tickButton.setEnabled(true);
                }

                if (leftTF.getText().matches(".*[/\\\\:*?\"<>|]")) {
                    JOptionPane.showMessageDialog(Dialog3b.this, "A file name can't contains any of the following characters:\n         \\ / : * ? \" < > |", "Input Error!!!", JOptionPane.WARNING_MESSAGE);
                    tickButton.setEnabled(false);
                } else {
                    if (leftTF.getText().isEmpty()) {
                        tickButton.setEnabled(false);
                    } else {
                        tickButton.setEnabled(true);
                    }
                }
            }
        });

        leftTF.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
                myMouse();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                myMouse();
            }

            private void myMouse() {
                leftTF.setToolTipText("<html>"
                        + "A file name can't contains any of the following characters:"
                        + "<br>"
                        + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                        + "\\ / : * ? \" < > |"
                        + "</html>");
            }
        });

        JPanel emptyPanel = new JPanel();
        tickButton = new JButton(new ImageIcon(getClass().getResource("images/tick_1.png")));

        tickButton.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
                myMouse();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                myMouse();
            }

            private void myMouse() {
                tickButton.setToolTipText("<html>"
                        + "Changes union's seperation characters between two labels."
                        + "<br>"
                        + "Uses the text that is in the left text field."
                        + "</html>");
            }
        });
        
        
        tickButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                unionString = leftTF.getText();
                int selectedIndex = rightList.getSelectedIndex();

////////////////////////////////////////////////////////////////////////////////
//eisagwgi tou unionString stin originalList2 kathe fora pou to allazei o xristis
                for (int i = 0; i < originalList2.size(); i++) {
                    if (i % 2 != 0 && originalList2.get(i).equals("\\") == false) {
                        System.out.println(i);
                        originalList2.set(i, unionString);
                    }
                }

////////////////////////////////////////////////////////////////////////////////
//ta parapanw swsta dedomena tis originalList2 ta pername kai stis rightListModel
//kai originalList
                rightListModel.clear();
                originalList.clear();
                counter = 0;
                int j = 0;
                for (int i = j; i < originalList2.size(); i++) {
                    String temp = "";

                    for (j = i; j < originalList2.size(); j++) {
                        if (originalList2.get(j).equals("\\") == true) {
                            break;
                        } else {
                            temp += originalList2.get(j);
                        }
                    }
                    i = j;
                    if (rightListModel.isEmpty()) {
                        rightListModel.addElement("Folder:" + temp);
                    } else {
                        counter++;
                        rightListModel.addElement("SubFolder " + counter + ":" + temp);
                    }
                    rightList.setSelectedIndex(selectedIndex);
                    originalList.add(temp);
                }

                setPathText();

                System.out.println("tick");
                for (int i = 0; i < originalList2.size(); i++) {
                    System.out.println(originalList2.get(i));
                }
                System.out.println();
            }
        }
        );

        leftTF.setMaximumSize(
                new Dimension(tickButton.getPreferredSize().width, tickButton.getPreferredSize().height));

        topPanel.add(Box.createHorizontalGlue());
        leftTopPanel.add(leftTF);

        leftTopPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        leftTopPanel.add(tickButton);

        topPanel.add(leftTopPanel);

        topPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        emptyPanel.setPreferredSize(leftTopPanel.getPreferredSize());
        emptyPanel.setMaximumSize(leftTopPanel.getMaximumSize());
        topPanel.add(emptyPanel);

        topPanel.add(Box.createHorizontalGlue());

        JPanel firstPanel = new JPanel();

        firstPanel.setLayout(
                new BoxLayout(firstPanel, BoxLayout.X_AXIS));

        leftListModel = new DefaultListModel<>();
        rightListModel = new DefaultListModel<>();
        originalList = new ArrayList<>();
        originalList2 = new ArrayList<>();
        counterList = new ArrayList<>();

        leftList = new JList<>(leftListModel);

        leftList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        leftList.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                if (leftList.isSelectionEmpty()) {
                    folderButton.setEnabled(false);
//                    unionButton.setEnabled(false);
                } else {
                    folderButton.setEnabled(true);
//                    unionButton.setEnabled(true);

                }
            }
        });

        JScrollPane leftScrollPane = new JScrollPane(leftList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        leftScrollPane.setPreferredSize(new Dimension(leftTopPanel.getPreferredSize().width, 200));
        leftScrollPane.setMaximumSize(new Dimension(leftTopPanel.getPreferredSize().width, 1000));
        firstPanel.add(Box.createHorizontalGlue());
        firstPanel.add(leftScrollPane);
        firstPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        addLeftValues();

        rightList = new JList<>(rightListModel);
        rightList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        rightListModel.addListDataListener(new ListDataListener() {

            @Override
            public void intervalAdded(ListDataEvent e) {
                myChanges();
            }

            @Override
            public void intervalRemoved(ListDataEvent e) {
                myChanges();
            }

            @Override
            public void contentsChanged(ListDataEvent e) {
                myChanges();
            }

            private void myChanges() {
                if (rightListModel.isEmpty() == true) {
                    deleteButton.setEnabled(false);
                    unionButton.setEnabled(false);
                    doneButton.setEnabled(false);
                } else {
                    deleteButton.setEnabled(true);
                    unionButton.setEnabled(true);
                    doneButton.setEnabled(true);
                }

            }
        });

        JScrollPane rightScrollPane = new JScrollPane(rightList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        rightScrollPane.setPreferredSize(leftScrollPane.getPreferredSize());
        rightScrollPane.setMaximumSize(leftScrollPane.getMaximumSize());
        firstPanel.add(rightScrollPane);
        firstPanel.add(Box.createHorizontalGlue());

        JPanel secondHelpPanel = new JPanel(new BorderLayout());
        secondHelpPanel.setPreferredSize(new Dimension(10 + 2 * leftTopPanel.getPreferredSize().width, leftTopPanel.getPreferredSize().height));
        secondHelpPanel.setMaximumSize(new Dimension(10 + 2 * leftTopPanel.getMaximumSize().width, leftTopPanel.getMaximumSize().height));
        JPanel secondPanel = new JPanel();
        secondPanel.setLayout(new BoxLayout(secondPanel, BoxLayout.X_AXIS));

        folderButton = new JButton("Folder");
        separatorTF = new JTextField(5);
        separatorTF.setHorizontalAlignment(JTextField.CENTER);
        unionButton = new JButton("Union");
        deleteButton = new JButton("Delete");

        folderButton.setEnabled(false);
        unionButton.setEnabled(false);
        deleteButton.setEnabled(false);

        folderButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String temp = leftList.getSelectedValue();
                int flag = 0;

                for (int i = 0; i < leftListModel.getSize(); i++) {
                    for (int j = 0; j < originalList.size(); j++) {
                        if (originalList.get(j).contains(temp)) {
                            flag = 1;
                        }
                    }
                }

                if (flag == 0) {
                    if (rightListModel.isEmpty()) {
                        rightListModel.addElement("Folder:" + temp);
                        counterList.add(2);
                    } else {
                        counter++;
                        rightListModel.addElement("SubFolder " + counter + ":" + temp);
                        counterList.add(2);
                    }
                    rightList.setSelectedIndex(rightListModel.getSize() - 1);
                    originalList.add(temp);
                    originalList2.add(temp);
                    originalList2.add("\\");

                    setPathText();
                } else {
                    JOptionPane.showMessageDialog(Dialog3b.this, "Please select another item to be inserted in the list.", "Your selection is already inserted!!!", JOptionPane.WARNING_MESSAGE);
                }

                for (int i = 0; i < originalList.size(); i++) {
                    System.out.println(originalList.get(i));

                }
                System.out.println("");

                for (int i = 0; i < originalList2.size(); i++) {
                    System.out.println(originalList2.get(i));

                }
                System.out.println("");

                for (int i = 0; i < counterList.size(); i++) {
                    System.out.println(counterList.get(i));

                }
                System.out.println("");

            }
        });

        unionButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String temp = rightList.getSelectedValue();
                String temp2 = leftList.getSelectedValue();
                int temp3 = rightList.getSelectedIndex();
                int flag = 0;

                for (int i = 0; i < leftListModel.getSize(); i++) {
                    for (int j = 0; j < originalList.size(); j++) {
                        if (originalList.get(j).contains(temp2)) {
                            flag = 1;
                        }
                    }
                }

                if (flag == 0) {
                    rightListModel.removeElement(rightList.getSelectedValue());

//                    rightListModel.add(temp3, temp + "-" + temp2);
                    rightListModel.add(temp3, temp + unionString + temp2);

                    rightList.setSelectedIndex(temp3);
//                    originalList.add(temp3, originalList.get(temp3) + "-" + temp2);
                    originalList.add(temp3, originalList.get(temp3) + unionString + temp2);
                    originalList.remove(temp3 + 1);

                    int tade = 0;
                    for (int i = 0; i <= temp3; i++) {
                        tade += counterList.get(i);
                    }

                    originalList2.add(tade - 1, temp2);
//                    originalList2.add(tade - 1, "-");
                    originalList2.add(tade - 1, unionString);

                    counterList.add(temp3, 2 + counterList.get(temp3));
                    counterList.remove(temp3 + 1);

                    setPathText();
                } else {
                    JOptionPane.showMessageDialog(Dialog3b.this, "Your selection is already inserted", "Your selection is already inserted", JOptionPane.WARNING_MESSAGE);
                }

                for (int i = 0; i < originalList.size(); i++) {
                    System.out.println(originalList.get(i));

                }
                System.out.println("");

                for (int i = 0; i < originalList2.size(); i++) {
                    System.out.println(originalList2.get(i));

                }
                System.out.println("");

                for (int i = 0; i < counterList.size(); i++) {
                    System.out.println(counterList.get(i));

                }
                System.out.println("");

            }
        });

        deleteButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String temp = rightList.getSelectedValue();
                int temp2 = rightList.getSelectedIndex();

                if (rightListModel.get(temp2).contains("SubFolder")) {
                    counter--;
                }

                rightListModel.removeElement(temp);
                originalList.remove(temp2);

                if (temp2 == 0) {
                    for (int i = 0; i < counterList.get(temp2); i++) {
                        originalList2.remove(temp2);
                    }
                } else {
                    int tade = 0;
                    for (int i = 0; i < temp2; i++) {
                        tade += counterList.get(i);
                    }

                    for (int i = 0; i < counterList.get(temp2); i++) {
                        originalList2.remove(tade - 1);
                    }
                }
                counterList.remove(temp2);

                setPathText();

                for (int i = temp2; i < rightListModel.getSize(); i++) {
                    String ii = rightListModel.get(i);

                    String iii = "";
                    rightListModel.remove(i);
                    if (temp2 == 0 && temp.contains("Folder:")) {
                        temp = "";
                        iii = ii.replaceFirst("SubFolder [0-9]+", "Folder");

                    } else if (ii.contains("SubFolder ")) {
                        iii = ii.replaceFirst("[1-9][0-9]*", "" + i);
                    }
                    rightListModel.add(i, iii);

                }

                if (temp2 < 1) {
                    rightList.setSelectedIndex(0);
                } else if (temp2 >= 1) {
                    rightList.setSelectedIndex(temp2);
                }

                if (rightList.getSelectedIndex() == -1) {
                    rightList.setSelectedIndex(rightList.getLastVisibleIndex());
                }

                for (int i = 0; i < originalList.size(); i++) {
                    System.out.println(originalList.get(i));

                }
                System.out.println("");

                for (int i = 0; i < originalList2.size(); i++) {
                    System.out.println(originalList2.get(i));

                }
                System.out.println("");

                for (int i = 0; i < counterList.size(); i++) {
                    System.out.println(counterList.get(i));

                }
                System.out.println("");
            }
        });

        //secondPanel.add(Box.createHorizontalGlue());
        secondPanel.add(folderButton);
        secondPanel.add(Box.createHorizontalGlue());
        secondPanel.add(Box.createHorizontalGlue());
        secondPanel.add(unionButton);
        secondPanel.add(Box.createHorizontalGlue());
        secondPanel.add(Box.createHorizontalGlue());
        secondPanel.add(deleteButton);
        //secondPanel.add(Box.createHorizontalGlue());

        secondHelpPanel.add(secondPanel);

        JPanel pathPanel = new JPanel(new BorderLayout());
        pathPanel.setPreferredSize(secondHelpPanel.getPreferredSize());
        pathPanel.setMaximumSize(secondHelpPanel.getMaximumSize());
        pathTF = new JTextField();
        pathTF.setEditable(false);

        System.out.println("pira2:" + parent.getDestinationFolderTF().getText() + "::");
        pathTF.setText(parent.getDestinationFolderTF().getText());

        pathPanel.add(pathTF);

        JPanel thirdPanel = new JPanel();
        thirdPanel.setLayout(new BoxLayout(thirdPanel, BoxLayout.X_AXIS));
        thirdPanel.add(Box.createHorizontalGlue());

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (originalList2.isEmpty()) {
                    leftList.clearSelection();
                    rightListModel.clear();
                    originalList.clear();
                    originalList2.clear();
                    counterList.clear();
                    parent1.getNextButton3().setEnabled(false);
                    done=false;
                }
                Dialog3b.this.dispose();
            }
        });

        doneButton = new JButton("Done");
        doneButton.setEnabled(false);
        doneButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                parent1.setSortingMethodDialogText(pathTF.getText());
                parent1.setOriginalList(originalList2);
                done = true;
                parent1.getNextButton3().setEnabled(true);
                Dialog3b.this.dispose();
            }
        });

        thirdPanel.add(cancelButton);
        thirdPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        thirdPanel.add(doneButton);
        thirdPanel.add(Box.createRigidArea(new Dimension(5, 0)));

        verticalBox.add(Box.createVerticalGlue());
        //verticalBox.add(Box.createRigidArea(new Dimension(0, 10)));
        verticalBox.add(topPanel);
        verticalBox.add(Box.createRigidArea(new Dimension(0, 5)));
        verticalBox.add(firstPanel);
        verticalBox.add(Box.createRigidArea(new Dimension(0, 10)));
        verticalBox.add(secondHelpPanel);
        verticalBox.add(Box.createRigidArea(new Dimension(0, 20)));
        verticalBox.add(pathPanel);

        JPanel verticalBox2 = new JPanel();
        verticalBox2.setLayout(new BoxLayout(verticalBox2, BoxLayout.Y_AXIS));

        verticalBox2.add(thirdPanel);
        verticalBox2.add(Box.createRigidArea(new Dimension(0, 5)));

        verticalBox.add(Box.createVerticalGlue());

        add(verticalBox, BorderLayout.CENTER);
        add(verticalBox2, BorderLayout.SOUTH);

    }

    public JTextField getPathTF() {
        System.out.println(pathTF.getText());
        return pathTF;
    }

    private void addLeftValues() {

        leftListModel.addElement("Year");
        leftList.add(new JSeparator(SwingConstants.HORIZONTAL));
        leftListModel.addElement("Month(1,2,3 etc.)");
        leftListModel.addElement("Month(Jan,Feb,Mar etc.)");
        leftListModel.addElement("Day(1,2,3 etc.)");
        leftListModel.addElement("Day(Mon,Tue,Wed etc.)");
        leftListModel.addElement("Location");
        leftListModel.addElement("Manufacturer");
        leftListModel.addElement("Model");
        leftListModel.addElement("Dimensions");

    }

    private void setPathText() {
        String a = "\\";
        for (int i = 0; i < originalList.size(); i++) {
            a += originalList.get(i);
            a += "\\";
        }
        pathTF.setText(parent1.getDestinationFolderTF().getText() + a);
    }

    public boolean isDone() {
        return done;
    }

}
