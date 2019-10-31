package com.huaixuan.network.biz.service.user.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.user.SearchKeywordDao;
import com.huaixuan.network.biz.service.user.SearchKeywordManager;
import com.ibatis.common.logging.Log;
import com.ibatis.common.logging.LogFactory;

@Service("searchKeywordManager")
public class SearchKeywordManagerImpl implements SearchKeywordManager{

    @Autowired
    private SearchKeywordDao searchKeywordDao;

//    @Autowired
//    private Executor executor;

    protected Log  log = LogFactory.getLog(this.getClass());
    @Override
    public void addSearchKeyword(String keyword) {
        if (keyword == null) {
            throw new NullPointerException("SearchKeyword can't be null.");
        }
//        this.executor.execute(new AddSearchKeywordTask(keyword));
    }

    private class AddSearchKeywordTask implements Runnable {

        private String keyword;

        public AddSearchKeywordTask(String keyword) {
            super();
            this.keyword = keyword;
        }

        public void run() {
            String canonical = canonicalKeyword(keyword);
            try {
                if (!searchKeywordDao.updateKeyword(canonical)) {
                    searchKeywordDao.createSearchKeyword(canonical);
                }
            } catch (Exception e) {
                log.error("error then add search keyword", e);
            }
        }

        private String canonicalKeyword(String original) {
            List<String> words = cutAndSortWords(original);
            StringBuilder sb = new StringBuilder();
            for (String s : words) {
                sb.append(s).append(' ');
            }
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        }

        /**
         * 把搜索关键词切分并且按照java的次序(字符串自然序)排序
         * 
         * @param keyword
         * @return
         */
        private final List<String> cutAndSortWords(String keyword) {
            List<String> list = new ArrayList<String>();
            char[] chs = keyword.toCharArray();
            StringBuilder sb = new StringBuilder();
            for (char c : chs) {
                if (Character.isWhitespace(c)) {
                    if (sb.length() != 0) {
                        list.add(sb.toString());
                        sb.setLength(0);
                    }
                } else {
                    sb.append(c);
                }
            }
            if (sb.length() != 0) {
                list.add(sb.toString());
            }
            Collections.sort(list);
            return list;
        }
    }
}
