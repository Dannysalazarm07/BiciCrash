package unal.edu.co.bicicrash.Utils;

import android.graphics.drawable.Drawable;

/**
 * Created by MiguelPC on 30-Sep-17.
 */

public class BiciContact {
    private String name;
    private String number;
    private Drawable image;

    public BiciContact(String name, String number){
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }


}
