package com.polsl.edziennik.desktopclient.view.student;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.polsl.edziennik.delegates.DelegateFactory;
import com.polsl.edziennik.desktopclient.controller.teacher.activities.ProjectActivityAction;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.controller.utils.factory.GuiComponentFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.IGuiComponentAbstractFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.ILabel;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.ITextArea;
import com.polsl.edziennik.desktopclient.controller.utils.workers.Worker;
import com.polsl.edziennik.desktopclient.model.comboBoxes.TeacherComboBoxModel;
import com.polsl.edziennik.desktopclient.model.tables.ProjectTableModel;
import com.polsl.edziennik.desktopclient.model.tables.StudentTableModel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.EdziennikDesktop;
import com.polsl.edziennik.desktopclient.view.common.dialogs.ProjectGradeDialog;
import com.polsl.edziennik.desktopclient.view.common.panels.button.ProjectPreviewButtonPanel;
import com.polsl.edziennik.modelDTO.person.StudentDTO;
import com.polsl.edziennik.modelDTO.project.ProjectDTO;

public class StudentProjectView extends JPanel {

	private JLabel name;
	private JLabel description;
	private JLabel teacher;

	private JTextField nameText;
	private JTextArea descriptionText;
	private JTextField teacherText;

	public static final int TEXT_SIZE = 30;
	private CellConstraints cc;
	private ProjectDTO currentProject;
	private IGuiComponentAbstractFactory factory = GuiComponentFactory.getInstance();
	public final ResourceBundle bundle = LangManager.getResource(Properties.Component);
	private ProjectTableModel model;
	private TeacherComboBoxModel teacherComboModel;
	private Integer selected;
	// TODO dialog z studentTablePanel
	// private StudentDualListDialog studentsDialog;
	private ProjectGradeDialog gradesDialog;
	private ProjectPreviewButtonPanel buttonPanel;
	private StudentTableModel studentTableModel;
	private int counter;
	private ILabel label;
	private ITextArea area;

	public StudentProjectView() {

		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(bundle.getString("ProjectTitle")),
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
		teacherText = new JTextField(TEXT_SIZE);

		buttonPanel = new ProjectPreviewButtonPanel();

		// buttonPanel.getStudentsButton().addActionListener(new
		// StudentsDualListListener());
		// buttonPanel.getGradesButton().addActionListener(new
		// GradesListener());
		//
		// buttonPanel.getStudentsButton().addActionListener(new
		// StudentsDualListListener());
		// buttonPanel.getGradesButton().addActionListener(new
		// GradesListener());

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

	}

	public void setComponents() {
		cc = new CellConstraints();

		add(name, cc.xy(1, 1));
		add(nameText, cc.xy(3, 1));
		add(description, cc.xy(1, 3));
		add(descriptionText, cc.xy(3, 3));
		add(teacher, cc.xy(1, 9));
		add(teacherText, cc.xy(3, 9));
		add(buttonPanel, cc.xyw(1, 19, 5));

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

	private class SelectedStudentsProvider extends Worker<List<StudentDTO>> {

		public SelectedStudentsProvider(Integer id) {
			super("get");
		}

		public SelectedStudentsProvider(Integer id, boolean b) {
			super("get");
		}

		@Override
		protected List<StudentDTO> doInBackground() throws Exception {
			ProjectDTO p = DelegateFactory.getCommonDelegate().getProjectWithStudents(currentProject.getId());
			if (p != null) return p.getStudents();
			return null;
		}

		@Override
		public void done() {
			stopProgress();

			// try {
			// // studentsDialog.setSelectedModel(get());
			// } catch (InterruptedException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// } catch (ExecutionException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }

			incCounter();
			// if (getCounter() >= 2) studentsDialog.setVisible(true);

		}
	}

	private class NotSelectedStudentsProvider extends Worker<List<StudentDTO>> {

		private Integer id;

		public NotSelectedStudentsProvider(Integer id) {
			super("get");
			this.id = id;
		}

		@Override
		protected List<StudentDTO> doInBackground() throws Exception {

			return DelegateFactory.getTeacherDelegate().getStudentsNotFromProject(id);
		}

		@Override
		public void done() {
			stopProgress();
			// try {
			// studentsDialog.setChoosableModel(get());
			// } catch (InterruptedException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// } catch (ExecutionException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			//
			// incCounter();
			// if (getCounter() == 2) studentsDialog.setVisible(true);

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
			// studentsDialog.clearSelection();

		}

	}

	private class CancelListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// if (studentsDialog != null) studentsDialog.dispose();
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
			stopProgress();
			try {
				gradesDialog.setModel(get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			gradesDialog.setVisible(true);
		}

	}
}
