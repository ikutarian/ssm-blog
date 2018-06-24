package cn.javaex.blog.view;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Result {

    private static final String CODE_SUCCESS = "000000";
    private static final String CODE_ERROR = "999999";

    /**
     * 状态码 成功 000000 失败 999999
     */
    private String code;
    private String message;
    private Map<String, Object> data = new HashMap<>();

    public static Result success() {
        Result result = new Result();
        result.setCode(CODE_SUCCESS);
        result.setMessage("操作成功");
        return result;
    }

    public static Result error(String message) {
        Result result = new Result();
        result.setCode(CODE_ERROR);
        if (StringUtils.isEmpty(message)) {
            result.setMessage("操作失败");
        } else {
            result.setMessage(message);
        }
        return result;
    }

    public Result add(String key, Object value) {
        getData().put(key, value);
        return this;
    }
}
