package com.polsl.edziennik.desktopclient.model.tables;

import java.util.ArrayList;
import java.util.List;

import com.polsl.edziennik.modelDTO.person.StudentDTO;

public class StudentTableModel extends TableModel<StudentDTO> {

	public StudentTableModel(List<StudentDTO> list) {
		super(list);
	}

	public StudentTableModel() {
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
				return rowIndex + 1;
			case 1:
				return data.get(rowIndex).getFirstName();
			case 2:
				return data.get(rowIndex).getLastName();
			case 3:
				if (data.get(rowIndex).getGroup() == null) return null;
				return data.get(rowIndex).getGroup().getName();
			default:
				return null;

			}
		} else {
			return null;
		}
	}

	public Integer getId(int rowIndex) {
		if (rowIndex > -1 && rowIndex < data.size() && data.get(rowIndex) != null) return data.get(rowIndex).getId();
		return -1;
	}

	public Integer getRow(Integer id) {
		for (int i = 0; i < data.size(); i++) {
			if (id.equals(getId(id))) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public void setColumns() {
		columnNames = new String[] { " ", entity.getString("name"), entity.getString("lastName"),
				entity.getString("group") };
		classNames = new Class[] { Integer.class, String.class, String.class, String.class };

	}

	public List<Integer> getModelIds() {
		List<Integer> list = new ArrayList<Integer>(getRowCount());
		for (int i = 0; i < getRowCount(); i++)
			if (get(i) != null) list.add(get(i).getId());

		return list;
	}

}
