package com.polsl.edziennik.desktopclient.view.common.panels.button;

import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.jgoodies.forms.layout.CellConstraints;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.controller.utils.factory.GuiComponentFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.IGuiComponentAbstractFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.IButton;
import com.polsl.edziennik.desktopclient.properties.Properties;

public class ProjectPreviewButtonPanel extends JPanel{
	
	private JButton students;
	private JButton projectActivities;
	private JButton grades;
	protected ResourceBundle components = LangManager.getResource(Properties.Component);
	protected IGuiComponentAbstractFactory factory = GuiComponentFactory.getInstance();
	protected IButton button;
	private CellConstraints cc;
	
	public ProjectPreviewButtonPanel()
	{
		//super(new FlowLayout(FlowLayout.LEADING, 6, 6));
	
//		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Zarz¹dzaj studentami"),
//				BorderFactory.createEmptyBorder(0, 6, 6, 6)));
		
//		FormLayout layout = new FormLayout(
//				"pref,10dlu, min,10dlu, min",
//				"pref, 2dlu, pref, 2dlu, pref,2dlu, pref,");
//
//		setLayout(layout);
		
		button = factory.createTextButton();
		
		students = button.getButton("studentsButton", "studentsHint");
		projectActivities = button.getButton("projectActivitiesButton", "projectActivitiesHint");
		grades = button.getButton("gradesButton", "gradesHint");
//		cc = new CellConstraints();
//		
//		add(students,cc.xy(1, 1));
//		add(grades,cc.xy(1, 3));
//		add(attendances, cc.xy(3, 1));
//		add(addGradesSheet, cc.xy(3, 3));
		
		add(students);
		add(projectActivities);
		add(grades);
		
		

	}
	
	
	public void activate(boolean value)
	{
		
		projectActivities.setEnabled(value);
		students.setEnabled(value);
		grades.setEnabled(value);
	}
	

	public JButton getStudentsButton()
	{
		return students;
	}


	public JButton getProjectActivitiesButton()
	{
		return projectActivities;
	}
	
	public JButton getGradesButton()
	{
		return grades;
	}
}
