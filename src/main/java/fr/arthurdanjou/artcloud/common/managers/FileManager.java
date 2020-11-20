package fr.arthurdanjou.artcloud.common.managers;

import fr.arthurdanjou.artcloud.ArtCloud;
import lombok.Getter;

import java.io.*;

@Getter
public class FileManager extends AbstractManager {

    // Files
    private File configFile;

    // Directories
    private File logsDirectory;
    private File templatesDirectory;
    private File imagesDirectory;

    public FileManager(ArtCloud artCloud) {
        super(artCloud, "File");
    }

    public String loadFile(File file) {
        StringBuilder sb = new StringBuilder();
        if (file.exists()) {
            try {
                String line;
                BufferedReader reader = new BufferedReader(new FileReader(file));

                while ((line = reader.readLine()) != null) sb.append(line);

                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public void saveFile(File file, String content) {
        if (file.exists()) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(content);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void enable() {
        this.configFile = new File("./config.json");

        this.logsDirectory = new File("./logs/");
        this.templatesDirectory = new File("./templates/");
        this.imagesDirectory = new File("./images/");
    }

    @Override
    public void disable() {

    }
}
