package cinnamint.com.taskcentral;

import android.graphics.Color;


public class Tasks {
    private String Title = "";
    private String Description = "";
    private int bColor;

    Tasks() {

    }

    Tasks(String t, String d) {
        Title = t;
        Description = d;
        bColor = Color.parseColor("SILVER");
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getbColor() {
        return bColor;
    }

    public void setbColor(int bColor) {
        this.bColor = bColor;
    }
}
