package q6;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class Q1 {

  Set<Long> longs;

  public static void main(String[] args) throws IOException {
    Q1 algo = new Q1();
    algo.loadLongs(args[0]);
    System.out.println("Loaded input, size " + algo.getLongs().size() + ".");
    int count = 0;
    for (int t = -10000; t <= 10000; ++t) {
      System.out.print("Analyzing " + t + "...");
      if (algo.hasAddends(t)) {
        ++count;
      }
      System.out.println(count);
    }
    System.out.println(count);
  }

  public void loadLongs(String fileName) throws IOException {
    longs = new HashSet<>();
    for (String line : Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8)) {
      longs.add(Long.parseLong(line));
    }
  }

  public boolean hasAddends(long t) {
    for (long i : longs) {
      long theOtherAddend = t - i;
      if ((i != theOtherAddend) && longs.contains(theOtherAddend)) {
        return true;
      }
    }
    return false;
  }

  public Set<Long> getLongs() {
    return longs;
  }

}
