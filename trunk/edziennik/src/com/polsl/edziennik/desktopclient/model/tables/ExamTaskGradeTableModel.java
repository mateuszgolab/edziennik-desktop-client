package com.polsl.edziennik.desktopclient.model.tables;

import java.util.HashMap;
import java.util.Map;

import com.polsl.edziennik.modelDTO.group.GroupDTO;
import com.polsl.edziennik.modelDTO.person.StudentDTO;

public class ExamTaskGradeTableModel extends TableModel<StudentDTO> {

	private Map<Integer, Float> grades;

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (rowIndex > -1 && rowIndex < data.size() && columnIndex > -1 && columnIndex < classNames.length) {
			switch (columnIndex) {
			case 4:
				if (grades == null) grades = new HashMap<Integer, Float>();
				grades.put(data.get(rowIndex).getId(), (Float) aValue);
			default:
			}
		}

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
				return data.get(rowIndex).getFirstName();
			case 2:
				return data.get(rowIndex).getLastName();
			case 3:
				GroupDTO g = data.get(rowIndex).getGroup();
				if (g != null) return g.getName();
			case 4:
				if (grades == null) return null;
				return grades.get(data.get(rowIndex).getId());
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
				entity.getString("group"), entity.getString("grade") };
		classNames = new Class[] { Integer.class, String.class, String.class, String.class, Float.class };

	}

	public void setGrades(Map<Integer, Float> grades) {
		this.grades = grades;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 4) return true;
		return false;
	}

	public Map<Integer, Float> getGrades() {
		return grades;
	}

}
