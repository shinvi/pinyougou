package com.pinyougou.web.common.aop;

import com.pinyougou.web.common.annotation.Valid;
import com.pinyougou.web.common.exception.ResponseException;
import com.pinyougou.web.common.validator.BaseValidator;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

/**
 * @author 邱长海
 */
@Aspect
public class RequestValidatorAop {

    @Before("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void validPost(JoinPoint point) {
        validImpl(point);
    }

    @Before("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void validGet(JoinPoint point) {
        validImpl(point);
    }

    @Before("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void validRequest(JoinPoint point) {
        validImpl(point);
    }

    @Before("@annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void validPut(JoinPoint point) {
        validImpl(point);
    }

    @Before("@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public void validDelete(JoinPoint point) {
        validImpl(point);
    }

    /**
     * 从方法的所有参数里取出@valid注解,包装成BaseValidator相应子类进行自动验证
     *
     * @param point
     */
    private void validImpl(JoinPoint point) {
        Object[] args = point.getArgs();
        if (args == null || args.length == 0) {
            return;
        }
        Signature signature = point.getSignature();
        Class declaringType = signature.getDeclaringType();
        String methodName = signature.getName();
        try {
            for (Method method : declaringType.getMethods()) {
                if (methodName.equals(method.getName())) {
                    validByAnnotatedElements(method.getParameters(), args);
                    break;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void validByAnnotatedElements(AnnotatedElement[] eles, Object[] eleValues) throws IllegalAccessException {
        if (eles == null) {
            return;
        }
        if (eleValues == null || eleValues.length == 0) {
            return;
        }
        for (int i = 0; i < eles.length; i++) {
            AnnotatedElement ele = eles[i];
            Class clzz = null;
            Object eleValue = null;
            String eleName = null;

            if (ele instanceof Parameter) {
                clzz = ((Parameter) ele).getType();
                eleValue = eleValues[i];
                eleName = ((Parameter) ele).getName();
            } else if (ele instanceof Field) {
                ((Field) ele).setAccessible(true);
                clzz = ((Field) ele).getType();
                eleValue = ((Field) ele).get(eleValues[0]);
                eleName = ((Field) ele).getName();
            }
            if (clzz != null && clzz.getAnnotation(Valid.class) != null) {
                validByAnnotatedElements(clzz.getDeclaredFields(), new Object[]{eleValue});
            } else {
                validByAnnotatedElement(ele, eleValue, eleName);
            }
        }
    }

    private void validByAnnotatedElement(AnnotatedElement annotatedElement, Object eleValue, String eleName) {
        List<BaseValidator> validators = BaseValidator.getValidator(annotatedElement);
        for (BaseValidator validator : validators) {
            if(!validator.verification(eleValue)){
                throw new ResponseException(eleName + validator.getSuffix());
            }
        }
    }
}
