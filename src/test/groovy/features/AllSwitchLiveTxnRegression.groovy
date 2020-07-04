package features

import api.ApiListForCommission
import api.ApiListForSwitchLiveRegression
import api.ApiPath
import api.ExpectedCommission
import groovy.json.JsonBuilder
import groovy.util.logging.Slf4j
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import io.qameta.allure.Allure
import model.User
import org.apache.tools.ant.taskdefs.email.EmailTask
import org.omg.IOP.Encoding
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll
import utils.Util

@Slf4j
class AllSwitchLiveTxnRegression extends Specification {
    @Shared
    public def restClient = new RESTClient(ApiPath.hosLivetURL)
    Util util = new Util()
    Properties prop = util.readPropData()
    public static String d1
    public static String d2
    Date time = new Date(System.currentTimeMillis())


    @Unroll("P2P from #tag")
    def '1. P2P from'() {
        def status
        HttpResponseDecorator response
        def fromRBLtoRBLWallet = prop.getProperty("p2pFromWalletRblToRBL")
        def toRBLtoRBLWallet = prop.getProperty("p2pToWalletRBLToRBL")
        def fromRBLtoBCBLWallet = prop.getProperty("p2pFromWalletRblToBCB")
        def toRBLtoBCBLWallet = prop.getProperty("p2pToWalletRBLToBCB")
        def fromRBLtoFSIBLWallet = prop.getProperty("p2pFromWalletRblToFS")
        def toRBLtoFSIBLWallet = prop.getProperty("p2pToWalletRblToFS")
        def fromRBLtoJBLWallet = prop.getProperty("p2pFromWalletRblToJBL")
        def toRBLtoJBLWallet = prop.getProperty("p2pToWalletRBLToJBL")
        def fromBCBLtoBCBLWallet = prop.getProperty("p2pfromBCBLtoBCBLWallet")
        def toBCBLtoBCBLWallet = prop.getProperty("p2ptoBCBLtoBCBLWallet")
        def fromBCBLtoRBLWallet = prop.getProperty("p2pfromBCBLtoRBLWallet")
        def toBCBLtoRBLWallet = prop.getProperty("p2ptoBCBLtoRBLWallet")
        def fromBCBLtoFSIBLWallet = prop.getProperty("p2pfromBCBLtoFSIBLWallet")
        def toBCBLtoFSIBLWallet = prop.getProperty("p2ptoBCBLtoFSIBLWallet")
        def fromBCBLtoJBLWallet = prop.getProperty("p2pfromBCBLtoJBLWallet")
        def toBCBLtoJBLWallet = prop.getProperty("p2ptoBCBLtoJBLWallet")
        def fromFSIBLtoFSIBLWallet = prop.getProperty("p2pfromFSIBLtoFSIBLWallet")
        def toFSIBLtoFSIBLWallet = prop.getProperty("p2ptoFSIBLtoFSIBLWallet")
        def fromFSIBLtoRBLWallet = prop.getProperty("p2pfromFSIBLtoRBLWallet")
        def toFSIBLtoRBLWallet = prop.getProperty("p2ptoFSIBLtoRBLWallet")
        def fromFSIBLtoBCBLWallet = prop.getProperty("p2pfromFSIBLtoBCBLWallet")
        def toFSIBLtoBCBLWallet = prop.getProperty("p2ptoFSIBLtoBCBLWallet")
        def fromFSIBLtoJBLWallet = prop.getProperty("p2pfromFSIBLtoJBLWallet")
        def toFSIBLtoJBLWallet = prop.getProperty("p2ptoFSIBLtoJBLWallet")
        def pin = prop.getProperty("p2pPin")
        def amt1 = prop.getProperty("p2pAmount")
        def auth = prop.getProperty("doPaymentAuth")
        def path = ApiPath.doPaymentPath

        when:
        def requestBody = [
                fromAc  : fromAc,
                pin     : pin,
                toAc    : toAc,
                amount  : amt,
                trnxCode: "1201",
                channel : "ussd"
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


        then:
        response.status == 200

                where:
                tag                         | fromAc                             | toAc                    | amt
                "RBL to RBL"                | ["fromRBLtoRBLWallet"]             | ["toRBLtoRBLWallet"]    | ["amt1"]
                "RBL to BCBL"               | ["fromRBLtoBCBLWallet"]            | ["toRBLtoBCBLWallet"]   | ["amt1"]
                "RBL to JBL"                | ["fromRBLtoJBLLWallet"]            | ["toRBLtoJBLWallet"]    | ["amt1"]
                "RBL to FSIBL"              | ["fromRBLtoFSIBLWallet"]           | ["toRBLtoFSIBLWallet"]  | ["amt1"]
                "BCBL to RBL"               | ["fromBCBLtoRBLWallet"]            | ["toBCBLtoRBLWallet"]   | ["amt1"]
                "BCBL to FSIBL"             | ["fromBCBLtoFSIBLWallet"]          | ["toBCBLtoFSIBLWallet"] | ["amt1"]
                "BCBL to BCBL"              | ["fromBCBLtoBCBLWallet"]           | ["toBCBLtoBCBLWallet"]  | ["amt1"]
                "BCBL to JBL"               | ["fromBCBLtoJBLWallet"]            | ["toBCBLtoJBLWallet"]   | ["amt1"]
                "JBL to JBL"                | ["fromJBLtoJBLWallet"]             | ["toJBLtoJBLWallet"]    | ["amt1"]
                "JBL to RBL"                | ["fromJBLtoRBLWallet"]             | ["toJBLtoRBLWallet"]    | ["amt1"]
                "JBL to BCBL"               | ["fromJBLtoBCBLWallet"]            | ["toJBLtoBCBLWallet"]   | ["amt1"]
                "JBL to FSIBL"              | ["fromJBLtoFSIBLWallet"]           | ["toJBLtoFSIBLWallet"]  | ["amt1"]
                "FSIBL to FSIBL"            | ["fromFSIBLtoFSIBLWallet"]         | ["toFSIBLtoFSIBLWallet"]| ["amt1"]
                "FSIBL to BCBL"             | ["fromFSIBLtoBCBLWallet"]          | ["toFSIBLtoBCBLWallet"] | ["amt1"]
                "FSIBL to RBL"              | ["fromFSIBLtoRBLWallet"]           | ["toFSIBLtoRBLWallet"]  | ["amt1"]
                "FSIBL to JBL"              | ["fromFSIBLtoJBLWallet"]           | ["toFSIBLtoJBLWallet"]  | ["amt1"]


    }

    @Unroll("Deposit from #tag")
    def '2. Deposit from'() {
        def status
        HttpResponseDecorator response
        def fromRBLtoRBLWallet = prop.getProperty("depositFromWalletRblToRBL")
        def toRBLtoRBLWallet = prop.getProperty("depositToWalletRBLToRBL")
        def fromRBLtoBCBLWallet = prop.getProperty("depositFromWalletRblToBCB")
        def toRBLtoBCBLWallet = prop.getProperty("depositToWalletRBLToBCB")
        def fromRBLtoFSIBLWallet = prop.getProperty("depositFromWalletRblToFS")
        def toRBLtoFSIBLWallet = prop.getProperty("depositToWalletRblToFS")
        def fromRBLtoJBLWallet = prop.getProperty("depositFromWalletRblToJBL")
        def toRBLtoJBLWallet = prop.getProperty("depositToWalletRBLToJBL")
        def fromBCBLtoBCBLWallet = prop.getProperty("depositfromBCBLtoBCBLWallet")
        def toBCBLtoBCBLWallet = prop.getProperty("deposittoBCBLtoBCBLWallet")
        def fromBCBLtoRBLWallet = prop.getProperty("depositfromBCBLtoRBLWallet")
        def toBCBLtoRBLWallet = prop.getProperty("deposittoBCBLtoRBLWallet")
        def fromBCBLtoFSIBLWallet = prop.getProperty("depositfromBCBLtoFSIBLWallet")
        def toBCBLtoFSIBLWallet = prop.getProperty("deposittoBCBLtoFSIBLWallet")
        def fromBCBLtoJBLWallet = prop.getProperty("depositfromBCBLtoJBLWallet")
        def toBCBLtoJBLWallet = prop.getProperty("deposittoBCBLtoJBLWallet")
        def fromFSIBLtoFSIBLWallet = prop.getProperty("depositfromFSIBLtoFSIBLWallet")
        def toFSIBLtoFSIBLWallet = prop.getProperty("deposittoFSIBLtoFSIBLWallet")
        def fromFSIBLtoRBLWallet = prop.getProperty("depositfromFSIBLtoRBLWallet")
        def toFSIBLtoRBLWallet = prop.getProperty("deposittoFSIBLtoRBLWallet")
        def fromFSIBLtoBCBLWallet = prop.getProperty("depositfromFSIBLtoBCBLWallet")
        def toFSIBLtoBCBLWallet = prop.getProperty("deposittoFSIBLtoBCBLWallet")
        def fromFSIBLtoJBLWallet = prop.getProperty("depositfromFSIBLtoJBLWallet")
        def toFSIBLtoJBLWallet = prop.getProperty("deposittoFSIBLtoJBLWallet")
        def pin = prop.getProperty("pin")
        def p2pAmount = prop.getProperty("depositAmount")
        def auth = prop.getProperty("doPaymentAuth")
        def path = ApiPath.doPaymentPath

        when:
        def requestBody = [
                fromAc  : fromAc,
                pin     : pin,
                toAc    : toAc,
                amount  : amt,
                trnxCode: "10",
                channel : "ussd"
        ]
        println "Deposit request: " + new JsonBuilder(requestBody.responseData).toPrettyString()

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

        println "Deposit response: " + new JsonBuilder(response.responseData).toPrettyString()


        then:
        response.status == 200

        where:
        tag                         | fromAc                             | toAc                    | amt
        "RBL to RBL"                | ["fromRBLtoRBLWallet"]             | ["toRBLtoRBLWallet"]    | ["amt"]
        "RBL to BCBL"               | ["fromRBLtoBCBLWallet"]            | ["toRBLtoBCBLWallet"]   | ["amt"]
        "RBL to JBL"                | ["fromRBLtoJBLLWallet"]            | ["toRBLtoJBLWallet"]    | ["amt"]
        "RBL to FSIBL"              | ["fromRBLtoFSIBLWallet"]           | ["toRBLtoFSIBLWallet"]  | ["amt"]
        "BCBL to RBL"               | ["fromBCBLtoRBLWallet"]            | ["toBCBLtoRBLWallet"]   | ["amt"]
        "BCBL to FSIBL"             | ["fromBCBLtoFSIBLWallet"]          | ["toBCBLtoFSIBLWallet"] | ["amt"]
        "BCBL to BCBL"              | ["fromBCBLtoBCBLWallet"]           | ["toBCBLtoBCBLWallet"]  | ["amt"]
        "BCBL to JBL"               | ["fromBCBLtoJBLWallet"]            | ["toBCBLtoJBLWallet"]   | ["amt"]
        "JBL to JBL"                | ["fromJBLtoJBLWallet"]             | ["toJBLtoJBLWallet"]    | ["amt"]
        "JBL to RBL"                | ["fromJBLtoRBLWallet"]             | ["toJBLtoRBLWallet"]    | ["amt"]
        "JBL to BCBL"               | ["fromJBLtoBCBLWallet"]            | ["toJBLtoBCBLWallet"]   | ["amt"]
        "JBL to FSIBL"              | ["fromJBLtoFSIBLWallet"]           | ["toJBLtoFSIBLWallet"]  | ["amt"]
        "FSIBL to FSIBL"            | ["fromFSIBLtoFSIBLWallet"]         | ["toFSIBLtoFSIBLWallet"]| ["amt"]
        "FSIBL to BCBL"             | ["fromFSIBLtoBCBLWallet"]          | ["toFSIBLtoBCBLWallet"] | ["amt"]
        "FSIBL to RBL"              | ["fromFSIBLtoRBLWallet"]           | ["toFSIBLtoRBLWallet"]  | ["amt"]
        "FSIBL to JBL"              | ["fromFSIBLtoJBLWallet"]           | ["toFSIBLtoJBLWallet"]  | ["amt"]


    }

    @Unroll("P2B from #tag")
    def '3. P2B from'() {
        def status
        HttpResponseDecorator response
        def fromRBLtoRBLWallet = prop.getProperty("p2bFromWalletRblToRBL")
        def toRBLtoRBLWallet = prop.getProperty("p2bToWalletRBLToRBL")
        def fromRBLtoBCBLWallet = prop.getProperty("p2bFromWalletRblToBCB")
        def toRBLtoBCBLWallet = prop.getProperty("p2bToWalletRBLToBCB")
        def fromRBLtoFSIBLWallet = prop.getProperty("p2bFromWalletRblToFS")
        def toRBLtoFSIBLWallet = prop.getProperty("p2bToWalletRblToFS")
        def fromRBLtoJBLWallet = prop.getProperty("p2bFromWalletRblToJBL")
        def toRBLtoJBLWallet = prop.getProperty("p2bToWalletRBLToJBL")
        def fromBCBLtoBCBLWallet = prop.getProperty("p2bfromBCBLtoBCBLWallet")
        def toBCBLtoBCBLWallet = prop.getProperty("p2btoBCBLtoBCBLWallet")
        def fromBCBLtoRBLWallet = prop.getProperty("p2bfromBCBLtoRBLWallet")
        def toBCBLtoRBLWallet = prop.getProperty("p2btoBCBLtoRBLWallet")
        def fromBCBLtoFSIBLWallet = prop.getProperty("p2bfromBCBLtoFSIBLWallet")
        def toBCBLtoFSIBLWallet = prop.getProperty("p2btoBCBLtoFSIBLWallet")
        def fromBCBLtoJBLWallet = prop.getProperty("p2bfromBCBLtoJBLWallet")
        def toBCBLtoJBLWallet = prop.getProperty("p2btoBCBLtoJBLWallet")
        def fromFSIBLtoFSIBLWallet = prop.getProperty("p2bfromFSIBLtoFSIBLWallet")
        def toFSIBLtoFSIBLWallet = prop.getProperty("p2btoFSIBLtoFSIBLWallet")
        def fromFSIBLtoRBLWallet = prop.getProperty("p2bfromFSIBLtoRBLWallet")
        def toFSIBLtoRBLWallet = prop.getProperty("p2btoFSIBLtoRBLWallet")
        def fromFSIBLtoBCBLWallet = prop.getProperty("p2bfromFSIBLtoBCBLWallet")
        def toFSIBLtoBCBLWallet = prop.getProperty("p2btoFSIBLtoBCBLWallet")
        def fromFSIBLtoJBLWallet = prop.getProperty("p2bfromFSIBLtoJBLWallet")
        def toFSIBLtoJBLWallet = prop.getProperty("p2btoFSIBLtoJBLWallet")
        def pin = prop.getProperty("pin")
        def p2pAmount = prop.getProperty("p2bAmount")
        def auth = prop.getProperty("doPaymentAuth")
        def path = ApiPath.doPaymentPath


        when:
        def requestBody = [
                fromAc  : fromAc,
                pin     : pin,
                toAc    : toAc,
                amount  : amt,
                trnxCode: "1202",
                channel : "ussd"
        ]
        println "P2B request: " + new JsonBuilder(requestBody.responseData).toPrettyString()

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

        println "P2B response: " + new JsonBuilder(response.responseData).toPrettyString()


        then:
        response.status == 200

        where:
        tag                         | fromAc                             | toAc                    | amt
        "RBL to RBL"                | ["fromRBLtoRBLWallet"]             | ["toRBLtoRBLWallet"]    | ["amt"]
        "RBL to BCBL"               | ["fromRBLtoBCBLWallet"]            | ["toRBLtoBCBLWallet"]   | ["amt"]
        "RBL to JBL"                | ["fromRBLtoJBLLWallet"]            | ["toRBLtoJBLWallet"]    | ["amt"]
        "RBL to FSIBL"              | ["fromRBLtoFSIBLWallet"]           | ["toRBLtoFSIBLWallet"]  | ["amt"]
        "BCBL to RBL"               | ["fromBCBLtoRBLWallet"]            | ["toBCBLtoRBLWallet"]   | ["amt"]
        "BCBL to FSIBL"             | ["fromBCBLtoFSIBLWallet"]          | ["toBCBLtoFSIBLWallet"] | ["amt"]
        "BCBL to BCBL"              | ["fromBCBLtoBCBLWallet"]           | ["toBCBLtoBCBLWallet"]  | ["amt"]
        "BCBL to JBL"               | ["fromBCBLtoJBLWallet"]            | ["toBCBLtoJBLWallet"]   | ["amt"]
        "JBL to JBL"                | ["fromJBLtoJBLWallet"]             | ["toJBLtoJBLWallet"]    | ["amt"]
        "JBL to RBL"                | ["fromJBLtoRBLWallet"]             | ["toJBLtoRBLWallet"]    | ["amt"]
        "JBL to BCBL"               | ["fromJBLtoBCBLWallet"]            | ["toJBLtoBCBLWallet"]   | ["amt"]
        "JBL to FSIBL"              | ["fromJBLtoFSIBLWallet"]           | ["toJBLtoFSIBLWallet"]  | ["amt"]
        "FSIBL to FSIBL"            | ["fromFSIBLtoFSIBLWallet"]         | ["toFSIBLtoFSIBLWallet"]| ["amt"]
        "FSIBL to BCBL"             | ["fromFSIBLtoBCBLWallet"]          | ["toFSIBLtoBCBLWallet"] | ["amt"]
        "FSIBL to RBL"              | ["fromFSIBLtoRBLWallet"]           | ["toFSIBLtoRBLWallet"]  | ["amt"]
        "FSIBL to JBL"              | ["fromFSIBLtoJBLWallet"]           | ["toFSIBLtoJBLWallet"]  | ["amt"]


    }
    @Unroll("CashOut from #tag")
    def '4. CashOut from'() {
        def status
        HttpResponseDecorator response
        def fromRBLtoRBLWallet = prop.getProperty("cashOutFromWalletRblToRBL")
        def toRBLtoRBLWallet = prop.getProperty("cashOutToWalletRBLToRBL")
        def fromRBLtoBCBLWallet = prop.getProperty("cashOutFromWalletRblToBCB")
        def toRBLtoBCBLWallet = prop.getProperty("cashOutToWalletRBLToBCB")
        def fromRBLtoFSIBLWallet = prop.getProperty("cashOutFromWalletRblToFS")
        def toRBLtoFSIBLWallet = prop.getProperty("cashOutToWalletRblToFS")
        def fromRBLtoJBLWallet = prop.getProperty("cashOutFromWalletRblToJBL")
        def toRBLtoJBLWallet = prop.getProperty("cashOutToWalletRBLToJBL")
        def fromBCBLtoBCBLWallet = prop.getProperty("cashOutfromBCBLtoBCBLWallet")
        def toBCBLtoBCBLWallet = prop.getProperty("cashOuttoBCBLtoBCBLWallet")
        def fromBCBLtoRBLWallet = prop.getProperty("cashOutfromBCBLtoRBLWallet")
        def toBCBLtoRBLWallet = prop.getProperty("cashOuttoBCBLtoRBLWallet")
        def fromBCBLtoFSIBLWallet = prop.getProperty("cashOutfromBCBLtoFSIBLWallet")
        def toBCBLtoFSIBLWallet = prop.getProperty("cashOuttoBCBLtoFSIBLWallet")
        def fromBCBLtoJBLWallet = prop.getProperty("cashOutfromBCBLtoJBLWallet")
        def toBCBLtoJBLWallet = prop.getProperty("cashOuttoBCBLtoJBLWallet")
        def fromFSIBLtoFSIBLWallet = prop.getProperty("cashOutfromFSIBLtoFSIBLWallet")
        def toFSIBLtoFSIBLWallet = prop.getProperty("cashOuttoFSIBLtoFSIBLWallet")
        def fromFSIBLtoRBLWallet = prop.getProperty("cashOutfromFSIBLtoRBLWallet")
        def toFSIBLtoRBLWallet = prop.getProperty("cashOuttoFSIBLtoRBLWallet")
        def fromFSIBLtoBCBLWallet = prop.getProperty("cashOutfromFSIBLtoBCBLWallet")
        def toFSIBLtoBCBLWallet = prop.getProperty("cashOuttoFSIBLtoBCBLWallet")
        def fromFSIBLtoJBLWallet = prop.getProperty("cashOutfromFSIBLtoJBLWallet")
        def toFSIBLtoJBLWallet = prop.getProperty("cashOuttoFSIBLtoJBLWallet")
        def pin = prop.getProperty("pin")
        def p2pAmount = prop.getProperty("cashOutAmount")
        def auth = prop.getProperty("doPaymentAuth")
        def path = ApiPath.doPaymentPath

        when:
        def requestBody = [
                fromAc  : fromAc,
                pin     : pin,
                toAc    : toAc,
                amount  : amt,
                trnxCode: "11",
                channel : "ussd"
        ]
        println "P2B request: " + new JsonBuilder(requestBody.responseData).toPrettyString()

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

        println "P2B response: " + new JsonBuilder(response.responseData).toPrettyString()


        then:
        response.status == 200

        where:
        tag                         | fromAc                             | toAc                    | amt
        "RBL to RBL"                | ["fromRBLtoRBLWallet"]             | ["toRBLtoRBLWallet"]    | ["amt"]
        "RBL to BCBL"               | ["fromRBLtoBCBLWallet"]            | ["toRBLtoBCBLWallet"]   | ["amt"]
        "RBL to JBL"                | ["fromRBLtoJBLLWallet"]            | ["toRBLtoJBLWallet"]    | ["amt"]
        "RBL to FSIBL"              | ["fromRBLtoFSIBLWallet"]           | ["toRBLtoFSIBLWallet"]  | ["amt"]
        "BCBL to RBL"               | ["fromBCBLtoRBLWallet"]            | ["toBCBLtoRBLWallet"]   | ["amt"]
        "BCBL to FSIBL"             | ["fromBCBLtoFSIBLWallet"]          | ["toBCBLtoFSIBLWallet"] | ["amt"]
        "BCBL to BCBL"              | ["fromBCBLtoBCBLWallet"]           | ["toBCBLtoBCBLWallet"]  | ["amt"]
        "BCBL to JBL"               | ["fromBCBLtoJBLWallet"]            | ["toBCBLtoJBLWallet"]   | ["amt"]
        "JBL to JBL"                | ["fromJBLtoJBLWallet"]             | ["toJBLtoJBLWallet"]    | ["amt"]
        "JBL to RBL"                | ["fromJBLtoRBLWallet"]             | ["toJBLtoRBLWallet"]    | ["amt"]
        "JBL to BCBL"               | ["fromJBLtoBCBLWallet"]            | ["toJBLtoBCBLWallet"]   | ["amt"]
        "JBL to FSIBL"              | ["fromJBLtoFSIBLWallet"]           | ["toJBLtoFSIBLWallet"]  | ["amt"]
        "FSIBL to FSIBL"            | ["fromFSIBLtoFSIBLWallet"]         | ["toFSIBLtoFSIBLWallet"]| ["amt"]
        "FSIBL to BCBL"             | ["fromFSIBLtoBCBLWallet"]          | ["toFSIBLtoBCBLWallet"] | ["amt"]
        "FSIBL to RBL"              | ["fromFSIBLtoRBLWallet"]           | ["toFSIBLtoRBLWallet"]  | ["amt"]
        "FSIBL to JBL"              | ["fromFSIBLtoJBLWallet"]           | ["toFSIBLtoJBLWallet"]  | ["amt"]


    }

    @Unroll("A2B from #tag")
    def '5. A2B from'() {
        def status
        HttpResponseDecorator response
        def fromRBLtoRBLWallet = prop.getProperty("a2bFromWalletRblToRBL")
        def toRBLtoRBLWallet = prop.getProperty("a2bToWalletRBLToRBL")
        def fromRBLtoBCBLWallet = prop.getProperty("a2bFromWalletRblToBCB")
        def toRBLtoBCBLWallet = prop.getProperty("a2bToWalletRBLToBCB")
        def fromRBLtoFSIBLWallet = prop.getProperty("a2bFromWalletRblToFS")
        def toRBLtoFSIBLWallet = prop.getProperty("a2bToWalletRblToFS")
        def fromRBLtoJBLWallet = prop.getProperty("a2bFromWalletRblToJBL")
        def toRBLtoJBLWallet = prop.getProperty("a2bToWalletRBLToJBL")
        def fromBCBLtoBCBLWallet = prop.getProperty("a2bfromBCBLtoBCBLWallet")
        def toBCBLtoBCBLWallet = prop.getProperty("a2btoBCBLtoBCBLWallet")
        def fromBCBLtoRBLWallet = prop.getProperty("a2bfromBCBLtoRBLWallet")
        def toBCBLtoRBLWallet = prop.getProperty("a2btoBCBLtoRBLWallet")
        def fromBCBLtoFSIBLWallet = prop.getProperty("a2bfromBCBLtoFSIBLWallet")
        def toBCBLtoFSIBLWallet = prop.getProperty("a2btoBCBLtoFSIBLWallet")
        def fromBCBLtoJBLWallet = prop.getProperty("a2bfromBCBLtoJBLWallet")
        def toBCBLtoJBLWallet = prop.getProperty("a2btoBCBLtoJBLWallet")
        def fromFSIBLtoFSIBLWallet = prop.getProperty("a2bfromFSIBLtoFSIBLWallet")
        def toFSIBLtoFSIBLWallet = prop.getProperty("a2btoFSIBLtoFSIBLWallet")
        def fromFSIBLtoRBLWallet = prop.getProperty("a2bfromFSIBLtoRBLWallet")
        def toFSIBLtoRBLWallet = prop.getProperty("a2btoFSIBLtoRBLWallet")
        def fromFSIBLtoBCBLWallet = prop.getProperty("a2bfromFSIBLtoBCBLWallet")
        def toFSIBLtoBCBLWallet = prop.getProperty("a2btoFSIBLtoBCBLWallet")
        def fromFSIBLtoJBLWallet = prop.getProperty("a2bfromFSIBLtoJBLWallet")
        def toFSIBLtoJBLWallet = prop.getProperty("a2btoFSIBLtoJBLWallet")
        def pin = prop.getProperty("pin")
        def p2pAmount = prop.getProperty("a2bAmount")
        def auth = prop.getProperty("doPaymentAuth")
        def path = ApiPath.doPaymentPath

        when:
        def requestBody = [
                fromAc  : fromAc,
                pin     : pin,
                toAc    : toAc,
                amount  : amt,
                trnxCode: "107",
                channel : "ussd"
        ]
        println "P2B request: " + new JsonBuilder(requestBody.responseData).toPrettyString()

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

        println "P2B response: " + new JsonBuilder(response.responseData).toPrettyString()


        then:
        response.status == 200

        where:
        tag                         | fromAc                             | toAc                    | amt
        "RBL to RBL"                | ["fromRBLtoRBLWallet"]             | ["toRBLtoRBLWallet"]    | ["amt"]
        "RBL to BCBL"               | ["fromRBLtoBCBLWallet"]            | ["toRBLtoBCBLWallet"]   | ["amt"]
        "RBL to JBL"                | ["fromRBLtoJBLLWallet"]            | ["toRBLtoJBLWallet"]    | ["amt"]
        "RBL to FSIBL"              | ["fromRBLtoFSIBLWallet"]           | ["toRBLtoFSIBLWallet"]  | ["amt"]
        "BCBL to RBL"               | ["fromBCBLtoRBLWallet"]            | ["toBCBLtoRBLWallet"]   | ["amt"]
        "BCBL to FSIBL"             | ["fromBCBLtoFSIBLWallet"]          | ["toBCBLtoFSIBLWallet"] | ["amt"]
        "BCBL to BCBL"              | ["fromBCBLtoBCBLWallet"]           | ["toBCBLtoBCBLWallet"]  | ["amt"]
        "BCBL to JBL"               | ["fromBCBLtoJBLWallet"]            | ["toBCBLtoJBLWallet"]   | ["amt"]
        "JBL to JBL"                | ["fromJBLtoJBLWallet"]             | ["toJBLtoJBLWallet"]    | ["amt"]
        "JBL to RBL"                | ["fromJBLtoRBLWallet"]             | ["toJBLtoRBLWallet"]    | ["amt"]
        "JBL to BCBL"               | ["fromJBLtoBCBLWallet"]            | ["toJBLtoBCBLWallet"]   | ["amt"]
        "JBL to FSIBL"              | ["fromJBLtoFSIBLWallet"]           | ["toJBLtoFSIBLWallet"]  | ["amt"]
        "FSIBL to FSIBL"            | ["fromFSIBLtoFSIBLWallet"]         | ["toFSIBLtoFSIBLWallet"]| ["amt"]
        "FSIBL to BCBL"             | ["fromFSIBLtoBCBLWallet"]          | ["toFSIBLtoBCBLWallet"] | ["amt"]
        "FSIBL to RBL"              | ["fromFSIBLtoRBLWallet"]           | ["toFSIBLtoRBLWallet"]  | ["amt"]
        "FSIBL to JBL"              | ["fromFSIBLtoJBLWallet"]           | ["toFSIBLtoJBLWallet"]  | ["amt"]


    }

    @Unroll("M2M from #tag")
    def '6. M2M from'() {
        def status
        HttpResponseDecorator response
        def fromRBLtoRBLWallet = prop.getProperty("m2mFromWalletRblToRBL")
        def toRBLtoRBLWallet = prop.getProperty("m2mToWalletRBLToRBL")
        def fromRBLtoBCBLWallet = prop.getProperty("m2mFromWalletRblToBCB")
        def toRBLtoBCBLWallet = prop.getProperty("m2mToWalletRBLToBCB")
        def fromRBLtoFSIBLWallet = prop.getProperty("m2mFromWalletRblToFS")
        def toRBLtoFSIBLWallet = prop.getProperty("m2mToWalletRblToFS")
        def fromRBLtoJBLWallet = prop.getProperty("m2mFromWalletRblToJBL")
        def toRBLtoJBLWallet = prop.getProperty("m2mToWalletRBLToJBL")
        def fromBCBLtoBCBLWallet = prop.getProperty("m2mfromBCBLtoBCBLWallet")
        def toBCBLtoBCBLWallet = prop.getProperty("m2mtoBCBLtoBCBLWallet")
        def fromBCBLtoRBLWallet = prop.getProperty("m2mfromBCBLtoRBLWallet")
        def toBCBLtoRBLWallet = prop.getProperty("m2mtoBCBLtoRBLWallet")
        def fromBCBLtoFSIBLWallet = prop.getProperty("m2mfromBCBLtoFSIBLWallet")
        def toBCBLtoFSIBLWallet = prop.getProperty("m2mtoBCBLtoFSIBLWallet")
        def fromBCBLtoJBLWallet = prop.getProperty("m2mfromBCBLtoJBLWallet")
        def toBCBLtoJBLWallet = prop.getProperty("m2mtoBCBLtoJBLWallet")
        def fromFSIBLtoFSIBLWallet = prop.getProperty("m2mfromFSIBLtoFSIBLWallet")
        def toFSIBLtoFSIBLWallet = prop.getProperty("m2mtoFSIBLtoFSIBLWallet")
        def fromFSIBLtoRBLWallet = prop.getProperty("m2mfromFSIBLtoRBLWallet")
        def toFSIBLtoRBLWallet = prop.getProperty("m2mtoFSIBLtoRBLWallet")
        def fromFSIBLtoBCBLWallet = prop.getProperty("m2mfromFSIBLtoBCBLWallet")
        def toFSIBLtoBCBLWallet = prop.getProperty("m2mtoFSIBLtoBCBLWallet")
        def fromFSIBLtoJBLWallet = prop.getProperty("m2mfromFSIBLtoJBLWallet")
        def toFSIBLtoJBLWallet = prop.getProperty("m2mtoFSIBLtoJBLWallet")
        def pin = prop.getProperty("pin")
        def p2pAmount = prop.getProperty("m2mAmount")
        def auth = prop.getProperty("doPaymentAuth")
        def path = ApiPath.doPaymentPath


        when:
        def requestBody = [
                fromAc  : fromAc,
                pin     : pin,
                toAc    : toAc,
                amount  : amt,
                trnxCode: "1601",
                channel : "ussd"
        ]
        println "P2B request: " + new JsonBuilder(requestBody.responseData).toPrettyString()

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

        println "P2B response: " + new JsonBuilder(response.responseData).toPrettyString()


        then:
        response.status == 200

        where:
        tag                         | fromAc                             | toAc                    | amt
        "RBL to RBL"                | ["fromRBLtoRBLWallet"]             | ["toRBLtoRBLWallet"]    | ["amt"]
        "RBL to BCBL"               | ["fromRBLtoBCBLWallet"]            | ["toRBLtoBCBLWallet"]   | ["amt"]
        "RBL to JBL"                | ["fromRBLtoJBLLWallet"]            | ["toRBLtoJBLWallet"]    | ["amt"]
        "RBL to FSIBL"              | ["fromRBLtoFSIBLWallet"]           | ["toRBLtoFSIBLWallet"]  | ["amt"]
        "BCBL to RBL"               | ["fromBCBLtoRBLWallet"]            | ["toBCBLtoRBLWallet"]   | ["amt"]
        "BCBL to FSIBL"             | ["fromBCBLtoFSIBLWallet"]          | ["toBCBLtoFSIBLWallet"] | ["amt"]
        "BCBL to BCBL"              | ["fromBCBLtoBCBLWallet"]           | ["toBCBLtoBCBLWallet"]  | ["amt"]
        "BCBL to JBL"               | ["fromBCBLtoJBLWallet"]            | ["toBCBLtoJBLWallet"]   | ["amt"]
        "JBL to JBL"                | ["fromJBLtoJBLWallet"]             | ["toJBLtoJBLWallet"]    | ["amt"]
        "JBL to RBL"                | ["fromJBLtoRBLWallet"]             | ["toJBLtoRBLWallet"]    | ["amt"]
        "JBL to BCBL"               | ["fromJBLtoBCBLWallet"]            | ["toJBLtoBCBLWallet"]   | ["amt"]
        "JBL to FSIBL"              | ["fromJBLtoFSIBLWallet"]           | ["toJBLtoFSIBLWallet"]  | ["amt"]
        "FSIBL to FSIBL"            | ["fromFSIBLtoFSIBLWallet"]         | ["toFSIBLtoFSIBLWallet"]| ["amt"]
        "FSIBL to BCBL"             | ["fromFSIBLtoBCBLWallet"]          | ["toFSIBLtoBCBLWallet"] | ["amt"]
        "FSIBL to RBL"              | ["fromFSIBLtoRBLWallet"]           | ["toFSIBLtoRBLWallet"]  | ["amt"]
        "FSIBL to JBL"              | ["fromFSIBLtoJBLWallet"]           | ["toFSIBLtoJBLWallet"]  | ["amt"]


    }

    @Unroll("Merchant Cash-out from #tag")
    def '7. Merchant Cash-out from'() {
        def status
        HttpResponseDecorator response
        def fromRBLtoRBLWallet = prop.getProperty("merchantCashOutFromWalletRblToRBL")
        def toRBLtoRBLWallet = prop.getProperty("merchantCashOutToWalletRBLToRBL")
        def fromRBLtoBCBLWallet = prop.getProperty("merchantCashOutFromWalletRblToBCB")
        def toRBLtoBCBLWallet = prop.getProperty("merchantCashOutToWalletRBLToBCB")
        def fromRBLtoFSIBLWallet = prop.getProperty("merchantCashOutFromWalletRblToFS")
        def toRBLtoFSIBLWallet = prop.getProperty("merchantCashOutToWalletRblToFS")
        def fromRBLtoJBLWallet = prop.getProperty("merchantCashOutFromWalletRblToJBL")
        def toRBLtoJBLWallet = prop.getProperty("merchantCashOutToWalletRBLToJBL")
        def fromBCBLtoBCBLWallet = prop.getProperty("merchantCashOutfromBCBLtoBCBLWallet")
        def toBCBLtoBCBLWallet = prop.getProperty("merchantCashOuttoBCBLtoBCBLWallet")
        def fromBCBLtoRBLWallet = prop.getProperty("merchantCashOutfromBCBLtoRBLWallet")
        def toBCBLtoRBLWallet = prop.getProperty("merchantCashOuttoBCBLtoRBLWallet")
        def fromBCBLtoFSIBLWallet = prop.getProperty("merchantCashOutfromBCBLtoFSIBLWallet")
        def toBCBLtoFSIBLWallet = prop.getProperty("merchantCashOuttoBCBLtoFSIBLWallet")
        def fromBCBLtoJBLWallet = prop.getProperty("merchantCashOutfromBCBLtoJBLWallet")
        def toBCBLtoJBLWallet = prop.getProperty("merchantCashOuttoBCBLtoJBLWallet")
        def fromFSIBLtoFSIBLWallet = prop.getProperty("merchantCashOutfromFSIBLtoFSIBLWallet")
        def toFSIBLtoFSIBLWallet = prop.getProperty("merchantCashOuttoFSIBLtoFSIBLWallet")
        def fromFSIBLtoRBLWallet = prop.getProperty("merchantCashOutfromFSIBLtoRBLWallet")
        def toFSIBLtoRBLWallet = prop.getProperty("merchantCashOuttoFSIBLtoRBLWallet")
        def fromFSIBLtoBCBLWallet = prop.getProperty("merchantCashOutfromFSIBLtoBCBLWallet")
        def toFSIBLtoBCBLWallet = prop.getProperty("merchantCashOuttoFSIBLtoBCBLWallet")
        def fromFSIBLtoJBLWallet = prop.getProperty("merchantCashOutfromFSIBLtoJBLWallet")
        def toFSIBLtoJBLWallet = prop.getProperty("merchantCashOuttoFSIBLtoJBLWallet")
        def pin = prop.getProperty("pin")
        def p2pAmount = prop.getProperty("merchantCashOutAmount")
        def auth = prop.getProperty("doPaymentAuth")
        def path = ApiPath.doPaymentPath


        when:
        def requestBody = [
                fromAc  : fromAc,
                pin     : pin,
                toAc    : toAc,
                amount  : amt,
                trnxCode: "1208",
                channel : "ussd"
        ]
        println "P2B request: " + new JsonBuilder(requestBody.responseData).toPrettyString()

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

        println "P2B response: " + new JsonBuilder(response.responseData).toPrettyString()


        then:
        response.status == 200

        where:
        tag                         | fromAc                             | toAc                    | amt
        "RBL to RBL"                | ["fromRBLtoRBLWallet"]             | ["toRBLtoRBLWallet"]    | ["amt"]
        "RBL to BCBL"               | ["fromRBLtoBCBLWallet"]            | ["toRBLtoBCBLWallet"]   | ["amt"]
        "RBL to JBL"                | ["fromRBLtoJBLLWallet"]            | ["toRBLtoJBLWallet"]    | ["amt"]
        "RBL to FSIBL"              | ["fromRBLtoFSIBLWallet"]           | ["toRBLtoFSIBLWallet"]  | ["amt"]
        "BCBL to RBL"               | ["fromBCBLtoRBLWallet"]            | ["toBCBLtoRBLWallet"]   | ["amt"]
        "BCBL to FSIBL"             | ["fromBCBLtoFSIBLWallet"]          | ["toBCBLtoFSIBLWallet"] | ["amt"]
        "BCBL to BCBL"              | ["fromBCBLtoBCBLWallet"]           | ["toBCBLtoBCBLWallet"]  | ["amt"]
        "BCBL to JBL"               | ["fromBCBLtoJBLWallet"]            | ["toBCBLtoJBLWallet"]   | ["amt"]
        "JBL to JBL"                | ["fromJBLtoJBLWallet"]             | ["toJBLtoJBLWallet"]    | ["amt"]
        "JBL to RBL"                | ["fromJBLtoRBLWallet"]             | ["toJBLtoRBLWallet"]    | ["amt"]
        "JBL to BCBL"               | ["fromJBLtoBCBLWallet"]            | ["toJBLtoBCBLWallet"]   | ["amt"]
        "JBL to FSIBL"              | ["fromJBLtoFSIBLWallet"]           | ["toJBLtoFSIBLWallet"]  | ["amt"]
        "FSIBL to FSIBL"            | ["fromFSIBLtoFSIBLWallet"]         | ["toFSIBLtoFSIBLWallet"]| ["amt"]
        "FSIBL to BCBL"             | ["fromFSIBLtoBCBLWallet"]          | ["toFSIBLtoBCBLWallet"] | ["amt"]
        "FSIBL to RBL"              | ["fromFSIBLtoRBLWallet"]           | ["toFSIBLtoRBLWallet"]  | ["amt"]
        "FSIBL to JBL"              | ["fromFSIBLtoJBLWallet"]           | ["toFSIBLtoJBLWallet"]  | ["amt"]

    }

    @Unroll("Bulk disburse from #tag")
    def '8. Bulk disburse from'() {
        def status
        HttpResponseDecorator response
        def fromRBLtoRBLWallet = prop.getProperty("bulkDisburseFromWalletRblToRBL")
        def toRBLtoRBLWallet = prop.getProperty("bulkDisburseToWalletRBLToRBL")
        def fromRBLtoBCBLWallet = prop.getProperty("bulkDisburseFromWalletRblToBCB")
        def toRBLtoBCBLWallet = prop.getProperty("bulkDisburseToWalletRBLToBCB")
        def fromRBLtoFSIBLWallet = prop.getProperty("bulkDisburseFromWalletRblToFS")
        def toRBLtoFSIBLWallet = prop.getProperty("bulkDisburseToWalletRblToFS")
        def fromRBLtoJBLWallet = prop.getProperty("bulkDisburseFromWalletRblToJBL")
        def toRBLtoJBLWallet = prop.getProperty("bulkDisburseToWalletRBLToJBL")
        def fromBCBLtoBCBLWallet = prop.getProperty("bulkDisbursefromBCBLtoBCBLWallet")
        def toBCBLtoBCBLWallet = prop.getProperty("bulkDisbursetoBCBLtoBCBLWallet")
        def fromBCBLtoRBLWallet = prop.getProperty("bulkDisbursefromBCBLtoRBLWallet")
        def toBCBLtoRBLWallet = prop.getProperty("bulkDisbursetoBCBLtoRBLWallet")
        def fromBCBLtoFSIBLWallet = prop.getProperty("bulkDisbursefromBCBLtoFSIBLWallet")
        def toBCBLtoFSIBLWallet = prop.getProperty("bulkDisbursetoBCBLtoFSIBLWallet")
        def fromBCBLtoJBLWallet = prop.getProperty("bulkDisbursefromBCBLtoJBLWallet")
        def toBCBLtoJBLWallet = prop.getProperty("bulkDisbursetoBCBLtoJBLWallet")
        def fromFSIBLtoFSIBLWallet = prop.getProperty("bulkDisbursefromFSIBLtoFSIBLWallet")
        def toFSIBLtoFSIBLWallet = prop.getProperty("bulkDisbursetoFSIBLtoFSIBLWallet")
        def fromFSIBLtoRBLWallet = prop.getProperty("bulkDisbursefromFSIBLtoRBLWallet")
        def toFSIBLtoRBLWallet = prop.getProperty("bulkDisbursetoFSIBLtoRBLWallet")
        def fromFSIBLtoBCBLWallet = prop.getProperty("bulkDisbursefromFSIBLtoBCBLWallet")
        def toFSIBLtoBCBLWallet = prop.getProperty("bulkDisbursetoFSIBLtoBCBLWallet")
        def fromFSIBLtoJBLWallet = prop.getProperty("bulkDisbursefromFSIBLtoJBLWallet")
        def toFSIBLtoJBLWallet = prop.getProperty("bulkDisbursetoFSIBLtoJBLWallet")
        def pin = prop.getProperty("pin")
        def amt1 = prop.getProperty("bulkDisburseAmount")
        def auth = prop.getProperty("doPaymentAuth")
        def path = ApiPath.bulkDisbursePath

        when:
        def requestBody = [
                requests: [
                        [
                                amount            : amt,
                                fromAc            : fromAc,
                                toAc              : toAc,
                                isSingleWithdrawal: false,
                                trnxCode          : 106,
                                pin               : pin,
                                disbursementRate  : 0,
                                channel           : "WEB",
                                note              : "Intra",
                                withdrawalFeesRate: 1,
                                disableSMSSending : true
                        ]

                ]
        ]
        println "Bulk disburse request: " + new JsonBuilder(requestBody.responseData).toPrettyString()

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

        println "Bulk Disburse response: " + new JsonBuilder(response.responseData).toPrettyString()


        then:
        response.status == 200

        where:
        tag                         | fromAc                             | toAc                    | amt
        "RBL to RBL"                | ["fromRBLtoRBLWallet"]             | ["toRBLtoRBLWallet"]    | ["amt1"]
        "RBL to BCBL"               | ["fromRBLtoBCBLWallet"]            | ["toRBLtoBCBLWallet"]   | ["amt1"]
        "RBL to JBL"                | ["fromRBLtoJBLLWallet"]            | ["toRBLtoJBLWallet"]    | ["amt1"]
        "RBL to FSIBL"              | ["fromRBLtoFSIBLWallet"]           | ["toRBLtoFSIBLWallet"]  | ["amt1"]
        "BCBL to RBL"               | ["fromBCBLtoRBLWallet"]            | ["toBCBLtoRBLWallet"]   | ["amt1"]
        "BCBL to FSIBL"             | ["fromBCBLtoFSIBLWallet"]          | ["toBCBLtoFSIBLWallet"] | ["amt1"]
        "BCBL to BCBL"              | ["fromBCBLtoBCBLWallet"]           | ["toBCBLtoBCBLWallet"]  | ["amt1"]
        "BCBL to JBL"               | ["fromBCBLtoJBLWallet"]            | ["toBCBLtoJBLWallet"]   | ["amt1"]
        "JBL to JBL"                | ["fromJBLtoJBLWallet"]             | ["toJBLtoJBLWallet"]    | ["amt1"]
        "JBL to RBL"                | ["fromJBLtoRBLWallet"]             | ["toJBLtoRBLWallet"]    | ["amt1"]
        "JBL to BCBL"               | ["fromJBLtoBCBLWallet"]            | ["toJBLtoBCBLWallet"]   | ["amt1"]
        "JBL to FSIBL"              | ["fromJBLtoFSIBLWallet"]           | ["toJBLtoFSIBLWallet"]  | ["amt1"]
        "FSIBL to FSIBL"            | ["fromFSIBLtoFSIBLWallet"]         | ["toFSIBLtoFSIBLWallet"]| ["amt1"]
        "FSIBL to BCBL"             | ["fromFSIBLtoBCBLWallet"]          | ["toFSIBLtoBCBLWallet"] | ["amt1"]
        "FSIBL to RBL"              | ["fromFSIBLtoRBLWallet"]           | ["toFSIBLtoRBLWallet"]  | ["amt1"]
        "FSIBL to JBL"              | ["fromFSIBLtoJBLWallet"]           | ["toFSIBLtoJBLWallet"]  | ["amt1"]

    }

    @Unroll("Bank Cash-in from #tag")
    def '9. Bank Cash-in from'() {
        HttpResponseDecorator response
        def pin = prop.getProperty("pin")
        def auth = prop.getProperty("doPaymentAuth")
        def toWallet = prop.getProperty("toWallet")
        def path = ApiPath.doPaymentPath


        when:
        def requestBody = [
                fromAc           : "",
                pin              : "2468",
                toAc             : toAc,
                amount           : amt,
                trnxCode         : "104",
                channel          : "ussd",
                disableSMSSending: true

        ]
        println "request: " + new JsonBuilder(requestBody.responseData).toPrettyString()

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

        println "response: " + new JsonBuilder(response.responseData).toPrettyString()

        d1=response.responseData.trnxId
        d2=response.responseData.toAccount
        println(d1)

        then:
        response.status == 200

        where:
        tag                     | toAc                      | amt
        "RBL to RBL"            | "016314347558"                        | "1"
        "FSIBL to FSIBL"        | "016314347556"                        | "1"
        "JBL to JBL"            | "019074766411"                        | "1"
        "BCBL to BCBL"          | "016314347515"                        | "1"

    }

    @Unroll("Reverse from #tag")
    def '10. Reverse from'() {
        HttpResponseDecorator response
        def pin = prop.getProperty("pin")
        def auth = prop.getProperty("doPaymentAuth")
        def toWallet = prop.getProperty("toWallet")
        def path = ApiPath.reversePath


        when:
        def requestBody = [
                txnId            : txnID,
                fromAc           : "",
                toAc             : toAc,
                amount           : "1"
        ]
        println "request: " + new JsonBuilder(requestBody.responseData).toPrettyString()

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

        println "response: " + new JsonBuilder(response.responseData).toPrettyString()

        then:
        response.status == 200
        response.responseData.reversalStatus=="SUCCESS"
        where:
        tag                     | toAc                      | txnID
        "RBL to RBL"            | d2                        | d1

    }

    @Unroll("Distributor Cash-in to #tag")
    def '11. Distributor Cash-in to'() {
        def status
        HttpResponseDecorator response
        def pin = prop.getProperty("pin")
        def p2pAmount = prop.getProperty("disCashOutAmount")
        def auth = prop.getProperty("doPaymentAuth")
        def path = ApiPath.doPaymentPath


        when:
        def requestBody = [
                fromAc           : fromAc,
                pin              : pin,
                toAc             : toAc,
                amount           : amt,
                trnxCode         : "1203",
                channel          : "ussd",
                disableSMSSending: true
        ]
        println "P2B request: " + new JsonBuilder(requestBody.responseData).toPrettyString()

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

        println "P2B response: " + new JsonBuilder(response.responseData).toPrettyString()


        then:
        response.status == 200

        where:
        tag                         | fromAc                             | toAc                    | amt
        "RBL to RBL"                | ["fromRBLtoRBLWallet"]             | ["toRBLtoRBLWallet"]    | ["amt"]
        "RBL to BCBL"               | ["fromRBLtoBCBLWallet"]            | ["toRBLtoBCBLWallet"]   | ["amt"]
        "RBL to JBL"                | ["fromRBLtoJBLLWallet"]            | ["toRBLtoJBLWallet"]    | ["amt"]
        "RBL to FSIBL"              | ["fromRBLtoFSIBLWallet"]           | ["toRBLtoFSIBLWallet"]  | ["amt"]
        "BCBL to RBL"               | ["fromBCBLtoRBLWallet"]            | ["toBCBLtoRBLWallet"]   | ["amt"]
        "BCBL to FSIBL"             | ["fromBCBLtoFSIBLWallet"]          | ["toBCBLtoFSIBLWallet"] | ["amt"]
        "BCBL to BCBL"              | ["fromBCBLtoBCBLWallet"]           | ["toBCBLtoBCBLWallet"]  | ["amt"]
        "BCBL to JBL"               | ["fromBCBLtoJBLWallet"]            | ["toBCBLtoJBLWallet"]   | ["amt"]
        "JBL to JBL"                | ["fromJBLtoJBLWallet"]             | ["toJBLtoJBLWallet"]    | ["amt"]
        "JBL to RBL"                | ["fromJBLtoRBLWallet"]             | ["toJBLtoRBLWallet"]    | ["amt"]
        "JBL to BCBL"               | ["fromJBLtoBCBLWallet"]            | ["toJBLtoBCBLWallet"]   | ["amt"]
        "JBL to FSIBL"              | ["fromJBLtoFSIBLWallet"]           | ["toJBLtoFSIBLWallet"]  | ["amt"]
        "FSIBL to FSIBL"            | ["fromFSIBLtoFSIBLWallet"]         | ["toFSIBLtoFSIBLWallet"]| ["amt"]
        "FSIBL to BCBL"             | ["fromFSIBLtoBCBLWallet"]          | ["toFSIBLtoBCBLWallet"] | ["amt"]
        "FSIBL to RBL"              | ["fromFSIBLtoRBLWallet"]           | ["toFSIBLtoRBLWallet"]  | ["amt"]
        "FSIBL to JBL"              | ["fromFSIBLtoJBLWallet"]           | ["toFSIBLtoJBLWallet"]  | ["amt"]

    }

    @Unroll("Distributor Cash-out from #tag")
    def '12. Distributor Cash-out from'() {
        def status
        HttpResponseDecorator response
        def pin = prop.getProperty("pin")
        def p2pAmount = prop.getProperty("disCashOutAmount")
        def auth = prop.getProperty("doPaymentAuth")
        def path = ApiPath.doPaymentPath


        when:
        def requestBody = [
                fromAc           : fromAc,
                pin              : pin,
                toAc             : toAc,
                amount           : amt,
                trnxCode         : "1204",
                channel          : "ussd",
                disableSMSSending: true
        ]
        println "P2B request: " + new JsonBuilder(requestBody.responseData).toPrettyString()

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

        println "P2B response: " + new JsonBuilder(response.responseData).toPrettyString()


        then:
        response.status == 200

        where:
        tag                         | fromAc                             | toAc                    | amt
        "RBL to RBL"                | ["fromRBLtoRBLWallet"]             | ["toRBLtoRBLWallet"]    | ["amt"]
        "RBL to BCBL"               | ["fromRBLtoBCBLWallet"]            | ["toRBLtoBCBLWallet"]   | ["amt"]
        "RBL to JBL"                | ["fromRBLtoJBLLWallet"]            | ["toRBLtoJBLWallet"]    | ["amt"]
        "RBL to FSIBL"              | ["fromRBLtoFSIBLWallet"]           | ["toRBLtoFSIBLWallet"]  | ["amt"]
        "BCBL to RBL"               | ["fromBCBLtoRBLWallet"]            | ["toBCBLtoRBLWallet"]   | ["amt"]
        "BCBL to FSIBL"             | ["fromBCBLtoFSIBLWallet"]          | ["toBCBLtoFSIBLWallet"] | ["amt"]
        "BCBL to BCBL"              | ["fromBCBLtoBCBLWallet"]           | ["toBCBLtoBCBLWallet"]  | ["amt"]
        "BCBL to JBL"               | ["fromBCBLtoJBLWallet"]            | ["toBCBLtoJBLWallet"]   | ["amt"]
        "JBL to JBL"                | ["fromJBLtoJBLWallet"]             | ["toJBLtoJBLWallet"]    | ["amt"]
        "JBL to RBL"                | ["fromJBLtoRBLWallet"]             | ["toJBLtoRBLWallet"]    | ["amt"]
        "JBL to BCBL"               | ["fromJBLtoBCBLWallet"]            | ["toJBLtoBCBLWallet"]   | ["amt"]
        "JBL to FSIBL"              | ["fromJBLtoFSIBLWallet"]           | ["toJBLtoFSIBLWallet"]  | ["amt"]
        "FSIBL to FSIBL"            | ["fromFSIBLtoFSIBLWallet"]         | ["toFSIBLtoFSIBLWallet"]| ["amt"]
        "FSIBL to BCBL"             | ["fromFSIBLtoBCBLWallet"]          | ["toFSIBLtoBCBLWallet"] | ["amt"]
        "FSIBL to RBL"              | ["fromFSIBLtoRBLWallet"]           | ["toFSIBLtoRBLWallet"]  | ["amt"]
        "FSIBL to JBL"              | ["fromFSIBLtoJBLWallet"]           | ["toFSIBLtoJBLWallet"]  | ["amt"]

    }

    @Unroll("DO Cash-in to #tag")
    def '13. Distributor Cash-in to'() {
        def status
        HttpResponseDecorator response
        def pin = prop.getProperty("pin")
        def p2pAmount = prop.getProperty("disCashOutAmount")
        def auth = prop.getProperty("doPaymentAuth")
        def path = ApiPath.doPaymentPath


        when:
        def requestBody = [
                fromAc           : fromAc,
                pin              : pin,
                toAc             : toAc,
                amount           : amt,
                trnxCode         : "1203",
                channel          : "ussd",
                disableSMSSending: true
        ]
        println "P2B request: " + new JsonBuilder(requestBody.responseData).toPrettyString()

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

        println "P2B response: " + new JsonBuilder(response.responseData).toPrettyString()


        then:
        response.status == 200

        where:
        tag                         | fromAc                             | toAc                    | amt
        "RBL to RBL"                | ["fromRBLtoRBLWallet"]             | ["toRBLtoRBLWallet"]    | ["amt"]
        "RBL to BCBL"               | ["fromRBLtoBCBLWallet"]            | ["toRBLtoBCBLWallet"]   | ["amt"]
        "RBL to JBL"                | ["fromRBLtoJBLLWallet"]            | ["toRBLtoJBLWallet"]    | ["amt"]
        "RBL to FSIBL"              | ["fromRBLtoFSIBLWallet"]           | ["toRBLtoFSIBLWallet"]  | ["amt"]
        "BCBL to RBL"               | ["fromBCBLtoRBLWallet"]            | ["toBCBLtoRBLWallet"]   | ["amt"]
        "BCBL to FSIBL"             | ["fromBCBLtoFSIBLWallet"]          | ["toBCBLtoFSIBLWallet"] | ["amt"]
        "BCBL to BCBL"              | ["fromBCBLtoBCBLWallet"]           | ["toBCBLtoBCBLWallet"]  | ["amt"]
        "BCBL to JBL"               | ["fromBCBLtoJBLWallet"]            | ["toBCBLtoJBLWallet"]   | ["amt"]
        "JBL to JBL"                | ["fromJBLtoJBLWallet"]             | ["toJBLtoJBLWallet"]    | ["amt"]
        "JBL to RBL"                | ["fromJBLtoRBLWallet"]             | ["toJBLtoRBLWallet"]    | ["amt"]
        "JBL to BCBL"               | ["fromJBLtoBCBLWallet"]            | ["toJBLtoBCBLWallet"]   | ["amt"]
        "JBL to FSIBL"              | ["fromJBLtoFSIBLWallet"]           | ["toJBLtoFSIBLWallet"]  | ["amt"]
        "FSIBL to FSIBL"            | ["fromFSIBLtoFSIBLWallet"]         | ["toFSIBLtoFSIBLWallet"]| ["amt"]
        "FSIBL to BCBL"             | ["fromFSIBLtoBCBLWallet"]          | ["toFSIBLtoBCBLWallet"] | ["amt"]
        "FSIBL to RBL"              | ["fromFSIBLtoRBLWallet"]           | ["toFSIBLtoRBLWallet"]  | ["amt"]
        "FSIBL to JBL"              | ["fromFSIBLtoJBLWallet"]           | ["toFSIBLtoJBLWallet"]  | ["amt"]

    }

    @Unroll("DO Cash-out to #tag")
    def '14. DO Cash-out to'() {
        def status
        HttpResponseDecorator response
        def pin = prop.getProperty("pin")
        def p2pAmount = prop.getProperty("disCashOutAmount")
        def auth = prop.getProperty("doPaymentAuth")
        def path = ApiPath.doPaymentPath


        when:
        def requestBody = [
                fromAc           : fromAc,
                pin              : pin,
                toAc             : toAc,
                amount           : amt,
                trnxCode         : "1203",
                channel          : "ussd",
                disableSMSSending: true
        ]
        println "P2B request: " + new JsonBuilder(requestBody.responseData).toPrettyString()

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

        println "P2B response: " + new JsonBuilder(response.responseData).toPrettyString()


        then:
        response.status == 200

        where:
        tag                         | fromAc                             | toAc                    | amt
        "RBL to RBL"                | ["fromRBLtoRBLWallet"]             | ["toRBLtoRBLWallet"]    | ["amt"]
        "RBL to BCBL"               | ["fromRBLtoBCBLWallet"]            | ["toRBLtoBCBLWallet"]   | ["amt"]
        "RBL to JBL"                | ["fromRBLtoJBLLWallet"]            | ["toRBLtoJBLWallet"]    | ["amt"]
        "RBL to FSIBL"              | ["fromRBLtoFSIBLWallet"]           | ["toRBLtoFSIBLWallet"]  | ["amt"]
        "BCBL to RBL"               | ["fromBCBLtoRBLWallet"]            | ["toBCBLtoRBLWallet"]   | ["amt"]
        "BCBL to FSIBL"             | ["fromBCBLtoFSIBLWallet"]          | ["toBCBLtoFSIBLWallet"] | ["amt"]
        "BCBL to BCBL"              | ["fromBCBLtoBCBLWallet"]           | ["toBCBLtoBCBLWallet"]  | ["amt"]
        "BCBL to JBL"               | ["fromBCBLtoJBLWallet"]            | ["toBCBLtoJBLWallet"]   | ["amt"]
        "JBL to JBL"                | ["fromJBLtoJBLWallet"]             | ["toJBLtoJBLWallet"]    | ["amt"]
        "JBL to RBL"                | ["fromJBLtoRBLWallet"]             | ["toJBLtoRBLWallet"]    | ["amt"]
        "JBL to BCBL"               | ["fromJBLtoBCBLWallet"]            | ["toJBLtoBCBLWallet"]   | ["amt"]
        "JBL to FSIBL"              | ["fromJBLtoFSIBLWallet"]           | ["toJBLtoFSIBLWallet"]  | ["amt"]
        "FSIBL to FSIBL"            | ["fromFSIBLtoFSIBLWallet"]         | ["toFSIBLtoFSIBLWallet"]| ["amt"]
        "FSIBL to BCBL"             | ["fromFSIBLtoBCBLWallet"]          | ["toFSIBLtoBCBLWallet"] | ["amt"]
        "FSIBL to RBL"              | ["fromFSIBLtoRBLWallet"]           | ["toFSIBLtoRBLWallet"]  | ["amt"]
        "FSIBL to JBL"              | ["fromFSIBLtoJBLWallet"]           | ["toFSIBLtoJBLWallet"]  | ["amt"]

    }



}
