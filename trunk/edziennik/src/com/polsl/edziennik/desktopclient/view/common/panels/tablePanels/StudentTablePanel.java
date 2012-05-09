package com.polsl.edziennik.desktopclient.view.common.panels.tablePanels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import com.polsl.edziennik.desktopclient.model.comboBoxes.ComboModel;
import com.polsl.edziennik.desktopclient.model.tables.StudentTableModel;
import com.polsl.edziennik.desktopclient.view.common.panels.button.ButtonPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.filter.DefaultFilterPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.filter.DefaultSimpleFilterPanel;
import com.polsl.edziennik.modelDTO.group.GroupDTO;
import com.polsl.edziennik.modelDTO.person.StudentDTO;

public class StudentTablePanel extends TablePanel {

	public StudentTablePanel(StudentTableModel tableModel, ComboModel<GroupDTO> comboBoxModel,
			DefaultFilterPanel filter, String title, ButtonPanel buttonPanel, JPanel preview) {
		super(title, tableModel, comboBoxModel, bundle.getString("studentNamesHint"), filter, buttonPanel, preview);
		setColumnWidths();
		filterPosition = 2;
		comboFilterPosition = 3;

		if (filter != null) {
			JComboBox comboBox = filter.getComboBox();
			comboBox.addActionListener(new ComboBoxActionListener(comboBox));
		}
	}

	public StudentTablePanel(StudentTableModel tableModel, DefaultSimpleFilterPanel filter, String title,
			final ButtonPanel buttonPanel, final JPanel preview) {
		super(title, tableModel, bundle.getString("studentNamesHint"), filter, buttonPanel, preview);
		setColumnWidths();
		filterPosition = 2;

	}

	public StudentTablePanel(StudentTableModel tableModel) {
		super(tableModel, bundle.getString("selectedStudents"));
		setColumnWidths();
	}

	public void setColumnWidths() {
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		for (int i = 1; i < 4; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(140);

		}

	}

	private class ComboBoxActionListener implements ActionListener {
		private JComboBox comboBox;

		public ComboBoxActionListener(JComboBox comboBox) {
			this.comboBox = comboBox;
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			GroupDTO g = (GroupDTO) comboBox.getSelectedItem();
			if (g != null) {

			}
		}

	}

	@Override
	public int getComboSelectedIndex() {
		if (filter == null) return -1;
		return filter.getComboSelectedIndex();

	}

	@Override
	public void runFilter(Long min, Long max) {
		// TODO Auto-generated method stub

	}

	@Override
	public void runFilter(Long min, Long max, String s, String s2) {
		// TODO Auto-generated method stub

	}

	public List<StudentDTO> getViewRows() {
		int count = sorter.getViewRowCount();
		List<StudentDTO> viewRows = new ArrayList<StudentDTO>(count);

		for (int i = 0; i < count; i++) {
			viewRows.add((StudentDTO) tableModel.get(sorter.convertRowIndexToModel(i)));
		}

		return viewRows;
	}

}
