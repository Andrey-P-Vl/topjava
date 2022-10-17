package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("save {}", meal);
        Map<Integer, Meal> userMeals = repository.computeIfAbsent(userId, k -> new ConcurrentHashMap<>());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            userMeals.put(meal.getId(), meal);
            return meal;
        }
        return userMeals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete {}", id);
        Map<Integer, Meal> userMeals = repository.get(userId);
        return userMeals != null && userMeals.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get {}", id);
        Map<Integer, Meal> userMeals = repository.get(userId);
        return userMeals == null ? null : userMeals.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("getAll");
        return getFilteredByPredicate(userId, filter -> true);
    }

    public List<Meal> getFilteredByDate(int userId, LocalDate startDate, LocalDate endDate) {
        log.info("get by Date");
        return getFilteredByPredicate(userId, meal ->
                DateTimeUtil.isBetweenDate(meal.getDate(), startDate, endDate));
    }

    public List<Meal> getFilteredByTime(int userId, LocalTime startTime, LocalTime endTime) {
        log.info("get by Time");
        return getFilteredByPredicate(userId, meal ->
                DateTimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime));
    }

    public List<Meal> getFilteredByPredicate(int userId, Predicate<Meal> filter) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        return userMeals == null ? new ArrayList<>() : userMeals.values().stream().
                filter(filter).
                sorted(Comparator.comparing(Meal::getDateTime).reversed()).
                collect(Collectors.toList());
    }
}