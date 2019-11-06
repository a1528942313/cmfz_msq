package com.baizhi.api;


import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


/**
 * 类描述信息 (通用BaseApi 父类)
 *
 * @author : maoshuqi
 * @date : 2019-10-27 15:32
 * @version: V_1.0.0
 */
@Component
public class BaseApiService {
    
    /**
     * 方法描述: (返回成功)
     *
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author : maoshuqi
     * @date : 2019-10-27 15:32
     * @version: V_1.0.0
     */
    public Map<String, Object> setResultSuccess() {
        return setResult(BaseApiConstants.HTTP_RES_CODE_200, BaseApiConstants.HTTP_RES_CODE_200_VALUE, null);
    }


    /**
     * 方法描述: (返回成功，有参数)
     *
     * @param msg
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author : maoshuqi
     * @date : 2019-10-27 15:32
     * @version: V_1.0.0
     */
    public Map<String, Object> setResultSuccess(String msg) {
        return setResult(BaseApiConstants.HTTP_RES_CODE_200, msg, null);
    }


    /**
     * 方法描述: (返回成功,有参数，有数据)
     *
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author : maoshuqi
     * @date : 2019-10-27 15:32
     * @version: V_1.0.0
     */
    public Map<String, Object> setResultSuccessData(Object data) {
        return setResult(BaseApiConstants.HTTP_RES_CODE_200, BaseApiConstants.HTTP_RES_CODE_200_VALUE, data);
    }


    /**
     * 方法描述: (返回失败)
     *
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author : maoshuqi
     * @date : 2019-10-27 15:32
     * @version: V_1.0.0
     */
    public Map<String, Object> setResultError() {
        return setResult(BaseApiConstants.HTTP_RES_CODE_500, BaseApiConstants.HTTP_RES_CODE_500_VALUE, null);
    }


    /**
     * 方法描述: (返回失败，有参数)
     *
     * @param msg
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author : maoshuqi
     * @date : 2019-10-27 15:32
     * @version: V_1.0.0
     */
    public Map<String, Object> setResultError(String msg) {
        return setResult(BaseApiConstants.HTTP_RES_CODE_500, msg, null);
    }


    /**
     * 方法描述: (参数错误)
     *
     * @param msg
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author : maoshuqi
     * @date : 2019-10-27 15:32
     * @version: V_1.0.0
     */
    public Map<String, Object> setResultParamterError(String msg) {
        return setResult(BaseApiConstants.HTTP_RES_CODE_400, msg, null);
    }

    /**
     * 方法描述: TODO(带分页的结果返回值)
     *
     * @Author buxy
     * @Date 2019/10/21 17:42 
     * @param data
     * @param page
     * @param total
     * @param records 
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @author : maoshuqi
     * @date : 2019-10-27 15:32
     * @version: V_1.0.0
     */
    public Map<String, Object> setResultSuccessDataByPage(Object data, Integer page, Integer total, Integer records) {
        return setResult(BaseApiConstants.HTTP_RES_CODE_200, BaseApiConstants.HTTP_RES_CODE_200_VALUE, data, page, total, records);
    }


    /**
     * 方法描述: (自定义返回 )
     *
     * @param code
     * @param msg
     * @param data
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author : maoshuqi
     * @date : 2019-10-27 15:32
     * @version: V_1.0.0
     */
    public Map<String, Object> setResult(Integer code, String msg, Object data) {
        HashMap<String, Object> result = new HashMap<>();
        result.put(BaseApiConstants.HTTP_RES_CODE_NAME, code);
        result.put(BaseApiConstants.HTTP_RES_CODE_MSG, msg);
        if (data != null) {
            result.put(BaseApiConstants.HTTP_RES_CODE_DATA, data);
        }
        return result;
    }

    /**
     * 方法描述: (自定义返回 根据分页查询结果)
     *
     * @param code    状态码
     * @param msg     携带信息
     * @param data    数据
     * @param page    当前页码
     * @param total   总页数
     * @param records 总条数
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author : maoshuqi
     * @date : 2019-10-27 15:32
     * @version: V_1.0.0
     */

    public Map<String, Object> setResult(Integer code, String msg, Object data, Integer page, Integer total, Integer records) {
        HashMap<String, Object> result = new HashMap<>();
        result.put(BaseApiConstants.HTTP_RES_CODE_NAME, code);
        result.put(BaseApiConstants.HTTP_RES_CODE_MSG, msg);
        result.put(PageConstants.PAGE_PAGE_PAGE, page);
        result.put(PageConstants.PAGE_PAGE_TOTAL, total);
        result.put(PageConstants.PAGE_PAGE_RECORDS, records);
        if (data != null) {
            result.put(PageConstants.PAGE_PAGE_ROWS, data);
        }
        return result;
    }


    /**
     * 方法描述: (封装分页查询RowBounds)
     *
     * @param page
     * @param size
     * @return org.apache.ibatis.session.RowBounds
     * @author : maoshuqi
     * @date : 2019-10-27 15:32
     * @version: V_1.0.0
     */

    public RowBounds getRowBounds(Integer page, Integer size) {
        if (page == null || page == 0) {
            page = 1;
        }
        Integer offset = (page - 1) * size;

        return new RowBounds(offset, size);
    }


    /**
     * 方法描述: (封装Controller中的check()方法)
     *
     * @param i  Service 查询/修改/添加/删除 到的结果数量
     * @param id 操作对象的ID
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author : maoshuqi
     * @date : 2019-10-27 15:32
     * @version: V_1.0.0
     */
    public Map<String, Object> setCheck(Integer i, String id) {
        if (i != 0) {
            return setResultSuccessData(id);
        }
        return setResultError("操作编号为： " + id + "  失败");
    }


}
