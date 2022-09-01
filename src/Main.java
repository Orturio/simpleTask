import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.util.HashMap;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Random rand = new Random();
        int number = rand.nextInt(0, 100);
        String url = String.format("http://numbersapi.com/%s/trivia", number);

        String response = GetResponse(url);

        String responseWithoutWhitespaces = response.replaceAll("\\s+", "").toLowerCase();
        OutputCountOfSymbolsInString(responseWithoutWhitespaces);
    }

    public static String GetResponse(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());

        return response.body().toString();
    }

    public static void OutputCountOfSymbolsInString(String response) {
        HashMap<Character, Integer> countOfChars = new HashMap<>();

        for (char symbol : response.toCharArray()) {
            if(countOfChars.containsKey(symbol)) {
                countOfChars.computeIfPresent(symbol, (k, v) -> v + 1);
            }
            else {
                countOfChars.put(symbol, 1);
            }
        }

        for (char symbol : countOfChars.keySet()) {
            System.out.println(symbol + " - " + countOfChars.get(symbol));
        }
    }
}