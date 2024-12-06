package vttp.ssf.practice_test.service;

import java.io.StringReader;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.ssf.practice_test.model.Information;
import vttp.ssf.practice_test.repository.ListRepository;

@Service
public class InformationService {

    @Autowired
    private ListRepository listRepo;

    private String generatedId;

    public List<Information> getInfo() {

        List<Information> info = new LinkedList<>();
        List<String> information = listRepo.getList("data");
        for (String s : information) {
            JsonReader reader = Json.createReader(new StringReader(s));
            JsonObject j = reader.readObject();
            // System.out.println(j.toString());
            // Converting epochMilliseconds back to date
            Date dueDate = Information.epochMilliSecondsToDate(j.getString("due_date"));
            Date created = Information.epochMilliSecondsToDate(j.getString("created_at"));
            Date updated = Information.epochMilliSecondsToDate(j.getString("updated_at"));
            Information data = new Information();
            data.setId(j.getString("id"));
            data.setName(j.getString("name"));
            data.setDescription(j.getString("description"));
            data.setDueDate(dueDate);
            data.setPriority(j.getString("priority"));
            data.setStatus(j.getString("status"));
            data.setCreated(created);
            data.setUpdated(updated);
            // Information data = new Information(j.getString("id"), j.getString("name"), j.getString("description"), dueDate, j.getString("priority_level"), j.getString("status"), created, updated);
            info.add(data);
        }
        return info;
    }
    public void setGeneratedId(String id) {
        this.generatedId = id;
    }
    public String getGeneratedId() {
        return generatedId;
    }
    public void saveInfo(String jsonInfo) {
        listRepo.rightPush("data", jsonInfo);
    }
}
