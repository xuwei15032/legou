package com.legou.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legou.common.pojo.SearchResult;
import com.legou.search.dao.SolrSearchDao;
import com.legou.search.service.SolrSearchService;

@Service
public class SolrSearchServiceImpl implements SolrSearchService {

	@Autowired
	SolrSearchDao solrSearchDao;

	@Override
	public SearchResult search(String keyword) throws Exception {

		SolrQuery query = new SolrQuery();
		query.setQuery(keyword);
		query.set("df", "item_title");
		query.setRows(100);

		SearchResult searchResult = solrSearchDao.search(query);

		return searchResult;
	}

}
