# WeatherChecker:
* Weather Checker app is created in kotlin language 
* The App starts with a Splash Screen that contains the app name and its logo,I have added animation to them,transparency animation and translation animation.
* Then the app is taken to Navgigation Activity,where i have created Navigation drawer with the menuitems to choose from:</br>
1.Home</br>
2.Maps</br>
3.Settings</br>
4.About</br>
* I have created fragments for each each menu items
#### Home:-
* The Navigation Activity opens with Home fragment it displays the weather Information like Temperature,Weather-type,min Temperature,max Temperature.
* I have used Volley Library to fetch data from the OpenWeatherApi using Json get Request which in response returns the Json objects of the weather details.
* Depending on the location set in the setting part of the app the weather response of the respective location is fetched.
* To the convert the unix code fetched as a Json Request into date and time i have used simpledataformat class.
* To get the date and time of the device i have used Calender class of java.
* I have used connectivity Manger to check the device is connected to internet or not,If its not connected then a dialog box appears which tells user to turn on internet facilites,If the user clicks it then the user is taken to the wireless setting of the app where he can turn on the connections.
#### Settings:-
* The user can choose to either use Device location or setting location manually for getting the weather for the city.
* If the user wants to set location manually he needs to enter the city name and the country name,He, needs to even set which System of units does he want metirc or imperial and click Done to apply the changes.<br/>
1.Metric- &deg;C , m/s <br/>
2.Imperial- &deg;F , mph
* If the user click the switch to use device location it checks whether the device location is enabled or not ,if its not enabled then a dialog box appears which tells the user to turn on location ,If the user clicks turn On then the user is navigated to the loaction settings of the device.
#### Maps:-
* I have used mapActivity for the maps section of app i have integrated it with gooogle maps Api.
* I have Attached pointer to it too which shows the loaction of the place whose weather you are looking for.
* It can get location of any place whether you have set the location manually or used device location.
#### About:-
* I have used set of hardcores String to make this fragment,Which guides an user how to use the app.
## <u>SnapShot of weatherChecker App</u>:
<table>
<tr><td><img width="170" alt="spalshScreen" src="https://user-images.githubusercontent.com/62636670/92086777-47bd3800-ede8-11ea-86b9-23621d7b6aa8.PNG">
</td>
<td><img width="170" alt="NavigationDrawer" src="https://user-images.githubusercontent.com/62636670/92086921-7804d680-ede8-11ea-93b3-f44eb08c8157.PNG">
</td>
<td><img width="170" alt="manualImperial" src="https://user-images.githubusercontent.com/62636670/92087173-d336c900-ede8-11ea-971a-b0b3aa87804e.PNG">
</td>

<td><img width="170" alt="manualImperialHome" src="https://user-images.githubusercontent.com/62636670/92087216-dfbb2180-ede8-11ea-86ce-8d54b6e90cbb.PNG">
</td>
</tr>
<tr>
<td><img width="170" alt="googlemaps" src="https://user-images.githubusercontent.com/62636670/92087361-04af9480-ede9-11ea-8f18-11d2881ba7bf.PNG">
<td><img width="170" alt="locationPermission" src="https://user-images.githubusercontent.com/62636670/92087075-b00c1980-ede8-11ea-8eb1-e8ca76387b33.PNG">
</td>
<td><img width="170" alt="deviceLoactionHome" src="https://user-images.githubusercontent.com/62636670/92087118-bdc19f00-ede8-11ea-99f4-cb005e1947c9.PNG">
</td>
<td><img width="170" alt="googlemapsGps" src="https://user-images.githubusercontent.com/62636670/92087466-227cf980-ede9-11ea-9011-fee5b044b29b.PNG">

</td>
</tr>
<tr>
  <td><img width="170" alt="manualMetric" src="https://user-images.githubusercontent.com/62636670/92086957-84892f00-ede8-11ea-85cf-89b8a13b348e.PNG">
</td>
<td> <img width="170" alt="manualMetricHome" src="https://user-images.githubusercontent.com/62636670/92086995-94a10e80-ede8-11ea-8cd3-04913df5ed1d.PNG">
</td>
<td><img width="170" alt="dialogueNetwork" src="https://user-images.githubusercontent.com/62636670/92088224-383eee80-edea-11ea-9b4c-b34f0618c2d0.PNG">
</td>
<td><img width="170" alt="about" src="https://user-images.githubusercontent.com/62636670/92087530-3d4f6e00-ede9-11ea-8aa6-39bb2d4d2d65.PNG">
</td>
</tr>
</table>
