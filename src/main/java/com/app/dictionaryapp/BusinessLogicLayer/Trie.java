package com.app.dictionaryapp.BusinessLogicLayer;

import com.app.dictionaryapp.DataAccessLayer.Database;

public class Trie {
  private Database database = new Database("jdbc:mysql://localhost:3306/DictionaryDatabase", "root", "Khongco2004@");
  private final int NUMBERSOFLETTER = 26;
  class TrieNode {
    TrieNode[] nodes = new TrieNode[NUMBERSOFLETTER];

  }
}
