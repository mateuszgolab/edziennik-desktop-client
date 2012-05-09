package com.polsl.edziennik.desktopclient.view.common.panels.preview;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jdesktop.swingx.JXDatePicker;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.polsl.edziennik.desktopclient.controller.utils.DateConverter;
import com.polsl.edziennik.desktopclient.controller.utils.factory.GuiComponentFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.IGuiComponentAbstractFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.ILabel;
import com.polsl.edziennik.desktopclient.model.comboBoxes.TeacherComboBoxModel;
import com.polsl.edziennik.desktopclient.model.tables.ProjectActivityTableModel;
import com.polsl.edziennik.modelDTO.activity.ProjectActivityDTO;
import com.polsl.edziennik.modelDTO.person.TeacherDTO;
import com.polsl.edziennik.modelDTO.project.ProjectDTO;

public abstract class ProjectActivityPreviewPanel extends PreviewPanel {

	public static final int TEXT_SIZE = 30;
	public static final String[] HOURS = new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09",
			"10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24" };
	public static final String[] MINUTES = new String[] { "00", "15", "30", "45" };
	private JLabel group;
	private JLabel date;
	private JLabel hour;
	private JLabel teacher;
	private JLabel room;
	private JLabel note;
	private JLabel length;
	private JLabel project;

	private JTextField roomText;
	private JTextArea noteText;
	private JXDatePicker datePicker;
	private JComboBox hours;
	private JComboBox minutes;
	private JComboBox lengthHours;
	private JComboBox lengthMinutes;
	private JTextField projectText;
	private JComboBox teacherComboBox;

	private TeacherComboBoxModel teacherComboBoxModel;

	protected ProjectActivityDTO currentActivity;
	private IGuiComponentAbstractFactory factory = GuiComponentFactory.getInstance();
	protected ProjectActivityTableModel model;
	protected Integer selected;
	private DateConverter dateConverter;
	protected ILabel Label;
	protected Integer projectId;

	public ProjectActivityPreviewPanel(String title, ProjectActivityTableModel model) {
		this.model = model;

		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(title),
				BorderFactory.createEmptyBorder(0, 6, 6, 6)));

		FormLayout layout = new FormLayout(
				"pref,4dlu,30dlu,4dlu,30dlu,4dlu,  28dlu,4dlu,28dlu, 4dlu, min",
				"pref, 2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref");

		setLayout(layout);

		Label = factory.createLabel();
		factory.createTextButton();

		create();

		setComponents();
		setEnabled(false);

	}

	public void create() {
		date = Label.getLabel("date");
		hour = Label.getLabel("hour");
		teacher = Label.getLabel("teacher");
		room = Label.getLabel("classRoom");
		note = Label.getLabel("note");
		group = Label.getLabel("group");
		length = Label.getLabel("timeLength");
		project = Label.getLabel("project");

		hours = new JComboBox(HOURS);
		minutes = new JComboBox(MINUTES);
		lengthHours = new JComboBox(HOURS);
		lengthMinutes = new JComboBox(MINUTES);
		roomText = new JTextField(TEXT_SIZE);
		noteText = new JTextArea(3, 3);

		teacherComboBoxModel = new TeacherComboBoxModel();
		teacherComboBox = new JComboBox(teacherComboBoxModel);

		projectText = new JTextField(TEXT_SIZE);
		projectText.setEditable(false);

		datePicker = new JXDatePicker(null, new Locale("pl"));

	}

	@Override
	public void setComponents() {
		cc = new CellConstraints();

		add(project, cc.xy(1, 1));
		add(projectText, cc.xyw(3, 1, 5));
		add(teacher, cc.xy(1, 3));
		add(teacherComboBox, cc.xyw(3, 3, 5));
		add(date, cc.xy(1, 5));
		add(datePicker, cc.xyw(3, 5, 5));
		// add(hour, cc.xy(1, 7));
		add(hours, cc.xy(9, 5));
		add(minutes, cc.xy(11, 5));
		add(length, cc.xy(1, 7));
		add(lengthHours, cc.xy(3, 7));
		add(lengthMinutes, cc.xy(5, 7));
		add(room, cc.xyw(1, 9, 5));
		add(roomText, cc.xyw(3, 9, 5));
		add(note, cc.xyw(1, 11, 5));
		add(noteText, cc.xyw(3, 11, 5));

	}

	public void setProject(ProjectDTO p) {
		if (p != null) {
			teacherComboBox.setSelectedItem(p.getTeacher());
			projectText.setText(p.getName());
			projectId = p.getId();

		}
	}

	public void setData(ProjectActivityDTO a) {
		// nowe spotkanie projektowe
		if (a == null) {
			clear();
		} else {

			// te.set
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(a.getDateFrom());

			Calendar c2 = Calendar.getInstance();
			c2.setTimeInMillis(a.getDateTo());

			hours.setSelectedItem(c.get(Calendar.HOUR));
			minutes.setSelectedItem(c.get(Calendar.MINUTE));
			teacherComboBox.setSelectedItem(a.getTeacher());
			if (a.getProject() != null) {
				projectText.setText(a.getProject().getName());
				projectId = a.getProjectId();
			}
			roomText.setText(a.getRoom());
			noteText.setText(a.getNote());
			lengthHours.setSelectedItem(c2.get(Calendar.HOUR) - c.get(Calendar.HOUR));
			lengthMinutes.setSelectedItem(c2.get(Calendar.MINUTE) - c.get(Calendar.MINUTE));
		}

		currentActivity = a;
	}

	@Override
	public void clear() {
		datePicker.setDate(null);
		hours.setSelectedIndex(0);
		minutes.setSelectedIndex(0);
		lengthHours.setSelectedIndex(0);
		lengthMinutes.setSelectedIndex(0);
		projectText.setText("");
		if (teacherComboBox.getItemCount() > 0) teacherComboBox.setSelectedIndex(0);
		roomText.setText("");
		noteText.setText("");
	}

	@Override
	public void setEnabled(boolean b) {
		datePicker.setEnabled(b);
		hours.setEnabled(b);
		minutes.setEnabled(b);
		lengthHours.setEnabled(b);
		lengthMinutes.setEnabled(b);
		projectText.setEnabled(b);
		teacherComboBox.setEnabled(b);
		roomText.setEnabled(b);
		noteText.setEnabled(b);

	}

	protected void setData() {

		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR, new Integer(lengthHours.getSelectedItem().toString()));
		c.set(Calendar.MINUTE, new Integer(lengthMinutes.getSelectedItem().toString()));

		Long date = 0L;
		if (datePicker.getDate() != null) date = datePicker.getDate().getTime();
		currentActivity.setDateFrom(dateConverter.getDate(date, (String) hours.getSelectedItem(),
				(String) minutes.getSelectedItem()));

		date = 0L;
		if (datePicker.getDate() != null) date = datePicker.getDate().getTime();
		currentActivity.setDateTo(dateConverter.getDate(date, (String) hours.getSelectedItem(),
				(String) minutes.getSelectedItem(), (String) lengthHours.getSelectedItem(),
				(String) lengthMinutes.getSelectedItem()));

		currentActivity.setProjectId(projectId);
		currentActivity.setTeacher((TeacherDTO) teacherComboBox.getSelectedItem());
		currentActivity.setRoom(roomText.getText());
		currentActivity.setNote(noteText.getText());
	}

	@Override
	public void setSelected(int index) {
		selected = index;
	}

	@Override
	public void disable() {
		clear();
		setEnabled(false);
	}

	public void setTeacherModel(List<TeacherDTO> teachers) {
		teacherComboBoxModel.setModel(teachers);
		teacherComboBox.setSelectedItem(null);
		teacherComboBox.updateUI();
	}

	@Override
	public abstract void save();

	@Override
	public abstract void delete();
}
