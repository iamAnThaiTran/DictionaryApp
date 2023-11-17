package com.app.dictionaryapp.BusinessLogicLayer;

import com.app.dictionaryapp.DataAccessLayer.Cache;
import com.app.dictionaryapp.DataAccessLayer.Database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

class TrieNode {
  Map<Character, TrieNode> nodeMap;
  boolean endOfWord;

  TrieNode() {
    nodeMap = new TreeMap<Character, TrieNode>();
    endOfWord = false;
  }

  TrieNode(Map<Character, TrieNode> nodeMap, boolean endOfWord) {
    this.nodeMap = nodeMap;
    this.endOfWord = endOfWord;
  }
}

public class Trie {
  TrieNode root;
  Database database;

  Cache cache;
  private void dfs(String curPrefix, TrieNode trieNode, ObservableList<String> observableList) {
    if (trieNode.endOfWord) {
      observableList.add(curPrefix);
    }

    for (Map.Entry<Character, TrieNode> entry : trieNode.nodeMap.entrySet()) {
      dfs(curPrefix + entry.getKey(), entry.getValue(), observableList);
    }
  }

  public Trie() {
    root = new TrieNode();
    database = new Database("jdbc:mysql://localhost:3306/DictionaryDatabase", "root", "Khongco2004@");
    cache = new Cache();
  }

  public void insertWord(String word) {
    TrieNode cur = root;
    for (char ele : word.toCharArray()) {
      cur.nodeMap.putIfAbsent(ele, new TrieNode());
      cur = cur.nodeMap.get(ele);
    }

    cur.endOfWord = true;
  }

  public void insertAllWord() throws SQLException {
    database.connectToDatabase();
    ResultSet resultSet = database.queryGetData("select word from av");
    while(resultSet.next()) {
      insertWord(upperCaseFirstLetter
          (resultSet.getString("word")));
    }
    resultSet.close();

    ResultSet resultSet1 = database.queryGetData("select word from YourDictionary");
    while (resultSet1.next()) {
      insertWord(resultSet1.getString("word"));
    }
    resultSet1.close();
    database.disconnectToDatabase();
  }

  public ObservableList<String> autoComplete(String prefix) {
    ObservableList<String> observableList = FXCollections.observableArrayList();
    TrieNode current = root;

    prefix = upperCaseFirstLetter(prefix);

    for (char ele : prefix.toCharArray()) {
      if (current.nodeMap.containsKey(ele)) {
        current = current.nodeMap.get(ele);
      } else {
        return observableList;
      }
    }

    dfs(prefix, current, observableList);

    return observableList;
  }

  String upperCaseFirstLetter(String text) {
    String firstText = text.substring(0, 1).toUpperCase();
    String remainText = text.substring(1);

    return firstText + remainText;
  }
}