# javaFX-project

## Create DB environment
in `src/main/java/service/` create a file named `DBCredentials.java` and add :
```java
package service;

public final class DBCredentials {
    private DBCredentials() {}
    public static final String DBMS = "mariadb";        // TO CHANGE, example : "mysql"
    public static final String HOST = "127.0.0.1";
    public static final String PORT = "3306";
    public static final String DATABASE = "wstore";     
    public static final String USERNAME = "root";       // TO CHANGE
    public static final String PASSWORD = "toor";       // TO CHANGE
}
```
