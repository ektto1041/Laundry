package com.mobileteam.laundry.domain;

import com.mobileteam.laundry.Converters;
import com.mobileteam.laundry.enums.Bleach;
import com.mobileteam.laundry.enums.ClothesColor;
import com.mobileteam.laundry.enums.Detergent;
import com.mobileteam.laundry.enums.Dry;
import com.mobileteam.laundry.enums.DryClean;
import com.mobileteam.laundry.enums.Iron;
import com.mobileteam.laundry.enums.Temperature;
import com.mobileteam.laundry.enums.WashingPower;
import com.mobileteam.laundry.enums.WashingType;
import com.mobileteam.laundry.enums.Weave;

import java.io.Serializable;

public class SerializableClothes implements Serializable {
    private long id;
    private WashingType washingType;
    private WashingPower washingPower;
    private Detergent detergent;
    private Temperature temperature;
    private ClothesColor clothesColor;
    private Bleach bleach;
    private Iron iron;
    private DryClean dryClean;
    private Weave weave;
    private Dry dry;
    //private Bitmap image;
    private byte[] image;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public WashingType getWashingType() {
        return washingType;
    }

    public void setWashingType(WashingType washingType) {
        this.washingType = washingType;
    }

    public WashingPower getWashingPower() {
        return washingPower;
    }

    public void setWashingPower(WashingPower washingPower) {
        this.washingPower = washingPower;
    }

    public Detergent getDetergent() {
        return detergent;
    }

    public void setDetergent(Detergent detergent) {
        this.detergent = detergent;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public ClothesColor getClothesColor() {
        return clothesColor;
    }

    public void setClothesColor(ClothesColor clothesColor) {
        this.clothesColor = clothesColor;
    }

    public Bleach getBleach() {
        return bleach;
    }

    public void setBleach(Bleach bleach) {
        this.bleach = bleach;
    }

    public Iron getIron() {
        return iron;
    }

    public void setIron(Iron iron) {
        this.iron = iron;
    }

    public DryClean getDryClean() {
        return dryClean;
    }

    public void setDryClean(DryClean dryClean) {
        this.dryClean = dryClean;
    }

    public Weave getWeave() {
        return weave;
    }

    public void setWeave(Weave weave) {
        this.weave = weave;
    }

    public Dry getDry() {
        return dry;
    }

    public void setDry(Dry dry) {
        this.dry = dry;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public SerializableClothes(Clothes clothes) {
        this.id = clothes.getId();
        this.washingType = clothes.getWashingType();
        this.washingPower = clothes.getWashingPower();
        this.detergent = clothes.getDetergent();
        this.temperature = clothes.getTemperature();
        this.clothesColor = clothes.getClothesColor();
        this.bleach = clothes.getBleach();
        this.iron = clothes.getIron();
        this.dryClean = clothes.getDryClean();
        this.weave = clothes.getWeave();
        this.dry = clothes.getDry();

        this.image = new Converters().toByteArray(clothes.getImage());
    }
}
