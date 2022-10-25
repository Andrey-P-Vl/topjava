package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


public class MealTestData {
    public static final int FIRST_USER_MEAL_ID = UserTestData.GUEST_ID + 1;
    public static final int SECOND_USER_MEAL_ID = FIRST_USER_MEAL_ID + 1;
    public static final int FIRST_ADMIN_MEAL_ID = FIRST_USER_MEAL_ID + 7;
    public static final int NOT_EXIST_MEAL_ID = FIRST_USER_MEAL_ID + 100;

    public static final Meal userMeal1 = new Meal(FIRST_USER_MEAL_ID,
            LocalDateTime.of(2022, Month.SEPTEMBER, 16, 10, 0), "Завтрак", 500);
    public static final Meal userMeal2 = new Meal(SECOND_USER_MEAL_ID,
            LocalDateTime.of(2022, Month.SEPTEMBER, 16, 13, 0), "Обед", 1000);
    public static final Meal userMeal3 = new Meal(FIRST_USER_MEAL_ID + 2,
            LocalDateTime.of(2022, Month.SEPTEMBER, 16, 20, 0), "Ужин", 500);
    public static final Meal userMeal4 = new Meal(FIRST_USER_MEAL_ID + 3,
            LocalDateTime.of(2022, Month.SEPTEMBER, 22, 0, 0), "Еда на граничное значение", 100);
    public static final Meal userMeal5 = new Meal(FIRST_USER_MEAL_ID + 4,
            LocalDateTime.of(2022, Month.SEPTEMBER, 22, 10, 0), "Завтрак", 1000);
    public static final Meal userMeal6 = new Meal(FIRST_USER_MEAL_ID + 5,
            LocalDateTime.of(2022, Month.SEPTEMBER, 22, 13, 0), "Обед", 500);
    public static final Meal userMeal7 = new Meal(FIRST_USER_MEAL_ID + 6,
            LocalDateTime.of(2022, Month.SEPTEMBER, 22, 20, 0), "Ужин", 410);
    public static final Meal adminMeal1 = new Meal(FIRST_ADMIN_MEAL_ID,
            LocalDateTime.of(2022, Month.SEPTEMBER, 20, 10, 0), "Завтрак Админа", 800);
    public static final Meal adminMeal2 = new Meal(FIRST_ADMIN_MEAL_ID + 1,
            LocalDateTime.of(2022, Month.SEPTEMBER, 20, 14, 0), "Обед админа", 1300);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2022, Month.SEPTEMBER, 20, 14, 0), "Обед", 1000);
    }

    public static Meal getUpdatedUserMeal() {
        Meal updated = new Meal(userMeal1);
        updated.setDescription("Завтрак пользователя");
        updated.setDateTime(LocalDateTime.of(2022, Month.SEPTEMBER, 16, 10, 10));
        updated.setCalories(450);
        return updated;
    }

    public static Meal getUpdatedWithDuplicate() {
        Meal updated = new Meal(userMeal1);
        updated.setDateTime(userMeal2.getDateTime());
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.stream(expected)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList()));
    }
}