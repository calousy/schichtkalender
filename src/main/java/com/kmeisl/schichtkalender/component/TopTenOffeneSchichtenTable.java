package com.kmeisl.schichtkalender.component;

import java.text.DecimalFormat;

import com.vaadin.data.Property;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public final class TopTenOffeneSchichtenTable extends Table {

    @Override
    protected String formatPropertyValue(final Object rowId,
            final Object colId, final Property<?> property) {
        String result = super.formatPropertyValue(rowId, colId, property);
        if (colId.equals("revenue")) {
            if (property != null && property.getValue() != null) {
                Double r = (Double) property.getValue();
                String ret = new DecimalFormat("#.##").format(r);
                result = "$" + ret;
            } else {
                result = "";
            }
        }
        return result;
    }

    public TopTenOffeneSchichtenTable() {
        setCaption("Top 10 Titles by Revenue");

        addStyleName(ValoTheme.TABLE_BORDERLESS);
        addStyleName(ValoTheme.TABLE_NO_STRIPES);
        addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
        addStyleName(ValoTheme.TABLE_SMALL);
        setSortEnabled(false);
        setColumnAlignment("revenue", Align.RIGHT);
        setRowHeaderMode(RowHeaderMode.INDEX);
        setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
        setSizeFull();

//        List<OffeneSchicht> movieRevenues = new ArrayList<OffeneSchicht>(
//                SchichtkalenderUI.getDataProvider().getTotalMovieRevenues());
//        Collections.sort(movieRevenues, new Comparator<OffeneSchicht>() {
//            @Override
//            public int compare(final OffeneSchicht o1, final OffeneSchicht o2) {
//                return o2.getRevenue().compareTo(o1.getRevenue());
//            }
//        });
//
//        setContainerDataSource(new BeanItemContainer<v>(
//        		OffeneSchicht.class, movieRevenues.subList(0, 10)));

        setVisibleColumns("title", "revenue");
        setColumnHeaders("Title", "Revenue");
        setColumnExpandRatio("title", 2);
        setColumnExpandRatio("revenue", 1);

        setSortContainerPropertyId("revenue");
        setSortAscending(false);
    }

}
