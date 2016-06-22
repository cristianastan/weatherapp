package learn

import javax.xml.stream.Location

class WeatherController {

    def weatherFetchService
    def index() {

    }

    def save() {

        def location = params.location
        render weatherFetchService.getData(location)
    }
}
