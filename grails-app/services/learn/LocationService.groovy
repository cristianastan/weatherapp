package learn

import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.json.JSONObject

@Transactional
class LocationService {
    def rest

    def getLocation() {
        def resp = rest.get("http://ip-api.com/json") {
            contentType 'application/json'
            accept JSONObject
        }

        def location = resp.body.country
        return location
    }

}
