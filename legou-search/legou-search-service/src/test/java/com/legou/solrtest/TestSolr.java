package com.legou.solrtest;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class TestSolr {

	@Test
	public void testAdd() throws SolrServerException, IOException {
		
		 SolrServer server = new HttpSolrServer("http://192.168.25.128:8080/solr/collection1");
		 SolrInputDocument document = new SolrInputDocument();
		 document.addField("id", "003");
		 document.addField("item_title", "移动联通电信4G手机");
		 document.addField("item_sell_point", "苹果(Apple) iPhone 6 (A1586) 64G 银色 移动联通电信4G手机");
		 document.addField("item_price", 1000);
		 server.add(document);
		 server.commit();
		
	}
	
	@Test
	public void testDelte() throws SolrServerException, IOException {
		SolrServer server = new HttpSolrServer("http://192.168.25.128:8080/solr/collection1");
		server.deleteById("001");
		server.commit();
	}
	
	
	@Test
	public void TestQuery() throws SolrServerException {
		
		 SolrServer server = new HttpSolrServer("http://192.168.25.128:8080/solr/collection1");
		 
		 SolrQuery solrQuery = new SolrQuery();
		 
		 solrQuery.set("q", "*:*");
		 
		 QueryResponse query = server.query(solrQuery);
		 
		 SolrDocumentList results = query.getResults();
		 
		 for (SolrDocument document : results) {
			
			 System.out.println( document.get("id"));
			 System.out.println( document.get("item_title"));
			 System.out.println( document.get("item_sell_point"));
			 System.out.println( document.get("item_price"));
			 
		}
		 
		 
		 
		 
		 
		 
		 
		 
		
		
	}
	
	
}
