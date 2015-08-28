package com.hugo.commons.web;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wangweiguang
 *         ps:WebDataBinder是用来绑定请求参数到指定的属性编辑器，可以继承WebBindingInitializer来实现一个全部controller共享的dataBiner。
 *         也可以在某个单独的contorller里定义一个dataBinder，使用@InitBinder注解就可以实现。
 *         WebDataBinder是用来绑定请求参数到指定的属性编辑器.由于前台传到controller里的值是String类型的，
 *         当往Model里Set这个值的时候，如果set的这个属性是个对象，Spring就会去找到对应的EDITOR进行转换，然后再SET进去
 */

public abstract class BaseController {

    public Logger log = LoggerFactory.getLogger(getClass());

    private String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
    private String FORMAT_SHORT = "yyyy-MM-dd";
    private String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";


    /**
     * 初始化映射格式.
     *
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            public void setAsText(String value) {
                try {
                    if (StringUtils.isNotBlank(value)) {
                        Date d = DateUtils.parseDate(value, FORMAT_LONG, FORMAT_SHORT, FORMAT_FULL);
                        setValue(d);
                    } else {
                        setValue(null);
                    }
                } catch (Exception e) {
                    setValue(null);
                    log.error("", e);
                }
            }
        });

        binder.registerCustomEditor(Integer.class, new PropertyEditorSupport() {
            public void setAsText(String value) {
                try {
                    if (StringUtils.isNotBlank(value)) {
                        setValue(Integer.valueOf(value));
                    } else {
                        setValue(null);
                    }
                } catch (Exception e) {
                    setValue(null);
                    log.error("", e);
                }
            }
        });
        binder.registerCustomEditor(Long.class, new PropertyEditorSupport() {
            public void setAsText(String value) {
                try {
                    if (StringUtils.isNotBlank(value)) {
                        setValue(Long.valueOf(value));
                    } else {
                        setValue(null);
                    }
                } catch (Exception e) {
                    setValue(null);
                    log.error("", e);
                }
            }
        });
        binder.registerCustomEditor(Double.class, new PropertyEditorSupport() {
            public void setAsText(String value) {
                try {
                    if (StringUtils.isNotBlank(value)) {
                        setValue(Double.valueOf(value));
                    } else {
                        setValue(null);
                    }
                } catch (Exception e) {
                    setValue(null);
                    log.error("", e);
                }
            }
        });

        binder.registerCustomEditor(BigDecimal.class,
                new PropertyEditorSupport() {
                    public void setAsText(String value) {
                        try {
                            if (StringUtils.isNotBlank(value)) {
                                setValue(new BigDecimal(value));
                            } else {
                                setValue(null);
                            }
                        } catch (Exception e) {
                            setValue(null);
                            log.error("", e);
                        }
                    }
                });

    }
}
