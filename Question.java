package pb;

enum Status {
	buzzed, // Someone has buzzed in
	complete, // The question has finished
	paused, // Someone has paused the question
	running // The question is being asked / not complete
}

public class Question {

private long start_date; // When the question is created / started
private long adjusted_start_date; // Used for lerping between start and end; considering pauses
private long end_of_question; // When the question is finished being posed
private long end_date; // When the question cannot be answered any longer

private long paused_date;

private String correct_ans;
private String[] words;

private Status status;

public static double seconds_per_word = 0.5;
public static double seconds_after_question = 5.0;

public Question (String ans, String question) {
	correct_ans = ans;
	words = question.split(" "); // Clear whitespace around passed question TODO
	status = Status.running;

	start_date = System.currentTimeMillis();
	adjusted_start_date = start_date;

	end_of_question = start_date + (long) (words.length * seconds_per_word * 1000);
	end_date = end_of_question + (long) (seconds_after_question * 1000);
}

public long get_end_date () { return end_date; }

public String get_public_question () {
	int returnWords = (int) (Math.ceil(words.length * progress()));

	StringBuilder returnString = new StringBuilder(words[0]);
	for (int i = 1; i < returnWords; i++)
		returnString.append(" " + words[i]);

	return returnString.toString();
}

public Status get_status () {
	if (status == Status.running && System.currentTimeMillis() > end_date) status = Status.complete;
	return status;
}

public void pause () {
	paused_date = System.currentTimeMillis();
	status = Status.paused;
}

public double progress () {
	if (status == Status.paused) return 0;
	long time_since_start = System.currentTimeMillis() - adjusted_start_date;
	long time_between = end_of_question - adjusted_start_date;
	return Math.min(1.0, (double) time_since_start / (double) time_between);
}

public void unpause () {
	status = Status.running;
	long delta = System.currentTimeMillis() - paused_date;
	adjusted_start_date += delta;
	end_of_question += delta;
	end_date += delta;
}

}
