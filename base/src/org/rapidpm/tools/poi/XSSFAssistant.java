package org.rapidpm.tools.poi;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.xssf.usermodel.*;

import java.io.IOException;
import java.io.OutputStream;

/**
 * RapidPM - www.rapidpm.org
 *
 * @author Sven Ruppert
 * @version 0.1
 *          <p/>
 *          This Source Code is part of the RapidPM - www.rapidpm.org project.
 *          please contact sven.ruppert@me.com
 * @since 16.06.2010
 *        Time: 10:49:30
 */

public class XSSFAssistant {
    private static final Logger logger = Logger.getLogger(XSSFAssistant.class);


    private XSSFWorkbook workbook;

    public XSSFAssistant(final XSSFWorkbook workbook) {
        this.workbook = workbook;
    }

    public enum Alignment {
        HORIZONTAL_LEFT(XSSFCellStyle.ALIGN_LEFT),
        HORIZONTAL_CENTER(XSSFCellStyle.ALIGN_CENTER),
        HORIZONTAL_RIGHT(XSSFCellStyle.ALIGN_RIGHT),
        VERTICAL_TOP(XSSFCellStyle.VERTICAL_TOP),
        VERTICAL_CENTER(XSSFCellStyle.VERTICAL_CENTER),
        VERTICAL_BOTTOM(XSSFCellStyle.VERTICAL_BOTTOM);

        private short value;

        private Alignment(final short value) {
            this.value = value;
        }
    }

    public enum Border {
        DASH_DOT(XSSFCellStyle.BORDER_DASH_DOT),
        DASH_DOT_DOT(XSSFCellStyle.BORDER_DASH_DOT_DOT),
        DASHED(XSSFCellStyle.BORDER_DASHED),
        DOTTED(XSSFCellStyle.BORDER_DOTTED),
        DOUBLE(XSSFCellStyle.BORDER_DOUBLE),
        HAIR(XSSFCellStyle.BORDER_HAIR),
        MEDIUM(XSSFCellStyle.BORDER_MEDIUM),
        MEDIUM_DASH_DOT(XSSFCellStyle.BORDER_MEDIUM_DASH_DOT),
        MEDIUM_DASH_DOT_DOT(XSSFCellStyle.BORDER_MEDIUM_DASH_DOT_DOT),
        MEDIUM_DASHED(XSSFCellStyle.BORDER_MEDIUM_DASHED),
        NONE(XSSFCellStyle.BORDER_NONE),
        SLANTED_DASH_DOT(XSSFCellStyle.BORDER_SLANTED_DASH_DOT),
        THICK(XSSFCellStyle.BORDER_THICK),
        THIN(XSSFCellStyle.BORDER_THIN);

        private short value;

        private Border(final short value) {
            this.value = value;
        }
    }

    public enum Color {
        BLACK(HSSFColor.BLACK.index),
        BROWN(HSSFColor.BROWN.index),
        OLIVE_GREEN(HSSFColor.OLIVE_GREEN.index),
        DARK_GREEN(HSSFColor.DARK_GREEN.index),
        DARK_TEAL(HSSFColor.DARK_TEAL.index),
        DARK_BLUE(HSSFColor.DARK_BLUE.index),
        INDIGO(HSSFColor.INDIGO.index),
        GREY_80_PERCENT(HSSFColor.GREY_80_PERCENT.index),
        ORANGE(HSSFColor.ORANGE.index),
        DARK_YELLOW(HSSFColor.DARK_YELLOW.index),
        GREEN(HSSFColor.GREEN.index),
        TEAL(HSSFColor.TEAL.index),
        BLUE(HSSFColor.BLUE.index),
        BLUE_GREY(HSSFColor.BLUE_GREY.index),
        GREY_50_PERCENT(HSSFColor.GREY_50_PERCENT.index),
        RED(HSSFColor.RED.index),
        LIGHT_ORANGE(HSSFColor.LIGHT_ORANGE.index),
        SEA_GREEN(HSSFColor.SEA_GREEN.index),
        AQUA(HSSFColor.AQUA.index),
        LIGHT_BLUE(HSSFColor.LIGHT_BLUE.index),
        VIOLET(HSSFColor.VIOLET.index),
        GREY_40_PERCENT(HSSFColor.GREY_40_PERCENT.index),
        PINK(HSSFColor.PINK.index),
        GOLD(HSSFColor.GOLD.index),
        YELLOW(HSSFColor.YELLOW.index),
        BRIGHT_GREEN(HSSFColor.BRIGHT_GREEN.index),
        TURQUOISE(HSSFColor.TURQUOISE.index),
        DARK_RED(HSSFColor.DARK_RED.index),
        SKY_BLUE(HSSFColor.SKY_BLUE.index),
        PLUM(HSSFColor.PLUM.index),
        GREY_25_PERCENT(HSSFColor.GREY_25_PERCENT.index),
        ROSE(HSSFColor.ROSE.index),
        LIGHT_YELLOW(HSSFColor.LIGHT_YELLOW.index),
        LIGHT_GREEN(HSSFColor.LIGHT_GREEN.index),
        LIGHT_TURQUOISE(HSSFColor.LIGHT_TURQUOISE.index),
        PALE_BLUE(HSSFColor.PALE_BLUE.index),
        LAVENDER(HSSFColor.LAVENDER.index),
        WHITE(HSSFColor.WHITE.index),
        CORNFLOWER_BLUE(HSSFColor.CORNFLOWER_BLUE.index),
        LEMON_CHIFFON(HSSFColor.LEMON_CHIFFON.index),
        MAROON(HSSFColor.MAROON.index),
        ORCHID(HSSFColor.ORCHID.index),
        CORAL(HSSFColor.CORAL.index),
        ROYAL_BLUE(HSSFColor.ROYAL_BLUE.index),
        LIGHT_CORNFLOWER_BLUE(HSSFColor.LIGHT_CORNFLOWER_BLUE.index),
        TAN(HSSFColor.TAN.index);

        private short index;

        private Color(final short index) {
            this.index = index;
        }
    }

    public enum FillPattern {
        NONE(XSSFCellStyle.NO_FILL),
        SOLID_FOREGROUND(XSSFCellStyle.SOLID_FOREGROUND),
        FINE_DOTS(XSSFCellStyle.FINE_DOTS),
        ALT_BARS(XSSFCellStyle.ALT_BARS),
        SPARSE_DOTS(XSSFCellStyle.SPARSE_DOTS),
        THICK_HORZ_BANDS(XSSFCellStyle.THICK_HORZ_BANDS),
        THICK_VERT_BANDS(XSSFCellStyle.THICK_VERT_BANDS),
        THICK_BACKWARD_DIAG(XSSFCellStyle.THICK_BACKWARD_DIAG),
        THICK_FORWARD_DIAG(XSSFCellStyle.THICK_FORWARD_DIAG),
        BIG_SPOTS(XSSFCellStyle.BIG_SPOTS),
        BRICKS(XSSFCellStyle.BRICKS),
        THIN_HORZ_BANDS(XSSFCellStyle.THIN_HORZ_BANDS),
        THIN_VERT_BANDS(XSSFCellStyle.THIN_VERT_BANDS),
        THIN_BACKWARD_DIAG(XSSFCellStyle.THIN_BACKWARD_DIAG),
        THIN_FORWARD_DIAG(XSSFCellStyle.THIN_FORWARD_DIAG),
        SQUARES(XSSFCellStyle.SQUARES),
        DIAMONDS(XSSFCellStyle.DIAMONDS),
        LESS_DOTS(XSSFCellStyle.LESS_DOTS),
        LEAST_DOTS(XSSFCellStyle.LEAST_DOTS);

        private short value;

        private FillPattern(final short value) {
            this.value = value;
        }
    }

    public enum Underline {
        NONE(HSSFFont.U_NONE),
        SINGLE(HSSFFont.U_SINGLE),
        SINGLE_ACCOUNTING(HSSFFont.U_SINGLE_ACCOUNTING),
        DOUBLE(HSSFFont.U_DOUBLE),
        DOUBLE_ACCOUNTING(HSSFFont.U_DOUBLE_ACCOUNTING);

        private byte value;

        private Underline(final byte value) {
            this.value = value;
        }
    }

    public static class Coordinate {

        private int rowIndex;
        private int columnIndex;

        public Coordinate(final int rowIndex, final int columnIndex) {
            this.rowIndex = rowIndex;
            this.columnIndex = columnIndex;
        }
    }

    public XSSFCell getCell(final Coordinate coordinate, final XSSFSheet sheet) {
        XSSFCell cell = null;
        final XSSFRow row = sheet.getRow(coordinate.rowIndex);
        if (row != null) {
            cell = row.getCell(coordinate.columnIndex);
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("row is null");
            }
        }
        return cell;
    }


    public XSSFCell[][] getCells(final Coordinate start, final Coordinate end, final XSSFSheet sheet) {
        final int rowCount = end.rowIndex - start.rowIndex + 1;
        final int columnCount = end.columnIndex - start.columnIndex + 1;
        final XSSFCell[][] cells = new XSSFCell[rowCount][columnCount];
        for (int rowIndex = start.rowIndex; rowIndex <= end.rowIndex; rowIndex++) {
            final XSSFRow row = sheet.getRow(rowIndex);
            if (row != null) {
                for (int columnIndex = start.columnIndex; columnIndex <= end.columnIndex; columnIndex++) {
                    cells[rowIndex - start.rowIndex][columnIndex - start.columnIndex] = row.getCell(columnIndex);
                }
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("row is null");
                }
            }
        }
        return cells;
    }

//    public XSSFCell[][] getCellsByName(final String name, final HSSFSheet sheet) {
//        final String[] splittedName = name.split(":");
//        final String startName = splittedName[0];
//        final int startColumn = startName.charAt(0) - 'A';
//        final int startRow = Integer.valueOf(startName.substring(1)) - 1;
//        final int endColumn;
//        final int endRow;
//        if (splittedName.length == 2) {
//            final String endName = splittedName[1];
//            endColumn = endName.charAt(0) - 'A';
//            endRow = Integer.valueOf(endName.substring(1)) - 1;
//        } else {
//            endColumn = startColumn;
//            endRow = startRow;
//        }
//        final int rowCount = endRow - startRow + 1;
//        final int columnCount = endColumn - startColumn + 1;
//        final XSSFCell[][] cells = new XSSFCell[rowCount][columnCount];
//        for (int rowIndex = startRow; rowIndex <= endRow; rowIndex++) {
//            HSSFRow row = sheet.getRow(rowIndex);
//            if (row == null) {
//                row = sheet.createRow(rowIndex);
//            } else {
//                if (logger.isDebugEnabled()) {
//                    logger.debug("row founded");
//                }
//            }
//            for (int columnIndex = startColumn; columnIndex <= endColumn; columnIndex++) {
//                cells[rowIndex][columnIndex] = row.getCell(columnIndex);
//                if (cells[rowIndex][columnIndex] == null) {
//                    cells[rowIndex][columnIndex] = row.createCell(columnIndex);
//                } else {
//                    if (logger.isDebugEnabled()) {
//                        logger.debug("cell founded");
//                    }
//                }
//            }
//        }
//        return cells;
//    }

    public void saveWorkbook(final OutputStream outputStream) throws IOException {
        workbook.write(outputStream);
    }

    public void setHorizontalAlignment(final Alignment alignment, final XSSFCell... cells) {
        for (final XSSFCell cell : cells) {
            final XSSFCellStyle cellStyle = createNewCellStyleForCell(cell);
            cellStyle.setAlignment(alignment.value);
        }
    }

    public void setVerticalAlignment(final Alignment alignment, final XSSFCell... cells) {
        for (final XSSFCell cell : cells) {
            if (cell != null) {
                final XSSFCellStyle cellStyle = createNewCellStyleForCell(cell);
                cellStyle.setVerticalAlignment(alignment.value);
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("cell is null");
                }
            }
        }
    }

    public void setAlignments(final Alignment horizontal, final Alignment vertical, final XSSFCell... cells) {
        for (final XSSFCell cell : cells) {
            if (cell != null) {
                final XSSFCellStyle cellStyle = createNewCellStyleForCell(cell);
                cellStyle.setAlignment(horizontal.value);
                cellStyle.setVerticalAlignment(vertical.value);
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("cell is null");
                }
            }
        }
    }

    public void setBorderTop(final Border border, final XSSFCell... cells) {
        for (final XSSFCell cell : cells) {
            if (cell != null) {
                final XSSFCellStyle cellStyle = createNewCellStyleForCell(cell);
                cellStyle.setBorderTop(border.value);
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("cell is null");
                }
            }
        }
    }

    public void setBorderRight(final Border border, final XSSFCell... cells) {
        for (final XSSFCell cell : cells) {
            if (cell != null) {
                final XSSFCellStyle cellStyle = createNewCellStyleForCell(cell);
                cellStyle.setBorderRight(border.value);
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("cell is null");
                }
            }
        }
    }

    public void setBorderBottom(final Border border, final XSSFCell... cells) {
        for (final XSSFCell cell : cells) {
            if (cell != null) {
                final XSSFCellStyle cellStyle = createNewCellStyleForCell(cell);
                cellStyle.setBorderBottom(border.value);
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("cell is null");
                }
            }
        }
    }

    public void setBorderLeft(final Border border, final XSSFCell... cells) {
        for (final XSSFCell cell : cells) {
            if (cell != null) {
                final XSSFCellStyle cellStyle = createNewCellStyleForCell(cell);
                cellStyle.setBorderLeft(border.value);
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("cell is null");
                }
            }
        }
    }

    public void setBorders(final Border top, final Border right, final Border bottom, final Border left, final XSSFCell... cells) {
        for (final XSSFCell cell : cells) {
            if (cell != null) {
                final XSSFCellStyle cellStyle = createNewCellStyleForCell(cell);
                cellStyle.setBorderTop(top.value);
                cellStyle.setBorderRight(right.value);
                cellStyle.setBorderBottom(bottom.value);
                cellStyle.setBorderLeft(left.value);
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("cell is null");
                }
            }
        }
    }

    public void setTopBorderColor(final Color color, final XSSFCell... cells) {
        for (final XSSFCell cell : cells) {
            if (cell != null) {
                final XSSFCellStyle cellStyle = createNewCellStyleForCell(cell);
                cellStyle.setTopBorderColor(color.index);
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("cell is null");
                }
            }
        }
    }

    public void setRightBorderColor(final Color color, final XSSFCell... cells) {
        for (final XSSFCell cell : cells) {
            if (cell != null) {
                final XSSFCellStyle cellStyle = createNewCellStyleForCell(cell);
                cellStyle.setRightBorderColor(color.index);
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("cell is null");
                }
            }
        }
    }

    public void setBottomBorderColor(final Color color, final XSSFCell... cells) {
        for (final XSSFCell cell : cells) {
            if (cell != null) {
                final XSSFCellStyle cellStyle = createNewCellStyleForCell(cell);
                cellStyle.setBottomBorderColor(color.index);
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("cell is null");
                }
            }
        }
    }

    public void setLeftBorderColor(final Color color, final XSSFCell... cells) {
        for (final XSSFCell cell : cells) {
            if (cell != null) {
                final XSSFCellStyle cellStyle = createNewCellStyleForCell(cell);
                cellStyle.setLeftBorderColor(color.index);
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("cell is null");
                }
            }
        }
    }

    public void setBorderColors(final Color top, final Color right, final Color bottom, final Color left, final XSSFCell... cells) {
        for (final XSSFCell cell : cells) {
            if (cell != null) {
                final XSSFCellStyle cellStyle = createNewCellStyleForCell(cell);
                cellStyle.setTopBorderColor(top.index);
                cellStyle.setRightBorderColor(right.index);
                cellStyle.setBottomBorderColor(bottom.index);
                cellStyle.setLeftBorderColor(left.index);
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("cell is null");
                }
            }
        }
    }

    public void setFillBackgroundColor(final Color color, final XSSFCell... cells) {
        for (final XSSFCell cell : cells) {
            if (cell != null) {
                final XSSFCellStyle cellStyle = createNewCellStyleForCell(cell);
                cellStyle.setFillBackgroundColor(color.index);
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("cell is null");
                }
            }
        }
    }

    public void setFillForegroundColor(final Color color, final XSSFCell... cells) {
        for (final XSSFCell cell : cells) {
            if (cell != null) {
                final XSSFCellStyle cellStyle = createNewCellStyleForCell(cell);
                cellStyle.setFillForegroundColor(color.index);
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("cell is null");
                }
            }
        }
    }

    public void setFillColors(final Color background, final Color foreground, final XSSFCell... cells) {
        for (final XSSFCell cell : cells) {
            if (cell != null) {
                final XSSFCellStyle cellStyle = createNewCellStyleForCell(cell);
                cellStyle.setFillBackgroundColor(background.index);
                cellStyle.setFillForegroundColor(foreground.index);
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("cell is null");
                }
            }
        }
    }

    public void setFillPattern(final FillPattern fillPattern, final XSSFCell... cells) {
        for (final XSSFCell cell : cells) {
            if (cell != null) {
                final XSSFCellStyle cellStyle = createNewCellStyleForCell(cell);
                cellStyle.setFillPattern(fillPattern.value);
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("cell is null");
                }
            }
        }
    }

    public void setFontBold(final boolean bold, final XSSFCell... cells) {
        for (final XSSFCell cell : cells) {
            if (cell != null) {
//                final XSSFCellStyle XSSFCellStyle = cell.getCellStyle();
                final XSSFCellStyle cellStyle = cell.getCellStyle();
                final short fontIndex = cellStyle.getFontIndex();
                final XSSFFont oldFont = workbook.getFontAt(fontIndex);
                final short boldweight;
                if (bold) {
                    boldweight = HSSFFont.BOLDWEIGHT_BOLD;
                } else {
                    boldweight = HSSFFont.BOLDWEIGHT_NORMAL;
                }
                final short color = oldFont.getColor();
                final short fontHeight = oldFont.getFontHeight();
                final String name = oldFont.getFontName();
                final boolean italic = oldFont.getItalic();
                final boolean strikeout = oldFont.getStrikeout();
                final short typeOffset = oldFont.getTypeOffset();
                final byte underline = oldFont.getUnderline();
                final XSSFFont newFont = getFont(boldweight, color, fontHeight, name, italic, strikeout, typeOffset, underline);
                cellStyle.setFont(newFont);
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("cell is null");
                }
            }
        }
    }

    public void setFontColor(final Color color, final XSSFCell... cells) {
        for (final XSSFCell cell : cells) {
            if (cell != null) {
                final XSSFCellStyle cellStyle = cell.getCellStyle();
                final short fontIndex = cellStyle.getFontIndex();
                final XSSFFont oldFont = workbook.getFontAt(fontIndex);
                final short boldweight = oldFont.getBoldweight();
                final short fontHeight = oldFont.getFontHeight();
                final String name = oldFont.getFontName();
                final boolean italic = oldFont.getItalic();
                final boolean strikeout = oldFont.getStrikeout();
                final short typeOffset = oldFont.getTypeOffset();
                final byte underline = oldFont.getUnderline();
                final XSSFFont newFont = getFont(boldweight, color.index, fontHeight, name, italic, strikeout, typeOffset, underline);
                cellStyle.setFont(newFont);
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("cell is null");
                }
            }
        }
    }

    public void setFontItalic(final boolean italic, final XSSFCell... cells) {
        for (final XSSFCell cell : cells) {
            if (cell != null) {
                final XSSFCellStyle cellStyle = cell.getCellStyle();
                final short fontIndex = cellStyle.getFontIndex();
                final XSSFFont oldFont = workbook.getFontAt(fontIndex);
                final short boldweight = oldFont.getBoldweight();
                final short color = oldFont.getColor();
                final short fontHeight = oldFont.getFontHeight();
                final String name = oldFont.getFontName();
                final boolean strikeout = oldFont.getStrikeout();
                final short typeOffset = oldFont.getTypeOffset();
                final byte underline = oldFont.getUnderline();
                final XSSFFont newFont = getFont(boldweight, color, fontHeight, name, italic, strikeout, typeOffset, underline);
                cellStyle.setFont(newFont);
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("cell is null");
                }
            }
        }
    }

    public void setFontName(final String name, final XSSFCell... cells) {
        for (final XSSFCell cell : cells) {
            if (cell != null) {
                final XSSFCellStyle cellStyle = cell.getCellStyle();
                final short fontIndex = cellStyle.getFontIndex();
                final XSSFFont oldFont = workbook.getFontAt(fontIndex);
                final short boldweight = oldFont.getBoldweight();
                final short color = oldFont.getColor();
                final short fontHeight = oldFont.getFontHeight();
                final boolean italic = oldFont.getItalic();
                final boolean strikeout = oldFont.getStrikeout();
                final short typeOffset = oldFont.getTypeOffset();
                final byte underline = oldFont.getUnderline();
                final XSSFFont newFont = getFont(boldweight, color, fontHeight, name, italic, strikeout, typeOffset, underline);
                cellStyle.setFont(newFont);
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("cell is null");
                }
            }
        }
    }

    public void setFontSize(final int fontSize, final XSSFCell... cells) {
        for (final XSSFCell cell : cells) {
            if (cell != null) {
                final XSSFCellStyle cellStyle = cell.getCellStyle();
                final short fontIndex = cellStyle.getFontIndex();
                final XSSFFont oldFont = workbook.getFontAt(fontIndex);
                final short boldweight = oldFont.getBoldweight();
                final short color = oldFont.getColor();
                final short fontHeight = (short) (fontSize * 20);
                final boolean italic = oldFont.getItalic();
                final String name = oldFont.getFontName();
                final boolean strikeout = oldFont.getStrikeout();
                final short typeOffset = oldFont.getTypeOffset();
                final byte underline = oldFont.getUnderline();
                final XSSFFont newFont = getFont(boldweight, color, fontHeight, name, italic, strikeout, typeOffset, underline);
                cellStyle.setFont(newFont);
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("cell is null");
                }
            }
        }
    }

    public void setFontStrikeout(final boolean strikeout, final XSSFCell... cells) {
        for (final XSSFCell cell : cells) {
            if (cell != null) {
                final XSSFCellStyle cellStyle = cell.getCellStyle();
                final short fontIndex = cellStyle.getFontIndex();
                final XSSFFont oldFont = workbook.getFontAt(fontIndex);
                final short boldweight = oldFont.getBoldweight();
                final short color = oldFont.getColor();
                final short fontHeight = oldFont.getFontHeight();
                final String name = oldFont.getFontName();
                final boolean italic = oldFont.getItalic();
                final short typeOffset = oldFont.getTypeOffset();
                final byte underline = oldFont.getUnderline();
                final XSSFFont newFont = getFont(boldweight, color, fontHeight, name, italic, strikeout, typeOffset, underline);
                cellStyle.setFont(newFont);
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("cell is null");
                }
            }
        }
    }

    public void setFontUnderline(final Underline underline, final XSSFCell... cells) {
        for (final XSSFCell cell : cells) {
            if (cell != null) {
                final XSSFCellStyle cellStyle = cell.getCellStyle();
                final short fontIndex = cellStyle.getFontIndex();
                final XSSFFont oldFont = workbook.getFontAt(fontIndex);
                final short boldweight = oldFont.getBoldweight();
                final short color = oldFont.getColor();
                final short fontHeight = oldFont.getFontHeight();
                final String name = oldFont.getFontName();
                final boolean italic = oldFont.getItalic();
                final boolean strikeout = oldFont.getStrikeout();
                final short typeOffset = oldFont.getTypeOffset();
                final XSSFFont newFont = getFont(boldweight, color, fontHeight, name, italic, strikeout, typeOffset, underline.value);
                cellStyle.setFont(newFont);
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("cell is null");
                }
            }
        }
    }

    public void setFormat(final String format, final XSSFCell... cells) {
        final CreationHelper creationHelper = workbook.getCreationHelper();
        final DataFormat dataFormat = creationHelper.createDataFormat();
        final short formatIndex = dataFormat.getFormat(format);
        for (final XSSFCell cell : cells) {
            if (cell != null) {
                final XSSFCellStyle cellStyle = createNewCellStyleForCell(cell);
                cellStyle.setDataFormat(formatIndex);
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("cell is null");
                }
            }
        }
    }

    public void setLocked(final boolean locked, final XSSFCell... cells) {
        for (final XSSFCell cell : cells) {
            if (cell != null) {
                final XSSFCellStyle cellStyle = createNewCellStyleForCell(cell);
                cellStyle.setLocked(locked);
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("cell is null");
                }
            }
        }
    }

    private XSSFCellStyle createNewCellStyleForCell(final XSSFCell cell) {
        final XSSFCellStyle newCellStyle = workbook.createCellStyle();
        final XSSFCellStyle oldCellStyle = cell.getCellStyle();
        newCellStyle.cloneStyleFrom(oldCellStyle);
        cell.setCellStyle(newCellStyle);
        return newCellStyle;
    }

    private XSSFFont getFont(final short boldweight, final short color, final short fontHeight, final String name,
                             final boolean italic, final boolean strikeout, final short typeOffset, final byte underline) {
        XSSFFont font = workbook.findFont(boldweight, color, fontHeight, name, italic, strikeout, typeOffset, underline);
        if (font == null) {
            font = workbook.createFont();
            font.setBoldweight(boldweight);
            font.setColor(color);
            font.setFontHeight(fontHeight);
            font.setFontName(name);
            font.setItalic(italic);
            font.setStrikeout(strikeout);
            font.setTypeOffset(typeOffset);
            font.setUnderline(underline);
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("font already existing");
            }
        }
        return font;
    }


}