import bom.FileObject;
import thread.FileObjectThread;
import utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static utils.Utils.getFutures;

public class Main {

  private static final int MAX_PRINTED_FILES = 10;

  private static List<FileObject> fileObject;
  
  private static List<FileObject> getFileObjects(List<Future<FileObject>> futures) throws InterruptedException, ExecutionException {
    if (fileObject == null) {
      fileObject = getFutures(futures);
    }
    return fileObject;
  }
  
  public static void main(String[] args) {
    if (args.length == 0) {
      throw new IllegalArgumentException("No directory given to index.");
    }
    final File indexableDirectory = new File(args[0]);
    if (!indexableDirectory.exists() && !indexableDirectory.isDirectory()) {
      throw new IllegalArgumentException("Directory does not exist.");
    }

    ExecutorService executorService = Executors.newCachedThreadPool();
    List<Future<FileObject>> fileObjectFutures = new ArrayList<>();
    for (File file : indexableDirectory.listFiles()) {
      fileObjectFutures.add(executorService.submit(new FileObjectThread(file)));
    }
    try {
      Scanner keyboard = new Scanner(System.in);
      while (true) {
        System.out.print("search> ");
        String readLine = keyboard.nextLine();
        if (readLine.equals(":quit")) {
          throw new InterruptedException();
        } else {
          Set<String> words = new HashSet<>(Arrays.asList(readLine.split(" ")));
          Map<FileObject, Integer> mapMatches = Utils.getMatches(words.stream().toArray(String[]::new), 
                  getFileObjects(fileObjectFutures));
          Iterator<Map.Entry<FileObject, Integer>> iterator = Utils.entriesSortedByValues(mapMatches).iterator();
          int i = 0;
          while (iterator.hasNext() && i < MAX_PRINTED_FILES) {
            Map.Entry<FileObject, Integer> fileObjectAndMatches = iterator.next();
            i++;
            System.out.println(fileObjectAndMatches.getKey().getName() + "  " + 
                    (100. * fileObjectAndMatches.getValue()) / words.size() + "%");
          }
        }
      }
    } catch (InterruptedException | ExecutionException e) {
      System.out.println("End program");
      System.exit(0);
    }
  }
  
}
