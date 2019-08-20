package com.legou.search.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.legou.common.pojo.SearchItem;
import com.legou.common.pojo.SearchResult;

@Repository
public class SolrSearchDao {

	@Autowired
	SolrServer solrServer;

	public SearchResult search(SolrQuery query) {

		SearchResult searchResult = new SearchResult();

		try {
			QueryResponse queryResponse = solrServer.query(query);
			SolrDocumentList results = queryResponse.getResults();

			List<SearchItem> itemList = new ArrayList<SearchItem>();
			for (SolrDocument solrDocument : results) {
				SearchItem item = new SearchItem();

				item.setId((String) solrDocument.get("id"));
				item.setCategory_name((String) solrDocument.get("item_category_name"));
				item.setImage((String) solrDocument.get("item_image"));
				item.setSell_point((String) solrDocument.get("item_sell_point"));
				item.setPrice((long) solrDocument.get("item_price"));
				item.setTitle((String) solrDocument.get("item_title"));
				itemList.add(item);

			}

			searchResult.setItemList(itemList);

		} catch (SolrServerException e) {
			e.printStackTrace();
		}

		return searchResult;
	}

}
