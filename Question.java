package pb;

public class Question {

private long start_date; // When the question is created / started
private long end_of_question; // When the question is finished being posed
private long end_date; // When the question cannot be answered any longer

private String correct_ans;
private String[] words;

public static double seconds_per_word = 0.5;
public static double seconds_after_question = 5.0;

public Question (String ans, String question) {
	start_date = System.currentTimeMillis();

	words = question.split(" ");

	end_of_question = start_date + (long) (words.length * seconds_per_word * 1000);
	end_date = end_of_question + (long) (seconds_after_question * 1000);
}

}
