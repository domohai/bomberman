package core.GameObject.components;

import util.Const;
import util.Prefabs;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class SpriteSheet {
    private List<BufferedImage> sheet;
    private String path;

    public SpriteSheet(String file_path, int cordX, int cordY, int numberOfSprites) {
        this.sheet = new ArrayList<>();
        this.path = file_path;
        BufferedImage parent = Prefabs.readImage(this.path);
        if (parent == null) return;
        for(int i = 0; i < numberOfSprites; i++) {
            this.sheet.add(parent.getSubimage(cordX + i * Const.TILE_W, cordY, Const.TILE_W, Const.TILE_H));
        }
    }
    
    public List<BufferedImage> getSheet() {
        return sheet;
    }
    
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
