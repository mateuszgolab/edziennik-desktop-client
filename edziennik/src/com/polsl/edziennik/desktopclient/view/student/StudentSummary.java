package com.polsl.edziennik.desktopclient.view.student;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.polsl.edziennik.delegates.DelegateFactory;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.controller.utils.factory.GuiComponentFactory;
import com.polsl.edziennik.desktopclient.controller.utils.workers.Worker;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.common.tabbedpanes.ClosableTabbedPane;
import com.polsl.edziennik.desktopclient.view.student.summary.ActivitiesSummaryView;
import com.polsl.edziennik.desktopclient.view.student.summary.ExamSummaryView;
import com.polsl.edziennik.desktopclient.view.student.summary.LabActivitiesSummaryView;
import com.polsl.edziennik.desktopclient.view.student.summary.ProjectSummaryView;
import com.polsl.edziennik.modelDTO.grade.FinalGradeDTO;
import com.polsl.edziennik.modelDTO.person.StudentDTO;

/**
 * Musisz sobie jakos przekazywac do tej klasy studentId. Nie wiem jak bedziesz
 * tego uzywal ale mam nadzieje ze ty wiesz:P
 * 
 * @author macienko
 * 
 */
public class StudentSummary extends JPanel {

	protected ResourceBundle bundle = LangManager.getResource(Properties.Component);
	protected ClosableTabbedPane tabbedPane;
	protected Integer id;

	public StudentSummary(Integer id) {
		this.id = id;
		FormLayout fl = new FormLayout("fill:pref:grow", "fill:pref:grow, 50dlu");
		DefaultFormBuilder builder = new DefaultFormBuilder(this, fl);
		tabbedPane = new ClosableTabbedPane();
		CellConstraints cc = new CellConstraints();
		// add(tabbedPane, BorderLayout.CENTER);
		builder.add(tabbedPane, cc.xy(1, 1));
		// setPreferredSize(new Dimension(800, 600));
		// TODO DLA MATIKA: wpisac poprawne id studenta
		builder.add(new FinalGradePanel(id), cc.xy(1, 2));
		// TODO DLA MATIKA: wpisac poprawne id studenta
		ExamSummaryView exams = new ExamSummaryView(id);
		// TODO DLA MATIKA: wpisac poprawne id studenta
		ProjectSummaryView projects = new ProjectSummaryView(id);
		// TODO DLA MATIKA: wpisac poprawne id studenta
		LabActivitiesSummaryView labs = new LabActivitiesSummaryView(id);
		// TODO DLA MATIKA: wpisac poprawne id studenta
		ActivitiesSummaryView activities = new ActivitiesSummaryView(id);
		// add(activities);
		addTab("egzaminy", exams, new ImageIcon());
		addTab("projekty", projects, new ImageIcon());
		addTab("laboratoria", labs, new ImageIcon());
		addTab("æwiczenia tablicowe", activities, new ImageIcon());
	}

	public void addTab(String name, JPanel panel, ImageIcon icon) {
		tabbedPane.addTab(name, panel);
		tabbedPane.setIconAt(tabbedPane.getTabCount() - 1, icon);
		tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
	}

	private class FinalGradePanel extends JPanel {

		private static final long serialVersionUID = 2976613427351861199L;

		private final Integer studentId;

		private JLabel ocenaLabel;

		private JTextField ocenaField;

		private JButton zapiszButton;

		private JButton obliczButton;

		private StudentDTO student;

		protected ResourceBundle bundle = LangManager.getResource(Properties.Entity);

		public FinalGradePanel(Integer studentId) {
			setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("ocenaKoncowa"),
					BorderFactory.createEmptyBorder(0, 6, 6, 6)));

			this.studentId = studentId;

			ocenaLabel = GuiComponentFactory.getInstance().createLabel().getLabel("finalGrade");
			ocenaField = new JTextField(4);
			new Worker<StudentDTO>() {

				@Override
				protected StudentDTO doInBackground() throws Exception {
					startProgress();
					return DelegateFactory.getTeacherDelegate().getStudent(FinalGradePanel.this.studentId);
				}

				@Override
				public void done() {
					try {
						stopProgress();
						student = get();
						if (student != null && student.getFinalGrade() != null) {
							ocenaField.setText("" + student.getFinalGrade().getFinalGrade());
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}.execute();
			zapiszButton = new JButton("Zapisz");
			obliczButton = new JButton("Oblicz");
			zapiszButton.setEnabled(false);
			FormLayout fl = new FormLayout("60dlu, 5dlu, 80dlu, 5dlu, 80dlu, 5dlu, 80dlu", "25dlu");
			DefaultFormBuilder builder = new DefaultFormBuilder(this, fl);
			CellConstraints cc = new CellConstraints();
			builder.add(ocenaLabel, cc.xy(1, 1));
			builder.add(ocenaField, cc.xy(3, 1));
			builder.add(zapiszButton, cc.xy(5, 1));
			builder.add(obliczButton, cc.xy(7, 1));
			ocenaField.addCaretListener(new CaretListener() {

				@Override
				public void caretUpdate(CaretEvent e) {
					try {
						Float f = Float.parseFloat(ocenaField.getText());
						if (f < 1.5f || f > 6.0f) {
							zapiszButton.setEnabled(false);
							return;
						}
						zapiszButton.setEnabled(true);
					} catch (NumberFormatException ex) {
						zapiszButton.setEnabled(false);
					}
				}
			});
			obliczButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					new Worker<Float>() {

						@Override
						protected Float doInBackground() throws Exception {
							startProgress();
							return DelegateFactory.getTeacherDelegate().getFinalGrade(FinalGradePanel.this.studentId);
						}

						@Override
						public void done() {
							try {
								stopProgress();
								if (get().equals(-1.0f)) {
									JOptionPane.showMessageDialog(FinalGradePanel.this,
											"Nie znaleziono poprawnej klasy lub metody do obliczenia oceny");
								} else {
									ocenaField.setText("" + get());
								}
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ExecutionException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					}.execute();
				}
			});
			zapiszButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					new Worker<FinalGradeDTO>() {

						@Override
						protected FinalGradeDTO doInBackground() throws Exception {
							startProgress();
							return DelegateFactory.getTeacherDelegate().setFinalGrade(
									new FinalGradeDTO(Float.parseFloat(ocenaField.getText()), student));
						}

						@Override
						public void done() {
							stopProgress();
						}
					}.execute();
				}
			});
		}
	}
}
