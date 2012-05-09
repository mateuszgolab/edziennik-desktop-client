package com.polsl.edziennik.desktopclient.model.tables;

import java.util.List;

import com.polsl.edziennik.modelDTO.exam.ExamTaskTypeDTO;

public class ExamTaskTypeTableModel extends TableModel<ExamTaskTypeDTO> {

	public ExamTaskTypeTableModel(List<ExamTaskTypeDTO> list) {
		super(list);
	}

	public ExamTaskTypeTableModel() {
		super();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (rowIndex > -1 && rowIndex < data.size() && columnIndex > -1 && columnIndex < classNames.length) {

			if (data.get(rowIndex) == null || columnIndex > 0) return null;

			switch (columnIndex) {

			case 0:
				return data.get(rowIndex).getType();
			case 1:
				return data.get(rowIndex).getWeight();
			default:
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public void setColumns() {
		columnNames = new String[] { entity.getString("taskType"), entity.getString("taskWeight") };
		classNames = new Class[] { String.class, Float.class };

	}

}