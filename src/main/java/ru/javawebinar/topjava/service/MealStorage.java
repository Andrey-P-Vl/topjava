package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealStorage {
    Meal add(Meal meal);

    Meal getById(int id);

    List<Meal> getAll();

    Meal update(Meal meal);

    void delete(int id);
}