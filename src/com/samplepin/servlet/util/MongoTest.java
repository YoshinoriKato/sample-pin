package com.samplepin.servlet.util;

import java.net.UnknownHostException;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.query.Query;
import com.mongodb.MongoException;
import com.samplepin.ACMongo;
import com.samplepin.IdGenerator;

public class MongoTest {

	static class Hoge {

		Hoge() {

		}

		@Id
		ObjectId id;

		Long key;

		String name;

		public Long getKey() {
			return key;
		}

		public void setKey(Long key) {
			this.key = key;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Hoge(Long key, String name) {
			super();
			this.key = key;
			this.name = name;
		}

	}

	public static void main(String[] args) {
		int offset = 0;
		int inc = 5;
		try (ACMongo mongo = new ACMongo()) {
			{
				offset += inc;
				Query<Hoge> q = mongo.createQuery(Hoge.class);
				q.offset(offset).order("-key").limit(5);
				for (Hoge hoge : q.asList()) {
					System.out.println(hoge.getKey() + ":" + hoge.getName());
				}
			}
			System.out.println("----------------------------");
			{
				offset += inc;
				Query<Hoge> q = mongo.createQuery(Hoge.class);
				q.offset(offset).order("-key").limit(5);
				for (Hoge hoge : q.asList()) {
					System.out.println(hoge.getKey() + ":" + hoge.getName());
				}
			}
			System.out.println("----------------------------");
			{
				offset += inc;
				Query<Hoge> q = mongo.createQuery(Hoge.class);
				q.offset(offset).order("-key").limit(5);
				for (Hoge hoge : q.asList()) {
					System.out.println(hoge.getKey() + ":" + hoge.getName());
				}
			}
			System.out.println("----------------------------");
			{
				offset += inc;
				Query<Hoge> q = mongo.createQuery(Hoge.class);
				q.offset(offset).order("-key").limit(5);
				for (Hoge hoge : q.asList()) {
					System.out.println(hoge.getKey() + ":" + hoge.getName());
				}
			}
		} catch (UnknownHostException | MongoException e) {
			e.printStackTrace();
		}
		System.out.println("bye.");
	}

	public static void main1(String[] args) {

		long mills = System.currentTimeMillis();

		try (ACMongo mongo = new ACMongo()) {
			for (int i = 0; i < 100; i++) {
				mongo.save(new Hoge(mills++, IdGenerator.randomAlphanumeric(12)));
			}
		} catch (UnknownHostException | MongoException e) {
			e.printStackTrace();
		}
		System.out.println("bye.");
	}
}
