package ru.kpfu.nastyaanastasya.helpers;

import ru.kpfu.nastyaanastasya.exceptions.ReadingImageException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ImageConverter {

    public static Image getImage(String res){
        try {
            return ImageIO.read(new File(res));
        } catch (IOException e) {
            throw new ReadingImageException("Can't get image", e);
        }
    }

    public static Image resizeImage(Image image, int width, int height) {
        return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }
}
