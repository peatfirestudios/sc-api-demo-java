import java.net.URI;
import java.net.http.HttpClient; // < New in Java 11, can also handle HTTP/2 requests!
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONObject;

/**
* Savings Champion API Java Client Proof of Concept.
*/
public class Main {
  private String clientId;
  private String clientSecret;
  private String baseUrl = "https://api.savingschampion.co.uk";

  private String accessToken;

  public Main(String clientId, String clientSecret) {
    this.clientId = clientId;
    this.clientSecret = clientSecret;
  }

  public static void main(String[] args) throws Exception {
    // Get the API Client ID from environment variable.
    String clientId = System.getenv("SAVINGSCHAMPION_API_CLIENT_ID");
    if (clientId == null) {
      System.err.println("SAVINGSCHAMPION_API_CLIENT_ID environment variable not set.");
      return;
    }
    // Get the API Client Secret from environment variable.
    String clientSecret = System.getenv("SAVINGSCHAMPION_API_CLIENT_SECRET");
    if (clientSecret == null) {
      System.err.println("SAVINGSCHAMPION_API_CLIENT_SECRET environment variable not set.");
      return;
    }
    
    Main poc = new Main(clientId, clientSecret);
    poc.demo();

  }

  public void demo() throws Exception {
    this.fetchAccessToken();
    String[] products = this.getProductNames(1);

    for (String product: products) {
      System.out.println(product);
    }
  }

  public void fetchAccessToken() throws Exception {
    // Get the access token.
    HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create(baseUrl + "/auth/get-token"))
      .header("Content-Type", "application/json")
      .POST(HttpRequest.BodyPublishers.ofString("{\"client_id\":\"" + clientId + "\",\"client_secret\":\"" + clientSecret + "\"}"))
      .build();
    HttpClient client = HttpClient.newHttpClient();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    JSONObject json = new JSONObject(response.body());
    this.accessToken = json.getString("access_token");
  }

 public String[] getProductNames(int page) throws Exception {
    
    HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create(baseUrl + "/products?page=" + page))
      .header("Authorization", "Bearer " + this.accessToken)
      .GET()
      .build();
    HttpClient client = HttpClient.newHttpClient();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    JSONArray json = new JSONArray(response.body());
    String[] products = new String[json.length()];
    for (int i = 0; i < json.length(); i++) {
      products[i] = json.getJSONObject(i).getString("title");
    }
    return products;
  }
}
