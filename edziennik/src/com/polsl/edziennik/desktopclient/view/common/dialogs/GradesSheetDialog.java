package com.polsl.edziennik.desktopclient.view.common.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.jidesoft.swing.JideScrollPane;
import com.polsl.edziennik.desktopclient.controller.utils.FrameToolkit;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.model.tables.RegularGradeTableModel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.common.panels.GradePanel;
import com.polsl.edziennik.desktopclient.view.common.panels.button.SaveExitButtonPanel;
import com.polsl.edziennik.modelDTO.grade.GradeTypeDTO;
import com.polsl.edziennik.modelDTO.grade.RegularGradeDTO;
import com.polsl.edziennik.modelDTO.person.StudentDTO;

public class GradesSheetDialog extends JDialog
{
    
    private JTable table;
    private JScrollPane scrollPane;
    private RegularGradeTableModel tableModel;
    private FrameToolkit frameToolkit = new FrameToolkit();
    protected static ResourceBundle bundle = LangManager.getResource(Properties.Component);
    private SaveExitButtonPanel buttonPanel;
    private GradePanel gradePanel;
    private JPanel panel;
    
    public GradesSheetDialog(RegularGradeTableModel model, String title)
    {
        setTitle(bundle.getString(title));
        Dimension preferredSize = frameToolkit.getSize();
        preferredSize.setSize(30 + 140 * 3 + 10 + 40, preferredSize.getHeight());
        setSize(preferredSize);
        setIconImage(frameToolkit.getTitleIcon("GradesIcon"));
        setLocationRelativeTo(null);
        
        tableModel = model;
        table = new JTable(tableModel);
        table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowSelectionAllowed(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        scrollPane = new JideScrollPane(table);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(""),
                BorderFactory.createEmptyBorder(0, 6, 6, 6)));
        
        add(scrollPane, BorderLayout.CENTER);
        
        gradePanel = new GradePanel();
        
        buttonPanel = new SaveExitButtonPanel("saveChangesHint");
        
        panel = new JPanel(new BorderLayout());
        
        panel.add(gradePanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(panel, BorderLayout.SOUTH);
        
        setColumnWidths();
    }
    
    public void setColumnWidths()
    {
        if (table.getColumnCount() <= 0)
            return;
        table.getColumnModel().getColumn(0).setPreferredWidth(30);
        for (int i = 1; i < 4; i++)
        {
            table.getColumnModel().getColumn(i).setPreferredWidth(140);
            
        }
    }
    
    public void setComboModel(List<GradeTypeDTO> l)
    {
        gradePanel.setModel(l);
    }
    
    public void setModel(List<StudentDTO> list)
    {
        if (list != null && list.size() > 0)
        {
            
            RegularGradeDTO g;
            List<RegularGradeDTO> grades = new ArrayList<RegularGradeDTO>(list.size());
            for (StudentDTO s : list)
            {
                g = new RegularGradeDTO();
                g.setStudent(s);
                if (s.getGrades() != null && s.getGrades().size() > 0)
                    g.setGrade(s.getGrades().get(0).getGrade());
                
                grades.add(g);
                
            }
            
            tableModel.setModel(grades);
            
        }
    }
    
    public Map<Integer, Float> getMap()
    {
        Map<Integer, Float> map = new HashMap<Integer, Float>(tableModel.getRowCount());
        for (RegularGradeDTO g : tableModel.getData())
        {
            
            if (g.getStudentId() != null)
                map.put(g.getStudentId(), g.getGrade());
            else if (g.getStudent() != null && g.getStudent().getId() != null)
                map.put(g.getStudent().getId(), g.getGrade());
        }
        
        return map;
    }
    
    public String getDescription()
    {
        return gradePanel.getDescription();
    }
    
    
    public String getTypeData()
    {
        return gradePanel.getType();
    }
    
    public void clearSelection()
    {
        table.clearSelection();
    }
    
    public JButton getSaveButton()
    {
        return buttonPanel.getSaveButton();
    }
    
    public JButton getExitButton()
    {
        return buttonPanel.getExitButton();
    }
    
}
