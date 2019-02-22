

@ComponentBuilder("com.happiness.builders.HomeTable")
public class HomeTable extends AbstractComponentBuilder {

    public static final int CURRENT_MONTH = 0;
    public static final int NEXT_MONTH = 1;

    @Override
    public Object buildComponentData(ComponentConfig arg0, ComponentParams arg1) throws Exception {
        WTPart serchingPart = null;
        String startDate = increaseDateAboutGivenMonths(CURRENT_MONTH);
        String endDate = increaseDateAboutGivenMonths(NEXT_MONTH);
        Timestamp givenStartDate = setTimeStamp(startDate);
        Timestamp givenEndDate = setTimeStamp(endDate);
        SearchCondition newSearchCondition = getSoftTypesSearchCondition("com.happiness.HappyPart");
        QuerySpec parts = new QuerySpec(WTPart.class);
        parts.appendWhere(newSearchCondition, null);
        parts.appendAnd();
        parts.appendWhere(new SearchCondition(WTPart.class, WTPart.MODIFY_TIMESTAMP, SearchCondition.GREATER_THAN_OR_EQUAL, givenStartDate), new int[]{0});
        parts.appendAnd();
        parts.appendWhere(new SearchCondition(WTPart.class, WTPart.MODIFY_TIMESTAMP, SearchCondition.LESS_THAN_OR_EQUAL, givenEndDate), new int[]{0});
        QueryResult partsResult = PersistenceHelper.manager.find((StatementSpec) parts);
        return partsResult;
    }
	
	public static String dodajMetodeGita(String text){
		return "to jest test do gita";

    public static SearchCondition getSoftTypesSearchCondition(String searchingSoftTypeInternalName) throws WTException {
        boolean includeDescended = true;
        String softTypeInternalName = searchingSoftTypeInternalName;
        com.ptc.core.meta.common.TypeIdentifier typeIdentifier = wt.type.TypedUtilityServiceHelper.service.getTypeIdentifier(softTypeInternalName);
        SearchCondition searchCondition = wt.type.TypedUtilityServiceHelper.service.getSearchCondition(typeIdentifier, includeDescended);
        return searchCondition;
    }

    public static Timestamp setTimeStamp(String yyyy_MM) throws ParseException {
        final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM");
        Timestamp setDate = new java.sql.Timestamp(DATE_FORMAT.parse(yyyy_MM).getTime());
        return  setDate;
    }

    public static String increaseDateAboutGivenMonths(int increaseValueOfMounth) {
        String dataString = null;
        LocalDate localDate = LocalDate.now();
        int month = localDate.getMonth().plus(increaseValueOfMounth).getValue();
        int currentYear = LocalDate.now().plusMonths(increaseValueOfMounth).getYear();

        if((month > 0) && (month < 10)) {
            dataString = currentYear + "-0" + month;
        } else {
            dataString = currentYear + "-" + month;
        }
        return dataString;
    }

    @Override
    public ComponentConfig buildComponentConfig(ComponentParams arg0) throws WTException {
        ComponentConfigFactory factory = getComponentConfigFactory();
        TableConfig table = factory.newTableConfig();
        table.setLabel("Home Page Table");
        table.setSelectable(false);
        //table.setActionModel("mvc_tables_toolbar");

        ColumnConfig nameColumn = factory.newColumnConfig("name", true);
        nameColumn.setLabel("Name");

        ColumnConfig numberColumn = factory.newColumnConfig("number", true);
        numberColumn.setInfoPageLink(false);
        numberColumn.setLabel("Number");

        ColumnConfig infoPageLinkColumn = factory.newColumnConfig(ColumnIdentifiers.INFO_ACTION, true);
        infoPageLinkColumn.setInfoPageLink(true);
        infoPageLinkColumn.setLabel("Details");

        //ColumnConfig warningColumn = factory.newColumnConfig("com.happiness.Smile", true);
        ColumnConfig warningColumn = factory.newColumnConfig("Smile_id", true);
        warningColumn.setInfoPageLink(false);
        warningColumn.setDataUtilityId("Smile_id_table");
        warningColumn.setLabel("Warning");

        ColumnConfig modifyStampColumn = factory.newColumnConfig("thePersistInfo.modifyStamp", true);
        modifyStampColumn.setLabel("Last Modified");

        ColumnConfig modifierColumn = factory.newColumnConfig("iterationInfo.modifier", true);
        modifierColumn.setLabel("Modifier");

        table.addComponent(nameColumn);
        table.addComponent(numberColumn);
        table.addComponent(infoPageLinkColumn);
        table.addComponent(warningColumn);
        table.addComponent(modifyStampColumn);
        table.addComponent(modifierColumn);

        return table;
    }
}
