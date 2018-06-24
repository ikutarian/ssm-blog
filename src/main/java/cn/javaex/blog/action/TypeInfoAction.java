package cn.javaex.blog.action;

import cn.javaex.blog.exception.QingException;
import cn.javaex.blog.service.TypeInfoService;
import cn.javaex.blog.view.Result;
import cn.javaex.blog.view.TypeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("type_info")
public class TypeInfoAction {

    @Autowired
    private TypeInfoService typeInfoService;

    @RequestMapping("list.action")
    public String list(ModelMap map) {
        List<TypeInfo> list = typeInfoService.list();
        map.put("list", list);
        return "admin/type_info/list";
    }

    @RequestMapping("save.json")
    @ResponseBody
    public Result save(@RequestParam("idArr") String[] idArr,
                       @RequestParam("sortArr") String[] sortArr,
                       @RequestParam("nameArr") String[] nameArr) {
        typeInfoService.save(idArr, sortArr, nameArr);
        return Result.success();
    }

    @RequestMapping("delete.json")
    @ResponseBody
    public Result delete(@RequestParam("idArr") String[] idArr) throws QingException {
        typeInfoService.delete(idArr);
        return Result.success();
    }
}
