package utils;

import bom.FileObject;
import thread.MatchesBuilderThread;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Utils {

  public static <K, V extends Comparable<? super V>>
  SortedSet<Map.Entry<K, V>> entriesSortedByValues(Map<K, V> map) {
    SortedSet<Map.Entry<K, V>> sortedEntries = new TreeSet<Map.Entry<K, V>>(
            new Comparator<Map.Entry<K, V>>() {
              @Override
              public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
                int res = e1.getValue().compareTo(e2.getValue());
                return res != 0 ? res : 1;
              }
            }
    );
    sortedEntries.addAll(map.entrySet());
    return sortedEntries;
  }

  public static Map<FileObject, Integer> getMatches(String[] words, List<FileObject> fileObjects)
          throws InterruptedException, ExecutionException {
    Map<FileObject, Integer> matchesMap = new HashMap<>();
    ExecutorService executorService = Executors.newCachedThreadPool();
    List<Future<Map.Entry<FileObject, Integer>>> futures =
            new ArrayList<>();
    for (FileObject fileObject : fileObjects) {
      futures.add(executorService.submit(new MatchesBuilderThread(fileObject, words)));
    }
    for (Map.Entry<FileObject, Integer> entry : getFutures(futures)) {
      matchesMap.put(entry.getKey(), entry.getValue());
    }
    return matchesMap;
  }

  public static <T> List<T> getFutures(List<Future<T>> futures) throws InterruptedException, ExecutionException {
    List<T> results = new ArrayList<>();
    List<Future<T>> completedFutures = new ArrayList<>();
    while (!futures.isEmpty()) {
      for (Future<T> future : futures) {
        if (future.isDone()) {
          results.add(future.get());
          completedFutures.add(future);
        }
      }
      futures.removeAll(completedFutures);
      Thread.sleep(100);
    }
    return results;
  }
}
