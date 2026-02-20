package dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConnectionProperty {
    
    public static final String CONFIG_NAME = "config.properties";
    public static final Properties GLOBAL_CONFIG = new Properties();
    
    public ConnectionProperty() throws IOException {
        // Загрузка свойств из classpath (WEB-INF/classes)
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(CONFIG_NAME);
        if (inputStream == null) {
            throw new FileNotFoundException("Файл " + CONFIG_NAME + " не найден в classpath");
        }
        GLOBAL_CONFIG.load(inputStream);
        inputStream.close();
        
        // Для отладки - выведем загруженные свойства
        System.out.println("Загружены настройки БД:");
        System.out.println("URL: " + GLOBAL_CONFIG.getProperty("db.url"));
        System.out.println("Login: " + GLOBAL_CONFIG.getProperty("db.login"));
    }
    
    // Получить значение параметра из конфигурации по имени свойства
    public static String getProperty(String property) {
        return GLOBAL_CONFIG.getProperty(property);
    }
}