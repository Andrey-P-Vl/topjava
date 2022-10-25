package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(FIRST_USER_MEAL_ID, USER_ID);
        assertMatch(meal, userMeal1);
    }

    @Test
    public void getForeign() {
        assertThrows(NotFoundException.class, () -> service.get(SECOND_USER_MEAL_ID, ADMIN_ID));
    }

    @Test
    public void delete() {
        service.delete(SECOND_USER_MEAL_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(SECOND_USER_MEAL_ID, USER_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_EXIST_MEAL_ID, USER_ID));
    }

    @Test
    public void deleteForeign() {
        assertThrows(NotFoundException.class, () -> service.delete(FIRST_ADMIN_MEAL_ID, USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> all = service.getBetweenInclusive(LocalDate.of(2022, Month.SEPTEMBER, 16),
                LocalDate.of(2022, Month.SEPTEMBER, 16), USER_ID);
        assertMatch(all, userMeal1, userMeal2, userMeal3);
    }

    @Test
    public void getBetweenInclusiveWithNullBorder() {
        List<Meal> all = service.getBetweenInclusive(null,
                LocalDate.of(2022, Month.SEPTEMBER, 16), USER_ID);
        assertMatch(all, userMeal1, userMeal2, userMeal3);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(ADMIN_ID);
        assertMatch(all, adminMeal1, adminMeal2);
    }

    @Test
    public void update() {
        Meal updated = getUpdatedUserMeal();
        service.update(updated, USER_ID);
        assertMatch(service.get(FIRST_USER_MEAL_ID, USER_ID), getUpdatedUserMeal());
    }

    @Test
    public void updateWithDuplicate() {
        Meal updated = getUpdatedWithDuplicate();
        assertThrows(DataAccessException.class, () -> service.update(updated, USER_ID));
    }

    @Test
    public void updateForeign() {
        Meal updated = getUpdatedUserMeal();
        assertThrows(NotFoundException.class, () -> service.update(updated, ADMIN_ID));
    }

    @Test
    public void create() {
        Meal created = service.create(getNew(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }

    @Test
    public void duplicateCreate() {
        Meal mealWithDuplicateDateTime = getNew();
        assertThrows(DataAccessException.class, () -> service.create(mealWithDuplicateDateTime, ADMIN_ID));
    }
}