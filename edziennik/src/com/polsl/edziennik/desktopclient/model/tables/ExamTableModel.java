package com.polsl.edziennik.desktopclient.model.tables;

import java.util.List;

import com.polsl.edziennik.desktopclient.controller.utils.DateConverter;
import com.polsl.edziennik.modelDTO.exam.ExamDTO;

public class ExamTableModel extends TableModel<ExamDTO> {

	private DateConverter dateConverter;

	public ExamTableModel() {

		super();
		dateConverter = new DateConverter();
	}

	public ExamTableModel(List<ExamDTO> exams) {

		super(exams);
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
				return data.get(rowIndex).getApproach();
			case 2:
				dateConverter.setDateConverter(data.get(rowIndex).getDate());
				return dateConverter.getTime();
			case 3:
				return dateConverter.getDuration(data.get(rowIndex).getDuration());
			case 4:
				return data.get(rowIndex).getPlace();
			default:
				return null;

			}
		} else {
			return null;
		}
	}

	@Override
	public void setColumns() {
		columnNames = new String[] { " ", entity.getString("approach"), entity.getString("date"),
				entity.getString("timeLength"), entity.getString("classRoom") };
		classNames = new Class[] { Integer.class, String.class, String.class, String.class, String.class };
	}

}