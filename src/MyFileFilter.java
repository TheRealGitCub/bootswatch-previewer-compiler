import java.io.File;
import java.io.FileFilter;

/**
 * Created by kobitate on 7/26/15.
 */
public class MyFileFilter implements FileFilter {
    @Override
    public boolean accept(File pathname) {
        String fileName = pathname.getName();
        String[] parts = fileName.split("\\.");

        if (fileName.matches("\\..*")) {
            return false;
        }

        else if (pathname.isDirectory() && fileName.matches("sections")) {
            return false;
        }

        else if (parts.length > 1) {
            String fileExt = parts[parts.length-1];

            if (fileExt.contains("sublime")) {
                return false;
            }
            else if (fileExt.matches("codekit|kit|less")) {
                return false;
            }
            else if (parts[0].matches("analytics|settings_functions|theme_functions") || (parts[0].matches("init") && !parts[1].matches("min"))) {
                return false;
            }
            else if (parts[0].matches("popup")) {
                if (fileExt.matches("html|css")) {
                    return true;
                }
                else if (fileExt.matches("js") && parts[1].matches("min")) {
                    return true;
                }
                else {
                    return false;
                }
            }

        }

        return true;
    }
}
