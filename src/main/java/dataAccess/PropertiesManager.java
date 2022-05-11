package dataAccess;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesManager {

    private void createPropertyKey(String filepath, String key, String value) {
        try {
            Properties props = new Properties();

            FileInputStream in = new FileInputStream(filepath);
            props.load(in);
            in.close();

            props.put(key, value);

            FileOutputStream out = new FileOutputStream(filepath);
            props.store(out, null);
            out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addTagToResources(String id, String text_en, String text_es, String text_eus) {
        createPropertyKey("src/main/scenes/Etiquetas.properties", id, text_en);
        createPropertyKey("src/main/scenes/Etiquetas_en.properties", id, text_en);
        createPropertyKey("src/main/scenes/Etiquetas_es.properties", id, text_es);
        createPropertyKey("src/main/scenes/Etiquetas_eus.properties", id, text_eus);
    }


    public boolean resourceBundleContains(String id) {

        try {
            Properties props = new Properties();

            FileInputStream in = new FileInputStream("src/main/scenes/Etiquetas.properties");
            props.load(in);
            in.close();

            return props.containsKey(id);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
