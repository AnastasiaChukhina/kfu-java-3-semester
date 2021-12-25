package ru.kpfu.nastyaanastasya.gui.panels;

import ru.kpfu.nastyaanastasya.data.ItemResources;
import ru.kpfu.nastyaanastasya.data.ItemType;
import ru.kpfu.nastyaanastasya.helpers.ImageConverter;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class FacePanel extends JPanel {
    protected final int DEFAULT_HEIGHT = 800;
    protected final int DEFAULT_WIDTH = 650;
    private Map<ItemType, Point> itemCoordinates;
    private Image eyes;
    private Image nose;
    private Image lips;
    private Image hair;

    private Image template;

    public FacePanel() {
        super();
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        getTemplate();
        setBackground();
        setItemsCoordinates();
    }

    public void paintEyes(Image eyes) {
        this.eyes = eyes;
        draw(eyes, itemCoordinates.get(ItemType.EYES).x, itemCoordinates.get(ItemType.EYES).y);
    }

    public void paintNose(Image nose) {
        this.nose = nose;
        draw(nose, itemCoordinates.get(ItemType.NOSE).x, itemCoordinates.get(ItemType.NOSE).y);
    }

    public void paintLips(Image lips) {
        this.lips = lips;
        draw(lips, itemCoordinates.get(ItemType.LIPS).x, itemCoordinates.get(ItemType.LIPS).y);
    }

    public void paintHair(Image hair) {
        this.hair = hair;
        draw(hair, itemCoordinates.get(ItemType.HAIR).x, itemCoordinates.get(ItemType.HAIR).y);
    }

    public void repaintItems() {
        if (hair != null) paintHair(hair);
        if (eyes != null) paintEyes(eyes);
        if (nose != null) paintNose(nose);
        if (lips != null) paintLips(lips);
    }

    private void setItemsCoordinates() {
        itemCoordinates = new HashMap<>();
        itemCoordinates.put(ItemType.HAIR, new Point(70, 30));
        itemCoordinates.put(ItemType.EYES, new Point(150, 300));
        itemCoordinates.put(ItemType.NOSE, new Point(290, 360));
        itemCoordinates.put(ItemType.LIPS, new Point(245, 520));
    }

    private void setBackground() {
        setLayout(new BorderLayout());
        add(new JLabel(new ImageIcon(template)), BorderLayout.CENTER);
    }

    private void getTemplate() {
        template = ImageConverter.resizeImage(
                ImageConverter.getImage(ItemResources.TEMPLATE_DIR),
                DEFAULT_WIDTH,
                DEFAULT_HEIGHT
        );
    }

    private void draw(Image img, int x, int y) {
        Graphics g = this.getGraphics();
        g.drawImage(img, x, y, null);
        g.dispose();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        repaintItems();
    }

    @Override
    public void repaint() {
        super.repaint();
        repaintItems();
    }
}
