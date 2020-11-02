package fr.efrei.tp1.repository;

import java.util.List;

import android.content.Context;

import androidx.room.Room;

import fr.efrei.tp1.bo.City;
import fr.efrei.tp1.database.CityDatabase;

//This class implement the singleton pattern
public final class UserRepository
{

  private static volatile UserRepository instance;

  // We accept the "out-of-order writes" case
  public static UserRepository getInstance(Context context)
  {
    if (instance == null)
    {
      synchronized (UserRepository.class)
      {
        if (instance == null)
        {
          instance = new UserRepository(context);
        }
      }
    }

    return instance;
  }

  private final CityDatabase cityDatabase;

  private UserRepository(Context context)
  {
    cityDatabase = Room.databaseBuilder(context, CityDatabase.class, "city-database").allowMainThreadQueries().build();
  }

  public List<City> getCity()
  {
    return cityDatabase.userDao().getUsers();
  }

  public void deleteCity(City user)
  {
    cityDatabase.userDao().deleteUser(user);
  }

  public void addCity(City user)
  {
    cityDatabase.userDao().addUser(user);
  }

  public List<City> sortCitiesByName()
  {
    return cityDatabase.userDao().sortUsersByName();
  }

}
