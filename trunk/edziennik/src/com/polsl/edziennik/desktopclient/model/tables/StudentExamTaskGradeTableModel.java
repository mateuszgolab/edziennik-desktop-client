package com.polsl.edziennik.desktopclient.model.tables;

import com.polsl.edziennik.modelDTO.exam.ExamTaskDTO;
import com.polsl.edziennik.modelDTO.grade.ExamTaskGradeDTO;

public class StudentExamTaskGradeTableModel extends TableModel<ExamTaskGradeDTO> {

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
				ExamTaskDTO task = data.get(rowIndex).getExamTask();
				if (task == null) return null;
				return task.getName();
			case 2:
				return data.get(rowIndex).getGrade();
			default:
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public void setColumns() {
		columnNames = new String[] { " ", entity.getString("task"), entity.getString("grade") };
		classNames = new Class[] { Integer.class, String.class, Float.class };

	}

}
