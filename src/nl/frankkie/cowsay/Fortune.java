package nl.frankkie.cowsay;

import android.content.Context;
import android.os.Environment;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author frankkie
 */
public class Fortune {

  static String fortunepath;
  static int recursie_number = 0;

  public static void main(String[] args) {
    getFortune();
  }

  public void initFortunepath() {
    fortunepath = Environment.getExternalStorageDirectory().getPath();
    if (!fortunepath.endsWith("/")) {
      fortunepath += "/";
    }
    fortunepath += "cowsay/fortunes/";
    File f = new File(fortunepath);
    f.mkdirs();
    if (f.list() == null || f.list().length == 0){
      copyFiles();
    }
  }
  
  public void copyFiles(){
    copyFile(MainActivity.c, R.raw.fortunes, new File(fortunepath+"fortune.u8"));
    copyFile(MainActivity.c, R.raw.riddles, new File(fortunepath+"riddles.u8"));
    copyFile(MainActivity.c, R.raw.literature, new File(fortunepath+"literature.u8"));
  }

  public void copyFile(Context c, int resource, File outf){
    if (!outf.exists()) {
      InputStream fIn = c.getResources().openRawResource(resource);
      try {
        int size = fIn.available();
        byte[] buffer = new byte[size];
        fIn.read(buffer);
        fIn.close();
        outf.createNewFile();
        FileOutputStream rtFOS = new FileOutputStream(outf);
        rtFOS.write(buffer);
        rtFOS.flush();
        rtFOS.close();
      } catch (Exception ex) {
        //System.out.println("Fout bij: MainActivity.checkOfSoundFileBestaat()");
        ex.printStackTrace();
      }
    }
  }

  public File getRandomFortuneFile(){
    File path = new File(fortunepath);
    File[] arr = path.listFiles(new FilenameFilter() {

      public boolean accept(File arg0, String arg1) {
        //throw new UnsupportedOperationException("Not supported yet.");
        return arg1.endsWith(".u8");
      }
    });
    if (arr.length == 0){
      throw new RuntimeException("There a no fortune files !!");
    }
    return arr[(int)(arr.length*Math.random())];
  }

  public Fortune() {
    initFortunepath();
  }
  
  private String make(){
    File ffile = getRandomFortuneFile();
    String content = getContents(ffile);
    String fortune_str = getFortune(content);
    return fortune_str;
  }

  public static String getFortune(){
    return new Fortune().make();
  }

public String getFortune(String content){
  String[] arr = content.split("%");
  int rr = (int)(Math.random() * arr.length);
  if (arr[rr].startsWith("\n")){
    return arr[rr].substring(1);
  }
  return arr[rr];
}

  /**
   * http://www.javapractices.com/topic/TopicAction.do?Id=42
   * @param aFile
   * @return
   * little mod: remove empty lines
   */
  static public String getContents(File aFile) {
    //...checks on aFile are elided
    StringBuilder contents = new StringBuilder();

    try {
      //use buffering, reading one line at a time
      //FileReader always assumes default encoding is OK!
      BufferedReader input = new BufferedReader(new FileReader(aFile));
      try {
        String line = null; //not declared within while loop
        /*
         * readLine is a bit quirky :
         * it returns the content of a line MINUS the newline.
         * it returns null only for the END of the stream.
         * it returns an empty String if two newlines appear in a row.
         */
        while ((line = input.readLine()) != null) {
          if (!line.equals("")) {
            contents.append(line);
            contents.append(System.getProperty("line.separator"));
          }
        }
      } finally {
        input.close();
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }

    return contents.toString();
  }
}
