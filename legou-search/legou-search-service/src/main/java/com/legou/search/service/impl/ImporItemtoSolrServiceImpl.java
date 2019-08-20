package com.legou.search.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legou.common.pojo.SearchItem;
import com.legou.common.utils.LegouResult;
import com.legou.search.mapper.ItemMapper;
import com.legou.search.service.ImporItemtoSolrService;

@Service
public class ImporItemtoSolrServiceImpl implements ImporItemtoSolrService {

	@Autowired
	ItemMapper itemMapper;

	@Autowired
	SolrServer solrServer;

	@Override
	public LegouResult importAllItemToSolr() {

		try {

			List<SearchItem> itemList = itemMapper.getItemList();
			for (SearchItem searchItem : itemList) {
				SolrInputDocument document = new SolrInputDocument();
				document.addField("id", searchItem.getId());
				document.addField("item_title", searchItem.getTitle());
				document.addField("item_sell_point", searchItem.getSell_point());
				document.addField("item_price", searchItem.getPrice());
				document.addField("item_image", searchItem.getImage());
				document.addField("item_category_name", searchItem.getCategory_name());
				solrServer.add(document);
			}
			solrServer.commit();
			
			return LegouResult.ok();

		} catch (Exception e) {
			// log
		 e.printStackTrace();
			return LegouResult.build(500, "导入数据异常");
		}

	
	}

}
