package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MemoryMealStorage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/meals")
public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private static final int CALORIES_PER_DAY = 2000;
    private static final String INSERT_OR_EDIT = "/editMeals.jsp";
    private static final String LIST_USER = "/meals.jsp";
    private final MemoryMealStorage memoryMealStorage;

    public MealServlet() {
        this.memoryMealStorage = new MemoryMealStorage();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forward;
        String action = req.getParameter("action");

        if (action.equalsIgnoreCase("delete")) {
            Integer id = Integer.parseInt(req.getParameter("id"));
            log.debug("delete Meals with id = {}", id);
            memoryMealStorage.delete(id);
            forward = LIST_USER;
            req.setAttribute("mealsToList", MealsUtil.filteredByStreams(memoryMealStorage.getAll(),
                    null, null, CALORIES_PER_DAY));
        } else if (action.equalsIgnoreCase("edit")) {
            Integer id = Integer.parseInt(req.getParameter("id"));
            forward = INSERT_OR_EDIT;
            synchronized (this) {
                Meal meal = memoryMealStorage.getById(id);
                meal.setId(id);
                req.setAttribute("meal", meal);
            }
        } else if (action.equalsIgnoreCase("listMeals")) {
            log.debug("show listMeals");
            forward = LIST_USER;
            req.setAttribute("mealsToList", MealsUtil.filteredByStreams(memoryMealStorage.getAll(),
                    null, null, CALORIES_PER_DAY));
        } else {
            forward = INSERT_OR_EDIT;
        }
        log.debug("redirect to {}", forward);
        req.getRequestDispatcher(forward).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("datetime"),
                DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        int calories = Integer.parseInt(req.getParameter("calories"));
        String id = req.getParameter("id");

        Meal meal = new Meal(dateTime, req.getParameter("description"), calories);
        synchronized (this) {
            if (id == null || id.isEmpty()) {
                int newIndex = memoryMealStorage.getAll().size() + 1;
                meal.setId(newIndex);
                log.debug("create Meal in Storage with id = {}", newIndex);
                memoryMealStorage.add(meal);
            } else {
                log.debug("edit Meal in Storage with id = {}", id);
                memoryMealStorage.update(Integer.parseInt(id), meal);
            }
        }
        RequestDispatcher view = req.getRequestDispatcher(LIST_USER);
        req.setAttribute("mealsToList", MealsUtil.filteredByStreams(memoryMealStorage.getAll(),
                null, null, CALORIES_PER_DAY));
        log.debug("redirect to {}", LIST_USER);
        view.forward(req, resp);
    }
}