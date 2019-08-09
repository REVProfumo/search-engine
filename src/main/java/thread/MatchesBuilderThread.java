package thread;


import bom.FileObject;

import java.util.Map;
import java.util.concurrent.Callable;

public class MatchesBuilderThread implements Callable<Map.Entry<FileObject, Integer>> {

  private FileObject fileObject;
  private String[] words;

  public MatchesBuilderThread(FileObject fileObject, String[] words) {
    this.fileObject = fileObject;
    this.words = words;
  }

  public Map.Entry<FileObject, Integer> call() {
    int nrMatches = 0;
    for (String word : words) {
      if (fileObject.getWords().contains(word)) {
        nrMatches += 1;
      }
    }
    return new java.util.AbstractMap.SimpleEntry(fileObject, nrMatches);
  }

}

