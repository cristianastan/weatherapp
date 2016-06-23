package learn

import grails.converters.JSON
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.json.JSONElement
import org.codehaus.groovy.grails.web.json.JSONObject

@Transactional
class WeatherFetchService {

    def rest
    def API_KEY = "61ae4591669ba9f38b46d26e6ee808d5"
    def baseUrl = "http://api.openweathermap.org/data/2.5/weather?"
    def APPID = "&appid=" + API_KEY


    def getData(String location) {

        //def tableEntry = Weather.findAllByLocation(location)

        def API_KEY = "61ae4591669ba9f38b46d26e6ee808d5"
        def addr
        def query

        if(location.matches(".*\\d.*"))
            query = "zip="
        else
            query = "q="

        addr = baseUrl + query + location + APPID
        def resp = rest.get(addr) {
            contentType 'application/json'
            accept JSONObject
        }

        if(resp.statusCode.value != 200)
            return ["Status": "Datele introduse sunt incorecte!", "Location": "", "Country":"", "Description":"", "Temperature":"", "Humidity":""]


        String country, description
        double temp, humidity

        country = resp.body.sys.country
        description = resp.body.weather.description
        description = description.substring(1, description.length() - 1)
        temp = resp.body.main.temp
        humidity = resp.body.main.humidity


        Weather weather = new Weather()
        weather.location = location
        weather.country = country
        weather.description = description
        weather.humidity = humidity
        weather.temp = temp

        weather.save(flush:true, failOnError: true)

        return ["Status": "", "Location": location, "Country": country, "Description": description, "Temperature": temp, "Humidity": humidity]
    }

}
