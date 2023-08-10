package com.myapp.todolist.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.myapp.todolist.util.DatabaseUtil.DatabaseException;

public class DatabaseTestUtil {
    private static final String CONFIG_PATH = "test-config.properties";
    private static Properties props = new Properties();
    
    static {
        try (InputStream input = DatabaseTestUtil.class.getClassLoader().getResourceAsStream(CONFIG_PATH)) {
        	props.load(input);
        } catch (IOException ex) {
            throw new DatabaseException("データベースの設定ファイルが見つかりません。", ex);
        }
    }

    public static Connection getTestConnection() throws SQLException {
        String url = props.getProperty("jdbc.url");
        String user = props.getProperty("jdbc.user");
        String password = props.getProperty("jdbc.password");
        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }
}
