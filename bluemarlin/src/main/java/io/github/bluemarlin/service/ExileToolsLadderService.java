package io.github.bluemarlin.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.gson.JsonParser;

import io.github.bluemarlin.Main;
import io.github.bluemarlin.util.UrlReaderUtil;
import io.jexiletools.es.model.json.ExileToolsHit;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

//public class ExileToolsLadderService extends Service<Void> {
//	
//	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
//
//	private StringProperty countdown = new SimpleStringProperty(this, "countdown", "Indexer Last Update: ");
//    public final void setCountdown(String value) { countdown.set(value); }
//    public final String getCountdown() { return countdown.get(); }
//    public final StringProperty countdownProperty() { return countdown; }
//    
//	@Override
//    protected Task<Void> createTask() {
//        return new Task<Void>() {  
//        	
//            @Override protected Void call() throws Exception {
//				String lastUpdate = retrieveIndexerLastUpdate();
//				if (!Main.DEVELOPMENT_MODE) {
//					// annoying, disable if on development
//					logger.debug("Last Indexer Update Value: " + lastUpdate);
//				}
//				updateMessage(lastUpdate);
//
//				for (int i = 60; i >= 0; i--) {
//					Thread.sleep(1000);
//					int _i = i;
//					Platform.runLater(() -> setCountdown("(" + _i + ")" + " Indexer Last Update: "));
//				}
//            	return null;
//            }
//        };
//	}
//	
//	private String retrieveIndexerLastUpdate() {
//		Optional<String> _league = ladderApiLeague();
//		Map<String, List<LadderHit>> accountLadderMap = new HashMap<>();
//		
//		if (_league.isPresent()) {
//			String url = "http://api.exiletools.com/ladder?league=" + _league.get() + "&accountName=";
//			List<String> accountNames = exileToolHits
//					.parallelStream()
//					.map(h -> h.getShop().getSellerAccount())
//					.distinct()
//					.collect(Collectors.toList());
//			
//			List<String> jsons = Lists.partition(accountNames, 200)
//				.stream()
//				.map(l -> joinByColons(l) )
//				.map(s -> callLadderApi(url + s))
//				.filter(r -> r.isPresent())
//				.map(r -> r.get())
//				.collect(Collectors.toList());
//			
//			jsons.stream()
//				.map(j -> new JsonParser().parse(j).getAsJsonObject())
//				.flatMap(jo -> jo.entrySet().stream())
//				.forEach(e -> {
//					// key is accountName.charName
//					String[] jsonKey = e.getKey().split("\\.");
//					String account = jsonKey[0];
//					LadderHit hit = new LadderHit(jsonKey[0], jsonKey[1], e.getValue());
//					if (!accountLadderMap.containsKey(account)) {
//						accountLadderMap.put(account, new ArrayList<>());
//					}
//					accountLadderMap.get(account).add(hit);
//				});
//		}
//		
//		mapLadderHitsToSearchHits(result.getExileToolHits(), accountLadderMap);
//		if (getOnlineOnly()) {
//			List<ExileToolsHit> collect = result.getExileToolHits().parallelStream()
//				.filter(e -> e.isOnline())
//				.collect(Collectors.toList());
//			result.setExileToolHits(collect);
//		}
//		return result;
//	}
//	
//	private Optional<String> callLadderApi(String url) {
//		String json = null;
//		try {
//			json = UrlReaderUtil.getString(url);
//			if (json.startsWith("ERROR")) {
//				json = "";
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//			updateMessage("Error occured while calling Ladder API:" + e.getMessage());
//		}
//		return Optional.ofNullable(json);
//	}
//	private String joinByColons(List<String> l) {
//		return StringUtils.join(l, ':');
//	}
//	
//    
//	private void mapLadderHitsToSearchHits(List<ExileToolsHit> exileToolHits,
//			Map<String, List<LadderHit>> accountLadderMap) {
//		exileToolHits.stream()
//			.forEach(e -> {
//				String account = e.getShop().getSellerAccount();
//				List<LadderHit> ladderHits = accountLadderMap.get(account);
//				if (ladderHits != null) {
//					String sellerIGN = StringUtils.trimToNull(e.getShop().getSellerIGN());
//					if (sellerIGN == null) {
//						ladderHits.stream().findFirst().ifPresent( lh -> {
//							e.getShop().setSellerIGN(lh.charName());
//						} );
//					}
//					e.setLadderHits(ladderHits);
//				}
//			});
//	}
//}
