package fr.efrei.tp1.bo;

import java.io.Serializable;
import java.util.Comparator;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//This class is used in order to represent a User
//This is an Entity
@Entity
final public class City
    implements Serializable
{

  //This class is used in order to sort a city
  public static final class CityComparator
          implements Comparator<City>
  {

    @Override
    public int compare(City o1, City o2)
    {
      return o1.name.compareTo(o2.name);
    }

  }


  @PrimaryKey(autoGenerate = true)
  public int id;

  @NonNull
  public final String name;



  public City(@NonNull String name)
  {
    //we set the id to 0 because 0 is considerating  as not-set while inserting the item
    id = 0;
    this.name = name;

  }


  //The methods "equals" and "hashcode" has been
  //automatically generated by Android Studio
  //They are used in order to compare cities (sort + deletion)
  @Override
  public boolean equals(Object o)
  {
    if (this == o)
    {
      return true;
    }
    if (!(o instanceof City))
    {
      return false;
    }

    City city = (City) o;

    if (!name.equals(city.name))
    {
      return false;
    }

    return false;
  }

  @Override
  public int hashCode()
  {
    int result = name.hashCode();

    return result;
  }



}

