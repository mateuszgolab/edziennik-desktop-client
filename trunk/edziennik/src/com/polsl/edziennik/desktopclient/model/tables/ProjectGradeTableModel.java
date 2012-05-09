package com.polsl.edziennik.desktopclient.model.tables;

import java.util.ArrayList;
import java.util.List;

import com.polsl.edziennik.modelDTO.grade.RegularGradeDTO;
import com.polsl.edziennik.modelDTO.person.StudentDTO;
import com.polsl.edziennik.modelDTO.summary.ProjectSummary;

public class ProjectGradeTableModel extends TableModel<StudentDTO> {

	private List<ProjectSummary<RegularGradeDTO>> projectSummaries;

	public ProjectGradeTableModel() {
		super();
		projectSummaries = new ArrayList<ProjectSummary<RegularGradeDTO>>();

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
				if (projectSummaries.size() <= rowIndex || projectSummaries.get(rowIndex) == null) return null;
				return projectSummaries.get(rowIndex).getFinalGrade();
			default:
				return null;

			}
		} else {
			return null;
		}
	}

	@Override
	public void setModel(List<StudentDTO> students) {
		super.setModel(students);
		projectSummaries = new ArrayList<ProjectSummary<RegularGradeDTO>>(students.size());
	}

	public void setProjectSummary(int row, ProjectSummary<RegularGradeDTO> ps) {
		projectSummaries.set(row, ps);
	}

	public void setGrade(Integer id, ProjectSummary ps) {

		for (int i = 0; i < getRowCount(); i++) {
			if (get(i).getId() == id) {
				projectSummaries.set(i, ps);
				break;
			}
		}

	}

	@Override
	public void setColumns() {
		columnNames = new String[] { " ", entity.getString("name"), entity.getString("lastName"),
				entity.getString("projectGrade") };
		classNames = new Class[] { Integer.class, String.class, String.class, Float.class };

	}

}
