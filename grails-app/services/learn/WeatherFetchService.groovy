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


    def getInfoByCity(String location) {
        def query = "q="

        def url =  baseUrl + query + location + APPID
        return url
    }

    def getInfoByZipCode(String location) {
        def query = "zip="

        def url =  baseUrl + query + location + APPID
        return url
    }

    def getData(String location) {
        def API_KEY = "61ae4591669ba9f38b46d26e6ee808d5"
        def addr

        if(location.matches(".*\\d.*")) {
            //zip code
            addr = getInfoByZipCode(location)


        }
        else {
            //location
            addr = getInfoByCity(location)
        }


        //        String mock = '{"coord":{"lon":-0.13,"lat":51.51},"weather":[{"id":500,"main":"Rain","description":"light rain","icon":"10d"}],"base":"cmc stations","main":{"temp":292.44,"pressure":1010,"humidity":92,"temp_min":290.37,"temp_max":294.82},"wind":{"speed":0.51,"deg":135,"gust":2.06},"rain":{"1h":0.25},"clouds":{"all":100},"dt":1466592619,"sys":{"type":3,"id":54708,"message":0.004,"country":"GB","sunrise":1466567013,"sunset":1466626909},"id":2643743,"name":"London","cod":200}'
        //
        def resp = rest.get(addr) {
            contentType 'applicatiob/json'
            accept JSONObject
        }

        String country, description
        double temp, humidity

        country = resp.body.sys.country
        description = resp.body.weather.description
        description = description.substring(1, description.length()-1)
        temp = resp.body.main.temp
        humidity = resp.body.main.humidity

        return ["Country":country, "Description":description, "Temperature":temp, "Humidity":humidity]
    }

    /*def parseJSON(String mock) {

        JSONElement res = JSON.parse(mock)

        String country, description
        double temp, humidity

        String id = 'sys'
        country = res[id].country

        id = 'weather'
        description = res[id].description
        description = description.substring(1, description.length()-1)

        id = 'main'
        temp = res[id].temp
        humidity = res[id].humidity

        return ["Country": country, "Description":description, "Temperature":temp, "Humidity":humidity]
    }*/
}
