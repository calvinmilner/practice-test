package vttp.ssf.practice_test.bootstrap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

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
    public void run(String... args) throws IOException {
        // Change FileReader to InputStreamReader to prevent containerization errors .getInputStream() for the resource
        BufferedReader br = new BufferedReader(new FileReader(resource.getFile())); 
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
        System.out.println(json);
        JsonReader reader = Json.createReader(new StringReader(json));
        JsonArray jArr = reader.readArray();
        for (int i = 0; i < jArr.size(); i++) {
            JsonObject j = jArr.getJsonObject(i);
            Information info = new Information(j.getString("id"), j.getString("name"), j.getString("description"),
                    j.getString("due_date"), j.getString("priority_level"), j.getString("status"),
                    j.getString("created_at"), j.getString("updated_at"));
            listRepo.rightPush((j.getString("id")), info.toString());
        }
    }
}
