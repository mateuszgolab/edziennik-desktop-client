package com.polsl.edziennik.desktopclient.model.tables;

import java.util.List;

import com.polsl.edziennik.modelDTO.group.GroupDTO;

public class GroupTableModel extends TableModel<GroupDTO> {

	public GroupTableModel() {

		super();
	}

	public GroupTableModel(List<GroupDTO> GroupDTOs) {

		super(GroupDTOs);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (rowIndex > -1 && rowIndex < data.size() && columnIndex > -1 && columnIndex < classNames.length) {
		
			if(data.get(rowIndex) == null)
			{
				if(columnIndex == 0)
					return rowIndex + 1;
				else return null;
			}
			
			switch (columnIndex) {
			case 0:
				return rowIndex + 1;
			case 1:
				return data.get(rowIndex).getName();
			case 2:
				return data.get(rowIndex).getSemester();
			case 3:
				return data.get(rowIndex).getCourse();
			case 4:
				return data.get(rowIndex).getFaculty();
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
			case 0:
				data.get(rowIndex).setId((Integer) aValue);
			case 1:
				data.get(rowIndex).setName((String) aValue);
			case 2:
				data.get(rowIndex).setSemester((String) aValue);
			case 3:
				data.get(rowIndex).setCourse((String) aValue);
			case 4:
				data.get(rowIndex).setFaculty((String) aValue);
			}
		}

	}

	@Override
	public void setColumns() {
		columnNames = new String[] { " ", entity.getString("standardName"), entity.getString("semester"),
				entity.getString("course"), entity.getString("faculty") };
		classNames = new Class[] { Integer.class, String.class, String.class, String.class, String.class };

	}

}
