package rf.product.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rf.product.model.ProductSpec;

/**
 * Created by zhouzheng on 2017/9/12.
 */
@Controller
@RequestMapping("/v0")
public class ProductController {

    @RequestMapping(value = "/product",method = RequestMethod.POST,produces = "application/json;charset=UTF-8",consumes = "application/json;charset=UTF-8")
    @ResponseBody
    public String crateProduct(@RequestBody String test){

        System.out.print(test);

        String result = "SUCCESS";

        return result;
    }

    @RequestMapping("/product/issue")
    @ResponseBody
    public String issueProduct(ProductSpec spec){
        String contractAddress = "";


        return contractAddress;
    }
}
