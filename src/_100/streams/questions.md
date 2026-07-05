Solve them in this order. Every group teaches one concept.

---

# Level 1 - Stream Basics

1. Filter all odd numbers.
2. Filter all even numbers.
3. Count elements.
4. Find first element.
5. Find any element.
6. Check if list is empty using streams.
7. Convert List to Stream.
8. Convert Stream to List.
9. Create streams using all possible methods (`of()`, `stream()`, `Arrays.stream()`, `iterate()`, `generate()`).
10. Difference between Collection and Stream. ([GeeksforGeeks][1])

---

# Level 2 - filter()

11. Strings starting with A.
12. Strings ending with x.
13. Numbers greater than 100.
14. Numbers between two values.
15. Prime numbers.
16. Palindrome strings.
17. Remove null values.
18. Remove empty strings.
19. Employees whose salary > 50000.
20. Employees from IT department.

---

# Level 3 - map()

21. Square every number.
22. Cube every number.
23. Convert names to uppercase.
24. Convert names to lowercase.
25. Find length of every string.
26. Extract employee names.
27. Extract employee salaries.
28. Convert Integer to String.
29. Trim all strings.
30. Add prefix to every string.

---

# Level 4 - sorted()

31. Sort ascending.
32. Sort descending.
33. Sort strings alphabetically.
34. Sort strings by length.
35. Sort employees by salary.
36. Sort employees by age.
37. Multi-level sorting.
38. Reverse order sorting.
39. Custom Comparator.
40. Stable sorting.

---

# Level 5 - distinct()

41. Remove duplicate numbers.
42. Remove duplicate strings.
43. Distinct employee departments.
44. Distinct objects using equals/hashCode.

---

# Level 6 - limit() and skip()

45. First 5 numbers.
46. Skip first 5 numbers.
47. Pagination.
48. Top 10 salaries.
49. Second highest salary.

---

# Level 7 - peek()

50. Debug stream pipeline.
51. Print values between operations.

---

# Level 8 - Matching Operations

52. allMatch()
53. anyMatch()
54. noneMatch()
55. Check if all salaries > 10000.
56. Check if any employee is HR.

---

# Level 9 - findFirst() and findAny()

57. First employee.
58. First odd number.
59. Any prime number.
60. Difference between findFirst and findAny.

---

# Level 10 - min(), max()

61. Largest number.
62. Smallest number.
63. Longest string.
64. Shortest string.
65. Employee with highest salary.
66. Youngest employee.

---

# Level 11 - count()

67. Count employees.
68. Count even numbers.
69. Count strings starting with A.

---

# Level 12 - reduce()

70. Sum.
71. Product.
72. Maximum.
73. Minimum.
74. Concatenate strings.
75. Factorial.
76. Sum of salaries.

---

# Level 13 - Collectors

77. Collect to List.
78. Collect to Set.
79. Collect to Map.
80. joining()
81. counting()
82. averagingInt()
83. averagingDouble()
84. summingInt()
85. summarizingInt()
86. groupingBy()
87. partitioningBy()
88. mapping()
89. collectingAndThen()
90. toUnmodifiableList()

---

# Level 14 - groupingBy()

91. Employees by department.
92. Students by grade.
93. Products by category.
94. Group by age.
95. Count employees per department.
96. Highest salary per department.
97. Average salary per department.

---

# Level 15 - partitioningBy()

98. Even/Odd.
99. Passed/Failed.
100. Adult/Minor.
101. Salary > 50000.

---

# Level 16 - flatMap()

102. Flatten list of lists.
103. Flatten employee skills.
104. Flatten nested collections.
105. Flatten Optional.

---

# Level 17 - Optional

106. Avoid NullPointerException.
107. orElse()
108. orElseGet()
109. orElseThrow()
110. ifPresent()
111. map()
112. flatMap()
113. filter()
114. Optional.stream().

---

# Level 18 - Primitive Streams

115. IntStream.
116. LongStream.
117. DoubleStream.
118. range().
119. rangeClosed().
120. mapToInt().
121. boxed().
122. summaryStatistics().

---

# Level 19 - Method References

123. `System.out::println`
124. `String::length`
125. `Integer::parseInt`
126. `Employee::getSalary`
127. Constructor reference (`Employee::new`).

---

# Level 20 - Functional Interfaces (Must Know)

128. Predicate
129. BiPredicate
130. Function
131. BiFunction
132. Consumer
133. BiConsumer
134. Supplier
135. UnaryOperator
136. BinaryOperator
137. Custom Functional Interface.

---

# Level 21 - Parallel Streams

138. parallelStream().
139. Difference between stream() and parallelStream().
140. When NOT to use parallel streams.
141. Thread safety.
142. Performance comparison. ([arXiv][2])

---

# Level 22 - Real Interview Questions

143. Second highest salary.
144. Second lowest salary.
145. Duplicate elements.
146. First non-repeated character.
147. Frequency of characters.
148. Frequency of words.
149. Longest string.
150. Shortest string.
151. Most frequent element.
152. Top 3 highest salaries.
153. Employees grouped by department.
154. Highest paid employee in each department.
155. Remove duplicate employees.
156. Find missing number.
157. Merge two lists.
158. Find common elements.
159. Union of lists.
160. Intersection of lists.
161. Difference of lists.
162. Sort employees by multiple fields.
163. Average salary by department.
164. Total salary by department.
165. Find duplicate words.
166. Reverse every word.
167. Reverse a sentence using streams.
168. Count vowels.
169. Count consonants.
170. Find palindromes.
171. Find anagrams.
172. Find top N records.
173. Pagination.
174. Employee hierarchy flattening.
175. Convert list to map.
176. Handle duplicate keys in `toMap()`.
177. Find kth largest element.
178. Find kth smallest element.
179. Custom collector.
180. Build your own stream pipeline. ([Medium][3])

## Interview Theory Questions

These are asked almost as often as coding:

* What is a Stream?
* Stream vs Collection
* Intermediate vs Terminal operations
* Lazy Evaluation
* Short Circuiting
* Stateful vs Stateless operations
* Why Streams cannot be reused
* What is a Stream Pipeline?
* Internal vs External Iteration
* Predicate vs Function vs Consumer vs Supplier
* Method Reference vs Lambda
* Optional best practices
* `map()` vs `flatMap()`
* `forEach()` vs `peek()`
* `sorted()` vs `Comparator`
* `reduce()` vs `collect()`
* `Collectors.toList()` vs `Stream.toList()`
* `findFirst()` vs `findAny()`
* `groupingBy()` vs `partitioningBy()`
* Parallel Stream internals
* Spliterator basics
* When Streams hurt performance
* Common Stream mistakes and anti-patterns. ([GeeksforGeeks][1])

This roadmap covers essentially every Stream API concept you're likely to encounter in modern Java interviews, from beginner through senior level.

[1]: https://www.geeksforgeeks.org/java/java-8-interview-questions-and-answers/?utm_source=chatgpt.com "Top 30 Java 8 Interview Questions and Answers for 2025"
[2]: https://arxiv.org/abs/2302.10006?utm_source=chatgpt.com "Profiling and Optimizing Java Streams"
[3]: https://medium.com/%40asishpanda444/stream-api-coding-qna-8df8682b7e2a?utm_source=chatgpt.com "65 Stream API Coding QnA"
