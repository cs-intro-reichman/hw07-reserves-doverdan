
public class SpellChecker {

	public static void main(String[] args) {
		String word = args[0];
		int threshold = Integer.parseInt(args[1]);
		String[] dictionary = readDictionary("dictionary.txt");
		String correction = spellChecker(word, threshold, dictionary);
		System.out.println(correction);
	}

	public static String tail(String str) {
		return str.substring(1);
	}

	public static int levenshtein(String word1, String word2) {
		word1 = word1.toLowerCase();
		word2 = word2.toLowerCase();
		if (word1.isEmpty()) {
			return word2.length();
		} else if (word2.isEmpty()) {
			return word1.length();
		} else if (word1.charAt(0) == word2.charAt(0)) {
			return levenshtein(tail(word1), tail(word2));
		} else {
			int check1 = levenshtein(tail(word1), word2) + 1;
			int check2 = levenshtein(word1, tail(word2)) + 1;
			int check3 = levenshtein(tail(word1), tail(word2)) + 1;
			return Math.min(check1, Math.min(check2, check3));
		}
	}

	public static String[] readDictionary(String fileName) {
		String[] dictionary = new String[3000];

		In in = new In(fileName);
		int i = 0;
		while (!in.isEmpty()) {
			dictionary[i] = in.readString();
			i++;
		}

		return dictionary;
	}

	public static String spellChecker(String word, int threshold, String[] dictionary) {
		// Your code goes here
		String closestWord = word;
		int minDistance = Integer.MAX_VALUE;

		for (String dictWord : dictionary) {
			int distance = levenshtein(word.toLowerCase(), dictWord.toLowerCase());
			if (distance < minDistance) {
				minDistance = distance;
				closestWord = dictWord;
			}
		}
		if (minDistance <= threshold) {
			return closestWord;
		} else {
			return word;
		}
	}

}
