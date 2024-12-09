package vttp.ssf.practice_test.service;

import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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
            info.add(data);
        }
        return info;
    }

    public Boolean saveInfo(String id, String jsonInfo) throws ParseException {
        List<String> information = listRepo.getList("data");
        for (String s : information) {
            JsonReader reader = Json.createReader(new StringReader(s));
            JsonObject j = reader.readObject();
            if ((j.getString("id")).contains(id)) {
                listRepo.remove("data", 0, s);
                listRepo.leftPush("data", jsonInfo);
                return true;
            }
        }
            listRepo.rightPush("data", jsonInfo);
            return false;
    }

    public void deleteRecord(String id) {
        List<String> information = listRepo.getList("data");
        for (String s : information) {
            JsonReader reader = Json.createReader(new StringReader(s));
            JsonObject j = reader.readObject();
            if (id.equals(j.getString("id"))) {
                listRepo.remove("data", 1, s);
            }
        }
    }

    public Information getInfoById(String id) {
        Information d = new Information();
        List<String> information = listRepo.getList("data");
        for (String s : information) {
            JsonReader reader = Json.createReader(new StringReader(s));
            JsonObject j = reader.readObject();
            if (j.getString("id").contains(id)) {
                Date dueDate = Information.epochMilliSecondsToDate(j.getString("due_date"));
                Date created = Information.epochMilliSecondsToDate(j.getString("created_at"));
                Date updated = Information.epochMilliSecondsToDate(j.getString("updated_at"));
                d.setId(j.getString("id"));
                d.setName(j.getString("name"));
                d.setDescription(j.getString("description"));
                d.setDueDate(dueDate);
                d.setPriority(j.getString("priority"));
                d.setStatus(j.getString("status"));
                d.setCreated(created);
                d.setUpdated(updated);
            }
        }
        return d;
    }
}