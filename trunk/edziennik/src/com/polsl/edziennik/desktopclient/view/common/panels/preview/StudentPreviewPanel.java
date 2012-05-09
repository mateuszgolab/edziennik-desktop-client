package com.polsl.edziennik.desktopclient.view.common.panels.preview;

import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.polsl.edziennik.delegates.AdminDelegate;
import com.polsl.edziennik.delegates.DelegateFactory;
import com.polsl.edziennik.delegates.TeacherDelegate;
import com.polsl.edziennik.delegates.exceptions.DelegateException;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.model.comboBoxes.GroupComboBoxModel;
import com.polsl.edziennik.desktopclient.model.tables.StudentTableModel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.modelDTO.group.GroupDTO;
import com.polsl.edziennik.modelDTO.person.StudentDTO;

public class StudentPreviewPanel extends JPanel {

	public static final int TEXT_SIZE = 30;
	private JLabel name;
	private JLabel secondName;
	private JLabel lastName;
	private JLabel indexNumber;
	private JLabel email;
	private JLabel group;
	private JTextField nameText;
	private JTextField secondNameText;
	private JTextField lastNameText;
	private JTextField indexNumberText;
	private JComboBox groupBox;
	private JTextField emailText;
	private CellConstraints cc;
	private StudentDTO currentStudent;
	private ResourceBundle bundle = LangManager.getResource(Properties.Entity);
	private StudentTableModel model;
	private Integer selected;
	private TeacherDelegate delegate; 
	private GroupComboBoxModel groupComboBoxModel;

	public StudentPreviewPanel(String title, StudentTableModel model, GroupComboBoxModel comboModel) {
		this.model = model;
		this.groupComboBoxModel = comboModel;
		selected = null;
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(title),
				BorderFactory.createEmptyBorder(0, 6, 6, 6)));

		FormLayout layout = new FormLayout(
				"pref,4dlu, 100dlu, 4dlu, min",
				"pref, 2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref");

		setLayout(layout);

		name = new JLabel(bundle.getString("name") + " : ");
		secondName = new JLabel(bundle.getString("secondName") + " : ");
		lastName = new JLabel(bundle.getString("lastName") + " : ");
		indexNumber = new JLabel(bundle.getString("indexNumber") + " : ");
		email = new JLabel(bundle.getString("email") + " : ");
		group = new JLabel(bundle.getString("group") + " : ");

		nameText = new JTextField(TEXT_SIZE);
		secondNameText = new JTextField(TEXT_SIZE);
		lastNameText = new JTextField(TEXT_SIZE);
		indexNumberText = new JTextField(TEXT_SIZE);
		emailText = new JTextField(TEXT_SIZE);
		
		groupBox = new JComboBox(groupComboBoxModel);
		

		setComponents();
		setEnabled(false);
		
	

	}

	public void setComponents() {
		cc = new CellConstraints();

		add(name, cc.xy(1, 3));
		add(nameText, cc.xy(3, 3));
		add(secondName, cc.xy(1, 5));
		add(secondNameText, cc.xy(3, 5));
		add(lastName, cc.xy(1, 7));
		add(lastNameText, cc.xy(3, 7));
		add(indexNumber, cc.xy(1, 9));
		add(indexNumberText, cc.xy(3, 9));
		add(email, cc.xy(1, 11));
		add(emailText, cc.xy(3, 11));
		add(group, cc.xy(1, 13));
		add(groupBox, cc.xy(3, 13));

	}

	public void setData(StudentDTO s) {
		// nowy student
		if (s == null) {
			clear();
		} else {
			nameText.setText(s.getFirstName());
			secondNameText.setText(s.getSecondName());
			lastNameText.setText(s.getLastName());
			indexNumberText.setText(s.getIndexNumber());
			emailText.setText(s.getEmail());
			groupBox.setSelectedItem(s.getGroup());
		}

		currentStudent = s;
	}

	public void clear() {
		nameText.setText("");
		secondNameText.setText("");
		lastNameText.setText("");
		indexNumberText.setText("");
		emailText.setText("");
		groupBox.removeAll();
	}

	public void setEnabled(boolean b) {
		nameText.setEnabled(b);
		secondNameText.setEnabled(b);
		lastNameText.setEnabled(b);
		indexNumberText.setEnabled(b);
		emailText.setEnabled(b);
		groupBox.setEnabled(b);
	}

	public void saveStudent() throws DelegateException 
	{
		delegate = DelegateFactory.getTeacherDelegate();
		
		// zapis nowego studenta
		if (currentStudent == null) {
			currentStudent = new StudentDTO();
			setStudentData();
			model.add(currentStudent);
			delegate.addStudent(currentStudent);

		} else {
			setStudentData();
			model.update(currentStudent, selected);
			delegate.updateStudent(currentStudent);

		}
	}

	public StudentDTO getCurrentStudent() {
		return currentStudent;

	}

	private void setStudentData() {
		currentStudent.setFirstName(nameText.getText());
		currentStudent.setSecondName(secondNameText.getText());
		currentStudent.setLastName(lastNameText.getText());
		currentStudent.setIndexNumber(indexNumberText.getText());
		currentStudent.setEmail(emailText.getText());
		currentStudent.setGroup((GroupDTO) groupBox.getSelectedItem());
		if(groupBox.getSelectedItem() != null) currentStudent.setGroupId(((GroupDTO) groupBox.getSelectedItem()).getId());
	}

	public void setSelected(int index) {
		selected = index;
	}

	public GroupComboBoxModel getGroupCombo()
	{
		return groupComboBoxModel;
	}
	
	@Override
	public void disable()
	{
		clear();
		setEnabled(false);
	}

	
}
