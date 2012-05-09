package com.polsl.edziennik.desktopclient.view.common.panels.tablePanels;

import javax.swing.JPanel;

import com.polsl.edziennik.desktopclient.model.comboBoxes.ComboModel;
import com.polsl.edziennik.desktopclient.model.tables.StudentTableModel;
import com.polsl.edziennik.desktopclient.view.common.panels.button.ButtonPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.filter.DefaultFilterPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.filter.DefaultSimpleFilterPanel;
import com.polsl.edziennik.modelDTO.group.GroupDTO;

public class StudentTightTablePanel extends StudentTablePanel {

	public StudentTightTablePanel(StudentTableModel tableModel, ComboModel<GroupDTO> comboBoxModel,
			DefaultFilterPanel filter, String title, ButtonPanel buttonPanel, JPanel preview) {
		super(tableModel, comboBoxModel, filter, title, buttonPanel, preview);

	}

	public StudentTightTablePanel(StudentTableModel tableModel, DefaultSimpleFilterPanel filter, String title,
			final ButtonPanel buttonPanel, final JPanel preview) {
		super(tableModel, filter, title, buttonPanel, preview);
	}

	public StudentTightTablePanel(StudentTableModel model) {
		super(model);
	}

	@Override
	public void setColumnWidths() {
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		for (int i = 1; i < 3; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(130);

		}
		table.getColumnModel().getColumn(3).setPreferredWidth(110);

	}

}
