package org.rapidpm.webapp.vaadin.ui.workingareas.zeiterfassung.logic.jira;

import org.rapidpm.webapp.vaadin.ui.workingareas.zeiterfassung.model.UserWorkLog;
import org.rapidpm.webapp.vaadin.ui.workingareas.zeiterfassung.model.jira.generated.JirasoapserviceV2SoapBindingStub;
import org.rapidpm.webapp.vaadin.ui.workingareas.zeiterfassung.model.jira.generated.RemoteIssue;
import org.rapidpm.webapp.vaadin.ui.workingareas.zeiterfassung.model.jira.generated.RemoteWorklog;

import javax.xml.rpc.ServiceException;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright by Sven Ruppert // chef@sven-ruppert.de
 * <p/>
 * User: svenruppert
 * Date: 01.10.12
 * Time: 16:57
 * <p/>
 * Version:
 */
public class JiraSoapClient {

    public static final String USER = "???";
    public static final String PASSWD = "xxx";

    private JiraRequestExecutor<List<UserWorkLog>> executor;
    private List<UserWorkLog> userWorkLogs;

    public JiraSoapClient(final String query) {
        executor = new JiraRequestExecutor<List<UserWorkLog>>(USER, PASSWD) {
            @Override
            public List<UserWorkLog> executeTask(final String secureToken, final JirasoapserviceV2SoapBindingStub binding) throws java.rmi.RemoteException {
//                final String query = "project=\"CDTMS\" and type = Story and status = Open";
                final List<UserWorkLog> userWorkLogs = new ArrayList<>();

                //final String query = "project = \"JTEL Developer\" AND updatedDate >= \"2012-10-01\"";
                final RemoteIssue[] issuesFromJqlSearch = binding.getIssuesFromJqlSearch(secureToken, query, Integer.MAX_VALUE);
                for (final RemoteIssue remoteIssue : issuesFromJqlSearch) {
                    final String remoteIssueKey = remoteIssue.getKey();
                    final RemoteWorklog[] worklogs = binding.getWorklogs(secureToken, remoteIssueKey);
                    if (worklogs.length > 0) {
                        for (final RemoteWorklog worklog : worklogs) {
                            final UserWorkLog userWorkLog = new UserWorkLog();
                            if (remoteIssueKey == null) {
                                userWorkLog.setIssueKey("");
                            } else {
                                userWorkLog.setIssueKey(remoteIssueKey);
                            }
                            if (remoteIssue.getSummary() == null) {
                                userWorkLog.setSummary("");
                            } else {
                                userWorkLog.setSummary(remoteIssue.getSummary());
                            }

                            //userWorkLog.setTimeInSeconds(worklog.getTimeSpentInSeconds());

                            if (worklog.getAuthor() == null) {
                                userWorkLog.setAuthor("");
                            } else {
                                userWorkLog.setAuthor(worklog.getAuthor());
                            }

                            if (remoteIssue.getAssignee() == null) {
                                userWorkLog.setAssignee("");
                            } else {
                                userWorkLog.setAssignee(remoteIssue.getAssignee());
                            }

                            if (worklog.getComment() == null) {
                                userWorkLog.setComment("");
                            } else {
                                userWorkLog.setComment(worklog.getComment());
                            }

                            userWorkLog.setCreated(worklog.getCreated().getTime());

                            userWorkLog.setUpdated(worklog.getUpdated().getTime());

                            userWorkLog.setTimeSpentString(worklog.getTimeSpent());

                            userWorkLog.setTimeSpentInMins(String.valueOf(worklog.getTimeSpentInSeconds() / 60));

                            userWorkLogs.add(userWorkLog);
                        }
                    } else {
                        //
                    }
                }
                return userWorkLogs;
            }

        };

        try {
            userWorkLogs = executor.execute();
        } catch (ServiceException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        //final TimeSheetReport timeSheetReport = new TimeSheetReport();

        //timeSheetReport.getUserWorkLogs().addAll(userWorkLogs);
        //timeSheetReport.execute();
//        final Map<TimeSheetReport.IssueDayUserKey,Long> issueDayUserKey2TimeMap = timeSheetReport.getIssueDayUserKey2TimeMap();

//        final Table table = new BaseTableSimpleCreatorExecutor() {
//            @Override
//            protected void initColDef(final TableCreator tableCreator) {
//                tableCreator.addNextColInfo(CREATED, String.class, Sortable);
//                tableCreator.addNextColInfo(AUTHOR, String.class, Sortable);
//
//                final CellValueFormatter<Float> formatter = new CellValueFormatter<Float>(){
//                    @Override
//                    public String format(Float aFloat) {
//                        return String.format("%,2f", aFloat) ;
//                    }
//                };
//                tableCreator.addNextColInfo(TIME_H, Float.class, formatter, Sortable);
//
//
//                tableCreator.addNextColInfo(ISSUE_KEY, String.class, Sortable);
////               tableCreator.addNextColInfo(UPDATED, String.class, ColumnProperty.Sortable);
////                tableCreator.addNextColInfo(SUMMARY, String.class, ColumnProperty.Sortable);
////                tableCreator.addNextColInfo(COMMENT, String.class, ColumnProperty.Sortable);
//            }
//
//            @SuppressWarnings("unchecked")
//            @Override
//            protected void createRows(final TableCreator tableCreator) {
//                for (final Map.Entry<TimeSheetReport.IssueDayUserKey, Long> entry : issueDayUserKey2TimeMap.entrySet()) {
//                    tableCreator.addNewRow();
//                    final TimeSheetReport.IssueDayUserKey key = entry.getKey();
//                    tableCreator.addNextCellRawData(AUTHOR, key.getAuthor());
//                    tableCreator.addNextCellRawData(ISSUE_KEY, key.getIssuekey());
//                    tableCreator.addNextCellRawData(CREATED, key.getCreated());
////                    tableCreator.addNextCellRawData(UPDATED, key.getDay());
////                    tableCreator.addNextCellRawData(COMMENT, );
////                    tableCreator.addNextCellRawData(SUMMARY,);
//                    final float timeInHours = entry.getValue() / 60f / 60f;
//                    tableCreator.addNextCellRawData(TIME_H, timeInHours);
//                }
//            }
//            @Override
//            protected String getTableName() {
//                return "TimeSheetReport";
//            }
//        }.execute();

        //final SoapTable soapTable = table.exportAsSoapTable();
//        final Table2XLSX table2XLSX = new Table2XLSX();
//        final ByteArrayOutputStream byteArrayOutputStream = table2XLSX.export(table);
//        try {
//            final FileOutputStream fos = new FileOutputStream(table.getTableName()+".xlsx");
//            fos.write(byteArrayOutputStream.toByteArray());
//            fos.flush();
//            fos.close();
//
//        } catch (FileNotFoundException e) {
//            System.out.println("e = " + e);
//        } catch (IOException e) {
//            System.out.println("e = " + e);
//        }
    }

    public List<UserWorkLog> getUserWorkLogs() {
        return userWorkLogs;
    }

    public JiraRequestExecutor<List<UserWorkLog>> getExecutor() {
        return executor;
    }
}
