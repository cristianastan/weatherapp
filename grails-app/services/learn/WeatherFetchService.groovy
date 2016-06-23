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

    def newRow(info) {

        String country, description
        double temp, humidity

        country = info.Country
        description = info.Description
        //description = description.substring(1, description.length() - 1)
        temp = info.Temperature
        humidity = info.Humidity


        Weather weather = new Weather()
        weather.location = info.Location
        weather.country = country
        weather.description = description
        weather.humidity = humidity
        weather.temp = temp

        weather.save(flush:true, failOnError: true)
    }

    def callFromSite( String location) {

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

        return ["Status": "", "Location": location, "Country": country, "Description": description, "Temperature": temp, "Humidity": humidity]
    }


    def getFromTable(tableRow) {
        String country, description
        double temp, humidity

        country = tableRow.country
        description = tableRow.description
        temp = tableRow.temp
        humidity = tableRow.humidity

        return ["Status": "", "Location": tableRow.location, "Country": country, "Description": description, "Temperature": temp, "Humidity": humidity]
    }

    def getHours(tableRow) {

        Date crtDate = new Date()
        Date tableDate = tableRow.lastUpdated

        use(groovy.time.TimeCategory) {
            def duration = crtDate - tableDate
            return duration.hours
        }
    }

    def updateDatabase(tableRow) {

        def info = callFromSite(tableRow.location)

        tableRow.temp = info.Temperature
        tableRow.humidity = info.Humidity
        tableRow.description = info.Description
        tableRow.lastUpdated = new Date()
        tableRow.save(flush:true, failOnError: true)

    }

    def getData(String location) {

        Weather tableRow = Weather.findByLocation(location)

        if(tableRow == null) {
            def info =  callFromSite(location)
            if(!info.Status.equals("Datele introduse sunt incorecte!"))
                newRow(info)

            return info
        }

        int hours = getHours(tableRow)

        if(hours >= 1) {
            updateDatabase(tableRow)
            return callFromSite(location)
        }


        return getFromTable(tableRow)
        //return ["Status": "", "Location": tableRow.location, "Country": tableRow.country.get(0), "Description": tableRow.description.get(0), "Temperature": tableRow.temp.get(0), "Humidity": tableRow.humidity]
    }

}
