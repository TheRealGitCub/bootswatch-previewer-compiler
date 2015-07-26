import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by kobitate on 7/26/15.
 */
public class FileProcessor {

    private static File file;
    private static File tempFile;

    public FileProcessor(String path) throws IOException {

        file = new File(path);
        tempFile = new File(path + "/compile-temp");

        FileUtils.copyDirectory(file, tempFile, new MyFileFilter());

        try {
            zipFolder();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FileUtils.deleteDirectory(tempFile);

    }

    static public String getVersion() {
        File manifestFile = new File(file + "/manifest.json");
        String manifestJSON = null;
        try {
            manifestJSON = FileUtils.readFileToString(manifestFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject manifest = new JSONObject(manifestJSON);

        return manifest.getString("version");
    }

    static public void zipFolder() throws Exception {
        ZipOutputStream zip;
        FileOutputStream fileWriter;

        fileWriter = new FileOutputStream(file + "/v"+ getVersion() +".zip");
        zip = new ZipOutputStream(fileWriter);

        addFolderToZip("", tempFile.getAbsolutePath(), zip);
        zip.flush();
        zip.close();
    }

    static private void addFileToZip(String path, String srcFile, ZipOutputStream zip)
            throws Exception {

        File folder = new File(srcFile);
        if (folder.isDirectory()) {
            addFolderToZip(path, srcFile, zip);
        } else {
            byte[] buf = new byte[1024];
            int len;
            FileInputStream in = new FileInputStream(srcFile);
            zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
            while ((len = in.read(buf)) > 0) {
                zip.write(buf, 0, len);
            }
        }
    }

    static private void addFolderToZip(String path, String srcFolder, ZipOutputStream zip)
            throws Exception {
        File folder = new File(srcFolder);

        for (String fileName : folder.list()) {
            if (path.equals("")) {
                addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip);
            } else {
                addFileToZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip);
            }
        }
    }

}
