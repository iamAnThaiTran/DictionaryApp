package com.app.dictionaryapp.DataAccessLayer;

import java.sql.ResultSet;
import java.util.List;
import redis.clients.jedis.Jedis;
public class Cache {

  private final Jedis jedis = new Jedis("localhost", 6379);
  private final Database database = new Database("jdbc:mysql://localhost:3306/DictionaryDatabase", "root",
      "Khongco2004@");


  public void putDataFromMySQL() {
    database.connectToDatabase();
    ResultSet resultSet = database.queryGetData("select word, html, description, pronounce from av");

    try {
      while (resultSet.next()) {
        String key = resultSet.getString("word").toLowerCase();

        String html = resultSet.getString("html");
        String description = resultSet.getString("description");
        String pronounce = resultSet.getString("pronounce");

        jedis.lpush(key, html);
        jedis.lpush(key, description);
        jedis.lpush(key, pronounce);
      }
    } catch (Exception e) {
      return;
    }
  }

  public List<String> getDataFromCache(String key) {
    return jedis.lrange(key, 0, -1);
  }

  public void deleteAllDataInCache() {
    jedis.flushDB();
  }

  public void disconnect() {
    jedis.disconnect();
  }
}
