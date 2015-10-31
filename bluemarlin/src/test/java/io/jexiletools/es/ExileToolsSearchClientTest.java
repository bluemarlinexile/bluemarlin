/*
 * Copyright (C) 2015 thirdy
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package io.jexiletools.es;

import java.util.LinkedList;
import java.util.List;

import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jexiletools.es.model.json.ExileToolsHit;
import io.searchbox.core.SearchResult;
import io.searchbox.core.SearchResult.Hit;

/**
 * @author thirdy
 *
 */
public class ExileToolsSearchClientTest {
	
	private static final String BLACK_MARKET_API_KEY = "4b1ccf2fce44441365118e9cd7023c38";

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	static ExileToolsSearchClient client;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		client = new ExileToolsSearchClient("http://api.exiletools.com/index", BLACK_MARKET_API_KEY);
	}
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		client.shutdown();
	}
	
	// TODO: we've excluded most of ES's transitve deps, however this breaks these tests when running with mvn test
	
	@Test
	@Ignore
	public void testQuery() throws Exception {
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
	    FilterBuilder filter = FilterBuilders.boolFilter()
	            .must(FilterBuilders.termFilter("attributes.league", "Flashback Event HC (IC002)"))
	            .must(FilterBuilders.termFilter("attributes.equipType", "Jewel"))
	            .must(FilterBuilders.rangeFilter("modsTotal.#% increased maximum Life").gt(4))
	            .must(FilterBuilders.termFilter("shop.verified", "yes"))
	           // .must(FilterBuilders.termFilter("attributes.rarity", "Magic"))
	            ;

	    searchSourceBuilder
	            .query(QueryBuilders.filteredQuery(QueryBuilders.boolQuery().minimumNumberShouldMatch(2)
	                    .should(QueryBuilders.rangeQuery("modsTotal.#% increased Area Damage"))
	                    .should(QueryBuilders.rangeQuery("modsTotal.#% increased Projectile Damage"))
	                    .should(QueryBuilders.rangeQuery("modsTotal.#% increased Chaos Damage")), filter))
	            .sort("_score");
//	    SearchResult result = client.execute(searchSourceBuilder.toString()).getSearchResult();
//		List<Hit<ExileToolsHit, Void>> hits = result.getHits(ExileToolsHit.class);
//		hits.stream().map(hit -> hit.source).forEach(System.out::println);
	}
	
	/**
	 * As per ES documentation/tome, the best way to do our search is via Filters
	 */
	@Test
	@Ignore
	public void testExecuteMjolnerUsingFilters() throws Exception {
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		List<FilterBuilder> filters = new LinkedList<>();
		
		filters.add(FilterBuilders.termFilter("attributes.league", "Flashback Event (IC001)"));
//		filters.add(FilterBuilders.termFilter("info.name", "Mjolner"));
		filters.add(FilterBuilders.termFilter("info.name", "Hegemony's Era"));
		filters.add(FilterBuilders.rangeFilter("properties.Weapon.Physical DPS").from(400));
		
		FilterBuilder filter = FilterBuilders.andFilter(filters.toArray(new FilterBuilder[filters.size()]));
		searchSourceBuilder.query(QueryBuilders.filteredQuery(null, filter));
		searchSourceBuilder.size(100);
//		SearchResult result = client.execute(searchSourceBuilder.toString()).getSearchResult();
//		List<Hit<ExileToolsHit, Void>> hits = result.getHits(ExileToolsHit.class);
//		for (Hit<ExileToolsHit, Void> hit : hits) {
////			logger.info(hit.source.toString());
////			hit.source.getQuality().ifPresent( q -> logger.info(q.toString()) );
//			hit.source.getPhysicalDPS().ifPresent( q -> logger.info(q.toString()) );
////			logger.info(hit.source.toString());
////			logger.info(hit.source.getRequirements().getLevel().toString());
////			logger.info(hit.source.getExplicitMods().toString());
//		}
	}
	

}
