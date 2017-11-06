package rf.product.eval;


public class GroovyScript {

    public static String rateTableScript(){
        String script = "/************Rate Table Closure***********/ \n" +
                        " def rateTableScript(){\n" +
                        "    rf.product.ds.RateTableService service = rf.product.conf.AppContext.getBean(rf.product.ds.RateTableService.class);\n" +
                        "    def script = { tableName,conditions -> service.findRateValue(tableName,conditions) }\n" +
                        "    return script\n" +
                        " }\n" +
                        " def RateTable = rateTableScript()\n";
        return script;
    }
}
