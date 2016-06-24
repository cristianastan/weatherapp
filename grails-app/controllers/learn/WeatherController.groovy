package learn

import javax.xml.stream.Location

class WeatherController {

    def weatherFetchService
    def locationService

    def index() {

        def location = locationService.getLocation()
        def result =  weatherFetchService.getData(location)

        render(view: "index", model:['result':result])
    }

    def save() {

        def location = params.location
        def result =  weatherFetchService.getData(location)

        render(view: "index", model:['result':result])
    }
}
