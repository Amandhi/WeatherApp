## Mobile Development - Practical Work 4 : Weather App

#### The above project contains the following java actvity :
* EmptyCityPage : page which is displayed when the user first opens the app or when there are no cities added the user's preferences list. (information message and add button)
* AddCityActivity : enables a user to add the city of his/her choice.
* CityDetailActivity : enables the user to view a city’s weather details when he/she clicks on the city he/she added and enables the user to delete that city from his/her list (menu option and a pop up warning message ). 
This activity also contains the part of the code calling the API (open weather map).
* CitiesACtivity : enables to display the list of cities added by the user (contains also an add button).


and contains the following xml layout :
* activity_empty_city_page.xml
* activity_add_city.xml
* activity_city_detail.xml
* activity_cities.xml
* viewholder_city.xml

#### This project also uses the following objects :
* Adapter
* Database
* Dao
* Retrofit instance (and the needed business objects)
* Interface (that describes the HTTP requests needed)
* JSON Parser (Moshi)
* Interceptor

The app also has a customized icon. 

#### Basically, what you can do with this app :
* Add a city of your choice
* View weather details (temperature, feels like, min tempreature , max temperature) of a city
* Delete a city that has been added (with a warning pop up message customized for each city)

