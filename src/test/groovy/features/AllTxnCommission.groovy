package features

import api.ApiPath
import groovy.json.JsonBuilder
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import io.qameta.allure.Allure
import model.User
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll
import utils.Util

class AllTxnCommission  extends Specification{
    @Shared
    public def restClient = new RESTClient(ApiPath.hostURL)
    Util util = new Util()
    Properties prop = util.readPropData()
    Date time = new Date(System.currentTimeMillis())

    @Unroll("Agent/Customer Assisted Withdraw Commission with #tag")
    def '1. Agent/Customer Assisted Withdraw Commission'() {
        def status
        HttpResponseDecorator response
        def fromWallet = prop.getProperty("agentAssistedWithdrawFrom")
        def toWallet = prop.getProperty("agentAssistedWithdrawTo")
        def pin = prop.getProperty("withdrawPIN")
        def auth =prop.getProperty("auth")
        def actualServiceCharge


        when:
        def requestBody = [
                fromAc: fromWallet,
                toAc: toWallet,
                amount: Amount,
                trnxCode: "11"
        ]
        println "Agent Assisted Withdraw Commission request: " + new JsonBuilder(requestBody.responseData).toPrettyString()

        try {

            response = restClient.post(
                    body: requestBody,
                    headers: ['Authorization': auth],
                    requestContentType: 'application/json'
            ) as HttpResponseDecorator
            actualServiceCharge=response.responseData["serviceFee"]


        } catch (HttpResponseException ex) {
            response = ex.response
        }

        println "Agent Assisted Withdraw Commission response: " + new JsonBuilder(response.responseData).toPrettyString()

        then:
        actualServiceCharge==expectedCom
        try{
        if(response.status in [200]) {
            Allure.addDescription("Status Code: " + response.status + " \n\r Status: PASS \n\r Type: Disburse withdraw commission  \n\r Date Time: " + (time.toString()) + "\n\r Actual Commission: " + actualServiceCharge
                    + "\n\r Expected Commission: " + expectedCom)
        }
          else
            Allure.addDescription("Status: FAIL \n\r Type: Activity List \n\r Date Time: " + (time.toString()))

    }catch(Exception e){
        println(e)
    }
        where:
        tag                                      | Amount         | expectedCom
        "General withdraw amount=1000"           | 1000           | 18.0
        "General withdraw amount=666"            | 666            | 11.988
        "Minimum withdraw amount=80"             | 80             | 5.0
        "Minimum invalid withdraw amount=25"     | 25             | 0
        "Max withdraw amount=8000"               | 8000           | 144
        "Negative case amount=999"               | 999            | 14

    }

    @Unroll("Disbursement Withdraw Commission with #tag")
    def '2. Disbursement Withdraw Commission'() {
        def status
        HttpResponseDecorator response
        def fromWallet = prop.getProperty("disburseWithdrawFrom")
        def toWallet = prop.getProperty("agentAssistedWithdrawTo")
        def auth =prop.getProperty("auth")
        def actualServiceCharge


        when:
        def requestBody = [
                fromAc: fromWallet,
                toAc: toWallet,
                amount: Amount,
                trnxCode: "11"
        ]
        println "Disbursement Withdraw Commission request: " + new JsonBuilder(requestBody.responseData).toPrettyString()

        try {

            response = restClient.post(
                    body: requestBody,
                    headers: ['Authorization': auth],
                    requestContentType: 'application/json'
            ) as HttpResponseDecorator

            actualServiceCharge=response.responseData["serviceFee"]


        } catch (HttpResponseException ex) {
            response = ex.response
        }

        println "Disbursement Withdraw Commission response: " + new JsonBuilder(response.responseData).toPrettyString()

        then:
        actualServiceCharge==expectedCom
        try {
            if (response.status in [200]) {
                Allure.addDescription("Status Code: " + response.status + " \n\r Status: PASS \n\r Type: Disburse withdraw commission  \n\r Date Time: " + (time.toString()) + "\n\r Actual Commission: " + actualServiceCharge
                        + "\n\r Expected Commission: " + expectedCom)
            }
            else
                Allure.addDescription("Status: FAIL \n\r Type: Activity List \n\r Date Time: " + (time.toString()))
        }catch(Exception e){
            println(e)

        }
        where:
        tag                                                                     | Amount         | expectedCom
        "disburse withdraw amount=1000 and rate=1%"                             | 1000           | 10.0
        "disburse withdraw amount=987 and rate=1%"                              | 987            | 9.87
        "disburse combined withdraw(general+disburse) amount=8745 and "         | 8745           | 134.5672224
        "disburse minimum withdraw amount=45 and "                              | 45             | 0.45
        "Set max disburse withdraw fee=20 and rate=1% and withdraw 3000 "                              | 3000             | 20

    }

    @Unroll("PESP Disbursement Withdraw Commission with #tag")
    def '3. PESP Disbursement Withdraw Commission'() {
        def status
        HttpResponseDecorator response
        def fromWallet = prop.getProperty("pespDisburseWithdrawFrom")
        def toWallet = prop.getProperty("agentAssistedWithdrawTo")
        def auth =prop.getProperty("auth")
        def actualServiceCharge


        when:
        def requestBody = [
                fromAc: fromWallet,
                toAc: toWallet,
                amount: Amount,
                trnxCode: "11"
        ]
        println "Disbursement Withdraw Commission request: " + new JsonBuilder(requestBody.responseData).toPrettyString()

        try {

            response = restClient.post(
                    body: requestBody,
                    headers: ['Authorization': auth],
                    requestContentType: 'application/json'
            ) as HttpResponseDecorator

            actualServiceCharge=response.responseData["serviceFee"]


        } catch (HttpResponseException ex) {
            response = ex.response
        }

        println "Disbursement Withdraw Commission response: " + new JsonBuilder(response.responseData).toPrettyString()

        then:
        actualServiceCharge==expectedCom
        try {
            if (response.status in [200]) {
                Allure.addDescription("Status Code: " + response.status + " \n\r Status: PASS \n\r Type: PESP Disburse withdraw commission  \n\r Date Time: " + (time.toString()) + "\n\r Actual Commission: " + actualServiceCharge
                        + "\n\r Expected Commission: " + expectedCom)
            }
            else
                Allure.addDescription("Status: FAIL \n\r Type: Activity List \n\r Date Time: " + (time.toString()))
        }catch(Exception e){
            println(e)
        }
        where:
        tag                                                                     | Amount         | expectedCom
        "PESP disburse withdraw amount=1000"                                    | 1000           | 0
        "PESP disburse withdraw amount=300"                                     | 300            | 0
        "PESP disburse combined withdraw(general+disburse) amount=1600"         | 1600           | 0
        "PESP disburse min amount=1600"                                         | 30             | 0

    }

    @Unroll("Send Money Commission with #tag")
    def '4. Send Money Commission'() {
        def status
        HttpResponseDecorator response
        def fromWallet = prop.getProperty("sendMoneyFrom")
        def toWallet = prop.getProperty("sendMoneyTo")
        def auth =prop.getProperty("auth")
        def actualServiceCharge


        when:
        def requestBody = [
                fromAc: fromWallet,
                toAc: toWallet,
                amount: Amount,
                trnxCode: "1201"
        ]
        println "Send Money Commission request: " + new JsonBuilder(requestBody.responseData).toPrettyString()

        try {

            response = restClient.post(
                    body: requestBody,
                    headers: ['Authorization': auth],
                    requestContentType: 'application/json'
            ) as HttpResponseDecorator

            actualServiceCharge=response.responseData["serviceFee"]


        } catch (HttpResponseException ex) {
            response = ex.response
        }

        println "Send Money Commission response: " + new JsonBuilder(response.responseData).toPrettyString()

        then:
        actualServiceCharge==expectedCom
        try {
            if (response.status in [200]) {
                Allure.addDescription("Status Code: " + response.status + " \n\r Status: PASS \n\r Type: Send Money commission  \n\r Date Time: " + (time.toString()) + "\n\r Actual Commission: " + actualServiceCharge
                        + "\n\r Expected Commission: " + expectedCom)
            }
            else
                Allure.addDescription("Status: FAIL \n\r Type: Activity List \n\r Date Time: " + (time.toString()))
        }catch(Exception e){
            println(e)
        }
        where:
        tag                                                                         | Amount         | expectedCom
        "Send money with negative case amount=5"                                    | 1000           | 0
        "Send money with negative case amount=450"                                  | 450            | 4
        "Send money with  amount=1600"                                              | 26000          | 4

    }

    @Unroll("Agent Assisted Payment(fee from customer) Commission with #tag")
    def '5. Agent Assisted Payment(fee from customer)'() {
        def status
        HttpResponseDecorator response
        def fromWallet = prop.getProperty("agentFromWalletPayment")
        def toWallet = prop.getProperty("merToWalletFeeFromCus")
        def externalCus = prop.getProperty("externalCus")
        def auth =prop.getProperty("auth")
        def actualServiceCharge


        when:
        def requestBody = [
                fromAc: fromWallet,
                toAc: toWallet,
                externalCustomer: externalCus,
                amount: Amount,
                trnxCode: "107"
        ]
        println "Agent Assisted Payment(fee from customer) request: " + new JsonBuilder(requestBody.responseData).toPrettyString()

        try {

            response = restClient.post(
                    body: requestBody,
                    headers: ['Authorization': auth],
                    requestContentType: 'application/json'
            ) as HttpResponseDecorator

            actualServiceCharge=response.responseData["serviceFee"]


        } catch (HttpResponseException ex) {
            response = ex.response
        }

        println "Agent Assisted Payment(fee from customer) response: " + new JsonBuilder(response.responseData).toPrettyString()

        then:
        actualServiceCharge==expectedCom
        try {
            if (response.status in [200]) {
                Allure.addDescription("Status Code: " + response.status + " \n\r Status: PASS \n\r Type: Agent Assisted Payment(fee from customer) commission  \n\r Date Time: " + (time.toString()) + "\n\r Actual Commission: " + actualServiceCharge
                        + "\n\r Expected Commission: " + expectedCom)
            }
            else
                Allure.addDescription("Status: FAIL \n\r Type: Activity List \n\r Date Time: " + (time.toString()))
        }catch(Exception e){
            println(e)
        }
        where:
        tag                                                                                         | Amount | expectedCom
        "payment with amount=19 and service charge from customer,min fee=4, service charge will be=4"         | 19             | 4
        "payment with amount=380 and service charge from customer,min fee=4, service charge will be=4"        | 380            | 4
        "Try to payment with amount=500,  and service charge from customer, rate=1%, service charge will be=5"  | 500          | 5
        "Try to payment with amount=1500,  and service charge from customer, rate=1%, service charge will be=15"  | 1500          | 15
        "Try to payment with amount=3500,  and service charge from customer,max fee=30, service charge will be=30"  | 3500          | 30
        "Try to payment with amount=5500,  and service charge from customer, max fee=30,service charge will be=30"  | 5500          | 30

    }

    @Unroll("Agent Assisted Payment(fee from merchant) Commission with #tag")
    def '6. Agent Assisted Payment(fee from merchant)'() {
        def status
        HttpResponseDecorator response
        def fromWallet = prop.getProperty("agentFromWalletPayment")
        def toWallet = prop.getProperty("merToWalletFeeFromMerchant")
        def externalCus = prop.getProperty("externalCus")
        def auth =prop.getProperty("auth")
        def actualServiceCharge


        when:
        def requestBody = [
                fromAc: fromWallet,
                toAc: toWallet,
                externalCustomer: externalCus,
                amount: Amount,
                trnxCode: "107"
        ]
        println "Agent Assisted Payment(fee from merchant) request: " + new JsonBuilder(requestBody.responseData).toPrettyString()

        try {

            response = restClient.post(
                    body: requestBody,
                    headers: ['Authorization': auth],
                    requestContentType: 'application/json'
            ) as HttpResponseDecorator

            actualServiceCharge=response.responseData["serviceFee"]


        } catch (HttpResponseException ex) {
            response = ex.response
        }

        println "Agent Assisted Payment(fee from merchant) response: " + new JsonBuilder(response.responseData).toPrettyString()

        then:
        actualServiceCharge==expectedCom
        try {
            if (response.status in [200]) {
                Allure.addDescription("Status Code: " + response.status + " \n\r Status: PASS \n\r Type: Agent Assisted Payment(fee from customer) commission  \n\r Date Time: " + (time.toString()) + "\n\r Actual Commission: " + actualServiceCharge
                        + "\n\r Expected Commission: " + expectedCom)
            }
            else
                Allure.addDescription("Status: FAIL \n\r Type: Activity List \n\r Date Time: " + (time.toString()))
        }catch(Exception e){
            println(e)
        }
        where:
        tag                                                                                                       | Amount           | expectedCom
        "payment with amount=380 and service charge from merchant, service charge will be=0 for customer"         | 380            | 0
        "payment with amount=1600 and service charge from merchant, service charge will be=0 for customer"         | 1600            | 0
        "payment with amount=3500 and service charge from merchant, service charge will be=0 for customer"         | 3500            | 0


    }

    @Unroll("Customer Assisted Payment(fee from customer) Commission with #tag")
    def '7. Customer Assisted Payment(fee from customer)'() {
        def status
        HttpResponseDecorator response
        def fromWallet = prop.getProperty("cusFromWalletPayment")
        def toWallet = prop.getProperty("merToWalletFeeFromCus")
        def auth =prop.getProperty("auth")
        def actualServiceCharge


        when:
        def requestBody = [
                fromAc: fromWallet,
                toAc: toWallet,
                amount: Amount,
                trnxCode: "1202"
        ]
        println "Customer Assisted Payment(fee from customer) request: " + new JsonBuilder(requestBody.responseData).toPrettyString()

        try {

            response = restClient.post(
                    body: requestBody,
                    headers: ['Authorization': auth],
                    requestContentType: 'application/json'
            ) as HttpResponseDecorator

            actualServiceCharge=response.responseData["serviceFee"]


        } catch (HttpResponseException ex) {
            response = ex.response
        }

        println "Customer Assisted Payment(fee from customer) response: " + new JsonBuilder(response.responseData).toPrettyString()

        then:
        actualServiceCharge==expectedCom
        try {
            if (response.status in [200]) {
                Allure.addDescription("Status Code: " + response.status + " \n\r Status: PASS \n\r Type: Customer Assisted Payment(fee from customer) commission  \n\r Date Time: " + (time.toString()) + "\n\r Actual Commission: " + actualServiceCharge
                        + "\n\r Expected Commission: " + expectedCom)
            }
            else
                Allure.addDescription("Status: FAIL \n\r Type: Activity List \n\r Date Time: " + (time.toString()))
        }catch(Exception e){
            println(e)
        }
        where:
        tag                                                                                                   | Amount | expectedCom
        "payment with amount=18 and service charge from customer,min fee=4, service charge will be=4"         | 18             | 4
        "payment with amount=350 and service charge from customer,min fee=4, service charge will be=4"        | 350            | 4
        "Try to payment with amount=550,  and service charge from customer, rate=1%, service charge will be=5.5"  | 550          | 5.5
        "Try to payment with amount=1750,  and service charge from customer, rate=1%, service charge will be=15"  | 1750          | 17.5
        "Try to payment with amount=3500,  and service charge from customer,max fee=25, service charge will be=25"  | 3500          | 25
        "Try to payment with amount=5500,  and service charge from customer, max fee=25,service charge will be=25"  | 5500          | 25

    }

    @Unroll("Customer Assisted Payment(fee from merchant) Commission with #tag")
    def '8. Customer Assisted Payment(fee from merchant)'() {
        def status
        HttpResponseDecorator response
        def fromWallet = prop.getProperty("cusFromWalletPayment")
        def toWallet = prop.getProperty("merToWalletFeeFromMerchant")
        def externalCus = prop.getProperty("externalCus")
        def auth =prop.getProperty("auth")
        def actualServiceCharge


        when:
        def requestBody = [
                fromAc: fromWallet,
                toAc: toWallet,
                amount: Amount,
                trnxCode: "1202"
        ]
        println "Customer Assisted Payment(fee from merchant) request: " + new JsonBuilder(requestBody.responseData).toPrettyString()

        try {

            response = restClient.post(
                    body: requestBody,
                    headers: ['Authorization': auth],
                    requestContentType: 'application/json'
            ) as HttpResponseDecorator

            actualServiceCharge=response.responseData["serviceFee"]


        } catch (HttpResponseException ex) {
            response = ex.response
        }

        println "Customer Assisted Payment(fee from merchant) response: " + new JsonBuilder(response.responseData).toPrettyString()

        then:
        actualServiceCharge==expectedCom
        try {
            if (response.status in [200]) {
                Allure.addDescription("Status Code: " + response.status + " \n\r Status: PASS \n\r Type: Customer Assisted Payment(fee from customer) commission  \n\r Date Time: " + (time.toString()) + "\n\r Actual Commission: " + actualServiceCharge
                        + "\n\r Expected Commission: " + expectedCom)
            }
            else
                Allure.addDescription("Status: FAIL \n\r Type: Activity List \n\r Date Time: " + (time.toString()))
        }catch(Exception e){
            println(e)
        }
        where:
        tag                                                                                                       | Amount           | expectedCom
        "payment with amount=350 and service charge from merchant, service charge will be=0 for customer"         | 350            | 0
        "payment with amount=2200 and service charge from merchant, service charge will be=0 for customer"         | 1750            | 0
        "payment with amount=3350 and service charge from merchant, service charge will be=0 for customer"         | 3500            | 0


    }

}
