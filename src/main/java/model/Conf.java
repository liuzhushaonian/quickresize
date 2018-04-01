package model;

import net.coobird.thumbnailator.geometry.Positions;

public class Conf {

    private int SCALE_TYPE;//缩放类型

    private float scaleW;
    private float scaleH;
    private float scaleB;

    private boolean isRotation;
    private int rotationCount;
    private boolean isRetro;
    private int RETRO_TYPE;
    private String retroType;

    private boolean addWater;
    private String waterPath;
    private boolean addTextWater;
    private String textWater;
    private float waterTrans;
    private int WATER_POSITION;
    private Positions positions;

    private float imageQuality;

    private String format;

    private String outputPath;//输出路径，不可以不写

    public Positions getPositions() {
        return positions;
    }

    public void setPositions(Positions positions) {
        this.positions = positions;
    }

    public String getRetroType() {
        return retroType;
    }

    public void setRetroType(String retroType) {
        this.retroType = retroType;
    }

    public float getScaleB() {
        return scaleB;
    }

    public void setScaleB(float scaleB) {
        this.scaleB = scaleB;
    }

    public int getSCALE_TYPE() {
        return SCALE_TYPE;
    }

    public void setSCALE_TYPE(int SCALE_TYPE) {
        this.SCALE_TYPE = SCALE_TYPE;
    }

    public float getScaleW() {
        return scaleW;
    }

    public void setScaleW(float scaleW) {
        this.scaleW = scaleW;
    }

    public float getScaleH() {
        return scaleH;
    }

    public void setScaleH(float scaleH) {
        this.scaleH = scaleH;
    }

    public boolean isRotation() {
        return isRotation;
    }

    public void setRotation(boolean rotation) {
        isRotation = rotation;
    }

    public int getRotationCount() {
        return rotationCount;
    }

    public void setRotationCount(int rotationCount) {
        this.rotationCount = rotationCount;
    }

    public boolean isRetro() {
        return isRetro;
    }

    public void setRetro(boolean retro) {
        isRetro = retro;
    }

    public int getRETRO_TYPE() {
        return RETRO_TYPE;
    }

    public void setRETRO_TYPE(int RETRO_TYPE) {
        this.RETRO_TYPE = RETRO_TYPE;
    }

    public boolean isAddWater() {
        return addWater;
    }

    public void setAddWater(boolean addWater) {
        this.addWater = addWater;
    }

    public String getWaterPath() {
        return waterPath;
    }

    public void setWaterPath(String waterPath) {
        this.waterPath = waterPath;
    }

    public boolean isAddTextWater() {
        return addTextWater;
    }

    public void setAddTextWater(boolean addTextWater) {
        this.addTextWater = addTextWater;
    }

    public String getTextWater() {
        return textWater;
    }

    public void setTextWater(String textWater) {
        this.textWater = textWater;
    }

    public float getWaterTrans() {
        return waterTrans;
    }

    public void setWaterTrans(float waterTrans) {
        this.waterTrans = waterTrans;
    }

    public int getWATER_POSITION() {
        return WATER_POSITION;
    }

    public void setWATER_POSITION(int WATER_POSITION) {
        this.WATER_POSITION = WATER_POSITION;
    }

    public float getImageQuality() {
        return imageQuality;
    }

    public void setImageQuality(float imageQuality) {
        this.imageQuality = imageQuality;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }
}
