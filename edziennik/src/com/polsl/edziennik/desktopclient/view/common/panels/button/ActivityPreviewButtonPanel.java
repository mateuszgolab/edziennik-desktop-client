package com.polsl.edziennik.desktopclient.view.common.panels.button;

import java.awt.FlowLayout;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.controller.utils.factory.GuiComponentFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.IGuiComponentAbstractFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.IButton;
import com.polsl.edziennik.desktopclient.properties.Properties;

public class ActivityPreviewButtonPanel extends JPanel{
	
	protected JButton students;
	protected JButton attendances;
	protected JButton grades;
	protected JButton addGradesSheet;
	protected ResourceBundle components = LangManager.getResource(Properties.Component);
	protected IGuiComponentAbstractFactory factory = GuiComponentFactory.getInstance();
	protected IButton Button;
	private CellConstraints cc;
	
	public ActivityPreviewButtonPanel()
	{
		//super(new FlowLayout(FlowLayout.LEADING, 6, 6));
	
//		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Zarz¹dzaj studentami"),
//				BorderFactory.createEmptyBorder(0, 6, 6, 6)));
		
		FormLayout layout = new FormLayout(
				"pref,10dlu, min,10dlu, min",
				"pref, 2dlu, pref, 2dlu, pref,2dlu, pref,");

		setLayout(layout);
		
		Button = factory.createTextButton();
		
		students = Button.getButton("studentsButton", "studentsButton");
		attendances = Button.getButton("attendancesButton", "attendancesButton");
		grades = Button.getButton("gradesButton", "gradesButton");
		addGradesSheet = Button.getButton("gradesSheetButton", "gradesSheetButton");
		
		cc = new CellConstraints();
		
		add(students,cc.xy(1, 1));
		add(grades,cc.xy(1, 3));
		add(attendances, cc.xy(3, 1));
		add(addGradesSheet, cc.xy(3, 3));
		
		
		

	}
	
	
	public void activate(boolean value)
	{
		attendances.setEnabled(value);
		addGradesSheet.setEnabled(value);
		students.setEnabled(value);
		grades.setEnabled(value);
	}
	

	public JButton getStudentsButton()
	{
		return students;
	}

	public JButton getAttendancesButton()
	{
		return attendances;
	}

	public JButton getAddGradesSheetButton()
	{
		return addGradesSheet;
	}
	
	public JButton getGradesButton()
	{
		return grades;
	}
}
