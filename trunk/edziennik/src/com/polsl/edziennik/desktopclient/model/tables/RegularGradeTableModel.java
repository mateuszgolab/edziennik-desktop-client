package com.polsl.edziennik.desktopclient.model.tables;

import com.polsl.edziennik.modelDTO.grade.RegularGradeDTO;

public class RegularGradeTableModel extends TableModel<RegularGradeDTO> {

	private boolean edit = true;

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
				if (data.get(rowIndex).getStudent() == null) return null;
				return data.get(rowIndex).getStudent().getFirstName();
			case 2:
				if (data.get(rowIndex).getStudent() == null) return null;
				return data.get(rowIndex).getStudent().getLastName();
			case 3:
				return data.get(rowIndex).getGrade();
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
				if (data.get(rowIndex).getStudent() != null)
					data.get(rowIndex).getStudent().setFirstName((String) aValue);
			case 2:

				if (data.get(rowIndex).getStudent() != null)
					data.get(rowIndex).getStudent().setLastName((String) aValue);
			case 3:
				data.get(rowIndex).setGrade((Float) aValue);
			default:
			}
		}

	}

	@Override
	public void setColumns() {
		columnNames = new String[] { " ", entity.getString("name"), entity.getString("lastName"),
				entity.getString("grade") };
		classNames = new Class[] { Integer.class, String.class, String.class, Float.class };

	}

	public void setEditable(boolean b) {
		edit = b;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (!edit)
			return false;
		else if (columnIndex == 3) return true;
		return false;
	}

}
