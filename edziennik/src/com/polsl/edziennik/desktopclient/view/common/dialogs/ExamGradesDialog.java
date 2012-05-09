package com.polsl.edziennik.desktopclient.view.common.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import javax.swing.JDialog;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.polsl.edziennik.delegates.DelegateFactory;
import com.polsl.edziennik.desktopclient.controller.utils.FrameToolkit;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.controller.utils.workers.Worker;
import com.polsl.edziennik.desktopclient.model.comboBoxes.ComboModel;
import com.polsl.edziennik.desktopclient.model.tables.ExamTaskGradeTableModel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.common.panels.button.SaveExitButtonPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.filter.DefaultFilterPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.tablePanels.ExamTaskGradeTablePanel;
import com.polsl.edziennik.modelDTO.group.GroupDTO;
import com.polsl.edziennik.modelDTO.person.StudentDTO;

public class ExamGradesDialog extends JDialog {

	private FrameToolkit frameToolkit = new FrameToolkit();
	protected static ResourceBundle bundle = LangManager.getResource(Properties.Component);
	private ExamTaskGradeTableModel model;
	private ExamTaskGradeTablePanel panel;
	private Integer taskId;
	private String name;
	private ComboModel<GroupDTO> comboModel;
	private DefaultFilterPanel filter;
	protected SaveExitButtonPanel buttonPanel;

	public ExamGradesDialog(Integer taskId, String name) {
		this.taskId = taskId;
		this.name = name;
		setTitle(bundle.getString("grades"));
		Dimension preferredSize = frameToolkit.getSize();
		preferredSize.setSize(30 + 140 * 3 + 10 + 40, preferredSize.getHeight());
		setSize(preferredSize);
		setIconImage(frameToolkit.getTitleIcon("GradesIcon"));
		setLocationRelativeTo(null);

		setLayout(new BorderLayout());

		init();
		buttonPanel.activateSave(false);
		new GroupsProvider().execute();
		new DataProvider().execute();

	}

	public void init() {
		model = new ExamTaskGradeTableModel();
		comboModel = new ComboModel<GroupDTO>();
		buttonPanel = new SaveExitButtonPanel("detailsButtonHint");
		filter = new DefaultFilterPanel(model, comboModel, bundle.getString("studentNamesHint"), "lastName",
				bundle.getString("groupHint"), "group");
		panel = new ExamTaskGradeTablePanel(model, comboModel, filter, bundle.getString("gradesForTask") + " : " + name);

		panel.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				buttonPanel.activateSave(true);

			}
		});

		buttonPanel.getSaveButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new SaveWorker().execute();
			}
		});

		buttonPanel.getExitButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		add(panel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
	}

	private class DataProvider extends Worker<List<StudentDTO>> {

		@Override
		protected List<StudentDTO> doInBackground() throws Exception {
			startProgress();
			return DelegateFactory.getTeacherDelegate().getAllStudentsWithGroups();
		}

		@Override
		public void done() {
			try {
				model.setModel(get());
				stopProgress();
				new GradesProvider().execute();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private class GroupsProvider extends Worker<List<GroupDTO>> {

		@Override
		protected List<GroupDTO> doInBackground() throws Exception {
			startProgress();
			return DelegateFactory.getTeacherDelegate().getAllGroups();
		}

		@Override
		public void done() {
			try {
				GroupDTO g = new GroupDTO();
				g.setName(bundle.getString("all"));
				g.setId(-1);
				List<GroupDTO> groups = get();
				groups.add(g);
				comboModel.setModel(groups);
				comboModel.setSelectedItem(g);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			stopProgress();

		}

	}

	private class GradesProvider extends Worker<Map<Integer, Float>> {

		@Override
		protected Map<Integer, Float> doInBackground() throws Exception {
			startProgress();
			return DelegateFactory.getTeacherDelegate().getStudentsGradesFromTask(taskId);

		}

		@Override
		public void done() {
			try {
				model.setGrades(get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			setVisible(true);
			stopProgress();
		}

	}

	private class SaveWorker extends Worker<Void> {

		@Override
		protected Void doInBackground() throws Exception {

			startProgress();
			for (Map.Entry<Integer, Float> entry : model.getGrades().entrySet())
				DelegateFactory.getTeacherDelegate().updateStudentGradeFromTask(taskId, entry.getKey(),
						entry.getValue());
			return null;
		}

		@Override
		public void done() {
			panel.clearSelection();
			stopProgress();

		}

	}
}
