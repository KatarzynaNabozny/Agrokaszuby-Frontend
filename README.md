# Agrokaszuby-Frontend

### **Features**:
* **F1** - Flat reservation
* **F2** - Related tables
* **F3** - [Frontend] Weather API - Forecast for seven days
* **F4** - [Frontend] Currency exchange API - Add Price with PLN and USD
* **F5** - Reservation form validation
* **F6** - Design patterns
* **F7** - [Backend] Scheduler - sending email with all reservations each day
* **F8** - Application instruction

####GitHub repos
* Backend: https://github.com/KatarzynaNabozny/Agrokaszuby-Backend

------
* Frontend: https://github.com/KatarzynaNabozny/Agrokaszuby-Frontend
------
Port: http://localhost:8080/

#####To run Frontend you need to run **AgrokaszubyFrontApplication.java**

##API (2. Data download - F3, F4)
###Currency
GET: https://openexchangerates.org/api/latest.json?app_id=bdf9664efe044ad1b1821463563b756d&symbols=PLN \
This is used in CurrencyExchangeService.java. \
Needed to calculate price of reservation which is calculated when client will fill beginning, end of reservation and currency. \
Then price will appear like below:

![img.png](src/main/resources/static/currency_and_price.png)

###Weather
GET: https://api.openweathermap.org/data/2.5/onecall?lat=54.45169&lon=18.21428&appid=29dfcc1425030e06fc5850aa06fc9ce1&lang=en&units=metric&exclude=current,minutely,hourly

To display one week forecast (in °C) for Destination of Hotel Agrokaszuby on main view as a table:

![img.png](src/main/resources/static/weather_forecast.png)

##Design patterns

Reservation Builder F6