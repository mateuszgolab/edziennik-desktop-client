package com.polsl.edziennik.desktopclient.controller.utils.filters;

import javax.swing.RowFilter;

import com.polsl.edziennik.desktopclient.model.tables.LabActivityTableModel;
import com.polsl.edziennik.desktopclient.model.tables.TableModel;
import com.polsl.edziennik.modelDTO.activity.LabActivityDTO;
import com.polsl.edziennik.modelDTO.group.GroupDTO;

public class LabActivityFilter extends DateFilter {

	private String group;

	public LabActivityFilter(Long min, Long max, String group) {
		super(min, max);
		this.group = group;

	}

	@Override
	public boolean include(RowFilter.Entry<? extends TableModel<?>, ? extends Integer> entry) {
		LabActivityTableModel model = (LabActivityTableModel) entry.getModel();
		LabActivityDTO activity = model.get(entry.getIdentifier());

		if (activity == null) return false;
		if (activity.getDateFrom() < min || activity.getDateTo() > max) return false;
		if (group != null && group.compareTo("Wszystkie") != 0) {
			GroupDTO g = activity.getGroup();
			if (g != null) if (g.getName().compareTo(group) != 0) return false;
		}

		return true;
	}
}