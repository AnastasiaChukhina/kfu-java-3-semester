package ru.kpfu.nastyaanastasya.data;

import ru.kpfu.nastyaanastasya.exceptions.NoAccessToFileException;
import ru.kpfu.nastyaanastasya.helpers.ImageConverter;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FaceItemsData {
    private List<Image> maleNoses;
    private List<Image> maleHairs;
    private List<Image> maleLips;
    private List<Image> maleEyes;
    private List<Image> femaleNoses;
    private List<Image> femaleHairs;
    private List<Image> femaleLips;
    private List<Image> femaleEyes;

    public FaceItemsData() {
        addData();
    }

    public List<Image> getMaleNoses() {
        return maleNoses;
    }

    public List<Image> getMaleHairs() {
        return maleHairs;
    }

    public List<Image> getMaleLips() {
        return maleLips;
    }

    public List<Image> getMaleEyes() {
        return maleEyes;
    }

    public List<Image> getFemaleNoses() {
        return femaleNoses;
    }

    public List<Image> getFemaleHairs() {
        return femaleHairs;
    }

    public List<Image> getFemaleLips() {
        return femaleLips;
    }

    public List<Image> getFemaleEyes() {
        return femaleEyes;
    }

    private void addData() {
        addMaleData();
        addFemaleData();
    }

    private void addFemaleData() {
        femaleEyes = getData(getPaths(ItemResources.EYES_DIR, ResourceGenderTypes.FEMALE));
        femaleNoses = getData(getPaths(ItemResources.NOSE_DIR, ResourceGenderTypes.FEMALE));
        femaleLips = getData(getPaths(ItemResources.LIPS_DIR, ResourceGenderTypes.FEMALE));
        femaleHairs = getData(getPaths(ItemResources.HAIR_DIR, ResourceGenderTypes.FEMALE));
    }

    private void addMaleData() {
        maleEyes = getData(getPaths(ItemResources.EYES_DIR, ResourceGenderTypes.MALE));
        maleNoses = getData(getPaths(ItemResources.NOSE_DIR, ResourceGenderTypes.MALE));
        maleLips = getData(getPaths(ItemResources.LIPS_DIR, ResourceGenderTypes.MALE));
        maleHairs = getData(getPaths(ItemResources.HAIR_DIR, ResourceGenderTypes.MALE));
    }

    private List<Path> getPaths(String folder, ResourceGenderTypes gender) {
        String prefix = getGenderResourcePrefix(gender);
        try {
            return Files.walk(Paths.get(folder).toAbsolutePath())
                    .filter(path -> path.getFileName().toString().startsWith(prefix))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new NoAccessToFileException("Can't get access to file", e);
        }
    }

    private String getGenderResourcePrefix(ResourceGenderTypes type) {
        switch (type){
            case MALE:
                return "m";
            case FEMALE:
                return "f";
            default:
                return "";
        }
    }

    private List<Image> getData(List<Path> paths) {
        List<Image> storage = new ArrayList<>();
        for (Path path : paths) {
            storage.add(ImageConverter.getImage(path.toString()));
        }
        return storage;
    }
}
