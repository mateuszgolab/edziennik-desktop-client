package com.polsl.edziennik.desktopclient.model.tables;

import java.util.List;

import com.polsl.edziennik.desktopclient.controller.utils.DateConverter;
import com.polsl.edziennik.modelDTO.happyhours.HappyHoursDTO;

public class HappyHoursTableModel extends TableModel<HappyHoursDTO> {

	private DateConverter dateConverter;

	public HappyHoursTableModel(List<HappyHoursDTO> list) {
		super();
		dateConverter = new DateConverter();
	}

	public HappyHoursTableModel() {
		super();
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
				dateConverter.setDateConverter(data.get(rowIndex).getDateFrom());
				return dateConverter.getTime();
			case 2:
				dateConverter.setDateConverter(data.get(rowIndex).getDateTo());
				return dateConverter.getTime();
			default:
				return null;

			}
		} else
			return null;

	}

	@Override
	public void setColumns() {
		columnNames = new String[] { " ", entity.getString("dateStart"), entity.getString("dateStop") };
		classNames = new Class[] { Integer.class, String.class, String.class };

	}

}
