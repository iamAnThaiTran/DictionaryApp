package com.app.dictionaryapp.DataAccessLayer;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
public class Cache {
  private Jedis jedis = new Jedis("localhost", 6379);
  private Database database = new Database("jdbc:mysql://localhost:3306/DictionaryDatabase", "root", "Khongco2004@");

  public void putDataFromMySQL() {
    database.connectToDatabase();
    ResultSet resultSet = database.queryGetData("select word, description, pronounce from av");

    try {
      while (resultSet.next()) {
        String key = resultSet.getString("word").toLowerCase();

        String description = resultSet.getString("description");
        String pronounce = resultSet.getString("pronounce");

        if (!pronounce.equals("")) {
          String value = description + "//////" + pronounce;
          jedis.set(key, value);
          jedis.expire(key, 100);
        }
      }
    } catch (Exception e) {
      return;
    }
  }

  public String getDataFromCache(String key) {
    return jedis.get(key);
  }

  public void deleteAllDataInCache() {
    jedis.flushDB();
  }
  public void disconnect() {
    jedis.disconnect();
  }

}
