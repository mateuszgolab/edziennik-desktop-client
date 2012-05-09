package com.polsl.edziennik.desktopclient.model.tables;

import java.util.List;

import com.polsl.edziennik.desktopclient.controller.utils.DateConverter;
import com.polsl.edziennik.modelDTO.activity.ActivityDTO;

public class ActivityTableModel extends TableModel<ActivityDTO> {

	private DateConverter dateConverter;

	public ActivityTableModel() {

		super();
		dateConverter = new DateConverter();
	}

	public ActivityTableModel(List<ActivityDTO> activities) {

		super(activities);
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

			switch (columnIndex) {
			case 0:
				return rowIndex + 1;
			case 1:
				dateConverter.setDateConverter(data.get(rowIndex).getDateFrom());
				return dateConverter.getTime();
			case 2:
				return data.get(rowIndex).getRoom();
			case 3:
				return data.get(rowIndex).getTeacher();
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

	// @Override
	// public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
	// if (rowIndex > -1 && rowIndex < data.size() && columnIndex > -1 &&
	// columnIndex < classNames.length) {
	// switch (columnIndex) {
	//
	// case 1:
	// data.get(rowIndex).setDateFrom((Long) aValue);
	// break;
	// case 2:
	// data.get(rowIndex).setFirstName((String) aValue);
	// break;
	// case 3:
	// data.get(rowIndex).setRoom(((String) aValue);
	// break;
	// case 4:
	// data.get(rowIndex).setTeacher(((String) aValue);
	// break;
	// }
	//
	// //TODO :
	// // Update ActivityDTO(data.get(rowIndex)
	// }
	//
	// }

}
