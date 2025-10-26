package domains.ui.models.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class Web {

    @Value("${web.url}")
    private String url;

    @Value("${web.browser:chrome}")
    private String browser;

    @Value("${web.showBrowser:true}")
    private boolean showBrowser;

    @Value("${web.implicitWait:15}")
    private int implicitWait;

    @Value("${web.pageLoadTimeout:45}")
    private int pageLoadTimeout;

    @Value("${web.scriptTimeout:30}")
    private int scriptTimeout;

    @Value("${web.deleteAllCookies:true}")
    private boolean deleteAllCookies;

    @Value("${web.maximize:true}")
    private boolean maximize;

    @Value("${web.windowSize:1920x1080}")
    private String windowSize;

    @Value("${web.clearBrowserCache:true}")
    private boolean clearBrowserCache;

    @Value("${web.disableNotifications:true}")
    private boolean disableNotifications;

    @Value("${web.disablePopupBlocking:true}")
    private boolean disablePopupBlocking;

    @Value("${web.disableGpu:false}")
    private boolean disableGpu;
}