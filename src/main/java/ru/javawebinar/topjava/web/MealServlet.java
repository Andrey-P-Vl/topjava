package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealStorage;
import ru.javawebinar.topjava.service.MemoryMealStorage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;


public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private static final int CALORIES_PER_DAY = 2000;
    private static final String INSERT_OR_EDIT = "/editMeals.jsp";
    private static final String LIST_USER = "/meals.jsp";
    private MealStorage memoryMealStorage;

    public void init() {
        this.memoryMealStorage = new MemoryMealStorage();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forward;
        String action = req.getParameter("action") == null ? "" :
                req.getParameter("action").toLowerCase();
        int id;

        switch (action) {
            case ("delete"):
                id = Integer.parseInt(req.getParameter("id"));
                log.debug("delete Meals with id = {}", id);
                memoryMealStorage.delete(id);
                forward = LIST_USER;
                req.setAttribute("mealsToList", MealsUtil.filteredByStreams(memoryMealStorage.getAll(),
                        null, null, CALORIES_PER_DAY));
                break;
            case ("edit"):
                id = Integer.parseInt(req.getParameter("id"));
                forward = INSERT_OR_EDIT;
                Meal meal = memoryMealStorage.getById(id);
                meal.setId(id);
                req.setAttribute("meal", meal);
                break;
            case ("add"):
                forward = INSERT_OR_EDIT;
                break;
            default:
                log.debug("show listMeals");
                forward = LIST_USER;
                req.setAttribute("mealsToList", MealsUtil.filteredByStreams(memoryMealStorage.getAll(),
                        null, null, CALORIES_PER_DAY));
                break;
        }
        log.debug("redirect to {}", forward);
        req.getRequestDispatcher(forward).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("datetime"));
        int calories = Integer.parseInt(req.getParameter("calories"));
        String id = req.getParameter("id");

        Meal meal = new Meal(dateTime, req.getParameter("description"), calories);
        if (id == null || id.isEmpty()) {
            log.debug("create Meal in Storage");
            memoryMealStorage.add(meal);
        } else {
            meal.setId(Integer.parseInt(id));
            log.debug("edit Meal in Storage with id = {}", id);
            memoryMealStorage.update(meal);
        }

        RequestDispatcher view = req.getRequestDispatcher(LIST_USER);
        req.setAttribute("mealsToList", MealsUtil.filteredByStreams(memoryMealStorage.getAll(),
                null, null, CALORIES_PER_DAY));
        log.debug("redirect to {}", LIST_USER);
        view.forward(req, resp);
    }
}