package fr.efrei.tp1.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import fr.efrei.tp1.bo.City;
import fr.efrei.tp1.dao.UserDao;

@Database(entities = { City.class }, version = 1)
public abstract class CityDatabase
    extends RoomDatabase
{

  public abstract UserDao userDao();

}
