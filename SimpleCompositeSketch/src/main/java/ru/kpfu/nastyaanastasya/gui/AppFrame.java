package ru.kpfu.nastyaanastasya.gui;

import ru.kpfu.nastyaanastasya.data.AppData;
import ru.kpfu.nastyaanastasya.data.FaceItemsData;
import ru.kpfu.nastyaanastasya.data.ItemType;
import ru.kpfu.nastyaanastasya.exceptions.LookAndFeelException;
import ru.kpfu.nastyaanastasya.gui.panels.FacePanel;
import ru.kpfu.nastyaanastasya.helpers.ImageConverter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppFrame extends JFrame {
    private boolean selectedGender;
    private java.util.List<Image> eyesItems;
    private java.util.List<Image> lipsItems;
    private java.util.List<Image> hairItems;
    private List<Image> noseItems;
    private Map<Boolean, ActionListener> selectedGenderListeners;
    private Map<ItemType, Dimension> typeDimensionMap;
    private JPanel instruments;
    private JPanel view;
    private FacePanel facePanel;
    private JRadioButton maleRButton;
    private JRadioButton femRButton;
    private Image nose;
    private Image eyes;
    private Image lips;
    private Image hair;
    private FaceItemsData data;

    public AppFrame(){
        super(AppData.APP_NAME);
        data = new FaceItemsData();
        facePanel = new FacePanel();
        selectedGender = true;
        initWindowProperties();
        setButtonSizes();
        addSelectedGenderListeners();
        addPanels(getContentPane());
    }

    public void showWindow(){
        setVisible(true);
    }

    private void initWindowProperties(){
        setMinimumSize(new Dimension(1100, 850));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLookAndFeel();
        addMasculinity();
    }

    private void addPanels(Container container){
        container.setLayout(new BorderLayout());
        container.add(createTextField(AppData.APP_TEXT, AppData.PRIMARY_FONT_SIZE), BorderLayout.NORTH);
        container.add(createInstrumentalPanel(), BorderLayout.EAST);
        container.add(createViewPanel(), BorderLayout.CENTER);

        pack();
    }

    private Component createViewPanel() {
        view = new JPanel();
        view.setBackground(Color.WHITE);
        view.add(facePanel, BorderLayout.CENTER);

        return view;
    }

    private JScrollPane createInstrumentalPanel() {
        instruments = new JPanel();
        instruments.setLayout(new BoxLayout(instruments, BoxLayout.PAGE_AXIS));
        setContent(instruments);

        JScrollPane pane = new JScrollPane(instruments, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        pane.setEnabled(true);
        pane.setPreferredSize(new Dimension(350, 850));
        return pane;
    }

    private void addMasculinity() {
        noseItems = data.getMaleNoses();
        eyesItems = data.getMaleEyes();
        lipsItems = data.getMaleLips();
        hairItems = data.getMaleHairs();
    }

    private void addFeminine() {
        noseItems = data.getFemaleNoses();
        eyesItems = data.getFemaleEyes();
        lipsItems = data.getFemaleLips();
        hairItems = data.getFemaleHairs();
    }

    private JLabel createTextField(String text, Float fontSize) {
        JLabel label = new JLabel(text);
        label.setFont(label.getFont().deriveFont(fontSize));
        return label;
    }

    private JPanel setContent(JPanel instruments) {
        if (instruments != null) instruments.removeAll();
        instruments.add(createRadioButtonPanel());
        instruments.add(createTextField("Select hair:", AppData.PRIMARY_FONT_SIZE));
        instruments.add(createSelectItemPanel(hairItems, ItemType.HAIR));
        instruments.add(createTextField("Select eyes:", AppData.PRIMARY_FONT_SIZE));
        instruments.add(createSelectItemPanel(eyesItems, ItemType.EYES));
        instruments.add(createTextField("Select nose:", AppData.PRIMARY_FONT_SIZE));
        instruments.add(createSelectItemPanel(noseItems, ItemType.NOSE));
        instruments.add(createTextField("Select lips:", AppData.PRIMARY_FONT_SIZE));
        instruments.add(createSelectItemPanel(lipsItems, ItemType.LIPS));

        return instruments;
    }

    private Component createSelectItemPanel(List<Image> items, ItemType itemType) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));
        Dimension dimension = typeDimensionMap.get(itemType);

        for (Image item : items) {
            panel.add(createItem(item, itemType, dimension));
        }
        return panel;
    }

    private Component createItem(Image image, ItemType itemType, Dimension dimension) {
        JPanel panel = new JPanel();
        JButton button = new JButton(new ImageIcon(ImageConverter.resizeImage(image, dimension.width, dimension.height)));
        button.setPreferredSize(new Dimension(dimension.width, dimension.height));
        setListener(button, image, itemType);
        panel.add(button);
        return panel;
    }

    private void setListener(JButton button, Image image, ItemType itemType) {
        switch (itemType) {
            case HAIR:
                button.addActionListener(e -> {
                    hair = ImageConverter.resizeImage(image, 500, 250);
                    facePanel.paintHair(hair);
                });
                break;
            case EYES:
                button.addActionListener(e -> {
                    eyes = ImageConverter.resizeImage(image, 360, 80);
                    facePanel.paintEyes(eyes);
                });
                break;
            case NOSE:
                button.addActionListener(e -> {
                    nose = ImageConverter.resizeImage(image, 80, 140);
                    facePanel.paintNose(nose);
                });
                break;
            case LIPS:
                button.addActionListener(e -> {
                    lips = ImageConverter.resizeImage(image, 170, 50);
                    facePanel.paintLips(lips);
                });
                break;
        }
    }

    private Component createRadioButtonPanel() {
        JPanel rButtonPanel = new JPanel();
        rButtonPanel.setLayout(new FlowLayout());
        maleRButton = new JRadioButton();
        femRButton = new JRadioButton();
        maleRButton.setSelected(selectedGender);
        maleRButton.addActionListener(selectedGenderListeners.get(true));
        femRButton.setSelected(!selectedGender);
        femRButton.addActionListener(selectedGenderListeners.get(false));
        rButtonPanel.add(createTextField("Select gender:", AppData.PRIMARY_FONT_SIZE));
        rButtonPanel.add(maleRButton);
        rButtonPanel.add(createTextField("Male", AppData.SECONDARY_FONT_SIZE));
        rButtonPanel.add(femRButton);
        rButtonPanel.add(createTextField("Female", AppData.SECONDARY_FONT_SIZE));

        return rButtonPanel;
    }

    private void addSelectedGenderListeners() {
        selectedGenderListeners = new HashMap<>();
        selectedGenderListeners.put(true, e -> {
            if (maleRButton.isSelected()) {
                selectedGender = true;
                femRButton.setSelected(false);
                addMasculinity();
            } else {
                femRButton.setSelected(true);
                selectedGender = false;
                addFeminine();
            }
            setContent(instruments);
            refreshView(instruments);
        });
        selectedGenderListeners.put(false, e -> {
            if (femRButton.isSelected()) {
                maleRButton.setSelected(false);
                selectedGender = false;
                addFeminine();
            } else {
                maleRButton.setSelected(true);
                selectedGender = true;
                addMasculinity();
            }
            setContent(instruments);
            refreshView(instruments);
        });
    }

    private void setButtonSizes() {
        typeDimensionMap = new HashMap<>();
        typeDimensionMap.put(ItemType.EYES, new Dimension(120, 50));
        typeDimensionMap.put(ItemType.NOSE, new Dimension(80, 120));
        typeDimensionMap.put(ItemType.LIPS, new Dimension(120, 50));
        typeDimensionMap.put(ItemType.HAIR, new Dimension(120, 80));
    }

    private void refreshView(Component component) {
        component.validate();
        component.repaint();
    }

    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e) {
            throw new LookAndFeelException("Unsupported Look And Feel", e);
        }
    }
}
