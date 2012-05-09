package com.polsl.edziennik.desktopclient.model.tables;

import com.polsl.edziennik.modelDTO.grade.GradeDTO;
import com.polsl.edziennik.modelDTO.person.StudentDTO;

public class StudentGradesTableModel extends TableModel<StudentDTO> {

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
			case 3:
				return getGrades(data.get(rowIndex));
			default:
				return null;

			}
		} else {
			return null;
		}
	}

	@Override
	public void setColumns() {
		columnNames = new String[] { " ", entity.getString("name"), entity.getString("lastName"),
				entity.getString("grades") };
		classNames = new Class[] { Integer.class, String.class, String.class, String.class };

	}

	public String getGrades(StudentDTO s) {
		String result = "";

		for (GradeDTO r : s.getGrades())
			if (r.getGrade() != null) result += r.getGrade() + " ";

		return result;
	}

}
