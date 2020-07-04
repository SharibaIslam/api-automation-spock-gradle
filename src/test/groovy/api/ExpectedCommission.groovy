package api

import groovyx.net.http.RESTClient
import model.User
import spock.lang.Shared
import utils.Util

class ExpectedCommission {
    @Shared
    Util util = new Util()
    Properties prop = util.readPropData()
    Date time = new Date(System.currentTimeMillis())

    def expectedWithdrawCom(User user){
        double calculation
        def amount=prop.getProperty("withdrawAmount")
        double amoun1=amount.toDouble()
        def rate=prop.getProperty("withdrawRate")
        double rate1=rate.toDouble()
        if(amoun1>=500)
            calculation=(double)((amoun1*rate1)/100)
        else
            calculation=5.0
        //def calculation=String.format("%.3f",calculation1)
        println(calculation)
        return calculation

    }
}
