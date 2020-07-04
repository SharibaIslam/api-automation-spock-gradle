package utils


class Util {
    public static String readConfigData() throws IOException {
        def ip=new Input();
        def result = ip.input()
        return result;
    }

    public Properties readPropData(){
        Properties prop=new Properties()
        def inputSteam
        try {
            def propFileName = "config.properties"
            inputSteam = getClass().getClassLoader().getResourceAsStream(propFileName)

            if (inputSteam != null) {
                prop.load(inputSteam);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

        }catch(Exception e){
            System.out.println("Exception: " + e)
        }finally {
            inputSteam.close()
        }
        return prop
    }

    public static String token() throws Exception {
        /*SecondLoginRegSpec secondLoginRegSpec = new SecondLoginRegSpec()
        String aToken = secondLoginRegSpec.sendHeader()
        System.out.printf("Token is " +aToken)
        return aToken*/

    }

}
