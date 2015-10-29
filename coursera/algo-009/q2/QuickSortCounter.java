package q2;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuickSortCounter {

  private List<Integer> ints;
  private long comparisons = 0;

  public static void main(String[] args) throws IOException {
    QuickSortCounter counter = new QuickSortCounter();
    System.out.println("Parsing input file...");
    counter.readFile(args[0]);
    List<Integer> ints = new ArrayList<Integer>(counter.getIntegers());
    System.out.println("Loaded " + counter.getIntegers().size() + " ints:");
    System.out.println("Verifying sortedness: " + counter.isSorted());
    // printArray(counter.getIntegers());
    counter.countComparisonsPivotingFirst();
    System.out
        .println("Comparisons observed using first element as pivot: " + counter.getComparisons());
    System.out.println("Verifying sortedness: " + counter.isSorted());
    // printArray(counter.getIntegers());
    counter.setIntegers(ints);
    counter.countComparisonsPivotingLast();
    System.out
        .println("Comparisons observed using last element as pivot: " + counter.getComparisons());
    System.out.println("Verifying sortedness: " + counter.isSorted());
    // printArray(counter.getIntegers());
    counter.setIntegers(ints);
    counter.countComparisonsPivotingMedianOfThree();
    System.out
        .println("Comparisons observed using last element as pivot: " + counter.getComparisons());
    System.out.println("Verifying sortedness: " + counter.isSorted());
    // printArray(counter.getIntegers());
  }

  public boolean isSorted() {
    for (int i = 1; i < ints.size(); ++i) {
      if (ints.get(i - 1) > ints.get(i)) {
        return false;
      }
    }
    return true;
  }

  public void countComparisonsPivotingFirst() {
    comparisons = quickSortPivotingFirst(0, ints.size());
  }

  public void countComparisonsPivotingLast() {
    comparisons = quickSortPivotingLast(0, ints.size());
  }

  public void countComparisonsPivotingMedianOfThree() {
    comparisons = quickSortPivotingMedianOfThree(0, ints.size());
  }

  private long quickSortPivotingFirst(int start, int end) {
    long count = 0;
    if (end - start > 1) {
      count = end - start - 1;
      int pivot = ints.get(start);
      int i = start + 1;
      for (int j = i; j < end; ++j) {
        if (ints.get(j) < pivot) {
          if (i < j) {
            swapInts(i, j);
          }
          ++i;
        }
      }

      if (i > start + 1) {
        swapInts(start, i - 1);
      }
      count += quickSortPivotingFirst(start, i - 1);
      count += quickSortPivotingFirst(i, end);
    }
    return count;
  }

  private long quickSortPivotingLast(int start, int end) {
    long count = 0;
    if (end - start > 1) {
      swapInts(start, end - 1);
      count = end - start - 1;
      int pivot = ints.get(start);
      int i = start + 1;
      for (int j = i; j < end; ++j) {
        if (ints.get(j) < pivot) {
          if (i < j) {
            swapInts(i, j);
          }
          ++i;
        }
      }

      if (i > start + 1) {
        swapInts(start, i - 1);
      }
      count += quickSortPivotingLast(start, i - 1);
      count += quickSortPivotingLast(i, end);
    }
    return count;
  }

  private long quickSortPivotingMedianOfThree(int start, int end) {
    long count = 0;
    if (end - start > 1) {
      count = end - start - 1;
      swapInts(getIndexOfMedianOfThree(start, end), start);
      int pivot = ints.get(start);
      int i = start + 1;
      for (int j = i; j < end; ++j) {
        if (ints.get(j) < pivot) {
          if (i < j) {
            swapInts(i, j);
          }
          ++i;
        }
      }

      if (i > start + 1) {
        swapInts(start, i - 1);
      }
      count += quickSortPivotingMedianOfThree(start, i - 1);
      count += quickSortPivotingMedianOfThree(i, end);
    }
    return count;
  }

  private int getIndexOfMedianOfThree(int start, int end) {
    int first = start;
    int last = end - 1;
    int half = (start + end - 1) / 2;
    int index;
    if (ints.get(first) > ints.get(last)) {
      if (ints.get(last) > ints.get(half)) {
        index = last;
      } else if (ints.get(half) > ints.get(first)) {
        index = first;
      } else {
        index = half;
      }
    } else {
      if (ints.get(first) > ints.get(half)) {
        index = first;
      } else if (ints.get(last) > ints.get(half)) {
        index = half;
      } else {
        index = last;
      }
    }
    return index;
  }

  public List<Integer> getIntegers() {
    return ints;
  }

  public void setIntegers(List<Integer> ints) {
    this.ints = new ArrayList<>(ints);
  }

  public void readFile(String filePath) throws IOException {
    if (filePath.startsWith("RANDOM")) {
      int length = Integer.parseInt(filePath.substring(6));
      ints = new ArrayList<>(length);
      Random rand = new Random(System.currentTimeMillis());
      for (int i = 0; i < length; ++i) {
        ints.add(rand.nextInt(1000));
      }
    } else {
      ints = new ArrayList<>();
      for (String line : Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8)) {
        ints.add(Integer.parseInt(line));
      }
    }
  }

  private static <T> void printArray(List<T> array) {
    for (T e : array) {
      System.out.print(e + "\t");
    }
    System.out.println();
  }

  private long getComparisons() {
    return comparisons;
  }

  private void swapInts(int i, int j) {
    Collections.swap(ints, i, j);
  }

}
