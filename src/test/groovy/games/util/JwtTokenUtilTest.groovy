package games.util

import games.configuration.JwtConfiguration
import spock.lang.Specification

class JwtTokenUtilTest extends Specification {
    JwtConfiguration jwtConfiguration = new JwtConfiguration()
    JwtTokenUtil jwtTokenUtil

    def setup() {
        jwtTokenUtil = new JwtTokenUtil(jwtConfiguration)

        jwtConfiguration.setDuration(60)
        jwtConfiguration.setSecret("F8GrX4GK2rqkfXCDHlgF19cDW72YFw7Vg9THRRGRs49BZ1NJT9")
    }

    def "Generate Token"() {
        given: "A request to generate token"
        String rut = "11111111-1"
        String username = "admin123"
        List<String> roles = ["ADMIN", "USER"]

        when: "Obtain token"
        String token = jwtTokenUtil.generateToken(rut, username, roles)

        then: "Validate if it was successful"
        !token.isEmpty()
    }

    def "Get Rut From Token"() {
        given: "A request to get rut from token"
        String rut = "11111111-1"
        String username = "admin123"
        List<String> roles = ["ADMIN", "USER"]

        String token = jwtTokenUtil.generateToken(rut, username, roles)

        when: "Obtain rut"
        String rutToken = jwtTokenUtil.getRutFromToken(token)

        then: "Validate if it was successful"
        rut == rutToken
    }

    def "Get Username From Token"() {
        given: "A request to get username from token"
        String rut = "11111111-1"
        String username = "admin123"
        List<String> roles = ["ADMIN", "USER"]

        String token = jwtTokenUtil.generateToken(rut, username, roles)

        when: "Obtain username"
        String usernameToken = jwtTokenUtil.getUsernameFromToken(token)

        then: "Validate if it was successful"
        username == usernameToken
    }

    def "Get Roles From Token"() {
        given: "A request to get roles from token"
        String rut = "11111111-1"
        String username = "admin123"
        List<String> roles = ["ADMIN", "USER"]

        String token = jwtTokenUtil.generateToken(rut, username, roles)

        when: "Obtain roles"
        String[] rolesToken = jwtTokenUtil.getRolesFromToken(token)

        then: "Validate if it was successful"
        roles.get(0) == rolesToken[0]
        roles.get(1) == rolesToken[1]
        roles.size() == rolesToken.size()
    }

    def "Get Expiration From Token"() {
        given: "A request to get expiration from token"
        String rut = "11111111-1"
        String username = "admin123"
        List<String> roles = ["ADMIN", "USER"]

        String token = jwtTokenUtil.generateToken(rut, username, roles)

        when: "Obtain expiration"
        int expiration = jwtTokenUtil.getExpirationFromToken(token)

        then: "Validate if it was successful"
        expiration != 0
    }

    def "Verify Token"() {
        given: "A request to validate token"
        String rut = "11111111-1"
        String username = "admin123"
        List<String> roles = ["ADMIN", "USER"]

        String token = jwtTokenUtil.generateToken(rut, username, roles)

        if(tokenRandom != null) {
            token = tokenRandom
        }

        when: "Validate"
        boolean validToken = jwtTokenUtil.isValidToken(token)

        then: "Validate if it was successful"
        result == validToken

        where:
        result | tokenRandom
        true   | null
        false  | UUID.randomUUID().toString()
    }
}