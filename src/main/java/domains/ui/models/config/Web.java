package domains.ui.models.config;

import lombok.Data;

@Data
public class Web {
    private String browser;
    private boolean showBrowser = true;
    private int implicitWait = 10;
    private int pageLoadTimeout = 30;
    private int scriptTimeout = 30;
    private boolean deleteAllCookies = false;
    private boolean maximize = true;
    private String windowSize;
    private boolean clearBrowserCache = false;
    private boolean disableNotifications = true;
    private boolean disablePopupBlocking = true;
    private boolean disableGpu = true;
}