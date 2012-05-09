package com.polsl.edziennik.desktopclient.view.student.summary;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jidesoft.swing.JideScrollPane;
import com.polsl.edziennik.delegates.DelegateFactory;
import com.polsl.edziennik.desktopclient.controller.utils.FrameToolkit;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.controller.utils.factory.GuiComponentFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.ILabel;
import com.polsl.edziennik.desktopclient.controller.utils.workers.Worker;
import com.polsl.edziennik.desktopclient.model.tables.TableModel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.common.panels.tablePanels.GradeTablePanel;
import com.polsl.edziennik.modelDTO.exam.ExamDTO;
import com.polsl.edziennik.modelDTO.grade.ExamTaskGradeDTO;
import com.polsl.edziennik.modelDTO.summary.ExamSummary;


public class ExamSummaryView extends JPanel {

	private ExamGradeTableModel model;
	private SummaryGradeTablePanel panel;
	// private GradePreviewPanel preview;
	private ExamSummaryDetails preview;
	private JSplitPane splitPane;
	protected static ResourceBundle bundle = LangManager.getResource(Properties.Component);
	private FrameToolkit frameToolkit = new FrameToolkit();
	private JScrollPane scrollPane;
	private Integer studentId;

	private ExamSummary<ExamDTO, ExamTaskGradeDTO> as;
	
	public ExamSummaryView(Integer studentId) {
		this.studentId = studentId;
		init();
	}
	private void init() {
		FormLayout fl = new FormLayout("fill:pref:grow", "fill:pref:grow");
		DefaultFormBuilder builder = new DefaultFormBuilder(this, fl);
		model = new ExamGradeTableModel();

		// preview = new GradePreviewPanel(model, null, studentId);
		preview = new ExamSummaryDetails();
		panel = new SummaryGradeTablePanel(model);

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setDividerLocation(0.1);
		splitPane.setDividerSize(1);
		splitPane.add(panel);
		splitPane.add(preview);
		scrollPane = new JideScrollPane(splitPane);
		scrollPane.setHorizontalScrollBarPolicy(JideScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		// add(scrollPane, BorderLayout.CENTER);
		add(scrollPane, "1,1");
		new SummaryProvider().execute();
	}

	private class SummaryProvider extends Worker<ExamSummary<ExamDTO, ExamTaskGradeDTO>> {

		@Override
		protected ExamSummary<ExamDTO, ExamTaskGradeDTO> doInBackground() throws Exception {
			startProgress();
			return DelegateFactory.getCommonDelegate().getExamSummary(studentId);
		}

		@Override
		public void done() {
			try {
				as = get();
//				model.setModel(get().getExTopicRegularGrades());
//				model.fireTableDataChanged();
				preview.setData(get());
				stopProgress();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private class SummaryGradeTablePanel extends GradeTablePanel {

		private static final long serialVersionUID = -1860137072833690365L;

		public SummaryGradeTablePanel(ExamGradeTableModel tableModel) {
			super(tableModel);
			setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(bundle.getString("grades")),
					BorderFactory.createEmptyBorder(0, 6, 6, 6)));
			setPreferredSize(new Dimension(480, 300));
		}

	}
	private class ExamSummaryDetails extends JPanel {

		private static final long serialVersionUID = -5294930824385127328L;

		public static final int TEXT_SIZE = 30;

		private ExamSummary<ExamDTO, ExamTaskGradeDTO> sum;

		private JLabel  sredniaLabel, labsLabel;

		private JTextField usprText;

		private JComboBox  labsCombo;

		public ExamSummaryDetails() {
			init();
		}

		private void init() {

			setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(bundle.getString("details")),
					BorderFactory.createEmptyBorder(0, 6, 6, 6)));

			ILabel label = GuiComponentFactory.getInstance().createLabel();
			sredniaLabel = label.getLabel("ocenaKoncowa");
			labsLabel = label.getLabel("egzaminyFiltr");
			labsCombo = new JComboBox();
			usprText = new JTextField(TEXT_SIZE);
			usprText.setEditable(false);
			
			
			labsCombo.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					List<ExamTaskGradeDTO> ets = null;
					int i = 0;
					for(ExamDTO ed : sum.getExamTaskGrades().keySet()) {
						if( i == labsCombo.getSelectedIndex()) {
							ets = sum.getExamTaskGrades().get(ed);
						}
					}
					ExamSummaryView.this.model.setData(ets);
					ExamSummaryView.this.model.fireTableDataChanged();
				}
			});
			// nieobecnyCombo.addItem("aaa");
			// nieobecnyCombo.addItem("bbb");
			// zInnymiCombo.addItem("aaa");
			// zInnymiCombo.addItem("bbb");

			FormLayout fl = new FormLayout("3dlu, 100dlu, 3dlu, 100dlu, 3dlu",
					"15dlu, pref, 15dlu, pref, 5dlu");
			DefaultFormBuilder builder = new DefaultFormBuilder(this, fl);
			CellConstraints cc = new CellConstraints();
			builder.add(labsLabel, cc.xy(2, 2));
			builder.add(labsCombo, cc.xy(4, 2));
			
			builder.add(sredniaLabel, cc.xy(2, 4));
			builder.add(usprText, cc.xy(4, 4));
		}

		public void setData(ExamSummary<ExamDTO, ExamTaskGradeDTO> as) {
			this.sum = as;
			for(ExamDTO e : as.getExamTaskGrades().keySet()) {
				labsCombo.addItem(e.toString());
			}
			usprText.setText("" + as.getFinalGrade());
//			for(String date : as.getAbsenceLabs()) {
//				nieobecnyCombo.addItem(date);
//			}
//			for(String key : as.getExTopicRegularGrades().keySet()) {
//				obecnyNaCombo.addItem(key);
//			}
//			for(String s : as.getWithOthergroup()) {
//				odrCombo.addItem(s);
//			}
//			usprText.setText("" + as.getAvgGrade());
//			for(String key : as.getExTopicRegularGrades().keySet()) {
//				labsCombo.addItem(key);
//			}

		}
	}
	private class ExamGradeTableModel extends TableModel<ExamTaskGradeDTO> {

		public ExamGradeTableModel(List<ExamTaskGradeDTO> g) {
			super();
			addGrades(g);
		}

		public ExamGradeTableModel() {
			super();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			if (rowIndex > -1 && rowIndex < data.size() && columnIndex > -1 && columnIndex < classNames.length) {

				if (data.get(rowIndex) == null) {
					if (columnIndex == 0)
						return rowIndex + 1;
					else
						return null;
				}

				switch (columnIndex) {
				case 0:
					return data.get(rowIndex).getGrade();
				case 1:
					return data.get(rowIndex).getDescription();
				default:
					return null;

				}
			} else {
				return null;
			}
		}

		@Override
		public void setColumns() {
			columnNames = new String[] { entity.getString("grade"), entity.getString("gradeType") };
			classNames = new Class[] { Float.class, String.class, Float.class };

		}

		public void addGrades(List<ExamTaskGradeDTO> grades) {
			for (ExamTaskGradeDTO g: grades)
				add(g);

		}

	}
}