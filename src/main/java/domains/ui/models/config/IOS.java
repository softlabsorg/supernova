package domains.ui.models.config;

import lombok.Data;

@Data
public class IOS {
    private String appName;
    private String bundleId;
    private Firebase firebase;
}