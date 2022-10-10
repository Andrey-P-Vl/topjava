package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemoryMealStorage implements MealStorage {

    private final List<Meal> mealsToList;

    public MemoryMealStorage() {
        this.mealsToList = Collections.synchronizedList(new ArrayList<>());
    }

    @Override
    public void add(Meal meal) {
        mealsToList.add(meal);
    }

    @Override
    public Meal getById(Integer id) {
        synchronized (mealsToList) {
            return mealsToList.stream().filter(meal -> meal.getId().equals(id)).findAny().orElse(null);
        }
    }

    @Override
    public List<Meal> getAll() {
        return mealsToList;
    }

    @Override
    public void update(Integer id, Meal meal) {
        synchronized (mealsToList) {
            Meal mealToUpdate = getById(id);
            mealToUpdate.setCalories(meal.getCalories());
            mealToUpdate.setDateTime(meal.getDateTime());
            mealToUpdate.setDescription(meal.getDescription());
        }
    }

    @Override
    public void delete(Integer id) {
        synchronized (mealsToList) {
            mealsToList.removeIf(meal -> meal.getId().equals(id));
        }
    }
}