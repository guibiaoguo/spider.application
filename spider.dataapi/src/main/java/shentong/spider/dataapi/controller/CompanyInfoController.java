package shentong.spider.dataapi.controller;
import org.springframework.web.bind.annotation.*;


/**
 * Created by ucs_yuananyun on 2016/3/21.
 */
@RestController
@RequestMapping(value = "/company", produces = "application/json;charset=UTF-8")
@SuppressWarnings("all")
public class CompanyInfoController {

    @RequestMapping(value = {"/", "/test"}, method = RequestMethod.GET)
    public Object index() {
        return "你好！";
    }
}
