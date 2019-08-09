package bom;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class FileObject {

  private String name;

  private Set<String> words;

  public FileObject(File file) throws IOException {
    name = file.getName();
    String fileContent = "";
    String line;
    if (file.isFile()) {
      BufferedReader bf = new BufferedReader(new FileReader(file));
      while ((line = bf.readLine()) != null) {
        fileContent += line + "\n";
      }
    }
    words = new HashSet<>();
    String[] wordsInFile;
    wordsInFile = fileContent.replace("\r"," ").replace("\t", " ").
                            replace("\n", " ").split(" ");
    for (String word : wordsInFile) {
      if (!word.equals(" ")) {
        words.add(word);
      }
    }
  }

  public String getName() {
    return name;
  }

  public Set<String> getWords() {
    return words;
  }
}
