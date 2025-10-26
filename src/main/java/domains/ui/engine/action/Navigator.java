package domains.ui.engine.action;

import domains.ui.models.config.Web;
import domains.ui.session.UiSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Navigator {

    private final UiSession session;

    @Value("${web.url}")
    private String baseUrl;

    public void navigate() {
        session.getDriver().navigate().to(baseUrl);
    }

    public void navigate(String target) {
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
