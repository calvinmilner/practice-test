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

@Component
public class AppBootStrap implements CommandLineRunner {

    @Autowired
    private ListRepository listRepo;

    @Value("classpath:/todos.txt")
    Resource resource;

    @Override
    public void run(String... args) throws IOException, ParseException {
        // Change FileReader to InputStreamReader to prevent containerization errors .getInputStream() for the resource
        BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream())); 
        String json = "";
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while(line != null) {
                sb.append(line);
                line = br.readLine();
            }
            json = sb.toString();
        } finally {
            br.close();
        }
        // System.out.println(json);
        JsonReader reader = Json.createReader(new StringReader(json));
        JsonArray jArr = reader.readArray();
        for (int i = 0; i < jArr.size(); i++) {
            JsonObject j = jArr.getJsonObject(i);

            // Setting date format followed by converting date to epochMilliseconds value to store in redis
            long dueDate = Information.stringDateToEpochMilliSeconds(j.getString("due_date"));
            long created = Information.stringDateToEpochMilliSeconds(j.getString("created_at"));
            long updated = Information.stringDateToEpochMilliSeconds(j.getString("updated_at"));

            // SimpleDateFormat sdf = new SimpleDateFormat("EEE, MM/dd/yyyy");
            // long dueDate = sdf.parse(j.getString("due_date")).getTime();
            // long created = sdf.parse(j.getString("created_at")).getTime();
            // long updated = sdf.parse(j.getString("updated_at")).getTime();
            
            JsonObject jObj = Json.createObjectBuilder()
            .add("id", j.getString("id"))
            .add("name", j.getString("name"))
            .add("description", j.getString("description"))
            .add("due_date", String.valueOf(dueDate))
            .add("priority", j.getString("priority_level"))
            .add("status", j.getString("status"))
            .add("created_at", String.valueOf(created))
            .add("updated_at", String.valueOf(updated))
            .build();

            // Information info = new Information(j.getString("id"), j.getString("name"), j.getString("description"),
            //         j.getString("due_date"), j.getString("priority_level"), j.getString("status"),
            //         j.getString("created_at"), j.getString("updated_at"));

            listRepo.rightPush("data", jObj.toString());
        }
    }
}
