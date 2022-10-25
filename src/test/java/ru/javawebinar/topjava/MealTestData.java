package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;


public class MealTestData {
    public static final int START_MEAL_ID = UserTestData.GUEST_ID + 1;

    public static final Meal userMeal1 = new Meal(START_MEAL_ID,
            DateTimeUtil.parseLocalDateTime("2022-09-16 10:00"), "Завтрак", 500);
    public static final Meal userMeal2 = new Meal(START_MEAL_ID + 1,
            DateTimeUtil.parseLocalDateTime("2022-09-16 13:00"), "Обед", 1000);
    public static final Meal userMeal3 = new Meal(START_MEAL_ID + 2,
            DateTimeUtil.parseLocalDateTime("2022-09-16 20:00"), "Ужин", 500);
    public static final Meal userMeal4 = new Meal(START_MEAL_ID + 3,
            DateTimeUtil.parseLocalDateTime("2022-09-22 00:00"), "Еда на граничное значение", 100);
    public static final Meal userMeal5 = new Meal(START_MEAL_ID + 4,
            DateTimeUtil.parseLocalDateTime("2022-09-22 10:00"), "Завтрак", 1000);
    public static final Meal userMeal6 = new Meal(START_MEAL_ID + 5,
            DateTimeUtil.parseLocalDateTime("2022-09-22 13:00"), "Обед", 500);
    public static final Meal userMeal7 = new Meal(START_MEAL_ID + 6,
            DateTimeUtil.parseLocalDateTime("2022-09-22 20:00"), "Ужин", 410);
    public static final Meal adminMeal1 = new Meal(START_MEAL_ID + 7,
            DateTimeUtil.parseLocalDateTime("2022-09-20 10:00"), "Завтрак Админа", 800);
    public static final Meal adminMeal2 = new Meal(START_MEAL_ID + 8,
            DateTimeUtil.parseLocalDateTime("2022-09-20 14:00"), "Обед админа", 1300);

    public static Meal getNew() {
        return new Meal(null,
                DateTimeUtil.parseLocalDateTime("2022-09-20 14:00"), "Обед", 1000);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(userMeal1);
        updated.setDescription("Завтрак пользователя");
        updated.setDateTime(DateTimeUtil.parseLocalDateTime("2022-09-16 10:10"));
        updated.setCalories(450);
        return updated;
    }

    public static Meal getUpdatedWithDuplicate() {
        Meal updated = new Meal(userMeal1);
        updated.setDateTime(DateTimeUtil.parseLocalDateTime("2022-09-22 10:00"));
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }
}