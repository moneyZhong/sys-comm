package com.sys.comm.util;

import lombok.Data;
import org.hibernate.validator.HibernateValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.Set;

/**
 * 对参数进行校验
 */
public class ValidParamUtil {

    private static Validator validator = Validation
            .byProvider(HibernateValidator.class).configure().failFast(false).buildValidatorFactory().getValidator();


    /**
     * 入参校验
     */

    public static String valid(Object obj){
        StringBuffer buff=new StringBuffer();
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(obj);
        if(constraintViolations.size() > 0){
            for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
                Path property = constraintViolation.getPropertyPath();
                String name = property.iterator().next().getName();
                String msg=constraintViolation.getMessage();
                buff.append(msg+";");
            }
        }
        if(buff!=null && buff.length()>0){
            return  buff.substring(0, buff.length()-1);
        }
        return null;

    }

    public static void main(String[] args) {
        //https://blog.csdn.net/u014786171/article/details/75050271?locationNum=3&fps=1

        System.out.println(valid(new User()));
        User user = new User();
        user.setAge(100);
        user.setDate("2019-10-23 00:23:23");
        user.setStatus("2");
        user.setOverStatus("NOT_OVER2");
        System.out.println(valid(user));
    }

}
@Data
class User{
    @NotBlank
    private String name;

    @Max(value=90,message = "年龄不能超过90")
    private int age;

    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2} [0-24]{2}:\\d{2}:\\d{2}",message="时间格式不对")
    private String date;

    @Pattern(regexp = "[0123]",message = "状态值不对")
    private String status;

    @Pattern(regexp = "(OVER)|(NOT_OVER)",message = "over状态不对")
    private  String overStatus;
}
