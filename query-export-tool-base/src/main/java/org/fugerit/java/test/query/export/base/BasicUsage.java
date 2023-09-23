package org.fugerit.java.test.query.export.base;

import org.fugerit.java.core.util.collection.ListMapStringKey;
import org.fugerit.java.query.export.catalog.QueryConfig;
import org.fugerit.java.query.export.catalog.QueryConfigCatalog;

public class BasicUsage {

	private BasicUsage() {}
	
	private static final QueryConfigCatalog CONFIG = QueryConfigCatalog.loadQueryConfigCatalogSafe( "cl://sample/query-catalog-config.xml" );
	
	public static ListMapStringKey<QueryConfig> getCatalog( String id ) {
		return CONFIG.getListMap(id);
	}
	
}
