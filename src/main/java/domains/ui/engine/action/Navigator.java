package domains.ui.engine.action;

import domains.ui.models.config.Web;
import domains.ui.session.UiSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Navigator {

    private final UiSession session;

    public void navigate(String baseUrl) {
        session.getDriver().navigate().to(baseUrl);
    }

    public void navigate(String baseUrl, String target) {
        session.getDriver().navigate().to(baseUrl + target);
    }

    public void navigateBack() {
        session.getDriver().navigate().back();
    }

    public void navigateForward() {
        session.getDriver().navigate().forward();
    }

    public void refreshPage() {
        session.getDriver().navigate().refresh();
    }

}
