package utils

import spock.lang.Specification

class Input extends Specification {

    public String loginAPI
    public String loginValue
    public def inputSteam

    public String getInput() throws Exception {

        try {
            Properties prop=new Properties()
            def propFileName = "config.properties"
            inputSteam = getClass().getClassLoader().getResourceAsStream(propFileName)

            if (inputSteam != null) {
                prop.load(inputSteam);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }
            def pu = prop.getProperty("deviceName")
            def pu1 = prop.getProperty("deviceVersion")
            this.loginAPI=pu.toString()
            this.loginValue=pu1.toString()



        }catch(Exception e){
            System.out.println("Exception: " + e)
        }finally {
            inputSteam.close()
        }
        return [loginAPI,loginValue]



    }



}
