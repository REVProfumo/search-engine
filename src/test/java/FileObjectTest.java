import bom.FileObject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class FileObjectTest {

  private Set<String> expectedWords;

  @Before
  public void setup() {
    expectedWords = new HashSet<>();
    expectedWords.add("one");
    expectedWords.add("two");
    expectedWords.add("three");
    expectedWords.add("four");
    expectedWords.add("five");
    expectedWords.add("six");
    expectedWords.add("ten");
    expectedWords.add("eleven");
  }
  
  @Test
  public void testFileRead() throws IOException {
    File file = new File("src/test/resources/dummy.txt");
    FileObject fileObject = new FileObject(file);
    String name = fileObject.getName();
    Set<String> words = fileObject.getWords();
    assertEquals("dummy.txt", name);
    assertEquals(expectedWords.size(), words.size());
    assertEquals(expectedWords, words);
  }


}
