package q6;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.PriorityQueue;

public class Q2 {

  public static void main(String[] args) throws IOException {
    PriorityQueue<Integer> lowerHeap = new PriorityQueue<>(5000, Collections.reverseOrder());
    PriorityQueue<Integer> upperHeap = new PriorityQueue<>(5000);
    long medianSum = 0;

    for (String line : Files.readAllLines(Paths.get(args[0]), StandardCharsets.UTF_8)) {
      int i = Integer.parseInt(line);
      if (lowerHeap.peek() != null && i < lowerHeap.peek()) {
        lowerHeap.add(i);
      } else {
        upperHeap.add(i);
      }
      if (lowerHeap.size() < upperHeap.size()) {
        lowerHeap.add(upperHeap.poll());
      } else if (lowerHeap.size() > upperHeap.size() + 1) {
        upperHeap.add(lowerHeap.poll());
      }
      medianSum += lowerHeap.peek();
    }
    System.out.println(medianSum % 10000);
  }

}
