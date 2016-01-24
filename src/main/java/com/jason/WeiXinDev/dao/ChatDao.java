package com.jason.WeiXinDev.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatDao {

	private static Logger logger = LoggerFactory.getLogger(ChatDao.class);
	private static Connection conn = null;

	public static void connect() {
		Properties info = new Properties();

		InputStream in = ChatDao.class.getClassLoader().getResourceAsStream("ace_jdbc.properties");

		try {
			info.load(in);
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String driverClassName = info.getProperty("driverClassName");
		String url = info.getProperty("url");
		String userName = info.getProperty("userName");
		String password = info.getProperty("password");
		try {
			Class.forName(driverClassName);
			conn = DriverManager.getConnection(url, userName, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void getAllKnowledgeItems(IndexWriter iw) {
		connect();
		String queryForAll = "select * from knowledge";
		try {
			PreparedStatement ptmt = conn.prepareStatement(queryForAll);
			ResultSet rs = ptmt.executeQuery();
			while (rs.next()) {

				int id = rs.getInt("id");
				String question = rs.getString("question");
				String answer = rs.getString("answer");
				int category = rs.getInt("category");
				Document doc = new Document();
				IntField id_field = new IntField("id", id, Field.Store.YES);
				TextField question_field = new TextField("question", question, Field.Store.YES);
				TextField answer_field = new TextField("answer", answer, Field.Store.YES);
				IntField category_field = new IntField("category", category, Field.Store.YES);
				doc.add(id_field);
				doc.add(question_field);
				doc.add(answer_field);
				doc.add(category_field);
				iw.addDocument(doc);
			}
			rs.close();
			ptmt.close();
			conn = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getOneItemById(int id) {
		String item = "";
		connect();
		String queryOneItemById = "select answer from knowledge_sub where pid = " + id;
		try {
			PreparedStatement ptmt = conn.prepareStatement(queryOneItemById);
			ResultSet rs = ptmt.executeQuery();
			ArrayList<String> al = new ArrayList<String>();
			while (rs.next()) {
				al.add(rs.getString("answer"));
			}
			Random rand = new Random();
			item = al.get(rand.nextInt(al.size()));
			rs.close();
			ptmt.close();
			conn = null;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return item;

	}

	public static String getJoke() {
		String joke = "";
		connect();
		String queryJoke = "select joke_content from joke";
		try {
			PreparedStatement ptmt = conn.prepareStatement(queryJoke);
			ResultSet rs = ptmt.executeQuery();
			ArrayList<String> al = new ArrayList<String>();
			while (rs.next()) {
				al.add(rs.getString("joke_content"));
			}
			Random rand = new Random();
			joke = al.get(rand.nextInt(al.size()));
			rs.close();
			ptmt.close();
			conn = null;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return joke;
	}

}
