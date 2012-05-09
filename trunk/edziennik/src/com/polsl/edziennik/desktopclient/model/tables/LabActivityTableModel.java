package com.polsl.edziennik.desktopclient.model.tables;

import java.util.List;

import com.polsl.edziennik.desktopclient.controller.utils.DateConverter;
import com.polsl.edziennik.modelDTO.activity.LabActivityDTO;

public class LabActivityTableModel extends TableModel<LabActivityDTO> {

	private DateConverter dateConverter;

	public LabActivityTableModel() {

		super();
		dateConverter = new DateConverter();
	}

	public LabActivityTableModel(List<LabActivityDTO> activities) {

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
				return data.get(rowIndex).getTeacher();

			case 3:
				if (data.get(rowIndex).getExerciseTopic() == null) return null;
				return data.get(rowIndex).getExerciseTopic().getSubject();
			case 4:
				return data.get(rowIndex).getRoom();
			default:
				return null;

			}
		} else {
			return null;
		}
	}

	@Override
	public void setColumns() {
		columnNames = new String[] { " ", entity.getString("date"), entity.getString("teacher"),
				entity.getString("exerciseTopic"), entity.getString("classRoom") };
		classNames = new Class[] { Integer.class, String.class, String.class, String.class, String.class };
	}

}
