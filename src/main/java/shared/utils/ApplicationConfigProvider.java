package shared.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import domains.database.models.DatabaseConnection;
import domains.ui.models.config.Android;
import domains.ui.models.config.Web;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import shared.config.application.models.GlobalConfiguration;
import shared.exception.ConfigurationParsingException;
import shared.exception.UnsupportedConfigFormatException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Data
@Component
public class ApplicationConfigProvider {

    private GlobalConfiguration globalConfiguration;

    public GlobalConfiguration get() {
        if (globalConfiguration == null) loadConfig();
        return globalConfiguration;
    }

    @Bean
    public Web webConfig() {
        return get().getWeb();
    }

    @Bean
    public Android androidConfig() {
        return get().getAndroid();
    }

    public String getDefaultDatabaseName() {
        if (get().getDatabases() == null || get().getDatabases().isEmpty()) {
            throw new IllegalStateException("No databases defined in configuration file.");
        }
        return get().getDatabases().getFirst().getName();
    }

    private void loadConfig() {
        List<String> supportedFiles = List.of("application.yaml", "application.yml", "application.json", "application.properties");

        InputStream inputStream = null;
        String fileType = null;

        for (String fileName : supportedFiles) {
            inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
            if (inputStream != null) {
                fileType = fileName.substring(fileName.lastIndexOf('.') + 1);
                break;
            }
        }

        if (inputStream == null) {
            this.globalConfiguration = new GlobalConfiguration();
            return;
        }

        switch (fileType) {
            case "yaml", "yml" -> loadYaml(inputStream);
            case "json" -> loadJson(inputStream);
            case "properties" -> loadProperties(inputStream);
            default -> throw new UnsupportedConfigFormatException("Unsupported config file type: " + fileType);
        }
    }


    private void loadYaml(InputStream inputStream) {
        try {
            Yaml yaml = new Yaml(new Constructor(GlobalConfiguration.class, new LoaderOptions()));
            this.globalConfiguration = yaml.load(inputStream);
        } catch (Exception e) {
            throw new ConfigurationParsingException("Failed to parse YAML configuration", e);
        }
    }

    private void loadJson(InputStream inputStream) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            this.globalConfiguration = mapper.readValue(inputStream, GlobalConfiguration.class);
        } catch (Exception e) {
            throw new ConfigurationParsingException("Failed to parse JSON configuration", e);
        }
    }

    private void loadProperties(InputStream inputStream) {
        try {
            Properties props = new Properties();
            props.load(inputStream);

            GlobalConfiguration config = new GlobalConfiguration();

            config.setWeb(parseWebConfig(props));
            config.setAndroid(parseAndroidConfig(props));
            config.setDatabases(parseDatabaseConfigs(props));

            this.globalConfiguration = config;

        } catch (IOException e) {
            throw new ConfigurationParsingException("Failed to parse Properties configuration", e);
        }
    }

    private Web parseWebConfig(Properties props) {
        Web web = new Web();
        web.setBrowser(props.getProperty("web.browser"));
        web.setShowBrowser(Boolean.parseBoolean(props.getProperty("web.showBrowser")));
        return web;
    }

    private Android parseAndroidConfig(Properties props) {
        Android android = new Android();
        android.setAppName(props.getProperty("android.appName"));
        android.setAppPackage(props.getProperty("android.appPackage"));
        android.setAppActivity(props.getProperty("android.appActivity"));
        return android;
    }

    private List<DatabaseConnection> parseDatabaseConfigs(Properties props) {
        List<DatabaseConnection> databases = new ArrayList<>();
        int i = 0;
        while (props.containsKey("databases[" + i + "].name")) {
            DatabaseConnection db = new DatabaseConnection();
            db.setName(props.getProperty("databases[" + i + "].name"));
            db.setUrl(props.getProperty("databases[" + i + "].url"));
            db.setUsername(props.getProperty("databases[" + i + "].username"));
            db.setPassword(props.getProperty("databases[" + i + "].password"));
            databases.add(db);
            i++;
        }
        return databases;
    }
}