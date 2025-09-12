package domains.ui.models.config;

import lombok.Data;

@Data
public class Android {
    private String appName;
    private String appPackage;
    private String appActivity;
    private Firebase firebase;
}