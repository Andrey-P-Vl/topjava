package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(MealRestController.class);

    private final MealService service;


    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal) {
        int userId = SecurityUtil.authUserId();
        log.info("create {} for user with id = {}", meal, userId);
        checkNew(meal);
        return service.create(meal, userId);
    }

    public void delete(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("delete meal(id={}) for user with id = {}", id, userId);
        service.delete(id, userId);
    }

    public Meal get(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("get meal(id={}) for user with id = {}", id, userId);
        return service.get(id, userId);
    }

    public List<MealTo> getAll() {
        int userId = SecurityUtil.authUserId();
        log.info("get all meals for user with id = {}", userId);
        return MealsUtil.getTos(service.getAll(userId), SecurityUtil.authUserCaloriesPerDay());
    }

    public void update(Meal meal, int id) {
        log.info("update {} for user with id = {}", meal, id);
        service.update(meal, SecurityUtil.authUserId());
    }
}