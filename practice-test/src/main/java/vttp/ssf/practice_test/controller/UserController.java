package vttp.ssf.practice_test.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vttp.ssf.practice_test.model.*;
import vttp.ssf.practice_test.service.InformationService;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private InformationService infoServ;

    @GetMapping("")
    public String getLogin(Model model) {
        model.addAttribute("user", new User());
        return "index";
    }

    @SuppressWarnings("unchecked")
    @PostMapping("")
    public String postLogin(@ModelAttribute("user") User entity, HttpSession session, Model model) {
        if (entity.getAge() > 10) {
            List<User> users = null;
            if (session.getAttribute("user") == null) {
                users = new LinkedList<>();
            } else {
                users = (List<User>) session.getAttribute("user");
            }
            users.add(entity);
            List<Information> dataList = infoServ.getInfo();
            session.setAttribute("user", users);
            model.addAttribute("user", users);
            model.addAttribute("dataList", dataList);
            return "page-listing";
        } else
            return "underage";
    }

    @GetMapping("/add")
    public String getNewToDo(Model model) {
        String id = UUID.randomUUID().toString();
        infoServ.setGeneratedId(id);
        model.addAttribute("id", id);
        model.addAttribute("information", new Information());
        return "page-add";
    }

    @PostMapping("/new")
    public String postNewToDo(Model model, @Valid @ModelAttribute("information") Information info)
            throws ParseException {
        String dd = new SimpleDateFormat("EEE, dd/MM/yyyy").format(info.getDueDate().getTime());
        String ca = new SimpleDateFormat("EEE, dd/MM/yyyy").format(info.getCreated().getTime());
        String ua = new SimpleDateFormat("EEE, dd/MM/yyyy").format(info.getUpdated().getTime());
        long dueDate = Information.stringDateToEpochMilliSeconds(dd);
        long created = Information.stringDateToEpochMilliSeconds(ca);
        long updated = Information.stringDateToEpochMilliSeconds(ua);
        JsonObject j = Json.createObjectBuilder()
                .add("id", infoServ.getGeneratedId())
                .add("name", info.getName())
                .add("description", info.getDescription())
                .add("due_date", String.valueOf(dueDate))
                .add("priority", info.getPriority())
                .add("status", info.getStatus())
                .add("created_at", String.valueOf(created))
                .add("updated_at", String.valueOf(updated))
                .build();

        // System.out.println(j.toString());
        // infoServ.saveInfo(infoServ.getGeneratedId(), j.toString());
        infoServ.saveInfo(j.toString());
        model.addAttribute("todo", "Todo added successfully");
        return "redirect:/user/add";
    }

    @GetMapping("/listing")
    public String getUpdatedList(Model model) {
        List<Information> dataList = infoServ.getInfo();
        model.addAttribute("dataList", dataList);
        return "page-listing";
    }

    @GetMapping("/clear")
    public String clearSession(HttpSession session) {
        session.removeAttribute("name");
        session.invalidate();
        return "refused";
    }

    @GetMapping("/delete")
    public String deleteRecord(@RequestParam("id") String id) {
        infoServ.deleteRecord(id);
        return "redirect:/user/listing";
    }
}
