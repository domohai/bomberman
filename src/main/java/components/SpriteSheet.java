package components;

import util.AssetsPool;
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
        BufferedImage parent = Prefabs.readImage(path);
        if(parent == null)
            return;
        for(int i = 0; i < numberOfSprites; i++) {
            sheet.add(parent.getSubimage(cordX + i * Const.TILE_W, cordY, Const.TILE_W, Const.TILE_H));
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
