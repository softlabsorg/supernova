package shared.utils;

import domains.ui.models.config.Firebase;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import shared.config.application.models.GlobalConfiguration;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class FirebaseApkDownloader {

    private final ApplicationConfigProvider configProvider;

    @SneakyThrows
    public void downloadApkIfNeeded() {
        Firebase firebase = getFirebaseConfigOrSkip();
        if (firebase == null) return;

        URI uri = URI.create(firebase.getUrl() + firebase.getApkPath());
        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();

        String auth = firebase.getEmail() + ":" + firebase.getPassword();
        String encoded = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
        connection.setRequestProperty("Authorization", "Basic " + encoded);

        File outputFile = new File("src/test/resources/apk/" + extractFileName(firebase.getApkPath()));
        FileUtils.copyInputStreamToFile(connection.getInputStream(), outputFile);
        System.out.println("APK downloaded to: " + outputFile.getAbsolutePath());
    }

    public void deleteDownloadedApkIfExists() {
        Firebase firebase = getFirebaseConfigOrSkip();
        if (firebase == null) return;

        String fileName = extractFileName(firebase.getApkPath());
        File apkFile = new File("src/test/resources/apk/" + fileName);

        if (apkFile.exists() && apkFile.isFile()) {
            boolean deleted = apkFile.delete();
            if (deleted) {
                System.out.println("APK deleted: " + apkFile.getAbsolutePath());
            } else {
                System.out.println("Failed to delete APK: " + apkFile.getAbsolutePath());
            }
        }
    }

    private Firebase getFirebaseConfigOrSkip() {
        GlobalConfiguration config = configProvider.get();

        if (config == null || config.getAndroid() == null || config.getAndroid().getFirebase() == null) {
            System.out.println("Firebase config is missing. Skipping APK download.");
            return null;
        }

        return config.getAndroid().getFirebase();
    }


    private String extractFileName(String apkPath) {
        return apkPath.substring(apkPath.lastIndexOf('/') + 1);
    }
}
