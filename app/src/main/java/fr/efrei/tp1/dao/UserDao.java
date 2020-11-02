package fr.efrei.tp1.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import fr.efrei.tp1.bo.City;
import fr.efrei.tp1.service.IUserService;

@Dao
public interface UserDao
  extends IUserService
{

  @Override
  @Query("SELECT * FROM City")
  List<City> getUsers();

  @Override
  @Delete
  void deleteUser(City user);

  @Override
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void addUser(City user);

  @Override
  @Query("SELECT * FROM City ORDER BY name DESC")
  List<City> sortUsersByName();

}
