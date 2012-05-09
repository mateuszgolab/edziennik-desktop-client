package com.polsl.edziennik.desktopclient.view.common.panels.tablePanels;

import javax.swing.JPanel;

import com.polsl.edziennik.desktopclient.controller.utils.filters.ActivityFilter;
import com.polsl.edziennik.desktopclient.model.comboBoxes.ComboModel;
import com.polsl.edziennik.desktopclient.model.tables.TableModel;
import com.polsl.edziennik.desktopclient.view.common.panels.button.ButtonPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.filter.DateFilterPanel;

public class ActivityTablePanel extends TablePanel {

	public ActivityTablePanel(TableModel<?> tableModel, ComboModel<?> comboBoxModel, DateFilterPanel filter,
			ButtonPanel buttonPanel, JPanel preview, String title) {
		super(bundle.getString(title), tableModel, comboBoxModel, bundle.getString("activityNamesHint"), filter,
				buttonPanel, preview);
		setColumnWidths();

	}

	public void setColumnWidths() {

		if (table.getColumnModel() == null) return;

		if (table.getColumnCount() == 4) {

			table.getColumnModel().getColumn(0).setPreferredWidth(25);
			for (int i = 1; i <= 3; i++) {
				table.getColumnModel().getColumn(i).setPreferredWidth(145);

			}
		} else if (table.getColumnCount() == 5) {
			table.getColumnModel().getColumn(0).setPreferredWidth(25);
			for (int i = 1; i <= 4; i++) {
				table.getColumnModel().getColumn(i).setPreferredWidth(107);

			}
		}

	}

	@Override
	public void runFilter(Long min, Long max, String s) {
		ActivityFilter rf = new ActivityFilter(min, max, s);
		sorter.setRowFilter(rf);

	}

	@Override
	public int getComboSelectedIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

}
