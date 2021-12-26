package com.springboot.prac.config;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class EnvironmentFromDbConfig implements EnvironmentPostProcessor {

    private static final String PROPERTY_SOURCE_NAME = "envPropFromDb";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {

        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        Map<String, Object> propertySource = new HashMap<>();

        try{
            DataSource ds = DataSourceBuilder.create()
                    .url(environment.getProperty("spring.datasource.url"))
                    .driverClassName(environment.getProperty("spring.datasource.driver-class-name"))
                    .username(environment.getProperty("spring.datasource.username"))
                    .password(environment.getProperty("spring.datasource.password"))
                    .build();

            con = ds.getConnection();
            preparedStatement = con.prepareStatement("SELECT PROPERTY_KEY, PROPERTY_VALUE FROM PROPERTIES_CONFIG");

            rs = preparedStatement.executeQuery();
            while (rs.next()){
                propertySource.put(rs.getString("PROPERTY_KEY"), rs.getString("PROPERTY_VALUE"));
            }

            environment.getPropertySources().addFirst(new MapPropertySource(PROPERTY_SOURCE_NAME, propertySource));

            System.out.println(new Gson().toJson(propertySource)); // 顯示載入的 application properties, 需要用 system, log 吃不到

        }catch (SQLException e){
            log.error(e.getMessage());
        }finally {
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if(con != null){
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
