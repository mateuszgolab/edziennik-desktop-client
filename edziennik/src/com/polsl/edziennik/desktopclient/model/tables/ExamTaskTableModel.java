package com.polsl.edziennik.desktopclient.model.tables;

import com.polsl.edziennik.modelDTO.exam.ExamTaskDTO;

public class ExamTaskTableModel extends TableModel<ExamTaskDTO> {

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
				return data.get(rowIndex).getName();
			case 2:
				return data.get(rowIndex).getType();
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
		columnNames = new String[] { " ", entity.getString("standardName"), entity.getString("type"),
				entity.getString("teacher") };
		classNames = new Class[] { Integer.class, String.class, String.class, String.class };
	}

}
