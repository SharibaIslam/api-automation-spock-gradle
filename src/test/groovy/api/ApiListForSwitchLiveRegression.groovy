package api

import groovy.json.JsonBuilder
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import model.User
import spock.lang.Shared
import utils.Util

class ApiListForSwitchLiveRegression {
    @Shared
    public def restClient = new RESTClient(ApiPath.hosLivetURL)
    Util util = new Util()
    Properties prop = util.readPropData()
    Date time = new Date(System.currentTimeMillis())


    def p2pFromRBLToFS(User user) {
        HttpResponseDecorator response
        def fromWallet = prop.getProperty("p2pFromWalletRblToFS")
        def toWallet = prop.getProperty("p2pToWalletRblToFS")
        def pin = prop.getProperty("pin")
        def p2pAmount = prop.getProperty("p2pAmount")
        def auth =prop.getProperty("doPaymentAuth")
        def path =ApiPath.doPaymentPath

        when:
        def requestBody = [
                fromAc: fromWallet,
                pin: pin,
                toAc: toWallet,
                amount: p2pAmount,
                trnxCode: "1201",
                channel: "ussd"
        ]
        println "P2P from RBL to FS request: " + new JsonBuilder(requestBody.responseData).toPrettyString()

        try {

            response = restClient.post(
                    path: path,
                    body: requestBody,
                    headers: ['Authorization': auth],
                    requestContentType: 'application/json'
            ) as HttpResponseDecorator


        } catch (HttpResponseException ex) {
            response = ex.response
        }

        println "P2P from RBL to FS response: " + new JsonBuilder(response.responseData).toPrettyString()
        return response

    }
    def p2pFromRBLToBCB(User user) {
        def status
        HttpResponseDecorator response
        def fromWallet = prop.getProperty("p2pFromWalletRblToBCB")
        def toWallet = prop.getProperty("p2pToWalletRBLToBCB")
        def pin = prop.getProperty("pin")
        def p2pAmount = prop.getProperty("p2pAmount")
        def auth =prop.getProperty("doPaymentAuth")
        def path =ApiPath.doPaymentPath

        when:
        def requestBody = [
                fromAc: fromWallet,
                pin: pin,
                toAc: toWallet,
                amount: p2pAmount,
                trnxCode: "1201",
                channel: "ussd"
        ]
        println "P2P from RBL to FS request: " + new JsonBuilder(requestBody.responseData).toPrettyString()

        try {

            response = restClient.post(
                    path: path,
                    body: requestBody,
                    headers: ['Authorization': auth],
                    requestContentType: 'application/json'
            ) as HttpResponseDecorator
            status = response.status

        } catch (HttpResponseException ex) {
            response = ex.response
        }

        println "P2P from RBL to FS response: " + new JsonBuilder(response.responseData).toPrettyString()
        return response

    }

    def p2pFromRBLToJBL(User user) {
        def status
        HttpResponseDecorator response
        def fromWallet = prop.getProperty("p2pFromWalletRblToJBL")
        def toWallet = prop.getProperty("p2pToWalletRBLToJBL")
        def pin = prop.getProperty("pin")
        def p2pAmount = prop.getProperty("p2pAmount")
        def auth =prop.getProperty("doPaymentAuth")
        def path =ApiPath.doPaymentPath

        when:
        def requestBody = [
                fromAc: fromWallet,
                pin: pin,
                toAc: toWallet,
                amount: p2pAmount,
                trnxCode: "1201",
                channel: "ussd"
        ]
        println "P2P from RBL to FS request: " + new JsonBuilder(requestBody.responseData).toPrettyString()

        try {

            response = restClient.post(
                    path: path,
                    body: requestBody,
                    headers: ['Authorization': auth],
                    requestContentType: 'application/json'
            ) as HttpResponseDecorator
            status = response.status

        } catch (HttpResponseException ex) {
            response = ex.response
        }

        println "P2P from RBL to FS response: " + new JsonBuilder(response.responseData).toPrettyString()
        return response

    }

    def p2pFromRBLToRBL(User user) {
        def status
        HttpResponseDecorator response
        def fromWallet = prop.getProperty("p2pFromWalletRblToRBL")
        def toWallet = prop.getProperty("p2pToWalletRBLToRBL")
        def pin = prop.getProperty("pin")
        def p2pAmount = prop.getProperty("p2pAmount")
        def auth =prop.getProperty("doPaymentAuth")
        def path =ApiPath.doPaymentPath

        when:
        def requestBody = [
                fromAc: fromWallet,
                pin: pin,
                toAc: toWallet,
                amount: p2pAmount,
                trnxCode: "1201",
                channel: "ussd"
        ]
        println "P2P from RBL to FS request: " + new JsonBuilder(requestBody.responseData).toPrettyString()

        try {

            response = restClient.post(
                    path: path,
                    body: requestBody,
                    headers: ['Authorization': auth],
                    requestContentType: 'application/json'
            ) as HttpResponseDecorator
            status = response.status

        } catch (HttpResponseException ex) {
            response = ex.response
        }

        println "P2P from RBL to FS response: " + new JsonBuilder(response.responseData).toPrettyString()
        return response

    }

    def p2bFromRBLToRBL(User user) {
        def status
        HttpResponseDecorator response
        def fromWallet = prop.getProperty("p2pFromWalletRblToRBL")
        def toWallet = prop.getProperty("p2pToWalletRBLToRBL")
        def pin = prop.getProperty("pin")
        def p2pAmount = prop.getProperty("p2pAmount")
        def auth =prop.getProperty("doPaymentAuth")
        def path =ApiPath.doPaymentPath

        when:
        def requestBody = [
                fromAc: fromWallet,
                pin: pin,
                toAc: toWallet,
                amount: p2pAmount,
                trnxCode: "1201",
                channel: "ussd"
        ]
        println "P2P from RBL to FS request: " + new JsonBuilder(requestBody.responseData).toPrettyString()

        try {

            response = restClient.post(
                    path: path,
                    body: requestBody,
                    headers: ['Authorization': auth],
                    requestContentType: 'application/json'
            ) as HttpResponseDecorator
            status = response.status

        } catch (HttpResponseException ex) {
            response = ex.response
        }

        println "P2P from RBL to FS response: " + new JsonBuilder(response.responseData).toPrettyString()
        return response

    }


}
