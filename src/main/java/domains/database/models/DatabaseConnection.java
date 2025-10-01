package domains.database.models;

import lombok.Data;

@Data
public class DatabaseConnection {
    private String name;
    private String url;
    private String username;
    private String password;
}