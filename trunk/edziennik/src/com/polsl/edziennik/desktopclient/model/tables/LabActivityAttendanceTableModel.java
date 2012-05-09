package com.polsl.edziennik.desktopclient.model.tables;

import java.util.List;

import com.polsl.edziennik.desktopclient.controller.utils.DateConverter;
import com.polsl.edziennik.modelDTO.activity.LabActivityDTO;
import com.polsl.edziennik.modelDTO.attendance.AttendanceDTO;

public class LabActivityAttendanceTableModel extends TableModel<AttendanceDTO> {

	private DateConverter dateConverter;

	public LabActivityAttendanceTableModel() {

		super();
		dateConverter = new DateConverter();
	}

	public LabActivityAttendanceTableModel(List<AttendanceDTO> a) {

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
			LabActivityDTO activity = (LabActivityDTO) data.get(rowIndex).getActivity();
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
				return activity.getExerciseTopic();
			case 4:
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
		columnNames = new String[] { " ", entity.getString("date"), entity.getString("room"),
				entity.getString("exerciseTopic"), entity.getString("teacher") };
		classNames = new Class[] { Integer.class, String.class, String.class, String.class, String.class };

	}

}
