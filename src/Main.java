import com.google.gson.Gson;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.Scanner;

public class Main {
    static final String API_URL = "https://pokeapi.co/api/v2/pokemon/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HttpClient client = HttpClient.newHttpClient();
        Gson gson = new Gson();

        while (true) {
            System.out.println("\nWelcome to the Pokémon API Program!");
            System.out.println("Choose an option:");
            System.out.println("1) Search by name");
            System.out.println("2) Search by number");
            System.out.println("3) Exit program");
            System.out.print(">> ");

            String choice = scanner.nextLine();

            if (choice.equals("3")) break;

            System.out.print(choice.equals("1") ? "\nEnter a name: " : "\nEnter a number: ");
            String input = scanner.nextLine().toLowerCase();

            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(API_URL + input))
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() != 200) {
                    System.out.println("Error: Pokémon not found.");
                } else {
                    Pokemon p = gson.fromJson(response.body(), Pokemon.class);
                    System.out.println("\nName: " + p.name);
                    System.out.println("Height: " + p.height);
                    System.out.println("Weight: " + p.weight);
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.println("\nPress enter to return to the Main Menu");
            scanner.nextLine();
        }

        System.out.println("\n[Program exits]");
    }
}
