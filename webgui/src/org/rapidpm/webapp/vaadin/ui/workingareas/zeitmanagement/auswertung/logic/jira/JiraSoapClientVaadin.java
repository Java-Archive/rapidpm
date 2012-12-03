package org.rapidpm.webapp.vaadin.ui.workingareas.zeitmanagement.auswertung.logic.jira;

import com.vaadin.ui.Table;
import org.rapidpm.data.table.BaseTableSimpleCreatorExecutor;
import org.rapidpm.data.table.TableCreator;
import org.rapidpm.data.table.export.Table2CSV;
import org.rapidpm.data.table.export.Table2XLSX;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.zeitmanagement.auswertung.model.*;

import java.io.*;
import java.util.List;
import java.util.Map;

import static org.rapidpm.data.table.ColumnProperty.Sortable;

/**
 * Copyright by Sven Ruppert // chef@sven-ruppert.de
 * <p/>
 * User: svenruppert
 * Date: 01.10.12
 * Time: 16:57
 * <p/>
 * Version:
 */

public class JiraSoapClientVaadin {

    private List<UserWorkLog> userWorkLogs;
    private org.rapidpm.data.table.Table table;

    public JiraSoapClientVaadin(final MainUI ui, final Table vaadinTable, final String fileName){
        userWorkLogs = (List<UserWorkLog>) vaadinTable.getItemIds();
        final TimeSheetReport timeSheetReport = new TimeSheetReport();

        timeSheetReport.getUserWorkLogs().addAll(userWorkLogs);
        timeSheetReport.execute();
        final Map<TimeSheetReport.IssueDayUserKey,Long> issueDayUserKey2TimeMap = timeSheetReport.getIssueDayUserKey2TimeMap();

        table = new BaseTableSimpleCreatorExecutor() {
            @Override
            protected void initColDef(final TableCreator tableCreator) {
                for(final Object column : vaadinTable.getVisibleColumns()){
                    if(!vaadinTable.isColumnCollapsed(column)){
                        tableCreator.addNextColInfo(column.toString(),String.class, Sortable);
                    }
                }
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void createRows(final TableCreator tableCreator) {
                for (final Map.Entry<TimeSheetReport.IssueDayUserKey, Long> entry : issueDayUserKey2TimeMap.entrySet()) {
                    tableCreator.addNewRow();

                    final TimeSheetReport.IssueDayUserKey key = entry.getKey();
                    for(Object column : vaadinTable.getVisibleColumns()){
                        switch(column.toString()){
                            case(UserWorkLog.ASSIGNEE):
                                tableCreator.addNextCellRawData(UserWorkLog.ASSIGNEE, key.getAssignee());
                                break;
                            case(UserWorkLog.COMMENT):
                                tableCreator.addNextCellRawData(UserWorkLog.COMMENT, key.getComment());
                                break;
                            case(UserWorkLog.ISSUEKEY):
                                tableCreator.addNextCellRawData(UserWorkLog.ISSUEKEY, key.getIssuekey());
                                break;
                            case(UserWorkLog.SUMMARY):
                                tableCreator.addNextCellRawData(UserWorkLog.SUMMARY, key.getSummary());
                                break;
                            case(UserWorkLog.UPDATED):
                                tableCreator.addNextCellRawData(UserWorkLog.UPDATED, key.getUpdated());
                                break;
                            case(UserWorkLog.AUTHOR):
                                tableCreator.addNextCellRawData(UserWorkLog.AUTHOR, key.getAuthor());
                                break;
                            case(UserWorkLog.CREATED):
                                tableCreator.addNextCellRawData(UserWorkLog.CREATED, key.getCreated());
                                break;
                            case(UserWorkLog.TIMESPENTSTRING):
                                tableCreator.addNextCellRawData(UserWorkLog.TIMESPENTSTRING, key.getTimeSpentString());
                                break;
                            case(UserWorkLog.TIMESPENTINMINS):
                                tableCreator.addNextCellRawData(UserWorkLog.TIMESPENTINMINS, key.getTimeSpentInMins());
                                break;

                        }
                    }
                }
            }
            @Override
            protected String getTableName() {
                return fileName;
            }
        }.execute();
    }

    public org.rapidpm.data.table.Table getTable(){
        return table;
    }


}
