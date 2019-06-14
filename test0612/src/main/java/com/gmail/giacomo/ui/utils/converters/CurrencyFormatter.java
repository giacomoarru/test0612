package com.gmail.giacomo.ui.utils.converters;

import com.vaadin.flow.templatemodel.ModelEncoder;
import com.gmail.giacomo.ui.dataproviders.DataProviderUtil;
import com.gmail.giacomo.ui.utils.FormattingUtils;

public class CurrencyFormatter implements ModelEncoder<Integer, String> {

	private static final long serialVersionUID = 1L;

	@Override
	public String encode(Integer modelValue) {
		return DataProviderUtil.convertIfNotNull(modelValue, FormattingUtils::formatAsCurrency);
	}

	@Override
	public Integer decode(String presentationValue) {
		throw new UnsupportedOperationException();
	}
}
