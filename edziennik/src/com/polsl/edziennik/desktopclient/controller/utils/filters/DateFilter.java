package com.polsl.edziennik.desktopclient.controller.utils.filters;

import javax.swing.RowFilter;

import com.polsl.edziennik.desktopclient.model.tables.TableModel;

public abstract class DateFilter extends RowFilter<TableModel<?>, Integer> {

	protected Long min;
	protected Long max;

	public DateFilter(Long min, Long max) {

		this.min = min;
		this.max = max;
	}

	@Override
	public abstract boolean include(javax.swing.RowFilter.Entry<? extends TableModel<?>, ? extends Integer> entry);
}
