package syvenko.config.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import syvenko.jsondb.JsonDB;

import java.io.IOException;

@Configuration
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class})
@Profile("json")
public class JsonDataSourceConfig {

    @Autowired
    Environment env;

    @Bean
    public JsonDB initDB(){
        JsonDB db = new JsonDB(env.getProperty("jsondb.path"));
        try {
            db.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return db;
    }

}
