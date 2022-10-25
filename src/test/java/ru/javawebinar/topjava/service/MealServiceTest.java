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
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

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
        Meal meal = service.get(START_MEAL_ID, USER_ID);
        assertMatch(meal, userMeal1);
    }

    @Test
    public void getForeign() {
        assertThrows(NotFoundException.class, () -> service.get(START_MEAL_ID + 1, ADMIN_ID));
    }

    @Test
    public void delete() {
        service.delete(START_MEAL_ID + 1, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(START_MEAL_ID + 1, USER_ID));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(START_MEAL_ID + 100, USER_ID));
    }

    @Test
    public void deletedNotFound2() {
        assertThrows(NotFoundException.class, () -> service.delete(START_MEAL_ID + 7, USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> all = service.getBetweenInclusive(DateTimeUtil.parseLocalDate("2022-09-16"),
                DateTimeUtil.parseLocalDate("2022-09-16"), USER_ID);
        assertMatch(all, userMeal1, userMeal2, userMeal3);
    }

    @Test
    public void getBetweenInclusive2() {
        List<Meal> all = service.getBetweenInclusive(null,
                DateTimeUtil.parseLocalDate("2022-09-16"), USER_ID);
        assertMatch(all, userMeal1, userMeal2, userMeal3);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(ADMIN_ID);
        assertMatch(all, adminMeal1, adminMeal2);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(START_MEAL_ID, USER_ID), getUpdated());
    }

    @Test
    public void duplicateUpdate() {
        Meal updated = getUpdatedWithDuplicate();
        assertThrows(DataAccessException.class, () -> service.update(updated, USER_ID));
    }

    @Test
    public void foreignUpdate() {
        Meal updated = getUpdated();
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
        assertThrows(DataAccessException.class, () -> service.create(getNew(), ADMIN_ID));
    }
}