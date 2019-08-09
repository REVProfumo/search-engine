import bom.FileObject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static utils.Utils.getMatches;

public class UtilsTest {

  @Test
  public void testMatches() throws IOException, InterruptedException, ExecutionException {
    File file = new File("src/test/resources/dummy.txt");
    FileObject fileObject = new FileObject(file);
    assertEquals(new Integer(1), getMatches(new String[]{"one", "seven"}, 
            new ArrayList<>(Arrays.asList(fileObject))).get(fileObject));
    assertEquals(new Integer(2), getMatches(new String[]{"one", "two"}, 
            new ArrayList<>(Arrays.asList(fileObject))).get(fileObject));
    assertEquals(new Integer(0), getMatches(new String[]{"nine"}, 
            new ArrayList<>(Arrays.asList(fileObject))).get(fileObject));
  }

}
