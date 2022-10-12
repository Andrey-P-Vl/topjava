package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MemoryMealStorage implements MealStorage {

    private final CopyOnWriteArrayList<Meal> mealList;

    public MemoryMealStorage() {
        this.mealList = new CopyOnWriteArrayList<>();
        MealsUtil.generateListMeals().forEach(this::add);
    }

    @Override
    public Meal add(Meal meal) {
        meal.setId(this.mealList.size() + 1);
        mealList.add(meal);
        return meal;
    }

    @Override
    public Meal getById(int id) {
        return mealList.stream().filter(meal -> meal.getId().equals(id)).findAny().orElse(null);
    }

    @Override
    public List<Meal> getAll() {
        return mealList.subList(0, mealList.size());
    }

    @Override
    public Meal update(Meal meal) {
        Meal mealToUpdate = getById(meal.getId());
        mealToUpdate.setCalories(meal.getCalories());
        mealToUpdate.setDateTime(meal.getDateTime());
        mealToUpdate.setDescription(meal.getDescription());
        return meal;
    }

    @Override
    public void delete(int id) {
        mealList.removeIf(meal -> meal.getId().equals(id));
    }
}