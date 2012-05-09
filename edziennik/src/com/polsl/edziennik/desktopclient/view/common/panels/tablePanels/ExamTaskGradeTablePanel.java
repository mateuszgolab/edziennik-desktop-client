package com.polsl.edziennik.desktopclient.view.common.panels.tablePanels;

import com.polsl.edziennik.desktopclient.model.comboBoxes.ComboModel;
import com.polsl.edziennik.desktopclient.model.tables.ExamTaskGradeTableModel;
import com.polsl.edziennik.desktopclient.view.common.panels.filter.DefaultFilterPanel;
import com.polsl.edziennik.modelDTO.group.GroupDTO;

public class ExamTaskGradeTablePanel extends TablePanel {

	public ExamTaskGradeTablePanel(ExamTaskGradeTableModel tableModel, ComboModel<GroupDTO> comboBoxModel,
			DefaultFilterPanel filter, String title) {
		super(title, tableModel, comboBoxModel, bundle.getString("studentNamesHint"), filter, null, null);
		setColumnWidths();
		filterPosition = 2;
		comboFilterPosition = 3;

	}

	public void setColumnWidths() {

		if (table.getColumnModel() == null) return;

		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		for (int i = 1; i <= 3; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(110);
			table.getColumnModel().getColumn(4).setPreferredWidth(90);
		}
	}

	@Override
	public int getComboSelectedIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

}
