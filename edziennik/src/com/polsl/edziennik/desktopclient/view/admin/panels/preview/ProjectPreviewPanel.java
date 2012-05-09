package com.polsl.edziennik.desktopclient.view.admin.panels.preview;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.polsl.edziennik.delegates.DelegateFactory;
import com.polsl.edziennik.delegates.exceptions.DelegateException;
import com.polsl.edziennik.desktopclient.controller.teacher.activities.ProjectActivityAction;
import com.polsl.edziennik.desktopclient.controller.utils.factory.GuiComponentFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.IGuiComponentAbstractFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.ILabel;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.ITextArea;
import com.polsl.edziennik.desktopclient.controller.utils.workers.Worker;
import com.polsl.edziennik.desktopclient.model.comboBoxes.TeacherComboBoxModel;
import com.polsl.edziennik.desktopclient.model.tables.ProjectGradeTableModel;
import com.polsl.edziennik.desktopclient.model.tables.ProjectTableModel;
import com.polsl.edziennik.desktopclient.model.tables.StudentTableModel;
import com.polsl.edziennik.desktopclient.view.EdziennikDesktop;
import com.polsl.edziennik.desktopclient.view.common.dialogs.ProjectGradeDialog;
import com.polsl.edziennik.desktopclient.view.common.dialogs.StudentDualListDialog;
import com.polsl.edziennik.desktopclient.view.common.panels.button.ProjectPreviewButtonPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.preview.PreviewPanel;
import com.polsl.edziennik.modelDTO.grade.RegularGradeDTO;
import com.polsl.edziennik.modelDTO.group.GroupDTO;
import com.polsl.edziennik.modelDTO.person.StudentDTO;
import com.polsl.edziennik.modelDTO.person.TeacherDTO;
import com.polsl.edziennik.modelDTO.project.ProjectDTO;
import com.polsl.edziennik.modelDTO.summary.ProjectSummary;

public class ProjectPreviewPanel extends PreviewPanel {

	private JLabel name;
	private JLabel description;
	private JLabel teacher;

	private JTextField nameText;
	private JTextArea descriptionText;
	private JComboBox teacherComboBox;

	private CellConstraints cc;
	private ProjectDTO currentProject;
	private IGuiComponentAbstractFactory factory = GuiComponentFactory.getInstance();
	private ProjectTableModel model;
	private TeacherComboBoxModel teacherComboModel;
	private Integer selected;
	private StudentDualListDialog studentsDialog;
	private ProjectGradeDialog gradesDialog;
	private ProjectPreviewButtonPanel buttonPanel;
	private ProjectGradeTableModel projectGradeTableModel;
	private StudentTableModel studentTableModel;
	private int counter;
	private ILabel label;
	private ITextArea area;

	public ProjectPreviewPanel(String title, ProjectTableModel model) {
		this.model = model;

		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(title),
				BorderFactory.createEmptyBorder(0, 6, 6, 6)));

		FormLayout layout = new FormLayout(
				"pref,4dlu, 100dlu, 50dlu, min",
				"pref, 2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref");

		setLayout(layout);

		label = factory.createLabel();
		area = factory.createTextArea();

		create();
		setComponents();
		setEnabled(false);

	}

	public void create() {
		name = label.getLabel("standardName");
		description = label.getLabel("description");
		teacher = label.getLabel("teacher");

		nameText = new JTextField(TEXT_SIZE);
		descriptionText = area.getTextArea(5, 5);

		teacherComboModel = new TeacherComboBoxModel();
		teacherComboBox = new JComboBox(teacherComboModel);

		buttonPanel = new ProjectPreviewButtonPanel();

		buttonPanel.getStudentsButton().addActionListener(new StudentsDualListListener());
		buttonPanel.getGradesButton().addActionListener(new GradesListener());

		buttonPanel.getProjectActivitiesButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				EdziennikDesktop edziennik = EdziennikDesktop.getInstance();
				if (edziennik.getAdminParent() != null)
					(new ProjectActivityAction(edziennik.getAdminParent(), model.get(selected))).actionPerformed(null);
				else if (edziennik.getTeacherParent() != null)
					(new ProjectActivityAction(edziennik.getTeacherParent(), model.get(selected)))
							.actionPerformed(null);
			}

		});

		studentTableModel = new StudentTableModel();

		projectGradeTableModel = new ProjectGradeTableModel();

		gradesDialog = new ProjectGradeDialog(projectGradeTableModel);
		gradesDialog.getExButton().addActionListener(new CancelListener());

		studentsDialog = new StudentDualListDialog(studentTableModel);
		studentsDialog.getCancelButton().addActionListener(new CancelListener());
		studentsDialog.getSaveButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				UpdateStudentsWorker uw = new UpdateStudentsWorker(studentsDialog.getSelectedIds());
				uw.execute();
				uw.startProgress();

			}
		});

	}

	@Override
	public void setComponents() {
		cc = new CellConstraints();

		add(name, cc.xy(1, 1));
		add(nameText, cc.xy(3, 1));
		add(description, cc.xy(1, 3));
		add(descriptionText, cc.xy(3, 3));
		add(teacher, cc.xy(1, 9));
		add(teacherComboBox, cc.xy(3, 9));
		add(buttonPanel, cc.xyw(1, 19, 5));

	}

	public void setProjectData() {
		if (currentProject == null) currentProject = new ProjectDTO();
		currentProject.setName(nameText.getText());
		currentProject.setTeacher((TeacherDTO) teacherComboBox.getSelectedItem());
		currentProject.setDescription(descriptionText.getText());

	}

	public void setData(ProjectDTO p) {
		// nowy projekt
		clear();
		if (p != null) {

			nameText.setText(p.getName());
			descriptionText.setText(p.getDescription());
			teacherComboBox.setSelectedItem(p.getTeacher());

		}

		currentProject = p;

	}

	@Override
	public void clear() {
		nameText.setText("");
		descriptionText.setText("");

	}

	@Override
	public void setEnabled(boolean b) {
		nameText.setEnabled(b);
		descriptionText.setEnabled(b);
		teacherComboBox.setEnabled(b);
		buttonPanel.activate(b);
	}

	@Override
	public void setSelected(int index) {
		selected = index;
	}

	private class StudentsDualListListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (currentProject != null && currentProject.getId() != null) {

				GroupsProvider gp = new GroupsProvider();
				gp.execute();

				new StudentsProvider().execute();

			} else
				studentsDialog.setVisible(true);

		}

	}

	private class GradesListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (currentProject != null && currentProject.getId() != null) {
				GradesProvider gp = new GradesProvider();
				gp.execute();
				gp.startProgress();

			} else
				gradesDialog.setVisible(true);

		}
	}

	@Override
	public void disable() {
		clear();
		setEnabled(false);
	}

	@Override
	public void save() {
		// zapis nowego projektu
		try {
			if (currentProject == null) {
				currentProject = new ProjectDTO();
				setProjectData();
				currentProject = DelegateFactory.getTeacherDelegate().addProject(currentProject);
				model.add(currentProject);

			} else {
				setProjectData();
				DelegateFactory.getTeacherDelegate().updateProject(currentProject);
				model.update(currentProject, selected);

			}
		} catch (DelegateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}

	public void setTeacherModel(List<TeacherDTO> teachers) {
		teacherComboModel.setModel(teachers);
		teacherComboBox.setSelectedItem(null);
		teacherComboBox.updateUI();
	}

	private class StudentsProvider extends Worker<List<StudentDTO>> {

		@Override
		protected List<StudentDTO> doInBackground() throws Exception {
			startProgress();
			ProjectDTO p = DelegateFactory.getCommonDelegate().getProjectWithStudents(currentProject.getId());
			if (p != null) return p.getStudents();
			return null;
		}

		@Override
		public void done() {

			try {
				studentsDialog.setSelectedModel(get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			stopProgress();
			new NotSelectedStudentsProvider().execute();

		}
	}

	private class NotSelectedStudentsProvider extends Worker<List<StudentDTO>> {

		@Override
		protected List<StudentDTO> doInBackground() throws Exception {
			startProgress();
			return DelegateFactory.getTeacherDelegate().getStudentsNotFromProject(currentProject.getId());
		}

		@Override
		public void done() {
			try {
				studentsDialog.setChoosableModel(get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			studentsDialog.setVisible(true);
			stopProgress();

		}

	}

	private class UpdateStudentsWorker extends Worker<Void> {

		private List<Integer> list;

		public UpdateStudentsWorker(List<Integer> list) {
			super("save");
			this.list = list;
		}

		@Override
		protected Void doInBackground() throws Exception {

			DelegateFactory.getTeacherDelegate().addStudentsToProject(list, currentProject.getId());
			return null;
		}

		@Override
		public void done() {
			stopProgress();
			studentsDialog.clearSelection();

		}

	}

	private class CancelListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (studentsDialog != null) studentsDialog.dispose();
			if (gradesDialog != null) gradesDialog.dispose();

		}
	};

	public synchronized void setCounter(int val) {
		counter = val;
	}

	public synchronized int getCounter() {
		return counter;
	}

	public synchronized void incCounter() {
		counter++;
	}

	private class GradesProvider extends Worker<List<StudentDTO>> {

		@Override
		protected List<StudentDTO> doInBackground() throws Exception {

			ProjectDTO p = DelegateFactory.getCommonDelegate().getProjectWithStudents(currentProject.getId());
			if (p != null) return p.getStudents();
			return null;

		}

		@Override
		public void done() {
			try {

				gradesDialog.setModel(get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			gradesDialog.setProjectId(currentProject.getId());
			stopProgress();

			new ProjectSummariesProvider().execute();
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
				get().add(g);

				studentsDialog.setComboModel(get());
				studentsDialog.setVisible(true);
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

	private class ProjectSummariesProvider extends Worker<List<ProjectSummary<RegularGradeDTO>>> {

		@Override
		protected List<ProjectSummary<RegularGradeDTO>> doInBackground() throws Exception {

			startProgress();
			for (int i = 0; i < projectGradeTableModel.getRowCount(); i++) {
				if (projectGradeTableModel.get(i) != null)
					projectGradeTableModel.setProjectSummary(i,
							DelegateFactory.getCommonDelegate()
									.getProjectSummary(projectGradeTableModel.get(i).getId()));
			}
			return null;

		}

		@Override
		public void done() {

			stopProgress();
			gradesDialog.setVisible(true);

		}
	}

}
