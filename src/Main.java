import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.util.HashMap;
import java.util.Random;

public class Main {

    private static HashMap<Character, Integer> countOfCharsInString = new HashMap<>();

    public static void main(String[] args) throws IOException, InterruptedException {
        Random rand = new Random();
        int number = rand.nextInt(0, 100);
        String url = String.format("http://numbersapi.com/%s/trivia", number);

        String response = GetResponse(url);

        String responseWithoutWhitespaces = response.replaceAll("\\s+", "").toLowerCase();
        String responseWithoutPunctuationMarks = responseWithoutWhitespaces.replaceAll("\\p{Punct}", "");
        OutputCountOfSymbolsInStringAndFrequencyAverage(responseWithoutPunctuationMarks);
    }

    private static String GetResponse(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());

        return response.body().toString();
    }

    private static void OutputCountOfSymbolsInStringAndFrequencyAverage(String response) {
        OutputCountOfSymbolsInString(response);
        OutputFrequencyAverage(response.length());
    }

    private static void OutputCountOfSymbolsInString(String response) {
        for (char symbol : response.toCharArray()) {
            if(countOfCharsInString.containsKey(symbol)) {
                countOfCharsInString.computeIfPresent(symbol, (s, i) -> i + 1);
            }
            else {
                countOfCharsInString.put(symbol, 1);
            }
        }

        System.out.println("Frequencies:");
        for (char symbol : countOfCharsInString.keySet()) {
            System.out.println(symbol + " - " + countOfCharsInString.get(symbol));
        }
    }

    private static void OutputFrequencyAverage(int length) {
        boolean firstIteration = true;
        float frequencyAverage = (float) length / (float) countOfCharsInString.size();
        float closestGapBetweenCountAndFrequency = 0;

        System.out.println("Frequency average value " + length + "/" + countOfCharsInString.size() + " = " + frequencyAverage);

        for (char symbol : countOfCharsInString.keySet()) {
            if (firstIteration) {
                closestGapBetweenCountAndFrequency = Math.abs(countOfCharsInString.get(symbol) - frequencyAverage);
                firstIteration = false;
            }
            else if (Math.abs(countOfCharsInString.get(symbol) - frequencyAverage) < closestGapBetweenCountAndFrequency) {
                closestGapBetweenCountAndFrequency = Math.abs(countOfCharsInString.get(symbol) - frequencyAverage);
            }
        }

        System.out.print("Symbols that satisfy the condition of the closest frequency value to the average value: ");

        for (char symbol : countOfCharsInString.keySet()) {
            if (Math.abs(countOfCharsInString.get(symbol) - frequencyAverage) == closestGapBetweenCountAndFrequency) {
                System.out.print(symbol + "(" + (int)symbol + ") ");
            }
        }
    }
}