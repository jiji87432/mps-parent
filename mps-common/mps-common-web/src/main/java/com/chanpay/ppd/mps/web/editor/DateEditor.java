package com.chanpay.ppd.mps.web.editor;

import com.chanpay.ppd.mps.common.utils.DateHelper;

import java.beans.PropertyEditorSupport;

/**
 * @author zhangxd
 */
public class DateEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) {
        setValue(DateHelper.parseDate(text));
    }

}
