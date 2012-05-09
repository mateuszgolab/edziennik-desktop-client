package com.polsl.edziennik.desktopclient.view.student.summary;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import com.polsl.edziennik.desktopclient.model.tables.GradeTableModel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.common.panels.tablePanels.GradeTablePanel;
import com.polsl.edziennik.modelDTO.grade.RegularGradeDTO;
import com.polsl.edziennik.modelDTO.summary.LabActivitySummary;

public class LabActivitiesSummaryView extends JPanel {
	private GradeTableModel model;
	private SummaryGradeTablePanel panel;
	// private GradePreviewPanel preview;
	private LabActivitySummaryDetails preview;
	private JSplitPane splitPane;
	protected static ResourceBundle bundle = LangManager.getResource(Properties.Component);
	private FrameToolkit frameToolkit = new FrameToolkit();
	private JScrollPane scrollPane;
	private Integer studentId;

	private LabActivitySummary<RegularGradeDTO> as;
	
	
	
	public LabActivitiesSummaryView(Integer studentId) {
		this.studentId = studentId;
		init();
	}

	private void init() {
		FormLayout fl = new FormLayout("fill:pref:grow", "fill:pref:grow");
		DefaultFormBuilder builder = new DefaultFormBuilder(this, fl);
		model = new GradeTableModel();

		// preview = new GradePreviewPanel(model, null, studentId);
		preview = new LabActivitySummaryDetails();
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

	private class SummaryProvider extends Worker<LabActivitySummary<RegularGradeDTO>> {

		@Override
		protected LabActivitySummary<RegularGradeDTO> doInBackground() throws Exception {
			startProgress();
			return DelegateFactory.getCommonDelegate().getLabActivitySummary(studentId);
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

		public SummaryGradeTablePanel(GradeTableModel tableModel) {
			super(tableModel);
			setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(bundle.getString("grades")),
					BorderFactory.createEmptyBorder(0, 6, 6, 6)));
			setPreferredSize(new Dimension(480, 300));
		}

	}

	private class LabActivitySummaryDetails extends JPanel {

		private static final long serialVersionUID = -5294930824385127328L;

		public static final int TEXT_SIZE = 30;

		private LabActivitySummary<RegularGradeDTO> sum;

		private JLabel obecnyNaLabel, nieobecnyNaLabel, odrLabel, sredniaLabel, labsLabel;

		private JTextField usprText;

		private JComboBox nieobecnyCombo, obecnyNaCombo, odrCombo, labsCombo;

		public LabActivitySummaryDetails() {
			init();
		}

		private void init() {

			setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(bundle.getString("details")),
					BorderFactory.createEmptyBorder(0, 6, 6, 6)));

			ILabel label = GuiComponentFactory.getInstance().createLabel();
			obecnyNaLabel = label.getLabel("obecnyNa");
			nieobecnyNaLabel = label.getLabel("nieobecnyNa");
			odrLabel = label.getLabel("odrabianie");
			sredniaLabel = label.getLabel("sredniaZLab");
			labsLabel = label.getLabel("laboratoriaFiltr");
			labsCombo = new JComboBox();
			obecnyNaCombo = new JComboBox();
			usprText = new JTextField(TEXT_SIZE);
			obecnyNaCombo.setEditable(false);
			usprText.setEditable(false);
			nieobecnyCombo = new JComboBox();
			odrCombo = new JComboBox();
			
			labsCombo.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					LabActivitiesSummaryView.this.model.setData(sum.getExTopicRegularGrades().get((String)labsCombo.getSelectedItem()));
					LabActivitiesSummaryView.this.model.fireTableDataChanged();
				}
			});
			// nieobecnyCombo.addItem("aaa");
			// nieobecnyCombo.addItem("bbb");
			// zInnymiCombo.addItem("aaa");
			// zInnymiCombo.addItem("bbb");

			FormLayout fl = new FormLayout("3dlu, 100dlu, 3dlu, 100dlu, 3dlu",
					"15dlu, pref, 15dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu");
			DefaultFormBuilder builder = new DefaultFormBuilder(this, fl);
			CellConstraints cc = new CellConstraints();
			builder.add(labsLabel, cc.xy(2, 2));
			builder.add(labsCombo, cc.xy(4, 2));
			builder.add(obecnyNaLabel, cc.xy(2, 4));
			builder.add(obecnyNaCombo, cc.xy(4, 4));
			builder.add(nieobecnyNaLabel, cc.xy(2, 6));
			builder.add(nieobecnyCombo, cc.xy(4, 6));
			builder.add(odrLabel, cc.xy(2, 8));
			builder.add(odrCombo, cc.xy(4, 8));
			builder.add(sredniaLabel, cc.xy(2, 10));
			builder.add(usprText, cc.xy(4, 10));
		}

		public void setData(LabActivitySummary<RegularGradeDTO> as) {
			this.sum = as;
			for(String date : as.getAbsenceLabs()) {
				nieobecnyCombo.addItem(date);
			}
			for(String key : as.getExTopicRegularGrades().keySet()) {
				obecnyNaCombo.addItem(key);
			}
			for(String s : as.getWithOthergroup()) {
				odrCombo.addItem(s);
			}
			usprText.setText("" + as.getAvgGrade());
			for(String key : as.getExTopicRegularGrades().keySet()) {
				labsCombo.addItem(key);
			}
//			this.sum = as;
//			for (String date : as.getAbsence()) {
//				nieobecnyCombo.addItem(date);
//			}
//			for (String date : as.getAttendanceOtherGroups()) {
//				zInnymiCombo.addItem(date);
//			}
//			obecnyNaText.setText("" + as.getAttendaceCount());
//			usprText.setText("" + as.getJustifiedCount());
		}
	}
}
