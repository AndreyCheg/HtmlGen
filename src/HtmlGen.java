import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by demo4 on 16.07.14.
 */
public class HtmlGen {
    static final String HEAD = "<html> <head> <title> ";
    static final String TITLE = "</title> </head> <body>";
    static final String ENDOF = "</body> </html>";

    public void Generate(String path, Writer writer){
        File dir = new File(path);
        File[] files = dir.listFiles();
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {

                if(o1.isDirectory() && !o2.isDirectory()){
                    return -1;
                }
                else if(!o1.isDirectory() && o2.isDirectory()){
                    return 1;
                }
                return o1.getName().compareTo(o2.getName());
            }
        });
        try {
            writer.write(HEAD);
            writer.write(dir.getName());
            writer.write(TITLE);
            for(File f: files){
                writer.write(f.getName() + "");
            }
            writer.write(ENDOF);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
