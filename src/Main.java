import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by demo4 on 16.07.14.
 */
public class Main {
    public static void main(String arg[]){
        HtmlGen gen = new HtmlGen();
        OutputStreamWriter writer = new OutputStreamWriter(System.out);
        gen.Generate("C:\\Windows", writer);
    }
}
