package com.dujc.leetcode;

import java.util.*;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _Heap_692 {
    public static void main(String[] args) {
        System.out.println(topKFrequent(new String[]{"the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"}, 4));
    }

    public static class WordAndFrequent implements Comparable<WordAndFrequent>{
        String word;
        Integer frequent;

        public WordAndFrequent(String word, Integer frequent) {
            this.word = word;
            this.frequent = frequent;
        }

        @Override
        public String toString() {
            return "WordAndFrequent{" +
                    "word='" + word + '\'' +
                    ", frequent=" + frequent +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            WordAndFrequent that = (WordAndFrequent) o;
            return word.equals(that.word) && frequent.equals(that.frequent);
        }

        @Override
        public int hashCode() {
            return Objects.hash(word, frequent);
        }

        @Override
        public int compareTo(WordAndFrequent o) {
            return this.frequent - o.frequent != 0 ? this.frequent - o.frequent :
                    o.word.compareTo(this.word)
             ;
        }
    }

    public static List<String> topKFrequent(String[] words, int k) {
        HashMap<String, Integer> word2Frequent = new HashMap<>();
        for (String word : words) {
            if (word2Frequent.containsKey(word)) {
                word2Frequent.put(word, word2Frequent.get(word) + 1);
            } else {
                word2Frequent.put(word, 1);
            }
        }
        PriorityQueue<WordAndFrequent> minHeap = new PriorityQueue<>(word2Frequent.size());
        for (Map.Entry<String, Integer> entry : word2Frequent.entrySet()) {
            final WordAndFrequent wordAndFrequent = new WordAndFrequent(entry.getKey(), entry.getValue());
            if (minHeap.size()<k) {
                minHeap.add(wordAndFrequent);
            }else{
                if (wordAndFrequent.compareTo(minHeap.peek())>0) {
                    minHeap.poll();
                    minHeap.add(wordAndFrequent);
                }
            }
        }

        System.out.println(minHeap.toString());
        List<String> res = new ArrayList<>(k);
        LinkedList<WordAndFrequent> stack = new LinkedList<>();
        while (!minHeap.isEmpty()){
            stack.push(minHeap.poll());
        }
        while (!stack.isEmpty()){
            res.add(stack.pop().word);
        }
        return res;
    }
}
