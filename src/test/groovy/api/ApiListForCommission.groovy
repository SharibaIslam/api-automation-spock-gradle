package api

import groovy.json.JsonBuilder
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import model.User
import spock.lang.Shared
import utils.Util

class ApiListForCommission {
    @Shared
    public def restClient = new RESTClient(ApiPath.hostURL)
    Util util = new Util()
    Properties prop = util.readPropData()
    Date time = new Date(System.currentTimeMillis())


    def agentAssistedWithdrawCommission(User user) {
        def status
        HttpResponseDecorator response
        def fromWallet = prop.getProperty("agentAssistedWithdrawFrom")
        def toWallet = prop.getProperty("agentAssistedWithdrawTo")
        def pin = prop.getProperty("withdrawPIN")
        def withdrawAmount = prop.getProperty("withdrawAmount")
        def auth =prop.getProperty("auth")

        when:
        def requestBody = [
                fromAc: fromWallet,
                pin: pin,
                toAc: toWallet,
                amount: withdrawAmount,
                trnxCode: "11",
                channel: "ussd"
        ]
        println "Agent Assisted Withdraw Commission request: " + new JsonBuilder(requestBody.responseData).toPrettyString()

        try {

            response = restClient.post(
                    body: requestBody,
                    headers: ['Authorization': auth],
                    requestContentType: 'application/json'
                    ) as HttpResponseDecorator
            status = response.status

        } catch (HttpResponseException ex) {
            response = ex.response
        }

        println "Agent Assisted Withdraw Commission response: " + new JsonBuilder(response.responseData).toPrettyString()
        return response

    }




}