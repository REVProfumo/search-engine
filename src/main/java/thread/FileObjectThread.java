package thread;

import bom.FileObject;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

public class FileObjectThread implements Callable<FileObject> {

  private File file;
  
  public FileObjectThread(File file) {
    this.file = file;
  }
  
  public FileObject call() throws IOException {
    return new FileObject(file);
  }
  
}
