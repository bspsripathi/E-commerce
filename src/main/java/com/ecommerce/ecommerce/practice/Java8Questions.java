package com.ecommerce.ecommerce.practice;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Collection of Java 8 style coding question solutions.
 * Each public static method demonstrates a small, self-contained solution using Java 8 APIs
 * (Streams, Optional, Collectors, CompletableFuture, java.time, functional interfaces).
 */
public class Java8Questions {

    // 1) Reverse words in a sentence
    // Input: "the sky is blue" -> Output: "blue is sky the"
    public static String reverseWords(String sentence) {
        if (sentence == null) return null;
        return Arrays.stream(sentence.trim().split("\\s+"))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
                    Collections.reverse(list);
                    return String.join(" ", list);
                }));
    }

    // 2) Top N frequent words
    public static List<String> topNFrequent(List<String> words, int n) {
        if (words == null || words.isEmpty() || n <= 0) return Collections.emptyList();
        return words.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(w -> w, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted((e1, e2) -> {
                    int cmp = Long.compare(e2.getValue(), e1.getValue());
                    return (cmp != 0) ? cmp : e1.getKey().compareTo(e2.getKey());
                })
                .limit(n)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    // 3) Merge intervals (each interval is int[2] with start and end)
    public static List<int[]> mergeIntervals(List<int[]> intervals) {
        if (intervals == null) return Collections.emptyList();
        return intervals.stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparingInt(a -> a[0]))
                .collect(() -> new ArrayList<int[]>(), (list, curr) -> {
                    if (list.isEmpty()) {
                        list.add(new int[]{curr[0], curr[1]});
                    } else {
                        int[] last = list.get(list.size() - 1);
                        if (curr[0] <= last[1]) {
                            last[1] = Math.max(last[1], curr[1]);
                        } else {
                            list.add(new int[]{curr[0], curr[1]});
                        }
                    }
                }, (l1, l2) -> l1.addAll(l2));
    }

    // 4) Partition integers into even/odd and count
    public static Map<Boolean, Long> partitionEvenOddCount(List<Integer> nums) {
        if (nums == null) return Collections.emptyMap();
        return nums.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.partitioningBy(i -> i % 2 == 0, Collectors.counting()));
    }

    // 5) Flatten nested lists and remove duplicates, sorted
    public static List<String> flattenAndUnique(List<List<String>> nested) {
        if (nested == null) return Collections.emptyList();
        return nested.stream()
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    // 6) Optional-based null-safe pipeline
    public static class User {
        private final Address address;
        public User(Address address) { this.address = address; }
        public Address getAddress() { return address; }
    }
    public static class Address {
        private final String city;
        public Address(String city) { this.city = city; }
        public String getCity() { return city; }
    }

    public static String getCityUppercaseOrUnknown(User user) {
        return Optional.ofNullable(user)
                .map(User::getAddress)
                .map(Address::getCity)
                .map(String::toUpperCase)
                .orElse("UNKNOWN");
    }

    // 7) Group objects and compute aggregate with Collectors
    public static class Product {
        private final String category;
        private final double price;
        public Product(String category, double price) { this.category = category; this.price = price; }
        public String getCategory() { return category; }
        public double getPrice() { return price; }
    }

    public static Map<String, Double> totalPricePerCategory(List<Product> products) {
        if (products == null) return Collections.emptyMap();
        return products.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Product::getCategory,
                        Collectors.summingDouble(Product::getPrice)));
    }

    // 8) Memoized Supplier (thread-safe)
    public static <T> Supplier<T> memoizeSupplier(Supplier<T> delegate) {
        Objects.requireNonNull(delegate);
        AtomicReference<T> ref = new AtomicReference<>();
        return () -> {
            T value = ref.get();
            if (value == null) {
                synchronized (ref) {
                    value = ref.get();
                    if (value == null) {
                        value = delegate.get();
                        ref.set(value);
                    }
                }
            }
            return value;
        };
    }

    // 9) Pagination using Stream skip/limit
    public static <T> List<T> paginate(List<T> source, int page, int size) {
        if (source == null || page < 1 || size < 1) return Collections.emptyList();
        return source.stream()
                .skip((long)(page - 1) * size)
                .limit(size)
                .collect(Collectors.toList());
    }

    // 10) Parallel-safe string concatenation using Collector (works with parallel streams)
    public static String concatWithDelimiterParallel(List<String> items, String delimiter) {
        if (items == null) return "";
        return items.parallelStream()
                .filter(Objects::nonNull)
                .collect(Collectors.joining(delimiter));
    }

    // 11) CompletableFuture pipeline with basic exception handling
    public static String asyncFetchTransformWithFallback() {
        CompletableFuture<String> fut = CompletableFuture.supplyAsync(() -> {
            // simulate fetch
            if (new Random().nextBoolean()) throw new RuntimeException("fetch failed");
            return "hello";
        }).thenApply(String::toUpperCase)
          .exceptionally(ex -> "FALLBACK");

        try {
            return fut.get();
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            return "ERROR";
        }
    }

    // Demo main - small examples
  /*  public static void main(String[] args) {
        System.out.println("reverseWords: " + reverseWords("the sky is blue"));
        System.out.println("topN: " + topNFrequent(Arrays.asList("a","b","a","c","b","a"), 2));
        List<int[]> merged = mergeIntervals(Arrays.asList(new int[]{1,3}, new int[]{2,6}, new int[]{8,10}));
        System.out.println("merged intervals: " + merged.stream().map(a -> Arrays.toString(a)).collect(Collectors.joining(",")));

        System.out.println("partition counts: " + partitionEvenOddCount(Arrays.asList(1,2,3,4,5,6)));
        System.out.println("flatten unique: " + flattenAndUnique(Arrays.asList(Arrays.asList("b","a"), Arrays.asList("a","c"))));

        User u = new User(new Address("New York"));
        System.out.println("city: " + getCityUppercaseOrUnknown(u));

        List<Product> products = Arrays.asList(new Product("x", 10.0), new Product("x", 5.0), new Product("y", 3.0));
        System.out.println("totals: " + totalPricePerCategory(products));

        Supplier<Instant> memo = memoizeSupplier(Instant::now);
        System.out.println("memo1: " + memo.get());
        System.out.println("memo2: " + memo.get());

        List<String> items = Arrays.asList("a","b","c","d","e");
        System.out.println("page2 size2: " + paginate(items, 2, 2));

        System.out.println("concatParallel: " + concatWithDelimiterParallel(Arrays.asList("a","b","c"), ","));
        System.out.println("async example: " + asyncFetchTransformWithFallback());
    }*/
}
