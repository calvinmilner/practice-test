package vttp.ssf.practice_test.bootstrap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.ssf.practice_test.model.Information;
import vttp.ssf.practice_test.repository.ListRepository;
import vttp.ssf.practice_test.repository.MapRepository;

@Component
public class AppBootStrap implements CommandLineRunner {

    @Autowired
    private ListRepository listRepo;

    @Autowired
    private MapRepository mapRepo;

    @Value("classpath:/todos.txt")
    Resource todos;

    @Value("classpath:/products.json")
    Resource products;

    @Override
    public void run(String... args) throws IOException, ParseException {
        // Change FileReader to InputStreamReader to prevent containerization errors
        // .getInputStream() for the resource
        BufferedReader br = new BufferedReader(new InputStreamReader(todos.getInputStream()));
        String todoJson = "";
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            todoJson = sb.toString();
        } finally {
            br.close();
        }
        // System.out.println(json);
        JsonReader todoReader = Json.createReader(new StringReader(todoJson));
        JsonArray todoArr = todoReader.readArray();
        for (int i = 0; i < todoArr.size(); i++) {
            JsonObject t = todoArr.getJsonObject(i);

            // Setting date format followed by converting date to epochMilliseconds value to
            // store in redis
            long dueDate = Information.stringDateToEpochMilliSeconds(t.getString("due_date"));
            long created = Information.stringDateToEpochMilliSeconds(t.getString("created_at"));
            long updated = Information.stringDateToEpochMilliSeconds(t.getString("updated_at"));

            JsonObject jObj = Json.createObjectBuilder()
                    .add("id", t.getString("id"))
                    .add("name", t.getString("name"))
                    .add("description", t.getString("description"))
                    .add("due_date", String.valueOf(dueDate))
                    .add("priority", t.getString("priority_level"))
                    .add("status", t.getString("status"))
                    .add("created_at", String.valueOf(created))
                    .add("updated_at", String.valueOf(updated))
                    .build();

            listRepo.rightPush("data", jObj.toString());
        }

        BufferedReader abr = new BufferedReader(new InputStreamReader(products.getInputStream()));
        String productsJson = "";
        try {
            StringBuilder nsb = new StringBuilder();
            String nline = abr.readLine();
            while (nline != null) {
                nsb.append(nline);
                nline = abr.readLine();
            }
            productsJson = nsb.toString();
        } finally {
            abr.close();
        }

        JsonReader productsReader = Json.createReader(new StringReader(productsJson));
        JsonArray productsArr = productsReader.readArray();
        for (int j = 0; j < productsArr.size(); j++) {
            JsonObject p = productsArr.getJsonObject(j);
            JsonObject jObj = Json.createObjectBuilder()
                    .add("id", p.getInt("id"))
                    .add("title", p.getString("title"))
                    .add("description", p.getString("description"))
                    .add("price", p.getInt("price"))
                    .add("discountPercentage", p.getJsonNumber("discountPercentage"))
                    .add("rating", p.getJsonNumber("rating"))
                    .add("stock", p.getInt("stock"))
                    .add("brand", p.getString("brand"))
                    .add("category", p.getString("category"))
                    .add("dated", p.getJsonNumber("dated"))
                    .add("buy", p.getInt("buy"))
                    .build();
            mapRepo.createHash("products", String.valueOf(p.getInt("id")), jObj.toString());
        }
    }
}
