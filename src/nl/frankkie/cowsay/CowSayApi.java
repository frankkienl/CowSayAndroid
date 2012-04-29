package nl.frankkie.cowsay;

import android.content.Context;
import android.os.Environment;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class CowSayApi {

  public static final String version = "3.03 ~ish";
  public static final String progname = "cowsay ~ish";
  public String eyes = "oo";
  public String tongue = "  ";
  public static String cowpath = "";
  public String message = "";
  public String thoughts = "";
  private String the_cow = "";
  public String file = "default.cow";
  //
  public boolean borg = false;
  public boolean dead = false;
  public boolean greedy = false;
  public boolean paranoid = false;
  public boolean stoned = false;
  public boolean tired = false;
  public boolean wired = false;
  public boolean young = false;
  //
  public boolean n_disableWordWrap = false;
  public int wordWrap = 40;
  private String stdin_str = "";
  private String[] stdin_arr = null;
  private String le_cow;
  private String balloon_str;

  public void initCowpath() {
    cowpath = Environment.getExternalStorageDirectory().getPath();
    if (!cowpath.endsWith("/")) {
      cowpath += "/";
    }
    cowpath += "cowsay/cows/";
    File f = new File(cowpath);
    //first time?
    f.mkdirs();
    if (f.list().length == 0){
      copyFiles();
    }
  }

  public void copyFiles(){
    copyFile(MainActivity.c, R.raw.android, new File(cowpath+"android.cows"));
    copyFile(MainActivity.c, R.raw.apt, new File(cowpath+"apt.cow"));
    copyFile(MainActivity.c, R.raw.beavis, new File(cowpath+"beavis.cow"));
    copyFile(MainActivity.c, R.raw.bong, new File(cowpath+"bong.cow"));
    copyFile(MainActivity.c, R.raw.bud_frogs, new File(cowpath+"bud_frogs.cow"));
    copyFile(MainActivity.c, R.raw.bunny, new File(cowpath+"bunny.cow"));
    copyFile(MainActivity.c, R.raw.calvin, new File(cowpath+"calvin.cow"));
    copyFile(MainActivity.c, R.raw.cheese, new File(cowpath+"cheese.cow"));
    copyFile(MainActivity.c, R.raw.cock, new File(cowpath+"cock.cow"));
    copyFile(MainActivity.c, R.raw.cower, new File(cowpath+"cower.cow"));
    copyFile(MainActivity.c, R.raw.daemon, new File(cowpath+"daemon.cow"));
    copyFile(MainActivity.c, R.raw.defaultcow, new File(cowpath+"default.cow"));
    copyFile(MainActivity.c, R.raw.dragon, new File(cowpath+"dragon.cow"));
    copyFile(MainActivity.c, R.raw.dragon_and_cow, new File(cowpath+"dragon-and-cow.cow"));
    copyFile(MainActivity.c, R.raw.elephant, new File(cowpath+"elephant.cow"));
    copyFile(MainActivity.c, R.raw.elephant_in_snake, new File(cowpath+"elephant-in-snake.cow"));
    copyFile(MainActivity.c, R.raw.flaming_sheep, new File(cowpath+"flaming-sheep.cow"));
    copyFile(MainActivity.c, R.raw.ghostbusters, new File(cowpath+"ghostbusters.cow"));
    copyFile(MainActivity.c, R.raw.gnu, new File(cowpath+"gnu.cow"));
    copyFile(MainActivity.c, R.raw.head_in, new File(cowpath+"head-in.cow"));
    copyFile(MainActivity.c, R.raw.hellokitty, new File(cowpath+"hellokitty.cow"));
    copyFile(MainActivity.c, R.raw.kiss, new File(cowpath+"kiss.cow"));
    copyFile(MainActivity.c, R.raw.kitty, new File(cowpath+"kitty.cow"));
    copyFile(MainActivity.c, R.raw.koala, new File(cowpath+"koala.cow"));
    copyFile(MainActivity.c, R.raw.kosh, new File(cowpath+"kosh.cow"));
    copyFile(MainActivity.c, R.raw.luke_koala, new File(cowpath+"luke-koala.cow"));
    copyFile(MainActivity.c, R.raw.mech_and_cow, new File(cowpath+"mech-and-cow.cow"));
    copyFile(MainActivity.c, R.raw.meow, new File(cowpath+"meow.cow"));
    copyFile(MainActivity.c, R.raw.milk, new File(cowpath+"milk.cow"));
    copyFile(MainActivity.c, R.raw.moofasa, new File(cowpath+"moofasa.cow"));
    copyFile(MainActivity.c, R.raw.moose, new File(cowpath+"moose.cow"));
    copyFile(MainActivity.c, R.raw.mutilated, new File(cowpath+"mutilated.cow"));
    copyFile(MainActivity.c, R.raw.pony, new File(cowpath+"pony.cow"));
    copyFile(MainActivity.c, R.raw.pony_smaller, new File(cowpath+"pony-smaller.cow"));
    copyFile(MainActivity.c, R.raw.ren, new File(cowpath+"ren.cow"));
    copyFile(MainActivity.c, R.raw.sheep, new File(cowpath+"sheep.cow"));
    copyFile(MainActivity.c, R.raw.skeleton, new File(cowpath+"skeleton.cow"));
    copyFile(MainActivity.c, R.raw.snowman, new File(cowpath+"snowman.cow"));
    copyFile(MainActivity.c, R.raw.sodomized_sheep, new File(cowpath+"sodomized-sheep.cow"));
    copyFile(MainActivity.c, R.raw.stegosaurus, new File(cowpath+"stegosaurus.cow"));
    copyFile(MainActivity.c, R.raw.stimpy, new File(cowpath+"stimpy.cow"));
    copyFile(MainActivity.c, R.raw.suse, new File(cowpath+"suse.cow"));
    copyFile(MainActivity.c, R.raw.three_eyes, new File(cowpath+"three-eyes.cow"));
    copyFile(MainActivity.c, R.raw.turkey, new File(cowpath+"turkey.cow"));
    copyFile(MainActivity.c, R.raw.turtle, new File(cowpath+"turtle.cow"));
    copyFile(MainActivity.c, R.raw.tux, new File(cowpath+"tux.cow"));
    copyFile(MainActivity.c, R.raw.unipony, new File(cowpath+"unipony.cow"));
    copyFile(MainActivity.c, R.raw.unipony_smaller, new File(cowpath+"unipony-smaller.cow"));
    copyFile(MainActivity.c, R.raw.vader, new File(cowpath+"vader.cow"));
    copyFile(MainActivity.c, R.raw.vader_koala, new File(cowpath+"koala.cow"));
    copyFile(MainActivity.c, R.raw.www, new File(cowpath+"www.cow"));
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

  public void performWordWrap() {
    stdin_str = stdin_str.replace("\t", "  ");
    if (n_disableWordWrap) {
      stdin_arr = new String[]{stdin_str};
    }
    stdin_arr = wrapText(stdin_str, wordWrap);
  }

  /**
   * Wrap text
   * @param input string to be wrap-ed
   * @param wordWrap max length of line
   * @return the wrapped text
   */
  public static String[] wrapText(String input, int wordWrap) {
    ArrayList<String> lines_temp = new ArrayList<String>();
    ArrayList<String> lines_done = new ArrayList<String>();
    String[] slashn_lines = input.split("\n");
    lines_temp.addAll(Arrays.asList(slashn_lines));
    //nu alles lines nog op lengte bekijken..
    for (int a = 0; a < lines_temp.size(); a++) {
      if (lines_temp.get(a).length() > wordWrap) {
        ///
        //System.out.println("wrapTextNew: " + lines_temp.get(a) + " length=" + lines_temp.get(a).length());
        StringBuilder temp_line = new StringBuilder();
        String[] words = lines_temp.get(a).split(" ");
        //temp_line.append(words[0]);
        for (int b = 0; b < words.length; b++) {
          if (words[b].length() <= wordWrap) {
            temp_line.append(words[b]);
          } else {
            //het Woord is langer dan de Zin zijn!
            for (int k = 0; k <= (words[b].length() / wordWrap); k++) {
              int l = (k + 1) * wordWrap;
              l--;
              //System.out.println("l = " + l + "&Lolzz=" + ((k * wordWrap) + words[b].length()) + "L=" + words[b].length());
              //if (l > ((k * wordWrap) + words[b].length())) {
              if (l > words[b].length()) {
                //System.out.println("A");
                temp_line.append(words[b].substring(k * wordWrap));
              } else {
                //System.out.println("B");
                //System.out.println("l = " + l + "&word.length=" + words[b].length() + "K=" + (k * wordWrap));
                temp_line.append(words[b].substring(k * wordWrap, l));
                lines_done.add(temp_line.toString());
                temp_line = new StringBuilder();
              }
            }
          }
          if (b != (words.length - 1)) {
            temp_line.append(" ");
          }
          //System.out.println("word=" + words[b] + "b=" + b + "&words.length=" + words.length + "&temp.length=" + temp_line.length());
          if (b == words.length - 1 || (temp_line.length() + words[b + 1].length()) >= wordWrap) {
            // || b == (words.length-1)
            lines_done.add(temp_line.toString());
            //System.out.println("wrapTextNew.temp_line = " + temp_line);
            temp_line = new StringBuilder();
          }
        }
        ///
      } else {
        lines_done.add(lines_temp.get(a));
      }
    }
    return lines_done.toArray(new String[0]);
  }

  public CowSayApi() {
    initCowpath();
  }

  /**
   * set the text for the balloon
   * the string will be WordWrap-ed.
   * @param s the text
   */
  public void setBalloonText(String s) {
    stdin_str = s;
    performWordWrap();
  }

  /**
   * set the cowfile using a cow-file
   * examples:
   * tux (if in cowpath)
   * tux.cow (if in cowpath)
   * /path/to/tux
   * /path/to/tux.com
   * @param filename of cow-file
   */
  public void setCowFile(String filename) {
    file = filename;
  }

  /**
   * Make the cowsay
   * @return the cowsay
   */
  public String make() {
    contruct_face();
    balloon_str = construct_balloon();
    le_cow = get_cow(file);
    StringBuilder s = new StringBuilder();
    s.append(balloon_str).append("\n").append(le_cow);
    return s.toString();
  }

  private int maxlength() {
    int ans = 0;
    for (String line : stdin_arr) {
      if (ans < line.length()) {
        ans = line.length();
      }
    }
    return ans;
  }

  private String get_cow(String filename) {
    if (!filename.endsWith(".cow")) {
      filename += ".cow";
    }
    File cowfile = null;
    cowfile = new File(filename);
    if (!cowfile.exists()) {
      cowfile = new File(cowpath + filename);
    }
    String c = getContentsOfFile(cowfile);
    c = fixCowStr(c);
    c = c.replace("$thoughts", thoughts);
    c = c.replace("$eyes", eyes);
    c = c.replace("${eyes}", eyes);
    c = c.replace("$tongue", tongue);
    c = c.replace("\\@", "@");
    c = c.replace("\\\\", "\\");
    return c;
  }

  private static String fixCowStr(String c) {
    String[] arr = c.split("\n");
    StringBuilder ans = new StringBuilder();
    for (String line : arr) {
      if (line.startsWith("#") || line.equals("") || line.contains("$the_cow") || line.contains("EOC") || line.contains("EOF")) {
        continue;
      }
      ans.append(line).append("\n");
    }
    return ans.toString();
  }

  /**
   * http://www.javapractices.com/topic/TopicAction.do?Id=42
   * @param aFile
   * @return
   * little mod: remove empty lines
   */
  static public String getContentsOfFile(File aFile) {
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
          //if (!line.equals("")) {
          contents.append(line);
          contents.append(System.getProperty("line.separator"));
          //}
        }
      } finally {
        input.close();
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }

    return contents.toString();
  }

  private String construct_balloon() {
    int max = maxlength();
    int max2 = max + 2;
    int num_lines = stdin_arr.length;
    int bordertype = -1; //0 = think; 1 = single-line; 2 = multi-line
    if (false) { //this is cowSAY not cowTHINK
      thoughts = "o";
      bordertype = 0;
    } else if (num_lines < 2) {
      thoughts = "\\";
      bordertype = 1;
    } else {
      thoughts = "\\";
      bordertype = 2;
    }
    ////
    StringBuilder balloon_str = new StringBuilder();
    //Top line
    balloon_str.append(" ");
    for (int idk_a = 0; idk_a < max2; idk_a++) {
      balloon_str.append("_");
    }
    balloon_str.append(" \n");
    //middle lines
    //TODO einde van de lines kloppend maken
    for (int idk_b = 0; idk_b < num_lines; idk_b++) {
      if (bordertype == 1) {
        balloon_str.append("< ").append(stdin_arr[0].replace("\n", "")).append(" >\n");
      }
      if (bordertype == 0) {
        balloon_str.append("( ");
        balloon_str.append(stdin_arr[idk_b]);
        for (int idk_c = stdin_arr[idk_b].length(); idk_c < (max); idk_c++) {
          balloon_str.append(" ");
        }
        balloon_str.append(" )\n)");
      }
      if (bordertype == 2) {
        if (idk_b == 0) {
          balloon_str.append("/ ").append(stdin_arr[0]);
          for (int idk_c = stdin_arr[idk_b].length(); idk_c < (max); idk_c++) {
            balloon_str.append(" ");
          }
          balloon_str.append(" \\\n");
        } else if (idk_b == num_lines - 1) {
          balloon_str.append("\\ ").append(stdin_arr[idk_b]);
          for (int idk_c = stdin_arr[idk_b].length(); idk_c < (max); idk_c++) {
            balloon_str.append(" ");
          }
          balloon_str.append(" /\n");
        } else {
          balloon_str.append("| ").append(stdin_arr[idk_b]);
          for (int idk_c = stdin_arr[idk_b].length(); idk_c < (max); idk_c++) {
            balloon_str.append(" ");
          }
          balloon_str.append(" |\n");
        }
      }
    }
    //last line
    balloon_str.append(" ");
    for (int idk_a = 0; idk_a < max2; idk_a++) {
      balloon_str.append("-");
    }
    //balloon_str.append(" \n");

    return balloon_str.toString();
  }

  public String list_cowfiles() {
    File cowpath_file = new File(cowpath);
    File[] files = cowpath_file.listFiles();
    StringBuilder s = new StringBuilder();
    for (File f : files) {
      //System.out.println(f.getAbsolutePath());
      s.append(f.getAbsolutePath()).append("\n");
    }
    //System.exit(0);
    return s.toString();
  }

  public String display_usage() {
    StringBuilder s = new StringBuilder();
    s.append("cow{say,think} version ").append(version).append(", (c) 1999 Tony Monroe").append("\n");
    s.append("Usage: ").append(progname).append(" [-bdgpstwy] [-h] [-e eyes] [-f cowfile]").append("\n");
    s.append("[-l] [-n] [-T tongue] [-W wrapcolumn] [message]\n");
    s.append("Java Port by FrankkieNL");
    return s.toString();
  }

  private void contruct_face() {
    if (borg) {
      eyes = "==";
    }
    if (dead) {
      eyes = "xx";
      tongue = "U ";
    }
    if (greedy) {
      eyes = "$$";
    }
    if (paranoid) {
      eyes = "@@";
    }
    if (stoned) {
      eyes = "**";
      tongue = "U ";
    }
    if (tired) {
      eyes = "--";
    }
    if (wired) {
      eyes = "OO";
    }
    if (young) {
      eyes = "..";
    }
  }
}
