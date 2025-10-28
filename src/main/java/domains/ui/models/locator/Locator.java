package domains.ui.models.locator;

import lombok.Data;

@Data
public class Locator {

    private String type;
    private String value;
    private int waitTime;

}