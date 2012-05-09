package com.polsl.edziennik.desktopclient.model.tables;

import com.polsl.edziennik.modelDTO.person.StudentDTO;

public class ProjectStudentTableModel extends TableModel<StudentDTO> {
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
				return data.get(rowIndex).getFirstName();
			case 2:
				return data.get(rowIndex).getLastName();
			default:
				return null;

			}
		} else {
			return null;
		}
	}

	@Override
	public void setColumns() {
		columnNames = new String[] { " ", entity.getString("name"), entity.getString("lastName") };
		classNames = new Class[] { Integer.class, String.class, String.class };

	}
}
