package learn

import javax.xml.stream.Location

class WeatherController {

    def weatherFetchService
    def index() {

    }

    def save() {

        def location = params.location
        def result =  weatherFetchService.getData(location)

        render(view: "index", model:['result':result])
    }
}
