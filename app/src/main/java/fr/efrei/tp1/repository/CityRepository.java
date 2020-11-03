package fr.efrei.tp1.repository;

import java.util.List;

import android.content.Context;

import androidx.room.Room;

import fr.efrei.tp1.bo.City;
import fr.efrei.tp1.database.CityDatabase;

//This class implement the singleton pattern
public final class CityRepository
{

  private static volatile CityRepository instance;

  // We accept the "out-of-order writes" case
  public static CityRepository getInstance(Context context)
  {
    if (instance == null)
    {
      synchronized (CityRepository.class)
      {
        if (instance == null)
        {
          instance = new CityRepository(context);
        }
      }
    }

    return instance;
  }

  private final CityDatabase cityDatabase;

  private CityRepository(Context context)
  {
    cityDatabase = Room.databaseBuilder(context, CityDatabase.class, "city-database").allowMainThreadQueries().build();
  }

  public List<City> getCities()
  {
    return cityDatabase.userDao().getCities();
  }

  public void deleteCity(City city)
  {
    cityDatabase.userDao().deleteCity(city);
  }

  public void addCity(City city)
  {
    cityDatabase.userDao().addCity(city);
  }

  public List<City> sortCitiesByName()
  {
    return cityDatabase.userDao().sortCitiesByName();
  }

}
