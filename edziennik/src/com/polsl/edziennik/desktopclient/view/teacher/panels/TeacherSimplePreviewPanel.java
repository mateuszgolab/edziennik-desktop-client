package com.polsl.edziennik.desktopclient.view.teacher.panels;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.polsl.edziennik.desktopclient.controller.utils.factory.GuiComponentFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.IGuiComponentAbstractFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.ILabel;
import com.polsl.edziennik.modelDTO.person.TeacherDTO;

public class TeacherSimplePreviewPanel extends JPanel {
	public static final int TEXT_SIZE = 30;
	protected JLabel name;
	protected JLabel lastName;
	protected JLabel academicTitle;
	protected JLabel room;
	protected JLabel email;
	protected JLabel exercises;
	protected JTextField nameText;
	protected JTextField secondNameText;
	protected JTextField lastNameText;
	protected JTextField emailText;
	protected JTextField academicTitleText;
	protected JTextField roomText;
	protected CellConstraints cc;
	protected TeacherDTO currentTeacher;
	protected IGuiComponentAbstractFactory factory = GuiComponentFactory.getInstance();
	protected ILabel label;

	public TeacherSimplePreviewPanel(String title) {
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(title),
				BorderFactory.createEmptyBorder(0, 6, 6, 6)));

		FormLayout layout = new FormLayout(
				"pref,4dlu, 100dlu, 4dlu, min",
				"pref, 2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref");

		setLayout(layout);

		label = factory.createLabel();

		create();
		setComponents();
		setEnabled(false);
		setEditable(false);

	}

	public void setComponents() {
		cc = new CellConstraints();

		add(academicTitle, cc.xy(1, 3));
		add(academicTitleText, cc.xy(3, 3));
		add(name, cc.xy(1, 5));
		add(nameText, cc.xy(3, 5));
		add(lastName, cc.xy(1, 7));
		add(lastNameText, cc.xy(3, 7));
		add(email, cc.xy(1, 9));
		add(emailText, cc.xy(3, 9));
		add(room, cc.xy(1, 11));
		add(roomText, cc.xy(3, 11));
	}

	public void clear() {
		nameText.setText("");
		lastNameText.setText("");
		academicTitleText.setText("");
		roomText.setText("");
		emailText.setText("");
	}

	@Override
	public void setEnabled(boolean b) {
		nameText.setEnabled(b);
		lastNameText.setEnabled(b);
		academicTitleText.setEnabled(b);
		emailText.setEnabled(b);
		roomText.setEnabled(b);
	}

	public void setEditable(boolean b) {
		nameText.setEditable(b);
		lastNameText.setEditable(b);
		academicTitleText.setEditable(b);
		emailText.setEditable(b);
		roomText.setEditable(b);
	}

	public void create() {
		name = label.getLabel("name");
		lastName = label.getLabel("lastName");
		email = label.getLabel("email");
		academicTitle = label.getLabel("academicTitle");
		room = label.getLabel("room");

		nameText = new JTextField(TEXT_SIZE);
		lastNameText = new JTextField(TEXT_SIZE);
		emailText = new JTextField(TEXT_SIZE);
		academicTitleText = new JTextField(TEXT_SIZE);
		roomText = new JTextField(TEXT_SIZE);

	}

	public void setData(TeacherDTO t) {

		clear();
		if (t != null) {

			nameText.setText(t.getFirstName());
			lastNameText.setText(t.getLastName());
			emailText.setText(t.getEmail());
			academicTitleText.setText(t.getAcademicTitle());
			roomText.setText(t.getRoom());

		}

		currentTeacher = t;
	}

	public void setCurrentTeacher() {
		currentTeacher.setAcademicTitle(academicTitleText.getText());
		currentTeacher.setFirstName(nameText.getText());
		currentTeacher.setLastName(lastNameText.getText());
		currentTeacher.setEmail(emailText.getText());
		currentTeacher.setRoom(roomText.getText());
	}

	@Override
	public void disable() {
		clear();
		setEnabled(false);
	}
}
