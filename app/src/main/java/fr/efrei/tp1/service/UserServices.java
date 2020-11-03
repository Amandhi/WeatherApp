package fr.efrei.tp1.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.efrei.tp1.bo.City;
//import fr.efrei.tp1.bo.City.UserComparator;

final public class UserServices
    implements IUserService
{

  private final List<City> cities = new ArrayList<>();

  @Override
  public List<City> getCities()
  {
    return cities;
  }

  @Override
  public void deleteCity(City city)
  {
    cities.remove(city);
  }

  @Override
  public void addCity(City city)
  {
    cities.add(city);
  }

  @Override
  public List<City> sortCitiesByName()
  {
    final List<City> sortedCities = new ArrayList<>(cities);

    //Collections.sort(sortedUsers, new UserComparator());

    return sortedCities;
  }

}
