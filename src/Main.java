import java.io.IOException;

/**
 * Bootswatch Previewer Compiler v0.1
 * Created by Kobi Tate on 7/26/15.
 *
 * Just a small-ish program I wrote to remove unnecessary files and create a ZIP that
 * can be uploaded to the Chrome Webstore.
 *
 * It currently only works on my machine. I plan on adding a simple GUI in the future.
 * There are probably lots of bugs.
 *
 */
public class Main {

    static String path = "/Users/kobitate/Documents/GitHub Clone/bootswatch-previewer";

    public static void main(String[] args) {
        try {
            FileProcessor fp = new FileProcessor(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
