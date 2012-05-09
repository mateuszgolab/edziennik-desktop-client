package com.polsl.edziennik.desktopclient.model.tables;

import java.util.List;

import com.polsl.edziennik.modelDTO.grade.GradeDTO;
import com.polsl.edziennik.modelDTO.grade.RegularGradeDTO;

public class GradeTableModel extends TableModel<RegularGradeDTO> {

	public GradeTableModel(List<GradeDTO> g) {
		super();
		addGrades(g);
	}

	public GradeTableModel() {
		super();
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
				return data.get(rowIndex).getGrade();
			case 1:
				return data.get(rowIndex).getType();
			default:
				return null;

			}
		} else {
			return null;
		}
	}

	@Override
	public void setColumns() {
		columnNames = new String[] { entity.getString("grade"), entity.getString("gradeType") };
		classNames = new Class[] { Float.class, String.class, Float.class };

	}

	public void addGrades(List<GradeDTO> grades) {
		for (GradeDTO g : grades)
			add((RegularGradeDTO) g);

	}

}
