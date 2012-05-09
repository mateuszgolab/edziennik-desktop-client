package com.polsl.edziennik.desktopclient.model.tables;

import java.util.List;

import com.polsl.edziennik.modelDTO.exercise.ExerciseTopicDTO;

public class ExerciseTopicTableModel extends TableModel<ExerciseTopicDTO>{

	
	
	public ExerciseTopicTableModel(List<ExerciseTopicDTO> list) {
		super(list);
	}
	
	public ExerciseTopicTableModel()
	{
		super();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (rowIndex > -1 && rowIndex < data.size() && columnIndex > -1 && columnIndex < classNames.length) {
		
			if(data.get(rowIndex) == null)
			 return null;
			
			
			switch (columnIndex) {
			case 0:
				return data.get(rowIndex).getNumber();
			case 1:
				return data.get(rowIndex).getSubject();
			default:
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public void setColumns() {
		columnNames = new String[] { entity.getString("number"), entity.getString("exerciseSubject") };
		classNames = new Class[] { String.class, String.class, };

		
	}

}
