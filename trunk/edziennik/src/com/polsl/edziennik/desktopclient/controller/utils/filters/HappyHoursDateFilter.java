package com.polsl.edziennik.desktopclient.controller.utils.filters;

import javax.swing.RowFilter;

import com.polsl.edziennik.desktopclient.model.tables.HappyHoursTableModel;
import com.polsl.edziennik.desktopclient.model.tables.TableModel;
import com.polsl.edziennik.modelDTO.happyhours.HappyHoursDTO;

public class HappyHoursDateFilter extends DateFilter {

	public HappyHoursDateFilter(Long min, Long max) {
		super(min, max);

	}

	@Override
	public boolean include(RowFilter.Entry<? extends TableModel<?>, ? extends Integer> entry) {
		HappyHoursTableModel model = (HappyHoursTableModel) entry.getModel();
		HappyHoursDTO hours = model.get(entry.getIdentifier());

		if (hours == null) return false;

		if (hours.getDateFrom() >= min && hours.getDateTo() <= max) return true;

		return false;
	}

}
