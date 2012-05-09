package com.polsl.edziennik.desktopclient.model.tables;

import java.util.List;

import com.polsl.edziennik.desktopclient.controller.utils.DateConverter;
import com.polsl.edziennik.modelDTO.activity.ActivityDTO;
import com.polsl.edziennik.modelDTO.attendance.AttendanceDTO;

public class ActivityAttendanceTableModel extends TableModel<AttendanceDTO> {

	private DateConverter dateConverter;

	public ActivityAttendanceTableModel() {

		super();
		dateConverter = new DateConverter();
	}

	public ActivityAttendanceTableModel(List<AttendanceDTO> a) {

		super(a);
		dateConverter = new DateConverter();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (rowIndex > -1 && rowIndex < data.size() && columnIndex > -1 && columnIndex < classNames.length) {

			if (data.get(rowIndex) == null) {
				if (columnIndex == 0)
					return rowIndex + 1;
				else
					return null;
			}
			ActivityDTO activity = (ActivityDTO) data.get(rowIndex).getActivity();
			if (activity == null) {
				if (columnIndex == 0)
					return rowIndex + 1;
				else
					return null;
			}

			switch (columnIndex) {
			case 0:
				return rowIndex + 1;
			case 1:
				dateConverter.setDateConverter(activity.getDateFrom());
				return dateConverter.getTime();
			case 2:
				return activity.getRoom();
			case 3:
				return activity.getTeacher();
			default:
				return null;

			}
		} else {
			return null;
		}
	}

	@Override
	public void setColumns() {

		columnNames = new String[] { " ", entity.getString("date"), entity.getString("classRoom"),
				entity.getString("teacher") };
		classNames = new Class[] { Integer.class, String.class, String.class, String.class };

	}

}
