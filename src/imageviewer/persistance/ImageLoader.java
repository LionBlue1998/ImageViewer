package imageviewer.persistance;

import imageviewer.model.Image;

public interface ImageLoader {
    
    Image load();

    public String getFileName();
    
}
