package com.okayhu.framework.ieasyexcel.read;

import com.alibaba.excel.context.AnalysisContext;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author leslie
 * @date 2021/3/22
 */
public interface ExcelReader<T> {

    Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    void read(List<T> excelDataList, AnalysisContext context);

    default BasedExcelReadModel check(T excelData) {
        BasedExcelReadModel validation = new BasedExcelReadModel();
        Set<ConstraintViolation<T>> validate = VALIDATOR.validate(excelData);
        if (validate != null && !validate.isEmpty()) {
            List<String> failureMsg = validate.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
            validation.setAvailable(false);
            validation.setMsg(String.join(";", failureMsg));
        } else {
            validation.setAvailable(true);
        }
        return validation;
    }
}
