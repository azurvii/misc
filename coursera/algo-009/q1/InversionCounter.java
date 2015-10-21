import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InversionCounter {

  private List<Integer> ints;
  private List<Integer> buffer;
  private Long inversions;

  public static void main(String[] args) throws IOException {
    InversionCounter counter = new InversionCounter();
    System.out.println("Parsing input file...");
    counter.readFile(args[0]);
    System.out.println("Input size: " + counter.getIntegers().size());
    System.out.println("Counting inversions...");
    counter.countInversions();
    System.out.println("Inversions observed: " + counter.getInversions());
  }

  public List<Integer> getIntegers() {
    return ints;
  }

  public Long getInversions() {
    return inversions;
  }

  public void countInversions() {
    inversions = countInversions(0, ints.size());
  }

  private Long countInversions(int start, int end) {
    long count = 0;
    if (end - start < 2) {
      count = 0;
    } else {
      int half = (start + end) / 2;
      long leftInversions = countInversions(start, half);
      long rightInversions = countInversions(half, end);
      long splitInversions = mergeAndCountSplitInversions(start, end);
      count = leftInversions + rightInversions + splitInversions;
    }
    return count;
  }

  private long mergeAndCountSplitInversions(int start, int end) {
    final int half = (start + end) / 2;
    int i = start;
    int j = half;
    long splitInversions = 0;
    for (int p = start; p < end; ++p) {
      if (i < half && j < end) {
        if (ints.get(i) < ints.get(j)) {
          buffer.set(p, ints.get(i));
          ++i;
        } else {
          buffer.set(p, ints.get(j));
          splitInversions += half - i;
          ++j;
        }
      } else {
        if (i < half) {
          buffer.set(p, ints.get(i));
          ++i;
        } else {
          buffer.set(p, ints.get(j));
          ++j;
        }
      }
    }
    Collections.copy(ints.subList(start, end), buffer.subList(start, end));
    return splitInversions;
  }

  public void readFile(String filePath) throws IOException {
    ints = new ArrayList<>();
    for (String line : Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8)) {
      ints.add(Integer.parseInt(line));
    }
    buffer = new ArrayList<>(ints);
  }

}
