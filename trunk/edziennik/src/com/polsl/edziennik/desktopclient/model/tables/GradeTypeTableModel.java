package com.polsl.edziennik.desktopclient.model.tables;

import java.util.List;

import com.polsl.edziennik.modelDTO.grade.GradeTypeDTO;

public class GradeTypeTableModel extends TableModel<GradeTypeDTO> {

	public GradeTypeTableModel(List<GradeTypeDTO> list) {
		super(list);
	}

	public GradeTypeTableModel() {
		super();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (rowIndex > -1 && rowIndex < data.size() && columnIndex > -1 && columnIndex < classNames.length) {

			if (data.get(rowIndex) == null || columnIndex > 0) return null;

			return data.get(rowIndex).getType();
		} else {
			return null;
		}
	}

	@Override
	public void setColumns() {
		columnNames = new String[] { entity.getString("gradeType") };
		classNames = new Class[] { String.class };

	}

}
