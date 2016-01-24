package com.jason.WeiXinDev.service;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.jason.WeiXinDev.dao.ChatDao;

public class ChatService {

	private static Logger logger = LoggerFactory.getLogger(ChatService.class);

	private static Document prevDoc;

	private static Analyzer analyzer = new IKAnalyzer(true);

	public static String process(String str) {
		return searchIndex(str, analyzer);
	}

	public static String searchIndex(String sentence, Analyzer analyzer) {

		String answerStr = "init";
		Directory dir = null;
		IndexReader reader = null;
		try {
			dir = FSDirectory.open(new File(System.getProperty("java.io.tmpdir")+"indexDir"));
			reader = DirectoryReader.open(dir);
			IndexSearcher searcher = new IndexSearcher(reader);
			QueryParser parser = new QueryParser(Version.LUCENE_46, "question", analyzer);
			Query query = parser.parse(sentence);
			logger.info("²éÑ¯Óï¾ä" + query.toString());
			TopDocs topDocs = searcher.search(query, 1);
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			if (scoreDocs.length == 0) {
				answerStr = "±§Ç¸£¡Ð¡ÏÀÌý²»¶®ÄúÔÚËµÊ²Ã´";
				return answerStr;
			}
			ScoreDoc scoreDoc = scoreDocs[0];
			Document doc = searcher.doc(scoreDoc.doc);

			// System.out.println(scoreDocs.length + "");
			answerStr = doc.get("answer");
			// System.out.println(answerStr);

			String doc_cat = doc.get("category");

			switch (doc_cat) {
			case "1":
				String doc_id = doc.get("id");
				switch (doc_id) {
				case "1":
					answerStr = ChatDao.getOneItemById(1);
					break;
				case "2":
					answerStr = ChatDao.getOneItemById(2);
					break;
				case "3":
					answerStr = ChatDao.getOneItemById(3);
					break;
				default:
					break;
				}
				break;

			case "2":
				answerStr = ChatDao.getJoke();
				break;

			case "3":
				String prevdoc_id = prevDoc.get("category");
				switch (prevdoc_id) {
				case "1":

					break;
				case "2":
					answerStr = ChatDao.getJoke();
					break;
				}
				break;
			default:
				break;
			}

			prevDoc = doc;
			// System.out.println(answerStr);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				if (dir != null) {
					dir.close();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return answerStr;
	}

	public static void createIndex(File dirFile) {
		try {
			Directory dir = FSDirectory.open(dirFile);
			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_46, analyzer);
			IndexWriter indexWriter = new IndexWriter(dir, indexWriterConfig);
			ChatDao.getAllKnowledgeItems(indexWriter);
			indexWriter.commit();
			indexWriter.close();
			dir.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
