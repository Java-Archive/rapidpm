package org.rapidpm.webapp.vaadin.ui.workingareas.zeiterfassung.logic.jira;

import com.vaadin.server.VaadinService;
import com.vaadin.ui.Table;
import org.rapidpm.data.table.BaseTableSimpleCreatorExecutor;
import org.rapidpm.data.table.TableCreator;
import org.rapidpm.data.table.export.Table2CSV;
import org.rapidpm.data.table.export.Table2XLSX;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.zeiterfassung.model.*;

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

    public JiraSoapClientVaadin(final MainUI ui, final Table vaadinTable, final String fileName,
                                final ExportTypes exportType){
        userWorkLogs = (List<UserWorkLog>) vaadinTable.getItemIds();
        final TimeSheetReport timeSheetReport = new TimeSheetReport();

        timeSheetReport.getUserWorkLogs().addAll(userWorkLogs);
        timeSheetReport.execute();
        final Map<TimeSheetReport.IssueDayUserKey,Long> issueDayUserKey2TimeMap = timeSheetReport.getIssueDayUserKey2TimeMap();

        final org.rapidpm.data.table.Table table = new BaseTableSimpleCreatorExecutor() {
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

        //final SoapTable soapTable = table.exportAsSoapTable();
        File file = null;
        switch(exportType){
            case CSV:
                file = new File(table.getTableName()+".csv");
                final Table2CSV table2CSV = new Table2CSV();
                final String string = table2CSV.export(table);
                try {
                    final FileOutputStream fos = new FileOutputStream(file);
                    fos.write(string.getBytes());
                    fos.flush();
                    fos.close();
                } catch (final FileNotFoundException e) {
                    System.out.println("e = " + e);
                } catch (final IOException e) {
                    System.out.println("e = " + e);
                }
                break;
            case XLSX:
                file = new File(table.getTableName()+".csv");
                final Table2XLSX table2XLSX = new Table2XLSX();
                final ByteArrayOutputStream byteArrayOutputStream2 = table2XLSX.export(table);
                try {
                    final FileOutputStream fos = new FileOutputStream(file);
                    fos.write(byteArrayOutputStream2.toByteArray());
                    fos.flush();
                    fos.close();
                } catch (final FileNotFoundException e) {
                    System.out.println("e = " + e);
                } catch (final IOException e) {
                    System.out.println("e = " + e);
                }
                break;
            default:
                break;
        }
        FileDownloadResource fileDownloadResource = new FileDownloadResource(file);



    }

}
