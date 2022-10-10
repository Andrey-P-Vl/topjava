package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealStorage {
    void add(Meal meal);

    Meal getById(Integer id);

    List<Meal> getAll();

    void update(Integer id, Meal meal);

    void delete(Integer id);
}