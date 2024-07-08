package com.validator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ValidatiorMain {
	private static final Set<String> validWordsWithOneLetter = Set.of("A", "I");

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		Set<String> words = new HashSet<>();

		loadData(words);

		long startTimeWithoutData = System.currentTimeMillis();
		
		Set<String> validWords = checkValidWords(words);
		System.out.println("Valid Words = " + validWords); // 56761 valid words

		long endTime = System.currentTimeMillis();
		System.out.println("Execution time for all program: " + (endTime - startTime) + " ms"); // ~ 1118 ms
		System.out.println("Execution time for program without loaded data: " + (endTime - startTimeWithoutData) + " ms"); // ~ 216 ms
	}

	private static void loadData(Set<String> words) {
		try {
			URL url = new URL("https://raw.githubusercontent.com/nikiiv/JavaCodingTestOne/master/scrabble-words.txt");
			try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
				Set<String> loadedWords = br.lines().skip(2).collect(Collectors.toSet());
				loadedWords.stream().filter(line -> (line.length() >= 1 && line.length() <= 9)).forEach(words::add);
			} 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static Set<String> checkValidWords(Set<String> words) {
		Set<String> result = words.stream().flatMap(word -> {
			Set<String> validWords = new HashSet<>();
			for (int i = 0; i < word.length(); i++) {
				StringBuilder sb = new StringBuilder(word);
				sb.deleteCharAt(i);
				String validWord = sb.toString();
				if (words.contains(validWord)) {
					validWords.add(validWord);
					break;
				}
				if (word.length() == 2) {
					sb = new StringBuilder(word);
					sb.deleteCharAt(i);
					validWord = sb.toString();
					if (validWordsWithOneLetter.contains(validWord)) {
						validWords.add(validWord);
						break;
					}
				}

			}
			return validWords.stream();
		}).collect(Collectors.toSet());
		return result;
	}
}
