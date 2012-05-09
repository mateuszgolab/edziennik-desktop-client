package com.polsl.edziennik.desktopclient.view.admin.panels.preview;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.polsl.edziennik.delegates.AdminDelegate;
import com.polsl.edziennik.delegates.DelegateFactory;
import com.polsl.edziennik.delegates.exceptions.DelegateException;
import com.polsl.edziennik.desktopclient.controller.utils.factory.GuiComponentFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.IGuiComponentAbstractFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.ILabel;
import com.polsl.edziennik.desktopclient.model.comboBoxes.StudentComboBoxModel;
import com.polsl.edziennik.desktopclient.model.tables.GroupTableModel;
import com.polsl.edziennik.modelDTO.group.GroupDTO;
import com.polsl.edziennik.modelDTO.person.StudentDTO;

public class GroupPreviewPanel extends JPanel {
	public static final int TEXT_SIZE = 30;
	private JLabel name;
	private JLabel semester;
	private JLabel course;
	private JLabel faculty;
	private JLabel senior;
	private JLabel seniorEmail;

	private JTextField nameText;
	private JTextField semesterText;
	private JTextField courseText;
	private JTextField facultyText;
	private JTextField studentsCountText;
	private JComboBox seniorBox;
	private JTextField seniorEmailText;

	private CellConstraints cc;
	private GroupDTO currentGroup;
	private GroupTableModel model;
	private AdminDelegate delegate;
	private Integer selected;
	private StudentComboBoxModel comboBoxModel;
	private boolean editable;

	private IGuiComponentAbstractFactory factory = GuiComponentFactory.getInstance();

	public GroupPreviewPanel(String title) {
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(title),
				BorderFactory.createEmptyBorder(0, 6, 6, 6)));

		FormLayout layout = new FormLayout(
				"pref,4dlu, 100dlu, 4dlu, min",
				"pref, 2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref");

		setLayout(layout);

		ILabel label = factory.createLabel();

		name = label.getLabel("standardName");
		semester = label.getLabel("semester");
		course = label.getLabel("course");
		senior = label.getLabel("senior");
		faculty = label.getLabel("faculty");
		seniorEmail = label.getLabel("email");

		nameText = new JTextField(TEXT_SIZE);
		semesterText = new JTextField(TEXT_SIZE);
		courseText = new JTextField(TEXT_SIZE);
		seniorEmailText = new JTextField(TEXT_SIZE);
		seniorEmailText.setEditable(false);
		facultyText = new JTextField(TEXT_SIZE);
		studentsCountText = new JTextField(TEXT_SIZE);
		studentsCountText.setEditable(false);
		comboBoxModel = new StudentComboBoxModel();
		seniorBox = new JComboBox(comboBoxModel);
		seniorBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (comboBoxModel.getSelectedItem() != null)

				seniorEmailText.setText(comboBoxModel.getSelectedItem().getEmail());

			}
		});

		setComponents();
		setEnabled(false);

	}

	public GroupPreviewPanel(String title, GroupTableModel model) {
		this(title);
		setModel(model);
	}

	public void setComponents() {
		cc = new CellConstraints();

		add(name, cc.xy(1, 3));
		add(nameText, cc.xy(3, 3));
		add(semester, cc.xy(1, 5));
		add(semesterText, cc.xy(3, 5));
		add(course, cc.xy(1, 7));
		add(courseText, cc.xy(3, 7));
		add(faculty, cc.xy(1, 9));
		add(facultyText, cc.xy(3, 9));
		add(senior, cc.xy(1, 11));
		add(seniorBox, cc.xy(3, 11));
		add(seniorEmail, cc.xy(1, 13));
		add(seniorEmailText, cc.xy(3, 13));

	}

	public void setData(GroupDTO t) {
		clear();
		// nowa Grupa
		if (t == null) {
			seniorBox.setEnabled(false);
			seniorEmailText.setEnabled(false);
		} else {

			nameText.setText(t.getName());
			semesterText.setText(t.getSemester());
			courseText.setText(t.getCourse());
			facultyText.setText(t.getFaculty());
			if (t.getSenior() != null) {
				seniorEmailText.setText(t.getSenior().getEmail());

			}
			if (!isEditable()) {
				comboBoxModel.removeAll();
				comboBoxModel.add(t.getSenior());
			}
			seniorBox.setSelectedItem(t.getSenior());
			seniorBox.updateUI();

		}

		currentGroup = t;
	}

	public GroupDTO getData() {
		if (currentGroup != null) {
			currentGroup.setName(nameText.getText());
			currentGroup.setSemester(semesterText.getText());
			currentGroup.setCourse(courseText.getText());
			currentGroup.setFaculty(facultyText.getText());
			currentGroup.setSenior((StudentDTO) seniorBox.getSelectedItem());
		}

		return currentGroup;
	}

	public void clear() {
		nameText.setText("");
		semesterText.setText("");
		courseText.setText("");
		facultyText.setText("");
		studentsCountText.setText("");
		seniorBox.removeAll();
		seniorBox.setSelectedItem(null);
		seniorEmailText.setText("");
	}

	@Override
	public void setEnabled(boolean b) {
		nameText.setEnabled(b);
		semesterText.setEnabled(b);
		courseText.setEnabled(b);
		facultyText.setEnabled(b);
		studentsCountText.setEnabled(b);
		seniorBox.setEnabled(b);
		seniorEmailText.setEnabled(b);

	}

	public void setEditable(boolean b) {
		nameText.setEditable(b);
		semesterText.setEditable(b);
		courseText.setEditable(b);
		facultyText.setEditable(b);
		seniorEmailText.setEditable(b);
		editable = b;

	}

	public void saveGroup() throws DelegateException {
		// / zapis nowej grupy

		delegate = DelegateFactory.getAdminDelegate();

		if (currentGroup == null) {
			currentGroup = new GroupDTO();
			getData();
			currentGroup = delegate.addGroup(currentGroup);
			model.add(currentGroup);

		} else {
			getData();
			model.update(currentGroup, selected);
			delegate.updateGroup(currentGroup);

		}
	}

	public void setSelected(int index) {
		selected = index;
	}

	public void setModel(GroupTableModel model) {
		this.model = model;
	}

	@Override
	public void disable() {
		clear();
		setEnabled(false);
	}

	public void setComboModel(List<StudentDTO> data) {
		comboBoxModel.setModel(data);
	}

	public boolean isEditable() {
		return editable;
	}
}
