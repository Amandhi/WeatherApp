package fr.efrei.tp1.service;

import java.util.List;

import fr.efrei.tp1.bo.City;

//Interface used in order to implement correctly the repository pattern
public interface IUserService
{

  /**
   * Get all cities
   *
   * @return {@link List}
   */
  List<City> getCities();

  /**
   * Delete a city
   *
   * @param city
   */
  void deleteCity(City city);

  /**
   * Add a city
   *
   * @param city
   */
  void addCity(City city);

  /**
   * Get all cities sorted by name
   *
   * @return {@link List}
   */
  List<City> sortCitiesByName();
}
