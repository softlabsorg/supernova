package shared.config.application.models;

import domains.database.models.DatabaseConnection;
import domains.ui.models.config.Android;
import domains.ui.models.config.IOS;
import domains.ui.models.config.Web;
import lombok.Data;

import java.util.List;

@Data
public class GlobalConfiguration {
    private Web web;
    private Android android;
    private IOS ios;
    private List<DatabaseConnection> databases;
}