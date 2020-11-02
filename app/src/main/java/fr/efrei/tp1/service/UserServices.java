package fr.efrei.tp1.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.efrei.tp1.bo.City;
//import fr.efrei.tp1.bo.City.UserComparator;

final public class UserServices
    implements IUserService
{

  private final List<City> users = new ArrayList<>();

  @Override
  public List<City> getUsers()
  {
    return users;
  }

  @Override
  public void deleteUser(City user)
  {
    users.remove(user);
  }

  @Override
  public void addUser(City user)
  {
    users.add(user);
  }

  @Override
  public List<City> sortUsersByName()
  {
    final List<City> sortedUsers = new ArrayList<>(users);

    //Collections.sort(sortedUsers, new UserComparator());

    return sortedUsers;
  }

}
