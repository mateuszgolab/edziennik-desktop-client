package com.polsl.edziennik.desktopclient.model.tables;

import java.util.List;

import com.polsl.edziennik.desktopclient.controller.utils.DateConverter;
import com.polsl.edziennik.modelDTO.resource.ReportDTO;

public class ReportTableModel extends TableModel<ReportDTO> {

	private DateConverter dateConverter;

	public ReportTableModel() {

		super();
		dateConverter = new DateConverter();
	}

	public ReportTableModel(List<ReportDTO> r) {

		super(r);
		dateConverter = new DateConverter();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

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
			if (data.get(rowIndex).getLabActivity() == null
					|| data.get(rowIndex).getLabActivity().getExerciseTopic() == null) return null;
			return data.get(rowIndex).getLabActivity().getExerciseTopic().getSubject();
		case 2:
			dateConverter.setDateConverter(data.get(rowIndex).getSentDate());
			return dateConverter.getTime();
		case 3:
			return data.get(rowIndex).getGradeValue();
		case 4:
			return data.get(rowIndex).getPassed();
		default:
			return null;

		}

	}

	@Override
	public void setColumns() {
		columnNames = new String[] { " ", entity.getString("exerciseSubject"), entity.getString("sendDate"),
				entity.getString("grade"), entity.getString("passed") };
		classNames = new Class[] { Integer.class, String.class, String.class, Float.class, Boolean.class };

	}

}
