package imageviewer.persistance.files;

import imageviewer.model.Image;
import imageviewer.persistance.ImageLoader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;

public class FileImageLoader implements ImageLoader{
    
    private final static String[] ImageExtensions = new String[] {"jpg","png","bmp"};
    private final File[] files;
    private String fileName;

    public FileImageLoader(String folder) {
        this.files = new File(folder).listFiles(withImageExtension());
        load();
    }
    
    @Override
    public Image load() {
        setFileName(0);
        return imageAt(0);
    }
    
    private Image imageAt(int index){
        return new Image(){
            @Override
            public byte[] bitmap() {
                try {
                    FileInputStream is = new FileInputStream(files[index]);
                    return read(is);
                } catch (IOException ex) {
                    return new byte[0];
                }
            }

            private byte[] read(FileInputStream is) throws IOException {
                byte[] buffer = new byte[4096];
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                while (true){
                    int length = is.read(buffer);
                    if (length < 0) break;
                    os.write(buffer, 0, length);
                }
                return os.toByteArray();
            }
            
            @Override
            public Image next() {
                setFileName((index > 0) ? (index-1): (files.length-1));
                return (index > 0) ? imageAt(index-1): imageAt(files.length-1);
            }

            @Override
            public Image prev() {
                setFileName((index < files.length-1) ? (index+1): 0);
                return (index < files.length-1) ? imageAt(index+1): imageAt(0);
            }
        };
    }

    private FilenameFilter withImageExtension() {
        return new FilenameFilter(){
            @Override
            public boolean accept(File dir, String name) {
                for (String extension : ImageExtensions)
                    if(name.endsWith(extension))
                        return true;
                return false;
            }
        };
    }
    private void setFileName(int i) {
        fileName = files[i].toString();
    }

    @Override
    public String getFileName(){
        return fileName;
    }
}
