package com.polsl.edziennik.desktopclient.controller.utils.filters;

import javax.swing.RowFilter;

import com.polsl.edziennik.desktopclient.model.tables.LabActivityAttendanceTableModel;
import com.polsl.edziennik.desktopclient.model.tables.TableModel;
import com.polsl.edziennik.modelDTO.activity.LabActivityDTO;
import com.polsl.edziennik.modelDTO.attendance.AttendanceDTO;
import com.polsl.edziennik.modelDTO.group.GroupDTO;

public class LabActivityAttendanceFilter extends DateFilter {

	private String group;

	public LabActivityAttendanceFilter(Long min, Long max, String group) {
		super(min, max);
		this.group = group;

	}

	@Override
	public boolean include(RowFilter.Entry<? extends TableModel<?>, ? extends Integer> entry) {
		LabActivityAttendanceTableModel model = (LabActivityAttendanceTableModel) entry.getModel();
		AttendanceDTO attendance = model.get(entry.getIdentifier());

		if (attendance == null) return false;
		LabActivityDTO a = (LabActivityDTO) attendance.getActivity();

		if (a != null && a.getDateFrom() != null && a.getDateTo() != null) {
			if (a.getDateFrom() < min || a.getDateTo() > max) return false;

			if (group != null && group.compareTo("Wszystkie") != 0) {
				GroupDTO g = a.getGroup();
				if (g != null) if (g.getName().compareTo(group) != 0) return false;
			}
		}

		return true;
	}
}
