package com.polsl.edziennik.desktopclient.view.student.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.jidesoft.swing.JideScrollPane;
import com.polsl.edziennik.desktopclient.controller.utils.FrameToolkit;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.model.tables.GradeTableModel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.common.panels.preview.GradePreviewPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.tablePanels.GradeTablePanel;
import com.polsl.edziennik.modelDTO.grade.RegularGradeDTO;
import com.polsl.edziennik.modelDTO.person.StudentDTO;

public class StudentGradeDetailsDialog extends JDialog {

	private GradeTableModel model;
	private GradeTablePanel panel;
	private GradePreviewPanel preview;
	private JSplitPane splitPane;
	protected static ResourceBundle bundle = LangManager.getResource(Properties.Component);
	private FrameToolkit frameToolkit = new FrameToolkit();
	private JScrollPane scrollPane;

	public StudentGradeDetailsDialog(StudentDTO s, Integer activityId) {

		Dimension preferredSize = frameToolkit.getSize();
		preferredSize.setSize(800, preferredSize.getHeight());
		setSize(preferredSize);
		setIconImage(frameToolkit.getTitleIcon("GradesIcon"));
		setLocationRelativeTo(null);

		setLayout(new BorderLayout());
		if (s != null) {
			model = new GradeTableModel();
			preview = new GradePreviewPanel(model, activityId, s.getId());
			setTitle(s.toString());
		} else {
			model = new GradeTableModel();
			preview = new GradePreviewPanel(model, activityId, null);
		}
		panel = new GradeTablePanel(model);

		panel.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				int selected = panel.getSelected();
				if (selected > -1) {
					preview.setSelected(selected);
					preview.setEnabled(true);
					preview.setData(model.get(panel.getSelected()));

				}
			}
		});

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setDividerLocation(0.1);
		splitPane.setDividerSize(1);
		splitPane.add(panel);
		splitPane.add(preview);
		scrollPane = new JideScrollPane(splitPane);

		add(scrollPane, BorderLayout.CENTER);

		setVisible(true);
	}

	public void setModel(List<RegularGradeDTO> list) {
		model.setModel(list);
	}

}
