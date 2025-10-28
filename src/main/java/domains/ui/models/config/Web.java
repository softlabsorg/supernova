package domains.ui.models.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
public class Web {

    private String browser;
    private boolean showBrowser;
    private int implicitWait;
    private int pageLoadTimeout;
    private int scriptTimeout;
    private boolean deleteAllCookies;
    private boolean maximize;
    private String windowSize;
    private boolean clearBrowserCache;
    private boolean disableNotifications;
    private boolean disablePopupBlocking;
    private boolean disableGpu;

}