package com.polsl.edziennik.desktopclient.model.tables;

import com.polsl.edziennik.modelDTO.person.TeacherDTO;

public class TeacherTableModel extends TableModel<TeacherDTO> {

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (rowIndex > -1 && rowIndex < data.size() && columnIndex > -1 && columnIndex < classNames.length) {
			
			if(data.get(rowIndex) == null)
			{
				if(columnIndex == 0)
					return rowIndex + 1;
				else return null;
			}
			
			switch (columnIndex) {
			case 0:
				return rowIndex + 1;
			case 1:
				return data.get(rowIndex).getAcademicTitle();
			case 2:
				return data.get(rowIndex).getFirstName();
			case 3:
				return data.get(rowIndex).getLastName();
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
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (rowIndex > -1 && rowIndex < data.size() && columnIndex > -1 && columnIndex < classNames.length) {
			switch (columnIndex) {

			case 1:
				data.get(rowIndex).setAcademicTitle((String) aValue);
				break;
			case 2:
				data.get(rowIndex).setFirstName((String) aValue);
				break;
			case 3:
				data.get(rowIndex).setLastName((String) aValue);
				break;
			case 4:
				data.get(rowIndex).setRoom((String) aValue);
				break;
			}

			// TODO :
			// Update teacher(data.get(rowIndex)
		}

	}

	@Override
	public void setColumns() {
		columnNames = new String[] { " ", entity.getString("academicTitle"), entity.getString("name"),
				entity.getString("lastName"), entity.getString("room") };
		classNames = new Class[] { Integer.class, String.class, String.class, String.class, String.class };

	}

}
