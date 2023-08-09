package com.myapp.todolist.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * データベース接続を管理するユーティリティクラス。
 */
public class DatabaseUtil {
    private static final String CONFIG_PATH = "config.properties";
    private static Properties props = new Properties();
    
    // カスタム例外の定義
    public static class DatabaseException extends RuntimeException {
        public DatabaseException(String message) {
            super(message);
        }
        
        public DatabaseException(String message, Throwable cause) {
            super(message, cause);
        }
    }


    /**
     * static初期化子を使用して、データベースの設定ファイルを読み込みます。
     */
    static {
        try (InputStream input = DatabaseUtil.class.getClassLoader().getResourceAsStream(CONFIG_PATH)) {
            if (input == null) {
                throw new DatabaseException("データベースの設定ファイルが見つかりません。");
            }
            props.load(input);
        } catch (IOException ex) {
            throw new DatabaseException("データベースの設定読み込みエラー。", ex);
        }
    }

    /**
     * 読み込んだプロパティを使用してデータベースへ接続します。
     *
     * @return データベースへの接続
     * @throws DatabaseException データベースへの接続中にエラーが発生した場合。
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");
            
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new DatabaseException("データベースへの接続エラー。", e);
        }
        return conn;
    }
    
    }
