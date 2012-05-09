package com.polsl.edziennik.desktopclient.view.common.panels.tablePanels;

import com.polsl.edziennik.desktopclient.model.tables.StudentGradesTableModel;

public class StudentGradesTablePanel extends TablePanel {

	public StudentGradesTablePanel(StudentGradesTableModel tableModel) {
		super(tableModel, "");
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getComboSelectedIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setColumnsWidths() {
		table.getColumnModel().getColumn(0).setPreferredWidth(25);
		for (int i = 1; i <= 3; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(145);

		}
	}

}
