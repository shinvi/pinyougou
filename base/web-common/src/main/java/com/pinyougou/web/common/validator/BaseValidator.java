package com.pinyougou.web.common.validator;


import com.pinyougou.common.util.PatternUtils;
import com.pinyougou.web.common.annotation.Email;
import com.pinyougou.web.common.annotation.Phone;
import org.apache.logging.log4j.util.Strings;

import javax.validation.constraints.NotBlank;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.*;

/**
 * @author 邱长海
 */
public abstract class BaseValidator {
    private String prefix;
    private String suffix;

    private static final List<? extends BaseValidator> validators =
            Arrays.asList(new NotBlankValidator(), new PhoneValidator(), new EmailValidator());

    public abstract String getPrefix();

    public abstract String getSuffix();

    public abstract boolean verification(Object obj);

    protected abstract Class<? extends Annotation> getCanHandleAnnotation();

    public static List<BaseValidator> getValidator(AnnotatedElement ele) {
        List<BaseValidator> baseValidators = new ArrayList<>();
        if (ele == null) {
            return baseValidators;
        }
        for (BaseValidator validator : validators) {
            if (ele.getAnnotation(validator.getCanHandleAnnotation()) != null) {
                baseValidators.add(validator);
            }
        }
        return baseValidators;
    }

    private static class NotBlankValidator extends BaseValidator {

        @Override
        public String getPrefix() {
            return "";
        }

        @Override
        public String getSuffix() {
            return "不能为空";
        }

        @Override
        public boolean verification(Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj.getClass().isArray()) {
                return ((Object[]) obj).length > 0;
            }
            if (obj instanceof Collection) {
                return !((Collection) obj).isEmpty();
            }
            return !Strings.isBlank(obj.toString());
        }

        @Override
        protected Class<? extends Annotation> getCanHandleAnnotation() {
            return NotBlank.class;
        }
    }

    private static class PhoneValidator extends BaseValidator {

        @Override
        public String getPrefix() {
            return "";
        }

        @Override
        public String getSuffix() {
            return "错误的手机号格式";
        }

        @Override
        public boolean verification(Object obj) {
            if (obj == null) {
                return true;
            }
            return PatternUtils.checkMobile(obj.toString());
        }

        @Override
        protected Class<? extends Annotation> getCanHandleAnnotation() {
            return Phone.class;
        }
    }

    private static class EmailValidator extends BaseValidator {

        @Override
        public String getPrefix() {
            return "";
        }

        @Override
        public String getSuffix() {
            return "错误的电子邮箱格式";
        }

        @Override
        public boolean verification(Object obj) {
            if (obj == null) {
                return true;
            }
            return PatternUtils.checkEmail(obj.toString());
        }

        @Override
        protected Class<? extends Annotation> getCanHandleAnnotation() {
            return Email.class;
        }
    }

}