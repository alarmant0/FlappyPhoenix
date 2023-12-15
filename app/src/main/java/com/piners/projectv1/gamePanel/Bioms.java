package com.piners.projectv1.gamePanel;

import java.util.ArrayList;
import java.util.List;

public class Bioms {

    List<Background> backgrounds = new ArrayList<Background>();

    public Bioms(List<Background> backgrounds) {
        this.backgrounds = backgrounds;
    }

    public Background getBackground(int score) {
        switch (score) {
            case 5:
                return backgrounds.get(1);
            case 40:
                return backgrounds.get(2);
            case 60:
                return backgrounds.get(3);
            default:
                return backgrounds.get(0);
        }
    }

}
