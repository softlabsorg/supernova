package domains.ui.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import domains.ui.models.locator.Locator;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Component
public class LocatorProviderContext {

    private static final String BASE_PATH = "src/test/resources/locators/";
    private final Map<String, Locator> locators = new HashMap<>();

    public void loadFeatureLocators(String featureName) {
        loadLocators("common-locators.json");

        String featureFile = featureName.toLowerCase()
                .replaceAll("\\s+", "-")
                .concat("-locators.json");

        loadLocators(featureFile);
    }

    public void reset() {
        locators.clear();
    }

    public void loadLocators(String filename) {
        File file = findLocatorFileRecursively(new File(BASE_PATH), filename);

        if (file == null || !file.exists()) {
            log("Locator file '" + filename + "' not found. Skipping...");
            return;
        }

        Map<String, Locator> loaded = parseFile(file, filename);
        if (loaded == null || loaded.isEmpty()) {
            log("Locator file '" + filename + "' is empty or invalid. Skipping...");
            return;
        }

        locators.putAll(loaded);
    }

    private File findLocatorFileRecursively(File directory, String targetFilename) {
        File[] files = directory.listFiles();
        if (files == null) return null;

        for (File file : files) {
            if (file.isDirectory()) {
                File found = findLocatorFileRecursively(file, targetFilename);
                if (found != null) return found;
            } else if (file.getName().equals(targetFilename)) {
                return file;
            }
        }
        return null;
    }

    private Map<String, Locator> parseFile(File file, String filename) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(
                    file,
                    mapper.getTypeFactory().constructMapType(Map.class, String.class, Locator.class)
            );
        } catch (Exception e) {
            log("Failed to parse '" + filename + "': " + e.getMessage());
            return null;
        }
    }

    public Locator getLocator(String key) {
        return locators.get(key);
    }

    private void log(String message) {
        System.out.println(message);
    }

}